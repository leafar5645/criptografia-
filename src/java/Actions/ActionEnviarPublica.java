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
import java.util.Base64;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Marcus
 */
public class ActionEnviarPublica extends ActionSupport {
    private InputStream  resourceStream;

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

   
    private String nombrepublica="publica.txt";
    private String path;
    
    public ActionEnviarPublica() {
    }
    
    public String execute() throws Exception {
        path=ServletActionContext.getServletContext().getRealPath("/archivos");
       File f = new File (path + "/" + nombrepublica);
       FileInputStream fi = new FileInputStream(f);
       byte [] b = new byte [(int)f.length()];
       fi.read(b);
      
       resourceStream= new ByteArrayInputStream(b);
        return SUCCESS;
    }
    
}
