/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import entity.Usuarios;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Marcus
 */
public class ActionEnviarPublica extends ActionSupport {
    private InputStream  resourceStream;
      private SecureRandom aleatorios;  
    private  final int keySize = 2048;
    private String correo;
    private String path;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
   

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

 
    
    public ActionEnviarPublica() {
    }
    
    public String execute() throws Exception {
       aleatorios =  SecureRandom.getInstance("SHA1PRNG");
         HttpSession sesion=ServletActionContext.getRequest().getSession();
       if(correo==null && sesion.getAttribute("user")!=null)
       {
         
           Usuarios us=(Usuarios)sesion.getAttribute("user");
           correo=us.getCorreo();
       }
       path=ServletActionContext.getServletContext().getRealPath("/");
       System.out.println("" + path);
       resourceStream  = new StringBufferInputStream("Bien");
       KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
       keyPairGenerator.initialize(keySize, aleatorios);
       KeyPair keyPair = keyPairGenerator.genKeyPair();
       PublicKey pubKey = keyPair.getPublic();
       byte []b =      escribirPublic(pubKey);
       PrivateKey privateKey = keyPair.getPrivate();
       escribirPrivate(privateKey);
       path=ServletActionContext.getServletContext().getRealPath("/archivos");
       resourceStream= new ByteArrayInputStream(b);
       return SUCCESS;
    }

    private void escribirPrivate(PrivateKey privateKey) throws IOException {
     byte []b= privateKey.getEncoded();
     byte []b2 = Base64.getEncoder().encode(b);
     File f= new File(path + "archivos/" + correo+".pl");
     if(!f.exists())
     {
         f.createNewFile();
     }
      FileOutputStream out = new FileOutputStream(f);
      out.write(b2);
      out.close();
        
   }

    private byte[] escribirPublic(PublicKey pubKey) {
      byte []b= pubKey.getEncoded();
      System.out.println("public" + b.length);
      byte []b2=null;
      return b2 = Base64.getEncoder().encode(b);
    }
    
}
