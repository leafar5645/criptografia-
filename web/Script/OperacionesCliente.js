function Logear(e)
{
   
    var a;
   
   /* var formData1= new FormData();
    formData1.append("operacion" , "llave");
     $.ajax({
            url: 'Logear',
            type: 'Post',
            data: formData1,
            async:false,
            processData: false,
            contentType: false,  
            success: function (data) {
                  a =data.toString();
   
            },
            error: function () {
                alert("Hubo un error en el servidor");
            }
          });*/
     var correo =document.getElementById("correo").value;
     var pass = document.getElementById("pass").value;
     var pass2=cifrarpublica(a , pass);
     console.log(pass2);
     var formData = new FormData();
     formData.append("correo" , correo);
     formData.append("pass" , pass2);
     formData.append("operacion" , "login");
      $.ajax({
            url: 'Logear',
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
          if(a!=="error")
          {
              window.location=a;
          }
          else
          {
             document.getElementById("label-login").innerHTML='Usuario o contraseña incorrecta';
             
          }
    
}
function cifrarpublica(a , pass)
{
    
    return pass;
}
function cifrarfile(file)
{
    return file;
}
function Upload ()
{ 
     var file= $('#archivo')[0].files[0];
     var fileC=cifrarfile(file);
     var formData = new FormData();
     var hola = "hola";
        formData.append('archivo', fileC );
        formData.append("llave" , hola);
      
        $.ajax({
            url: 'Subir',
            type: 'POST',
            data: formData,
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


