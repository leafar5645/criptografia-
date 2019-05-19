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
       var tabla ="<table><th>Empleado</th> <tr><td>Nombre</td><td>Id</td><td>Permisos</td>" ;
   
       
        for (var i = 0; i < split.length; i++) 
          {
              var split2 =split[i].split(":");
              var options;
             
              if(split2[1]=="ALL") {options="<option value='ALL' selected='selected'>ALL</option><option value='upload'>Upload</option><option value='download'>Download</option><option value='none'>None</option> ";}
              if(split2[1]=="upload")  options="<option value='ALL'>ALL</option><option value='upload'  selected='selected'>Upload</option><option value='download'>Download</option><option value='none'>None</option> ";   
              if(split2[1]=="download")options="<option value='ALL'>ALL</option><option value='upload'>Upload</option><option value='download' selected='selected'>Download</option><option value='none'>None</option>";    
              if(split2[1]=="none")options="<option value='ALL'>ALL</option><option value='upload'>Upload</option><option value='download' >Download</option><option value='none' selected='selected'>None</option>";  
        tabla+="<tr><td>"+split2[0]+"</td><td>"+split2[2]+"</td><td><select class='form-control' name='"+split2[2]+"' onchange='CambioPermisos(this)'>"+options+"</select> </td></tr>";
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
function Criterio(e)
{
   var option1=e.options[e.selectedIndex].value;
   console.log(option1);
   if(option1==='0')
   {
        eliminarElemento("submit-criterio") ;
       alert("elige un valor valido"); return 0;}
   var formdata = document.getElementById("form-bitacora");
   
   if(option1==='1')
   {
       eliminarElemento('submit-cirterio') ;
       console.log("entre");
       eliminarElemento("input-criterio");
       var array=['Download','Upload'];
       var selectList = document.createElement("select");
       selectList.id = "select-operacion";
       selectList.name="valor";
       selectList.class="form-control";
       formdata.appendChild(selectList);
       for (var i = 0; i < array.length; i++) {
    var option = document.createElement("option");
    option.value = array[i];
    option.text = array[i];
    selectList.appendChild(option);
}
   var input2 = document.createElement("input");
       input2.type='submit';
    
       input2.id='submit-cirterio';
       input2.class="btn btn-block mybtn btn-primary tx-tfm";
       formdata.appendChild(input2);
   }
    if(option1==='2' || option1==='3')
    {
        console.log("entra");
       eliminarElemento('submit-cirterio') ;
        console.log("sale");
       eliminarElemento("select-operacion") ;
       eliminarElemento("input-criterio") ;
       var input = document.createElement("input");
       input.type='text';
       input.name='valor';
       input.id='input-criterio';
       input.required='required';
       input.class='form-control';
       formdata.appendChild(input);
       var input2 = document.createElement("input");
       input2.type='submit';
       input2.class="btn btn-block mybtn btn-primary tx-tfm";
       input2.id='submit-cirterio';
       formdata.appendChild(input2);
    }
    if(option1==='4')
    {
         eliminarElemento('submit-cirterio') ;
        console.log("sale");
       eliminarElemento("select-operacion") ;
       eliminarElemento("input-criterio") ; 
          var input2 = document.createElement("input");
       input2.type='submit';
    
       input2.id='submit-cirterio';
       formdata.appendChild(input2);
    }
   // eliminarElemento("submit-criterio") ;
    
   
   
}
function eliminarElemento(id){
	imagen = document.getElementById(id);	
	if (!imagen){
		console.log("El elemento selecionado no existe");
	} else {
		padre = imagen.parentNode;
		padre.removeChild(imagen);
	}
}
function NuevasGenerales()
{
    if(confirm("Si continuas se cerrara tu sesion y se generan las nuevas llaves"))
    { var a;
        var formData = new FormData();
         $.ajax({
            url: 'Nuevas',
            type: 'Post',
            data: formData,
            async:false,
            processData: false,
            contentType: false,  
            success: function (data) {
                  a =data.toString();
   
            },
            error: function () {
                alert("Hubo un error en el servidor");
            }
          });
          if(a==="Bien")
          {
              window.location="Login.html";
          }
    }
}
function RegisterA(e)
{
    var correo=document.getElementById("correo").value;
   
    let formData = new FormData();
    formData.append("correo" , correo);
     $.ajax({
            url: 'RegistrarCorreo',
            type: 'Post',
            data: formData,
            async:false,
            processData: false,
            contentType: false,  
            success: function (data) {
                 alert(data);
   
            },
            error: function () {
                alert("Hubo un error en el servidor");
            }
          });
    
    
}
function Registrame()
{
    var nombre = document.getElementById("nombre").value;
    var pass = document.getElementById("pass").value;
    var correo=document.getElementById("correo").value;
    var b= pedirPublica(correo);
    var pass2=cifrarpublica(b , pass);
    let formData = new FormData();
    formData.append("correo",correo);
    formData.append("pass", pass2);
    formData.append("nombre", nombre);
     $.ajax({
            url: 'Registrame',
            type: 'Post',
            data: formData,
            async:false,
            processData: false,
            contentType: false,  
            success: function (data) {
                 alert(data);
                 
   
            },
            error: function () {
                alert("Hubo un error en el servidor");
            }
          });
    window.location="Login.html";
}

