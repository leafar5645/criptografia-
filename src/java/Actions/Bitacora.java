/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.security.Timestamp;
import java.util.Date;

/**
 *
 * @author Marcus
 */
public class Bitacora {
    private String correo;
    private int idop;
    private String operacion;
    private Date fecha;
    private String filename;
    private int  id;

    public Bitacora(String correo, Integer idop, String operacion, Date fecha, String filename, int id) {
        this.correo = correo;
        this.idop = idop;
        this.operacion = operacion;
        this.fecha = fecha;
        this.filename = filename;
        this.id = id;
    }

   
    

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdop() {
        return idop;
    }

    public void setIdop(int idop) {
        this.idop = idop;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
