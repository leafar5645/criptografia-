var llave;
async function Logear(e)
{   
    var a;
     var correo =document.getElementById("correo").value;
     var pass = document.getElementById("pass").value;
     
    var b=pedirPublica(correo);
    var llaveMAC= await generarLlaveMac();
    var MACexportada= await exportarKeyMAC(llaveMAC);
    sessionStorage.setItem('MACkey',MACexportada);
    var MACcifrada=cifrarpublica(b, MACexportada);

     var pass2=cifrarpublica(b , pass);
     var formData = new FormData();
     formData.append("correo" , correo);
     formData.append("pass" , pass2);
     formData.append("operacion" , "login");
     formData.append("keyMAC" , MACcifrada);
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
  var cifrada=cifrarpublica(a, exportedAsBase64);
  return cifrada;
}
async function Upload ()
{ 
    var key=await llavesAES(); 
     var file=  $('#archivo')[0].files[0];
     //cifrdoa AES
   var fileC= await cifrarfile(key);
    var myBlob= new Blob([fileC]);
    myBlob.lastModifiedDate= new Date();
    myBlob.name=file.name;
    var importada =await exportarKey(key);
    var formData = new FormData();
    var ivexportedAsString = ab2str(iv);
    var ivexportedAsBase64 = await window.btoa(ivexportedAsString);
    //MAC
    var llaveMAC = sessionStorage.getItem('MACkey');
    llaveMAC= await importKeyMAC(llaveMAC);
    var MAC= await generarMAC(llaveMAC,fileC);
    //var correcto= await verificarMAC(llaveMAC,MAC,arraybuffer);
     //var hola = "hola";
        formData.append('archivo', myBlob );
        formData.append('nombre' , file.name);
        formData.append("llave" , importada);
        formData.append("iv" , ivexportedAsBase64);
        formData.append("MAC" , MAC);
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

function ab2str(buf) {
  return String.fromCharCode.apply(null, new Uint8Array(buf));
}
