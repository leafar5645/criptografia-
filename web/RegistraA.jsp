<%-- 
    Document   : RegistraA
    Created on : 15/05/2019, 10:08:41 AM
    Author     : Marcus
--%>

<%@page import="entity.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="Script/jsencrypt.js"></script>
        <script src="Script/jquery-3.3.1.min.js"></script>
        <script src="Script/MAC.js"></script>
        <script src="Script/intercambioLlaves.js"></script>
        <script src="Script/OperacionesCliente.js"></script>
        <script src="Script/OperacionesClienteBajarArchivos.js"></script>
       <script src="Script/Empleados.js"></script>
    </head>
    <body>
        <%Usuarios user = (Usuarios) session.getAttribute("user");
        
           if (user!=null && user.getTipo().equalsIgnoreCase("Administrador"))
   {%>
   <form onsubmit="RegisterA(this)" method="Post">
       <input name="correo" id="correo" type="email"/>
       <input type="submit" value="Registrar"/>
   </form>
   <% } %>
    </body>
</html>
