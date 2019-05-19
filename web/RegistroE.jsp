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
         <LINK rel="stylesheet" href="CSS/bootstrap.min.css" type="text/css">
        <LINK rel="stylesheet" href="CSS/estilo.css" type="text/css">
        <script src="Script/jsencrypt.js"></script>
        <script src="Script/jquery-3.3.1.min.js"></script>
        <script src="Script/MAC.js"></script>
        <script src="Script/intercambioLlaves.js"></script>
        <script src="Script/OperacionesCliente.js"></script>
        <script src="Script/OperacionesClienteBajarArchivos.js"></script>
       <script src="Script/Empleados.js"></script>
       <script src="Script/bootstrap.min.js"></script>
    </head>
    <body>
         <div class="container">
        <div class="row">
          <div class="col-md-5 mx-auto">
            <div class="myform form" id="root">
              <div class="col-md-12 text-center">
                <h1>Llena lo siguiente para registrarte</h1>
              </div>
                <div class="form-group">
                  <label>Correo</label>
                  <input type="text" placeholder="Correo" name="correo" class="form-control" id="correo" required/>
                  </div>
                <div class="form-group">
                  <label>Nombre</label>
                  <input type="text" placeholder="nombre" name="nombre" id="nombre" class="form-control" required/>
                </div>
                <div class="form-group">
                  <label>Pass</label>
                  <input type="password" placeholder="Contraseña" name="pass" id="pass" class="form-control" required/>
                </div>
              <button value="registrar"  class=" btn btn-block mybtn btn-primary tx-tfm" onclick="Registrame()">Registrar</button>
        <br>
        <h6> <a href="Login.html">Login</a> </h6>
           </div>
          </div>
        </div>
      </div>
    </body>
</html>
