/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Marcus
 */
public class ActionPedirPublica extends ActionSupport {
    private String path;
    private InputStream resourceStream;

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }
    private final String nombrepublica="publica.txt";
    public ActionPedirPublica() {
    }
    
    public String execute() throws Exception {
        path=ServletActionContext.getServletContext().getRealPath("/archivos");
       File f = new File (path + "/" + nombrepublica);
       FileInputStream fi = new FileInputStream(f);
       byte [] b = new byte [(int)f.length()];
       fi.read(b);
     // byte [] c= Base64.getDecoder().decode(b);
        System.out.println("---" + new String (b) );//BORRAR AL TERMINAR
       resourceStream= new ByteArrayInputStream(b);
        return SUCCESS;
    }
    
}
