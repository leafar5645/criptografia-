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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import java.text.Normalizer;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.HibernateUtil;
import java.io.BufferedInputStream;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author betoj
 */

public class ActionSubir extends ActionSupport {
    private InputStream resourceStream;
    private String llave;
    private String iv;
    private String contenidoA;
    private String nombre;
    private String nombreprivada="pruebaCifradoEstilo.txt";
    private String MAC;
    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
     
    
    public String getContenidoA() {
        return contenidoA;
    }

    public void setContenidoA(String contenidoA) {
        this.contenidoA = contenidoA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

   
    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

  public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getArchivoFileName() {
        return archivoFileName;
    }

    public void setArchivoFileName(String archivoFileName) {
        this.archivoFileName = archivoFileName;
    }

    public String getArchivoContentType() {
        return archivoContentType;
    }

    public void setArchivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
    
    File archivo;
    String archivoFileName;
    String archivoContentType;
 
 
    
    public String execute() throws Exception {
        System.out.println("" + archivo.length());

        HttpSession s =   ServletActionContext.getRequest().getSession();
        Usuarios user =(Usuarios)s.getAttribute("user");
        
         Session hibernateSession;
        hibernateSession= HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Usuarios where id= :id");
        consulta.setParameter("id", user.getId());
        List l=consulta.list();
        byte[] kMAc;
        if(l!=null && l.size()!=0)
        {
           Usuarios us =(Usuarios)l.get(0);
           kMAc=us.getKeyMac().getBytes();
           kMAc=Base64.getDecoder().decode(kMAc);
        }
        else
            return ERROR;
        
        PrivateKey privateKey = obtenerPrivada();
        Cipher cipher = Cipher.getInstance("RSA"); 
        cipher.init(Cipher.DECRYPT_MODE, privateKey);//String p ="hola qyye jace";
        byte [] bkmac=cipher.doFinal(kMAc);
        bkmac=Base64.getDecoder().decode(bkmac);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bkmac, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        
       /*
       byte[] llavefile = obtenerLlave(privateKey);
       byte[] fileAndIv = contenidoA.getBytes();
       byte[] fileAndIv2=Base64.getDecoder().decode(fileAndIv);
       byte[] IV = getIv(fileAndIv2);*/
      // copyInputStreamToFile lo usare despues ;
    
       // System.out.println("arch:"+archivoContentType);
       String path= ServletActionContext.getServletContext().getRealPath("/");
       String cadenaNormalize = Normalizer.normalize(nombre, Normalizer.Form.NFD);   
       String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
      System.out.println("Resultado: " + cadenaSinAcentos);
      //generando directorios
      path+="archivos";
       File dir =new File(path);
       dir.mkdirs();
       path=path + "/" + cadenaSinAcentos;
       String path2= path + ".key" ;
       String path3=path+".iv";
        System.out.println( path );
        try
        {
            File f2= new File(path2);
       File f = new File(path);
       File f3= new File(path3);
       //MAC
        DataInputStream in = new DataInputStream(new FileInputStream(archivo));
        byte [] informacion= new byte[(int) archivo.length()];
        in.readFully(informacion);
        byte[] tag =mac.doFinal(informacion);
            System.out.println(tag[0]);
        String Tag = new String(Base64.getEncoder().encode(tag));
            System.out.println("TAg:"+Tag);
            System.out.println("MAC:"+MAC);
       if(!Tag.equals(MAC))
       {
           System.out.println("MAC Incorrecta");
           return ERROR;
       }
       //---
        FileUtils.copyFile(archivo, f);
      FileUtils.writeStringToFile(f3,iv);
      FileUtils.writeStringToFile(f2, llave);
        }
        catch(Exception e)
        {
            System.out.println("" + e);
        }
      resourceStream = new StringBufferInputStream("Bien");
        
       // System.out.println("input"+archivoFileName);
       
            return SUCCESS;
    }
    private PrivateKey obtenerPrivada() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    
        String path= ServletActionContext.getServletContext().getRealPath("/");
         File f= new File(path + "CSS/"+nombreprivada);
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        byte[] all = new byte [(int)f.length()] ;
        in.read(all);
        byte [] all2=Base64.getDecoder().decode(all);
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
        return privateKey;
    }
    /*
  public PrivateKey obtenerPrivada() 
  {
   
      return null;
  }
  public byte[]  obtenerLlave (PrivateKey pk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {
  
      byte [] llave2 =llave.getBytes();
      byte [] result=null ;
      llave2=Base64.getDecoder().decode(llave2);
      // bkey=Base64.getEncoder().encode(bkey);
       Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, pk);
        try{
        result = cipher.doFinal(llave2);
        } catch(Exception e)
        {
            System.out.println("" + e.toString());
        }
        //System.out.println("llave" + s);
        return result;
    
  }

    private byte[] getIv(byte[] fileAndIv) {
       byte [] iv = new byte[8];
       System.arraycopy(fileAndIv, 0, iv, 0, 8);
       return iv;
    }
 */   
}
