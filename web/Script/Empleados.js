/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function pedirEmpleados()
{ 
    var a;
    var formData = new FormData();
     $.ajax({
            url: 'PedirEmpleados',
            type: 'POST',
            data: formData,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
               a=data;
            },
            error: function () {
                alert("Archivo invalido");
            }
        });
        
    var split=a.split("@");
    var tabla=document.getElementById("tabla")
       var tabla ="<table><th>Empleado</th> <tr><td>Nombre</td><td>Permisos</td>" ;
   
       
        for (var i = 0; i < split.length; i++) 
          {
              var split2 =split[i].split(":");
              var options;
             
              if(split2[1]=="ALL") {options="<option value='ALL' selected='selected'>ALL</option><option value='upload'>Upload</option><option value='download'>Download</option><option value='none'>None</option> ";}
              if(split2[1]=="upload")  options="<option value='ALL'>ALL</option><option value='upload'  selected='selected'>Upload</option><option value='download'>Download</option><option value='none'>None</option> ";   
              if(split2[1]=="download")options="<option value='ALL'>ALL</option><option value='upload'>Upload</option><option value='download' selected='selected'>Download</option><option value='none'>None</option>";    
              if(split2[1]=="none")options="<option value='ALL'>ALL</option><option value='upload'>Upload</option><option value='download' >Download</option><option value='none' selected='selected'>None</option>";  
        tabla+="<tr><td>"+split2[0]+"</td><td><select name='"+split2[2]+"' onchange='CambioPermisos(this)'>"+options+"</select> </td></tr>";
          }
          tabla+="</table>";
          document.getElementById("tabla").innerHTML=tabla;
    
}
function CambioPermisos(e)
{
    var id = e.name;
    var permiso= e.options[e.selectedIndex].value;
     var formData = new FormData();
     formData.append("id", id);
     formData.append("permiso", permiso);
     $.ajax({
            url: 'ActualizarPermisos',
            type: 'POST',
            data: formData,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
               alert(data);
            },
            error: function () {
                alert("Archivo invalido");
            }
        });
    
}

