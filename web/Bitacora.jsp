<%-- 
    Document   : Bitacora
    Created on : 11/05/2019, 10:59:46 AM
    Author     : Marcus
--%>

<%@page import="entity.Usuarios"%>
<%@page import="Actions.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <script src="Script/Empleados.js"></script>
                  <script src="Script/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" type="text/css" href="CSS/estilo.css">
    </head>
    <body>
        <h1><% Bitacora [] bt =(Bitacora []) session.getAttribute("bitacora");
                //out.print(bt.length);
                 if(session.getAttribute("user")!=null)
            {
        Usuarios us = (Usuarios)session.getAttribute("user");
                if(us.getTipo().equalsIgnoreCase("Administrador"))
                {
                if(bt!=null) 
                {%>
                 <ul><li><a href='empleado.jsp'>Inicio</a></li><%if(us.getTipo().equalsIgnoreCase("Administrador")){%>
            <li><a href='permisos.jsp'>Permisos</a> </li>
            <li><a onclick="NuevasGenerales()">Nuevas LLaves</a></li>
            <% }%>
            <li><a href='Logout'>Salir</a></li></ul>
                <table border='1'>
                    <tr><td>Id Operacion</td><td>Operacion</td><td>fecha</td><td>Nombre Archivo</td><td>Id Empleado</td><td>Correo</td></tr>            
                    <%for (int i=0 ; i<bt.length ; i++ ){%>
                    <tr><td><% out.print(bt[i].getIdop()); %></td><td><% out.print(bt[i].getOperacion()); %></td><td><% out.print(bt[i].getFecha()); %></td> <td><% out.print(bt[i].getFilename()); %></td><td><% out.print(bt[i].getId()); %></td><td><% out.print(bt[i].getCorreo()); %></td>    </tr> 
                    
                    <% } %>
                </table>
                <%}

                    if(bt==null || bt.length==0)
{
                    out.print("<h1>Sin resultados</h1>");
}
}else{response.sendRedirect("empleado.jsp");}
} else{response.sendRedirect("Login.html");}       %></h1>
    </body>