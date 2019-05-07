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
     var b=pedirPublica();
     a=atob(b);
    
          
     var pass2=cifrarpublica(b , pass);
     
     //pass=btoa(pass2);
     alert("pass 2" + pass2);
     //alert("pass 1" + pass);
     //console.log(a);
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
      var encrypt = new JSEncrypt();
          encrypt.setPublicKey(a);
          var encrypted = encrypt.encrypt(pass);
          alert("que pasa" + encrypted);
    return encrypted;
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
function pedirPublica()
{
    var formdata = new FormData();
    var a;
          $.ajax({
            url: 'PedirPublica',
            type: 'POST',
            data:  formdata,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
                alert(data);
                a=data;
               
            },
            error: function () {
                alert("error");
            }
        });
       return a;
}


