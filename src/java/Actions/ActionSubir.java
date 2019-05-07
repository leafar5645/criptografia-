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
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringWriter;

import java.text.Normalizer;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

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
    File archivo;
    String archivoFileName;
    String archivoContentType;
 
 
    
    public String execute() throws Exception {
        System.out.println("" + archivo.length());
      /* PrivateKey privateKey = obtenerPrivada();
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
