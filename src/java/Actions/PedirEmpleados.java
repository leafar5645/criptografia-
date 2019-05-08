/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import entity.HibernateUtil;
import entity.Usuarios;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcus
 */
public class PedirEmpleados extends ActionSupport {
     InputStream responseStream;

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }
    
    public PedirEmpleados() {
    }
    
    public String execute() throws Exception {
         Session hibernateSession;
      
 hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
 Query consulta=hibernateSession.createQuery("from Usuarios where 1=1 ");

 
 List l=consulta.list();
 String respuesta="";
 if(l!=null && l.size()!=0)
 {
for(int i=0; i<l.size(); i++)
{
    Usuarios us =(Usuarios)l.get(i);
    if(!us.getTipo().equalsIgnoreCase("Administrador"))
    respuesta=respuesta + us.getNombre() +":" + us.getPermisos() + ":" + us.getId() +"@";
}
      respuesta=respuesta.substring(0, respuesta.length()-1);
     System.out.println("" + respuesta);
    responseStream = new StringBufferInputStream(respuesta);
        
 }
 return SUCCESS;
  
    
    
}
}
