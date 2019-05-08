<%-- 
    Document   : permisos
    Created on : 7/05/2019, 07:36:52 PM
    Author     : Marcus
--%>


<%@page import="entity.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
       
        <script src="Script/jquery-3.3.1.min.js"></script>
        <script src="Script/intercambioLlaves.js"></script>
         <script src="Script/Empleados.js"></script>
    
        <link rel="stylesheet" type="text/css" href="CSS/estilo.css">

    </head>
    <body>
        <% 
            if(session.getAttribute("user")!=null)
            {
               
        Usuarios us = (Usuarios)session.getAttribute("user");
    if(!us.getTipo().equalsIgnoreCase("Administrador")){response.sendRedirect("empleado.jsp");}%>
       
        <ul><li><a href='empleado.jsp'>Inicio</a></li>
            <li><a href='permisos.jsp'>Permisos</a> </li>
            
            <li><a href='Logout'>Salir</a></li></ul>
        
        <h1><% out.print(us.getNombre()); %></h1> 
        <br>
        <br>
        <button onclick="pedirEmpleados()">Mostrar empleados</button>
        <div id='tabla'>
            
            
        </div>
        
        <% 
            
           
        }
  
            else
            {
                response.sendRedirect("Login.html");
            }
        %>
        
        
        
        
        
        
    </body>
</html>