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
public class ActionLogear extends ActionSupport {
    private String correo;
    private String pass;
    private String operacion;
      InputStream responseStream; 

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }
      
    
    public ActionLogear() {
    }
    
    public String execute() throws Exception {
        String respuesta="";
        System.out.println(operacion + correo + pass);
        if(operacion.equals("llave"))
            respuesta=llave();
        else
            respuesta=iniciar();
        
        responseStream = new StringBufferInputStream(respuesta);
        return SUCCESS;
    }
    private String llave()
    {
       return "nada" ;
    }
    private String iniciar()
    {
           Session hibernateSession;
        System.out.println("------" + correo + pass);
 hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
 Query consulta=hibernateSession.createQuery("from Usuarios where correo= :correo and pass= :pass ");
 consulta.setParameter("correo", correo);
 consulta.setParameter("pass", pass);
 List l=consulta.list();
 String respuesta="";
 if(l!=null && l.size()!=0)
 {
 Usuarios us =(Usuarios)l.get(0);
        System.out.println(us.getNombre());
        respuesta="" + us.getTipo() + ".jsp";
     HttpSession s =   ServletActionContext.getRequest().getSession();
     s.setAttribute("user", us);
    
      return respuesta;
 }
 else
 {
    return respuesta="error";
    
    }
  }
    
}
