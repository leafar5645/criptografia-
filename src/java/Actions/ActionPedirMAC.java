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
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.HibernateUtil;
import entity.Usuarios;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author betoj
 */
public class ActionPedirMAC extends ActionSupport {
     private InputStream  resourceStream;
    private String nombre;
    private String path;
    public ActionPedirMAC() {
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
        HttpSession s =   ServletActionContext.getRequest().getSession();
        Usuarios user =(Usuarios)s.getAttribute("user");
        if(user==null)
            return ERROR;
         Session hibernateSession;
        hibernateSession= HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Usuarios where id= :id");
        consulta.setParameter("id", user.getId());
        List l=consulta.list();
        byte[] kMAc;
        if(l!=null && l.size()!=0)
        {
           Usuarios us =(Usuarios)l.get(0);
           kMAc=us.getKeyMac().getBytes();
           kMAc=Base64.getDecoder().decode(kMAc);
        }
        else
            return ERROR;
        
        PrivateKey privateKey = obtenerPrivada();
        Cipher cipher = Cipher.getInstance("RSA"); 
        cipher.init(Cipher.DECRYPT_MODE, privateKey);//String p ="hola qyye jace";
        byte [] bkmac=cipher.doFinal(kMAc);
        bkmac=Base64.getDecoder().decode(bkmac);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bkmac, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        String path= ServletActionContext.getServletContext().getRealPath("/archivos");
        File archivo= new File(path+"/"+nombre);
        System.out.println(path+"/"+nombre);
        if(!archivo.exists())
        {
            System.out.println("Archivo Inexistente");
            return ERROR;
        }
        DataInputStream in = new DataInputStream(new FileInputStream(archivo));
        byte [] informacion= new byte[(int) archivo.length()];
        in.readFully(informacion);
        byte[] tag =mac.doFinal(informacion);
        tag= Base64.getEncoder().encode(tag);
        //System.out.println( "MAC-------------------------------"+new String(tag));
        resourceStream= new ByteArrayInputStream(tag);
        return SUCCESS;
    }
    
    private PrivateKey obtenerPrivada() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String nombreprivada="pruebaCifradoEstilo.txt";
        String path= ServletActionContext.getServletContext().getRealPath("/");
         File f= new File(path + "CSS/"+nombreprivada);
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        byte[] all = new byte [(int)f.length()] ;
        in.read(all);
        byte [] all2=Base64.getDecoder().decode(all);
        int total =all2.length;
        //System.out.println("total" + total);
        int p =total;
        byte[] oldprivateKeyBytes = new byte [p/3];
        int z=0;
        for(int i=0;i<p;i=i+3)
        {
          oldprivateKeyBytes[z]=all2[i+1];
          z= z+1;
        }
         //System.out.println("la ida" + new String(Base64.getEncoder().encode(oldprivateKeyBytes)));
        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever

        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(oldprivateKeyBytes));
        return privateKey;
    }
}
