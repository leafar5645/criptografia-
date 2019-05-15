/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import static Actions.ActionLogear.sha256;
import com.opensymphony.xwork2.ActionSupport;
import entity.HibernateUtil;
import entity.Usuarios;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcus
 */
public class ActionRegistrarCorreo extends ActionSupport {
    private String correo;
    private InputStream resourceStream;
    private String nombreprivada="pruebaCifradoEstilo.txt";
    private String nombrepublica="publica.txt";
    private SecureRandom aleatorios;  
    private  final int keySize = 2048;
    private String path;
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }
    
    public ActionRegistrarCorreo() {
    }
    
    public String execute() throws Exception {
 String respuesta;
 Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession();
 Query consulta=hibernateSession.createQuery("from Usuarios where correo= :correo");
 consulta.setParameter("correo", correo);
 List l=consulta.list();
 if(l.isEmpty()|| l==null)
 {
    Usuarios user = new Usuarios(null , null , correo , null , null , null, null);
    Transaction tx = hibernateSession.beginTransaction();
    hibernateSession.saveOrUpdate(user); 
    tx.commit();
    respuesta="Usuarios registrado con exito";
    resourceStream = new StringBufferInputStream(respuesta);
    return SUCCESS;          
         
 }
 else
 {
     respuesta="El usuario ya estaba registrado";
     resourceStream = new StringBufferInputStream(respuesta);
     return SUCCESS;
 }
        
    }
    
}
