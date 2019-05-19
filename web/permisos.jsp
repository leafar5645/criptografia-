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
        <script src="Script/bootstrap.min.js"></script>
       <LINK rel="stylesheet" href="CSS/bootstrap.min.css" type="text/css">
       <link rel="stylesheet" type="text/css" href="CSS/estilo.css">
       <link href="CSS/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">
    
        <link rel="stylesheet" type="text/css" href="CSS/estilo.css">

    </head>
    <body>
        <% 
            if(session.getAttribute("user")!=null)
            {
               
        Usuarios us = (Usuarios)session.getAttribute("user");
    if(!us.getTipo().equalsIgnoreCase("Administrador")){response.sendRedirect("empleado.jsp");}%>
   
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
                <form class="form-inline my-2 my-lg-0">
    			      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
    			      <button class="btn btn-primary" type="submit">
    			      <span class="oi oi-magnifying-glass"></span>
    			      </button>
    			    </form>
                </div>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    			    <span class="navbar-toggler-icon"></span>
    			  </button>
        </nav>
        </header>
        <section class="jumbotron">
        <div class="container"> 
        <h1><% out.print(us.getNombre()); %></h1> 
        <br>
        <br>
        <button class="btn btn-primary" onclick="pedirEmpleados()">Mostrar empleados</button>
       
        <div id='tabla'>
            
            
        </div>
        </div>
         </section>
        
        <% 
            
           
        }
  
            else
            {
                response.sendRedirect("Login.html");
            }
        %>
        
        
        
        
        
        
    </body>
</html>