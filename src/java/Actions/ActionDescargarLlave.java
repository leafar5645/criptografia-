 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Marcus
 */
public class ActionDescargarLlave extends ActionSupport {
   private String nombreprivada="pruebaCifradoEstilo.txt";
   private String publicKeyClient;
   private String filename;
   private InputStream resourceStream ;
   private String path;

    public String getPublicKeyClient() {
        return publicKeyClient;
    }

    public void setPublicKeyClient(String publicKeyClient) {
        this.publicKeyClient = publicKeyClient;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }
    
    public ActionDescargarLlave() {
    }
    
    public String execute() throws Exception {
      PublicKey pk=obtenerPk();  
      filename= filename+".key";
     
      path=ServletActionContext.getServletContext().getRealPath("/");
      File f2 = new File(path + "archivos/" + filename);
      File f= new File(path + "CSS/" + nombreprivada);
      byte []all = new byte [(int)f2.length()] ;
      byte []llavefile = null;
      byte []all2 = null;
      DataInputStream in = new DataInputStream(new FileInputStream(f2));
      in.read(all);
      llavefile=Base64.getDecoder().decode(all);
      //llavefile=all.clone();
        System.out.println("lenght" + llavefile.length);
      in = new DataInputStream(new FileInputStream(f));
      all = new byte [(int)f.length()] ;
      in.read(all);
      all2=Base64.getDecoder().decode(all);
      int total =all2.length;
      //System.out.println("total" + total);
      int p =total;
      byte[] oldprivateKeyBytes = new byte [p/3];
      int z=0;
      for(int i=0;i<p;i=i+3)
      {
        oldprivateKeyBytes[z]=all2[i+1];
        z= z+1;
      }
       //System.out.println("la ida" + new String(Base64.getEncoder().encode(oldprivateKeyBytes)));
      KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever

      PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(oldprivateKeyBytes));
      Cipher cipher = Cipher.getInstance("RSA");  
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte [] limpio=cipher.doFinal(llavefile);
      cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, pk);  

        byte [] limpio2=cipher.doFinal(limpio);
        byte salida[] =Base64.getEncoder().encode(limpio2);
        resourceStream= new ByteArrayInputStream(salida);
        return SUCCESS;
    }

    private PublicKey obtenerPk() throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("" + publicKeyClient);
        String [] spli=publicKeyClient.split("-----");
        spli[2]=spli[2].replace("\n", "");
        System.out.println("" + spli[2]);
       byte b[] =spli[2].getBytes();
       b=Base64.getDecoder().decode(b);
       X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(b);
       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
       PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
       return pubKey;
      
    }

 
    
}
