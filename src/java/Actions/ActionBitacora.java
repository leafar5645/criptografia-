/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import entity.HibernateUtil;
import entity.Operaciones;
import entity.Usuarios;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcus
 */
public class ActionBitacora extends ActionSupport {
    private String criterio;
    private String valor;

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    public ActionBitacora() {
    }
    
    public String execute() throws Exception {
        System.out.println("" + criterio + valor);
        String operacion="";
      
        if(criterio.equalsIgnoreCase("1")) criterioOperacion();
        if(criterio.equalsIgnoreCase("2")) { operacion="archivo"; criterioArchivo();}
        if(criterio.equalsIgnoreCase("3")) { 
            System.out.println("----------------" + criterio);
            operacion="correo";
        Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
 Query consulta=hibernateSession.createQuery("from Usuarios where correo= :correo");
 consulta.setParameter("correo", valor);
 List l=consulta.list();
           // System.out.println("<<<<<" + l.size());
 String respuesta="";
 if(l!=null && l.size()!=0)
 {
    Usuarios us =(Usuarios)l.get(0);
    int id = us.getId();
     System.out.println("---------------------" + id);
    consulta=hibernateSession.createQuery("from Operaciones where idusuario= :idu");
    consulta.setParameter("idu", id);
    List l2=consulta.list();
     Bitacora [] bt=null ;
    if(l2!=null && l2.size()!=0)
    {   
        System.out.println("entre " + l2.size());
       bt= new Bitacora[l2.size()]; 
       for(int i=0;i<bt.length;i++)
       {
           Operaciones op = (Operaciones) l2.get(i);
           bt[i]= new Bitacora(us.getCorreo() , op.getIdop() ,op.getOperacion(), op.getFecha() , op.getFilename() , id );
       }
    }
       HttpSession s =   ServletActionContext.getRequest().getSession();
       s.setAttribute("bitacora", bt);
    }
        }
    
        return SUCCESS;
    
}
    private void criterioArchivo()
    {
        Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession();
 Query consulta=hibernateSession.createQuery("from Operaciones where filename= :fl");
 consulta.setParameter("fl", valor); 
 List l2=consulta.list();
     Bitacora [] bt=null ;
    if(l2!=null && l2.size()!=0)
    {   
        System.out.println("entre " + l2.size());
       bt= new Bitacora[l2.size()]; 
       for(int i=0;i<bt.length;i++)
       {
           Operaciones op = (Operaciones) l2.get(i);
           bt[i]= new Bitacora(op.getUsuarios().getCorreo(), op.getIdop() ,op.getOperacion(), op.getFecha() , op.getFilename() , op.getUsuarios().getId() );
       }
    }
       HttpSession s =   ServletActionContext.getRequest().getSession();
       s.setAttribute("bitacora", bt);
        
    }

    private void criterioOperacion() {
        Session hibernateSession;
 hibernateSession=HibernateUtil.getSessionFactory().openSession();
 Query consulta=hibernateSession.createQuery("from Operaciones where operacion= :fl");
 consulta.setParameter("fl", valor); 
 List l2=consulta.list();
 Bitacora [] bt=null ;
 if(l2!=null && l2.size()!=0)
    {   
        System.out.println("entre " + l2.size());
       bt= new Bitacora[l2.size()]; 
       for(int i=0;i<bt.length;i++)
       {
           Operaciones op = (Operaciones) l2.get(i);
           bt[i]= new Bitacora(op.getUsuarios().getCorreo(), op.getIdop() ,op.getOperacion(), op.getFecha() , op.getFilename() , op.getUsuarios().getId() );
       }
    }
       HttpSession s =   ServletActionContext.getRequest().getSession();
       s.setAttribute("bitacora", bt);
     }
}
