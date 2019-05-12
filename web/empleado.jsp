
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
        <link rel="stylesheet" type="text/css" href="CSS/estilo.css">

    </head>
    <body>
        <%
            if(session.getAttribute("user")!=null)
            {
        Usuarios us = (Usuarios)session.getAttribute("user");

        %>
        <ul><li><a href='empleado.jsp'>Inicio</a></li><%if(us.getTipo().equalsIgnoreCase("Administrador")){%>
            <li><a href='permisos.jsp'>Permisos</a> </li>
            <% }%>
            <li><a href='Logout'>Salir</a></li></ul>

        <h1><% out.print(us.getNombre()); %></h1>
        <br>
        <br>
        <% if(us.getPermisos().equalsIgnoreCase("ALL") || us.getPermisos().equalsIgnoreCase("upload") ) {%>
        <h2>Selecciona el archivo a subir</h2>
        <input type="file" name='archivo' id="archivo" onchange="cargar()"/>
        <button  onclick="Upload()">Subir Archivo</button>
        <% }%>
            <br>
        <br>
        <% if(us.getPermisos().equalsIgnoreCase("ALL") || us.getPermisos().equalsIgnoreCase("download") ) {%>
        <button  onclick="verArchivos()">ver Archivos</button>
        <div id="archivos-mostrar">
        </div>
        <br>
        <br>
        <br>
        <% } if(us.getTipo().equalsIgnoreCase("Administrador")) {%>
        <h2>Bitacora</h2>
        <form action="Bitacora" id='form-bitacora'>
            <select placeholder='Criterio de busqueda' name='criterio' id='select-bitacora' onchange='Criterio(this)'>
                <option selected='selected' value='0'>Criterio de busqueda</option>
                <option value="1">Operacion</option>
                <option value="2">Archivo</option>
                <option value="3">Correo Usuario</option>
                <option value="4">Todos los registros </option>
            </select>

        </Form>

        <%
            }
        }

            else
            {
                response.sendRedirect("Login.html");
            }
        %>






    </body>
</html>
