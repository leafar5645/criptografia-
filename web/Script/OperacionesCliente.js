var llave;
function Logear(e)
{   
    var a;
     var correo =document.getElementById("correo").value;
     var pass = document.getElementById("pass").value;
     var b=pedirPublica();
   //  a=atob(b);
    
          
     var pass2=cifrarpublica(b , pass);
     
     //pass=btoa(pass2);
    // alert("pass 2" + pass2);
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
function cargar()
{
     archivo= new FileReader(); 
   console.log("entre");
   
    archivo.onload= function()
    {
        arraybuffer=this.result;
        console.log("entre2");
    }
     archivo.readAsArrayBuffer($('#archivo')[0].files[0]);
}
function cifrarfile(file, key)
{
  
   
   
    var myBlob = new Blob([file],{type: "blob"});
    console.log(myBlob);
    var arr = new Uint8Array(myBlob);
    //var plaintext= archivo.readAsArrayBuffer(myBlob);
    //console.log(plaintext);
    //myBlob = new Blob([new Uint8Array(plaintext)]);
    //console.log(myBlob);
    console.log(arraybuffer);
    //var key=await llavesAES(); 
    llave=key;
    //importada = await importarKey(llave);
    console.log(key);
    console.log(llave);
    var iv =  window.crypto.getRandomValues(new Uint8Array(16));
  return  window.crypto.subtle.encrypt(
    {
      name: "AES-CBC",
      iv
    },
    key,
    arraybuffer
    ).then(function(result){
        console.log("hola"+result);
        return new Uint8Array(result);
    });
    
}
async function importarKey(key)
{
    console.log(key);
   
    const exported = await window.crypto.subtle.exportKey(
    "raw",
    key
  );
  console.log(exported)
  var exportedAsString = ab2str(exported);
  var exportedAsBase64 = await window.btoa(exportedAsString);
  return exportedAsBase64;
}
async function Upload ()
{ 
    var key=await llavesAES(); 
    console.log(key);
    
     var file=  $('#archivo')[0].files[0];
     //var file=new File("hola","oh");
    //var file=null;
   var fileC= await cifrarfile(file,key);
    console.log(fileC);
    var myBlob= new Blob([fileC],{type:'blob'})
    console.log(myBlob);
    var importada =await importarKey(key);
    console.log(importada);
    var fileEnvio= new File(fileC,file.name);
    
    var formData = new FormData();
     //var hola = "hola";
        formData.append('archivo', fileEnvio );
        formData.append("llave" , importada);
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