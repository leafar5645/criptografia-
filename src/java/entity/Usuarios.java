package entity;
// Generated 23/04/2019 06:24:39 PM by Hibernate Tools 4.3.1



/**
 * Usuarios generated by hbm2java
 */
public class Usuarios  implements java.io.Serializable {


     private int id;
     private String nombre;
     private String tipo;
     private String correo;
     private String pass;

    public Usuarios() {
    }

	
    public Usuarios(int id) {
        this.id = id;
    }
    public Usuarios(int id, String nombre, String tipo, String correo, String pass) {
       this.id = id;
       this.nombre = nombre;
       this.tipo = tipo;
       this.correo = correo;
       this.pass = pass;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }




}


