<%-- 
    Document   : CambioPass
    Created on : 13/05/2019, 03:59:20 PM
    Author     : Marcus
--%>

<%@page import="entity.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
              <script src="Script/jquery-3.3.1.min.js"></script>
        <script src="Script/jsencrypt.js"></script>
        <script src="Script/MAC.js"></script>
        <script src="Script/intercambioLlaves.js"></script>
        <script src="Script/OperacionesCliente.js"></script>
        <script src="Script/OperacionesClienteBajarArchivos.js"></script>
         <script src="Script/Empleados.js"></script>
              <script src="Script/Pass.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
   </head>
    <body>
        <%
            if(session.getAttribute("user")!=null)
            {
        Usuarios us = (Usuarios)session.getAttribute("user");

        %>
        <ul><li><a href='empleado.jsp'>Inicio</a></li><%if(us.getTipo().equalsIgnoreCase("Administrador")){%>
            <li><a href='permisos.jsp'>Permisos</a> </li>
            <li><a onclick="NuevasGenerales()">Nuevas LLaves</a></li>
            <li><a href="CambioPass.jsp">Cambiar Contraseña</a></li>
            <% }%>
            <li><a href='Logout'>Salir</a></li></ul>
            <br>
            <br>
        <h1>Cambiar Contraseña</h1>
        <br>
        <br>
        contraseña actual <input type="password" id="pass1" /><br/>
        Nueva contraseña  <input type="password" id="passn" /><br/>
        <input type="password" id="passn2"  placeholder="Escribela de nuevo"/><br/>
        <button onclick="CambioPass()">Cambiar</button>
        
        <%
           } else
            {
                response.sendRedirect("Login.html");
            }
        %>






    </body>
</html>
