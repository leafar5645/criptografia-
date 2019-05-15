/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static Actions.ActionLogear.sha256;
import com.opensymphony.xwork2.ActionSupport;
import entity.HibernateUtil;
import entity.Usuarios;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcus
 */
public class ActionRegistrame extends ActionSupport {
    private String correo;
    private String pass;
    private String nombre;
    private InputStream resourceStream;
    private String nombrepublica="publica.txt";
    private SecureRandom aleatorios;  
    private  final int keySize = 2048;
    private String path;
    private String passD;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }
    
    public ActionRegistrame() {
    }
    
    public String execute() throws Exception {
         path=ServletActionContext.getServletContext().getRealPath("/");
        String respuesta="";
        byte [] b = pass.getBytes();
        byte [] c=Base64.getDecoder().decode(b);
        PrivateKey prk=obtenerPrivada();
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, prk);
        byte [] limpio=cipher.doFinal(c);
        passD= new String(limpio);
        respuesta=iniciar();
        resourceStream = new StringBufferInputStream(respuesta);
        return SUCCESS;
    }
    private PrivateKey obtenerPrivada() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    
         File f= new File(path + "archivos/" + correo +".pl");
                //System.out.println("---" + f.length());
                byte []all = new byte [(int)f.length()] ;
          byte []all2 = null;
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            in.read(all);
            all2=Base64.getDecoder().decode(all);


          KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever

          PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(all2));
                  //System.out.println("nice");
                  in.close();
          if(f.delete()) System.out.println("");
          return privateKey;
    }
    public String iniciar() throws NoSuchAlgorithmException
    {
 String respuesta="";
 Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession();
 Query consulta=hibernateSession.createQuery("from Usuarios where correo= :correo  ");
 consulta.setParameter("correo", correo);
 List l=consulta.list();
 
 if(l!=null && l.size()!=0)
 {
  Usuarios user =(Usuarios)l.get(0);
  if(user.getNombre()==null && user.getPass()==null && user.getPermisos()==null && user.getTipo()==null)
  {
      passD=sha256(passD);
      user.setNombre(nombre);
      user.setPass(passD);
      user.setPermisos("none");
      user.setTipo("empleado");
      Transaction t = hibernateSession.beginTransaction();
      hibernateSession.update(user);
      t.commit();
      hibernateSession.close();
      respuesta="Registrado con exito";
      return respuesta;
      
  }
  else
  {
  respuesta="El usuario ya existe";    
  return respuesta;  
  }
    }
 else
 {
  respuesta="El correo no tiene permiso de registro";
  return respuesta;
 }
 }
   public String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
}
