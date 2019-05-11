/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import entity.HibernateUtil;
import entity.Usuarios;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ActionActualizarPermisos extends ActionSupport {
    InputStream responseStream;
    private int id;
    private String permiso;

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
    
    public ActionActualizarPermisos() {
    }
    
    public String execute() throws Exception {
         Session session;
        System.out.println("------" + id + permiso);
 session=HibernateUtil.getSessionFactory().openSession(); 
// Query consulta=session.createQuery("from Usuarios where id= :ids ");
 //consulta.setParameter("ids", id);
 //List l=consulta.list();
 //Usuarios user = (Usuarios) l.get(0);
 Usuarios user = (Usuarios) session.get(Usuarios.class, id); 
 user.setPermisos(permiso);
 Transaction tx = session.beginTransaction();
 session.update(user); 
         tx.commit();
       
         responseStream = new StringBufferInputStream("Cambio realizado");
         return SUCCESS;
    }
    
}
