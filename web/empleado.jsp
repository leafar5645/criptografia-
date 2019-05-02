<%-- 
    Document   : empleado
    Created on : 25/04/2019, 11:12:37 AM
    Author     : Marcus
--%>

<%@page import="entity.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="Script/OperacionesCliente.js"></script>
        <script src="Script/OperacionesClienteBajarArchivos.js"></script>
        <script src="Script/jquery-3.3.1.min.js"></script>
        
        
        
    </head>
    <body>
        <% 
            if(session.getAttribute("user")!=null)
            {
        Usuarios us = (Usuarios)session.getAttribute("user");
          
        %>
        <h1>Hello World! <% out.print(us.getNombre()); %></h1>  <h3><a href='Logout'>Salir</a></h3>
        <br>
        <br>
        <% if(us.getPermisos().equalsIgnoreCase("ALL") || us.getPermisos().equalsIgnoreCase("upload") ) {%>
        <h2>Selecciona el archivo a subir</h2>
        <input type="file" name='archivo' id="archivo"/>
        <button  onclick="Upload()">Subir Archivo</button>
        <% }%>
            <br>
        <br>
        <% if(us.getPermisos().equalsIgnoreCase("ALL") || us.getPermisos().equalsIgnoreCase("download") ) {%>
        <button  onclick="verArchivos()">ver Archivos</button>
        <div id="archivos-mostrar">
        </di>
        <% }
        
        }
  
            else
            {
                response.sendRedirect("Login.html");
            }
        %>
        
        
        
        
        
        
    </body>
</html>
