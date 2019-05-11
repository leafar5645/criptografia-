/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;


public class ActionDescargar extends ActionSupport {
    private InputStream resourceStream;
    private String llave;
    private String nombreArchivo;
    private InputStream error= new StringBufferInputStream("_ERROR_");
    private long contentLength = 0;
     
    public ActionDescargar() {
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
    
    public InputStream getError() {
        return error;
    }

    public void setError(InputStream error) {
        this.error = error;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

   
    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public String execute() throws Exception 
    {
        HttpSession s =   ServletActionContext.getRequest().getSession();
             if(s.getAttribute("user")==null)
             {
                 System.out.println("Session Invalida");
                 return ERROR;
             }
         String path= ServletActionContext.getServletContext().getRealPath("/archivos");
        File archivo= new File(path+"/"+nombreArchivo);
        System.out.println(path+"/"+nombreArchivo);
        if(!archivo.exists())
        {
            System.out.println("Archivo Inexistente");
            return ERROR;
        }
        /*
        resourceStream = new FileInputStream(archivo);
        contentLength=archivo.length();
        System.out.println(contentLength);
        */
        HttpServletResponse response = ServletActionContext.getResponse();
        FileInputStream lector= new FileInputStream(archivo);
        BufferedInputStream bis =new BufferedInputStream(lector);
        ServletOutputStream out = response.getOutputStream();
        byte[] buffer = new byte[3000];
        long tam= (int)archivo.length();
        long l=0;
        while ( l < tam) 
        {
            int n= bis.read(buffer); 
            l+=n;
            out.write(buffer,0,n);
        }
        //System.out.println("l="+l);
        response.setContentLength((int)archivo.length());
        out.flush();
        bis.close();
        
        return NONE;
    }
    
}
