/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static com.opensymphony.xwork2.Action.SUCCESS;
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
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcus
 */
public class ActionLogear extends ActionSupport {
    private String correo;
    private String pass;
    private String operacion;
      InputStream responseStream;
         private String nombreprivada="pruebaCifradoEstilo.txt";
    private String nombrepublica="publica.txt";
    private SecureRandom aleatorios;  
    private  final int keySize = 2048;
    private String path;

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

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }
      
    
    public ActionLogear() {
    }
    
    public String execute() throws Exception {
       
        path=ServletActionContext.getServletContext().getRealPath("/");
        String respuesta="";
        System.out.println(operacion + correo );
        System.out.println("--- " + pass);
        byte [] b = pass.getBytes();
        byte [] c=Base64.getDecoder().decode(b);
        System.out.println("tam" + c.length);
        PrivateKey prk=obtenerPrivada();
       // PublicKey pubk=obtenerPublica();
       // String prueba="12345";
          Cipher cipher = Cipher.getInstance("RSA");  
         cipher.init(Cipher.DECRYPT_MODE, prk);//String p ="hola qyye jace";
          byte [] limpio=cipher.doFinal(c);
         // byte [] limpio2=Base64.getEncoder().encode(limpio);
          String prueba2= new String(limpio);
          System.out.println("que pasa" + prueba2);
          pass=prueba2;
         // System.out.println("nueva" + pass);
        if(operacion.equals("llave"))
            respuesta=llave();
        else
            respuesta=iniciar();
        
        responseStream = new StringBufferInputStream(respuesta);
        return SUCCESS;
    }
    private String llave()
    {
       return "nada" ;
    }
    private String iniciar()
    {
           Session hibernateSession;
        System.out.println("------" + correo + pass);
 hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
 Query consulta=hibernateSession.createQuery("from Usuarios where correo= :correo and pass= :pass ");
 consulta.setParameter("correo", correo);
 consulta.setParameter("pass", pass);
 List l=consulta.list();
 String respuesta="";
 if(l!=null && l.size()!=0)
 {
 Usuarios us =(Usuarios)l.get(0);
        System.out.println(us.getNombre());
        respuesta="empleado.jsp";
     HttpSession s =   ServletActionContext.getRequest().getSession();
     s.setAttribute("user", us);
    
      return respuesta;
 }
 else
 {
    return respuesta="error";
    
    }
  }

    private PrivateKey obtenerPrivada() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    
         File f= new File(path + "CSS/" + nombreprivada);
      System.out.println("---" + f.length());
      byte []all = new byte [(int)f.length()] ;
byte []all2 = null;
  DataInputStream in = new DataInputStream(new FileInputStream(f));
  in.read(all);
  all2=Base64.getDecoder().decode(all);
  int total =all2.length;
      System.out.println("total" + total);
  int p =total;
  byte[] oldprivateKeyBytes = new byte [p/3];
  int z=0;
      System.out.println("la p" + p);
  for(int i=0;i<p;i=i+3)
  {
     oldprivateKeyBytes[z]=all2[i+1];
    z= z+1;
  }
       System.out.println("la ida" + new String(Base64.getEncoder().encode(oldprivateKeyBytes)));
KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever

PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(oldprivateKeyBytes));
return privateKey;
    }

    private PublicKey obtenerPublica() throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException {
   
       File f= new File(path + "archivos/" + nombrepublica);
      System.out.println("---" + f.length());
      byte []all = new byte [(int)f.length()] ;
byte []all2 = null;
  DataInputStream in = new DataInputStream(new FileInputStream(f));
  in.read(all);
  all2=Base64.getDecoder().decode(all);

    X509EncodedKeySpec spec =
      new X509EncodedKeySpec(all2);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(spec);  
    }
    
}
