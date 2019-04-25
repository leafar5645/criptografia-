/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import entity.Usuarios;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.text.Normalizer;
import javax.servlet.http.HttpServletRequest;
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
       
       
      // copyInputStreamToFile lo usare despues ;
    
        System.out.println("arch:"+archivoContentType);

       
       String path= ServletActionContext.getServletContext().getRealPath("/archivos");
       String cadenaNormalize = Normalizer.normalize(archivoFileName, Normalizer.Form.NFD);   
       String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
      System.out.println("Resultado: " + cadenaSinAcentos);
             
       path=path + "/" + cadenaSinAcentos;
       String path2= path + ".key" ;
        System.out.println( path );
        try
        {
            File f2= new File(path2);
       File f = new File(path);
        System.out.println("pase");
        System.out.println(archivo.length());
        
      FileUtils.copyFile(archivo, f);
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
  
    
    
}
