
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
       <script src="Script/bootstrap.min.js"></script>
       <LINK rel="stylesheet" href="CSS/bootstrap.min.css" type="text/css">
       <link rel="stylesheet" type="text/css" href="CSS/estilo.css">
       <link href="CSS/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">

    </head>
    <body>
        <%
            if(session.getAttribute("user")!=null)
            {
        Usuarios us = (Usuarios)session.getAttribute("user");

        %>
        <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <a href='empleado.jsp' class="nav-link">Inicio</a>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
          <%if(us.getTipo().equalsIgnoreCase("Administrador")){%>
            
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

        <section class="jumbotron">
          <div class="container">
            <h1><% out.print(us.getNombre()); %></h1>
          </div>
          <div class="container">
                  <% if(us.getPermisos().equalsIgnoreCase("ALL") || us.getPermisos().equalsIgnoreCase("upload") ) {%>
                  <h2>Selecciona el archivo a subir</h2>
                  <input class="form-control-file" type="file" name='archivo' id="archivo" onchange="cargar()"/><br><br>
                  <button class="btn btn-primary" onclick="Upload()">Subir Archivo</button><br><br>
                  <% }%>
                  <% if(us.getPermisos().equalsIgnoreCase("ALL") || us.getPermisos().equalsIgnoreCase("download") ) {%>
                  <button  onclick="verArchivos()" class="btn btn-primary">ver Archivos</button>
                  <div id="archivos-mostrar">
                  </div>
          </div>
          <div class="container">
              <% } if(us.getTipo().equalsIgnoreCase("Administrador")) {%>
               <br><br>
              <h2>Bitacora</h2>
              <form action="Bitacora" id='form-bitacora'>
                  <select class="form-control"  placeholder='Criterio de busqueda' name='criterio' id='select-bitacora' onchange='Criterio(this)'>
                      <option selected='selected' value='0'>Criterio de busqueda</option>
                      <option value="1">Operacion</option>
                      <option value="2">Archivo</option>
                      <option value="3">Correo Usuario</option>
                      <option value="4">Todos los registros </option>
                  </select>

              </Form>

          </div>
        </section>



        <%
            }
        }

            else
            {
                response.sendRedirect("Login.html");
            }
        %>

                <footer>
           <div class="container">
              <div class="row text-center d-flex justify-content-center pt-5 mb-3">
                <!-- Grid column -->
                <div class="col-md-2 mb-3">
                  <h6 class="text-uppercase font-weight-bold">
                    <a href="#!">About us</a>
                  </h6>
                </div>
                <!-- Grid column -->
                <div class="col-md-2 mb-3">
                  <h6 class="text-uppercase font-weight-bold">
                    <a href="#!">Help</a>
                  </h6>
                </div>
                <!-- Grid column -->

                <!-- Grid column -->
                <div class="col-md-2 mb-3">
                  <h6 class="text-uppercase font-weight-bold">
                    <a href="#!">Contact</a>
                  </h6>
                </div>
                <!-- Grid column -->

              </div>
              <!-- Grid row-->
              <hr class="rgba-white-light" style="margin: 0 15%;">

              <!-- Grid row-->
              <div class="row d-flex text-center justify-content-center mb-md-0 mb-4">


              </div>
              <!-- Grid row-->
              <hr class="clearfix d-md-none rgba-white-light" style="margin: 10% 15% 5%;">

              <!-- Grid row-->
              <div class="row pb-3">



              </div>
              <!-- Grid row-->

            </div>
            <!-- Footer Links -->
               <div class="footer-copyright text-center py-3">© 2018 Copyright: IPN ESCOM
              <a href="#"></a>
            </div>
           </footer>

    </body>
</html>
