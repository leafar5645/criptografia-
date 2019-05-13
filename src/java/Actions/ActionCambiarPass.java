/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

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
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcus
 */
public class ActionCambiarPass extends ActionSupport {
    private String pass1;
    private String passn;
    private InputStream resourceStream;
    private String nombreprivada="pruebaCifradoEstilo.txt";
    private String nombrepublica="publica.txt";
    private SecureRandom aleatorios;  
    private  final int keySize = 2048;
    private String path;
    private String pass1d;
    private String passnd;
    
    
     String correo="";

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPassn() {
        return passn;
    }

    public void setPassn(String passn) {
        this.passn = passn;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }
    
    public ActionCambiarPass() {
    }
    
    public String execute() throws Exception {
        path=ServletActionContext.getServletContext().getRealPath("/");
        HttpSession sesion=ServletActionContext.getRequest().getSession();
        Usuarios us=(Usuarios)sesion.getAttribute("user");
        correo=us.getCorreo();
        String respuesta="";
        //System.out.println(operacion + correo );
        //System.out.println("--- " + pass);
        byte [] b1 = pass1.getBytes();
        byte [] c1=Base64.getDecoder().decode(b1);
         byte [] b2 = passn.getBytes();
        byte [] c2=Base64.getDecoder().decode(b2);
     
        //keyMAC
       
        //keyMAC=new String(kMAc);
        PrivateKey prk=obtenerPrivada();
       // PublicKey pubk=obtenerPublica();
       // String prueba="12345";
          Cipher cipher = Cipher.getInstance("RSA");  
         cipher.init(Cipher.DECRYPT_MODE, prk);//String p ="hola qyye jace";
          byte [] limpio=cipher.doFinal(c1);
          
         cipher.init(Cipher.DECRYPT_MODE, prk);//String p ="hola qyye jace";
          byte [] limpio2=cipher.doFinal(c2);
          
          

       
      
         // byte [] limpio2=Base64.getEncoder().encode(limpio);
          pass1d= new String(limpio);
          passnd= new String (limpio2);
        
     
            respuesta=iniciar();
        
        resourceStream = new StringBufferInputStream(respuesta);
       return SUCCESS;
    }
    private String iniciar() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
           Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession();

String pass=sha256(pass1d); 
String npass=sha256(passnd);
 Query consulta=hibernateSession.createQuery("from Usuarios where correo= :correo and pass= :pass ");
 consulta.setParameter("correo", correo);
 consulta.setParameter("pass", pass);
 List l=consulta.list();
 String respuesta="";
 if(l!=null && l.size()!=0)
 {
    Usuarios us =(Usuarios)l.get(0);
        System.out.println(us.getNombre());
        respuesta="Bien";
      Transaction t = hibernateSession.beginTransaction();
    us.setPass(npass);
    hibernateSession.update(us);
    t.commit();
    hibernateSession.close();
    
     //HttpSession s =   ServletActionContext.getRequest().getSession();
     us.setPass("********");
    // us.setKeyMac("*********");
     //s.setAttribute("user", us);
     
    return respuesta;
 }
 else
 {
    return respuesta="error";
    
    }
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

    private PublicKey obtenerPublica() throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException {
   
       File f= new File(path + "archivos/" + nombrepublica);
      //System.out.println("---" + f.length());
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

    

