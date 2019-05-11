<%-- 
    Document   : Bitacora
    Created on : 11/05/2019, 10:59:46 AM
    Author     : Marcus
--%>

<%@page import="Actions.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><% Bitacora [] bt =(Bitacora []) session.getAttribute("bitacora");
                //out.print(bt.length);
                if(bt!=null) 
                {%>
                <table border='1'>
                    <tr><td>Id Operacion</td><td>Operacion</td><td>fecha</td><td>Nombre Archivo</td><td>Id Empleado</td><td>Correo</td></tr>            
                    <%for (int i=0 ; i<bt.length ; i++ ){%>
                    <tr><td><% out.print(bt[i].getIdop()); %></td><td><% out.print(bt[i].getOperacion()); %></td><td><% out.print(bt[i].getFecha()); %></td> <td><% out.print(bt[i].getFilename()); %></td><td><% out.print(bt[i].getId()); %></td><td><% out.print(bt[i].getCorreo()); %></td>    </tr> 
                    
                    <% } %>
                </table>
                <% }
            %></h1>
    </body>
</html>
