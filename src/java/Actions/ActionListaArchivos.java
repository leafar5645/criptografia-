/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author betoj
 */
public class ActionListaArchivos extends ActionSupport {
    private InputStream resourceStream;
    private static InputStream Error= new StringBufferInputStream("_ERROR_");
    
        public ActionListaArchivos() {
    }
    
    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

    public static InputStream getError() {
        return Error;
    }

    public static void setError(InputStream Error) {
        ActionListaArchivos.Error = Error;
    }
    
    public String execute() throws Exception {
        try
        {
            HttpSession s =   ServletActionContext.getRequest().getSession();
             if(s.getAttribute("user")==null)
                 return ERROR;
            String path= ServletActionContext.getServletContext().getRealPath("/");
            path+="archivos";
            File dir= new File(path);
            dir.mkdirs();
            String[] archivos= dir.list();
            String salida= "";
            if(archivos.length>0)
                for (int i = 0; i < archivos.length; i++) 
                {
                    if(!archivos[i].split("\\.")[archivos[i].split("\\.").length - 1].equalsIgnoreCase("key"))
                    salida+=archivos[i]+"@";
                }
            else
                salida="_VACIO_";
            resourceStream = new StringBufferInputStream(salida);
            return SUCCESS;
        }
        catch(Exception e)
        {
            System.out.println("ERROR: "+e);
            return ERROR;
        }
    }
    
}
