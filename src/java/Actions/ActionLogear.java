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
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.MessageDigest;
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
import static javax.management.Query.lt;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcus
 */
public class ActionLogear extends ActionSupport {
    private String correo;
    private String pass;
    private String keyMAC;
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

    public String getKeyMAC() {
        return keyMAC;
    }

    public void setKeyMAC(String keyMAC) {
        this.keyMAC = keyMAC;
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
        //keyMAC
        b = keyMAC.getBytes();
        byte [] kMAc = Base64.getDecoder().decode(b);
        //keyMAC=new String(kMAc);
        PrivateKey prk=obtenerPrivada();
       // PublicKey pubk=obtenerPublica();
       // String prueba="12345";
          Cipher cipher = Cipher.getInstance("RSA");  
         cipher.init(Cipher.DECRYPT_MODE, prk);//String p ="hola qyye jace";
          byte [] limpio=cipher.doFinal(c);
          
          byte [] bkmac=cipher.doFinal(kMAc);

          cipher.init(Cipher.ENCRYPT_MODE, obtenerPublica());
          bkmac=cipher.doFinal(bkmac);
          bkmac=Base64.getEncoder().encode(bkmac);
          keyMAC= new String(bkmac);
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
    private String iniciar() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
           Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession();

pass=sha256(pass); 
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
      Transaction t = hibernateSession.beginTransaction();
    us.setKeyMac(keyMAC);
    hibernateSession.update(us);
    t.commit();
    hibernateSession.close();
    
     HttpSession s =   ServletActionContext.getRequest().getSession();
     us.setPass("********");
     us.setKeyMac("*********");
     s.setAttribute("user", us);
     
    return respuesta;
 }
 else
 {
    return respuesta="error";
    
    }
  }

    private PrivateKey obtenerPrivada() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    
         File f= new File(path + "archivos/" + correo +".pl");
                System.out.println("---" + f.length());
                byte []all = new byte [(int)f.length()] ;
          byte []all2 = null;
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            in.read(all);
            all2=Base64.getDecoder().decode(all);


          KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever

          PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(all2));
                  System.out.println("nice");
                  in.close();
          if(f.delete()) System.out.println("lo trone");
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
     static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
 
}
