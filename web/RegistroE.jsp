<%-- 
    Document   : RegistroE
    Created on : 15/05/2019, 05:02:17 PM
    Author     : Marcus
--%>

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
        <h1>Llena lo siguiente para registrarte</h1>
        <br>
        <br>
        Correo: <input type="email" name="correo" id="correo"/></br>
        Nombre: <input type="text" name="nombre" id="nombre"/></br>
        Contraseña: <input type="password" name="pass" id="pass"/></br>
        <button value="registrar" onclick="Registrame()">Registrar</button>
    </body>
</html>
