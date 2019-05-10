var llave;
function Logear(e)
{   
    var a;
     var correo =document.getElementById("correo").value;
     var pass = document.getElementById("pass").value;
     var b=pedirPublica(correo);
   //  a=atob(b);
    
          
     var pass2=cifrarpublica(b , pass);
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
         // alert("que pasa" + encrypted);
    return encrypted;
}
async function llavesAES()
{
    return window.crypto.subtle.generateKey(
  {
    name: "AES-CBC",
    length: 256,
  },
  true,
  ["encrypt", "decrypt"]
    ).then(function(key){return key});
}
var importada;
var arraybuffer;
var archivo;
var iv;
function cargar()
{
     archivo= new FileReader();   
    archivo.onload= function()
    {
        arraybuffer=this.result;
        
        console.log("Archivo Cargado");
    }
     archivo.readAsArrayBuffer($('#archivo')[0].files[0]);
}
function cifrarfile( key)
{
    llave=key;

    iv =  window.crypto.getRandomValues(new Uint8Array(16));
  return  window.crypto.subtle.encrypt(
    {
      name: "AES-CBC",
      iv
    },
    key,
    arraybuffer
    ).then(function(result){
        return new Uint8Array(result);
    });
    
}
async function exportarKey(key)
{
    const exported = await window.crypto.subtle.exportKey(
    "raw",
    key
  );
  var exportedKeyBuffer = new Uint8Array(exported);
  var exportedAsString = ab2str(exportedKeyBuffer);
  var exportedAsBase64 = await btoa(exportedAsString);
  var a= pedirPublicaGeneral();
  alert("antes de " +  exportedAsBase64  );
  var cifrada=cifrarpublica(a, exportedAsBase64);
  alert(cifrada)
  return cifrada;
}
async function Upload ()
{ 
    var key=await llavesAES(); 
     var file=  $('#archivo')[0].files[0];
   var fileC= await cifrarfile(key);
    var myBlob= new Blob([fileC],{type:file.type});
    myBlob.lastModifiedDate= new Date();
    myBlob.name=file.name;
    var importada =await exportarKey(key);
    var formData = new FormData();
    var ivexportedAsString = ab2str(iv);
    var ivexportedAsBase64 = await window.btoa(ivexportedAsString);
     //var hola = "hola";
        formData.append('archivo', myBlob );
        formData.append('nombre' , file.name);
        formData.append("llave" , importada);
        formData.append("iv" , ivexportedAsBase64);
     peticion(formData);
}

function peticion(formData)
{
    $.ajax({
            url: 'Subir',
            type: 'POST',
            data: formData,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
                if(data.toString()=="Bien")
                alert("Archivo Subido Con Exito");
            },
            error: function () {
                alert("Archivo invalido");
            }
        });
}
function pedirPublicaGeneral()
{
    var formdata = new FormData();
    var a;
          $.ajax({
            url: 'PedirPublicaGeneral',
            type: 'POST',
            data:  formdata,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
               // alert(data);
                a=data;
                
               
            },
            error: function () {
                alert("error-publica");
            }
        });
       return a; 
}
function pedirPublica(correo)
{
    var formdata = new FormData();
    formdata.append("correo", correo);
    var a;
          $.ajax({
            url: 'PedirPublica',
            type: 'POST',
            data:  formdata,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
               // alert(data);
                a=data;
               
            },
            error: function () {
                alert("error");
            }
        });
       return a;
}
function ab2str(buf) {
  return String.fromCharCode.apply(null, new Uint8Array(buf));
}
