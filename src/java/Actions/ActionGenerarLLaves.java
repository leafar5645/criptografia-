/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import entity.Usuarios;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Marcus
 */
public class ActionGenerarLLaves extends ActionSupport {
    private String nombreprivada="pruebaCifradoEstilo.txt";
    private String nombrepublica="publica.txt";
    private SecureRandom aleatorios;  
    private  final int keySize = 2048;
    private String path;
    private InputStream resourceStream ;
    public ActionGenerarLLaves() {
    }
    
    public String execute() throws Exception {
        aleatorios =  SecureRandom.getInstance("SHA1PRNG");
        path=ServletActionContext.getServletContext().getRealPath("/");
        System.out.println("" + path);
        HttpSession sesion = ServletActionContext.getRequest().getSession();
        Usuarios user = (Usuarios) sesion.getAttribute("user");
       resourceStream  = new StringBufferInputStream("Bien");
        if(user !=null)
        {
           if(user.getTipo().equalsIgnoreCase("Administrador"))
                   {
                       
                        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                        keyPairGenerator.initialize(keySize, aleatorios);
                        
                        
                        KeyPair keyPair = keyPairGenerator.genKeyPair();
                       
                        
                         PublicKey pubKey = keyPair.getPublic();
                         alterarTodasLlaves(pubKey);
                         escribirPublic(pubKey);
                         PrivateKey privateKey = keyPair.getPrivate();
                         escribirPrivate(privateKey);
                         
        
       // System.out.println("input"+archivoFileName);
       
            return SUCCESS;
                   }
        }
        
        return ERROR;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }
  public void escribirPublic(PublicKey pubKey) throws FileNotFoundException, NoSuchAlgorithmException, IOException
  {
      
     byte []b= pubKey.getEncoded();
      System.out.println("public" + b.length);
     byte []b2 = Base64.getEncoder().encode(b);
     File f= new File(path + "archivos/" + nombrepublica);
     
      FileOutputStream out = new FileOutputStream(f);
      out.write(b2);
      out.close();
  }
  public void alterarTodasLlaves (PublicKey pk) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {



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
String path2="";
 File dir= new File(path2);
 String[] archivos= dir.list();
 for (int i=0; i<archivos.length;i++)
 {
     if(archivos[i].contains(".key"))
     {
         recifrar(archivos[i] , privateKey , pk);
     }
 }
  //   byte [] all3 = privateKey.getEncoded();
    //System.out.println("el regreso" + new String(Base64.getEncoder().encode(all3))); 
  }

    private void escribirPrivate(PrivateKey privateKey) throws FileNotFoundException, IOException {
        
        byte []b1= privateKey.getEncoded();
        System.out.println("la ida"+ new String(Base64.getEncoder().encode(b1)));
        int size=b1.length;
        System.out.println("" + size);
   //  byte []b1 = Base64.getEncoder().encode(b);
     byte []b0 = new byte[size]  ;
     byte []b2 = new byte [size];
     byte []b3= new byte [512];
     byte [] last = new byte [size*3];
        System.out.println("size " + size*(3) );
    
        aleatorios.nextBytes(b0);
        aleatorios.nextBytes(b2);
        aleatorios.nextBytes(b3);
        int z=0;
        System.out.println("---" + last.length );
for(int i=0; i<last.length ; i=i+3)
{
    last[i]=b0[z];
    last[i+1]=b1[z];
    last[i+2]=b2[z];
    z++;
}
byte [] last2= Base64.getEncoder().encode(last);
byte [] last3=Base64.getEncoder().encode(b3);
     File f= new File(path + "CSS/" + nombreprivada);
        System.out.println(path + "CSS/" + nombreprivada);
        
      FileOutputStream out = new FileOutputStream(f);
      out.write(last2);
     // out.write(last3);
      out.close();
        System.out.println("inicial" + f.length());
    }

    private void recifrar(String archivo, PrivateKey privateKey, PublicKey pk) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        File f =new File(path + "archivos/" + archivo );
        byte [] b = new byte [(int)f.length()];
        byte [] b2 = Base64.getDecoder().decode(b);
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        in.read(b);
        
        
         Cipher cipher = Cipher.getInstance("RSA");  
         cipher.init(Cipher.DECRYPT_MODE, privateKey);
          byte [] limpio=cipher.doFinal(b2);
         // f.delete
          //File f2= new File(path + "archivos/" + archivo );
        cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, pk);  

        byte [] limpio2=cipher.doFinal(limpio);
        byte salida[] =Base64.getEncoder().encode(limpio2);
         FileOutputStream out = new FileOutputStream(f);
      out.write(salida);
      out.close();
        
        
    }
}
