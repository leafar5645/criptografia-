/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author betoj
 */
public class ActionPedirIV extends ActionSupport {
    private InputStream  resourceStream;
    private String nombre;
    private String path;
    public ActionPedirIV() {
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String execute() throws Exception {
        String llave=nombre+".iv";
         path=ServletActionContext.getServletContext().getRealPath("/archivos");
       File f = new File (path + "/" + llave);
       FileInputStream fi = new FileInputStream(f);
       byte [] b = new byte [(int)f.length()];
       fi.read(b);
     // byte [] c= Base64.getDecoder().decode(b);
       // System.out.println("---" + new String (b) );//BORRAR AL TERMINAR
       resourceStream= new ByteArrayInputStream(b);
        
        return SUCCESS;
    }
    
}
