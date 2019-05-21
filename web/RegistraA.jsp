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
        <LINK rel="stylesheet" href="CSS/bootstrap.min.css" type="text/css">
       <link rel="stylesheet" type="text/css" href="CSS/estilo.css">
       <link href="CSS/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">

    </head>
    <body>
        <%Usuarios user = (Usuarios) session.getAttribute("user");
        
           if (user!=null && user.getTipo().equalsIgnoreCase("Administrador"))
   {%>
   <header> 
   <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <a href='empleado.jsp' class="nav-link">Inicio</a><%if(user.getTipo().equalsIgnoreCase("Administrador")){%>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                <a href='permisos.jsp' class="nav-link">Permisos</a>
                </li>
                <li class="nav-item active">
               <a href="#" onclick="NuevasGenerales()" class="nav-link">Nuevas Llaves</a>
               </li>
                <li class="nav-item active">
                <a href='RegistraA.jsp' class="nav-link">Registrar Usuario</a>
                </li>
               <% }%>
               <li class="nav-item active">
               <a href='Logout' class="nav-link">Salir</a>
           </li>
            <li class="nav-item active"><a href="CambioPass.jsp" class="nav-link">Cambiar Contraseña</a></li>
            </ul>
               
                </div>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    			    <span class="navbar-toggler-icon"></span>
    			  </button>
        </nav>
        </header>
               <br>
               <br>
                 <div class="container">
        <div class="row">
          <div class="col-md-5 mx-auto">
            <div class="myform form" id="root">
              <div class="col-md-12 text-center">
                <h3>Habilita el registro a un correo</h3>
              </div>
                <form onsubmit="RegisterA(this)" id="form-login"  method='post'>
                <div class="form-group">
                  <label>Correo</label>
                  <input type="text" placeholder="Correo" name="correo" class="form-control" id="correo" required/>
                </div>
         <input type="submit"  class=" btn btn-block mybtn btn-primary tx-tfm"  value="Registrar"/>
   </form>
                 </div>
          </div>
        </div>
      </div>
   <% } %>
    </body>
</html>
