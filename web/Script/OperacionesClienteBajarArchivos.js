var keyPublic;
function verArchivos()
{
    //pruebas();//solo es para pruebas BORRAR AL TERMINAR
    var archivos=[];
      $.ajax({
            url: 'ListaArchivos',
            type: 'Post',
            async:false,
            processData: false,
            contentType: false,  
            success: function (data) {
                  var a =data.toString();
                  if(a=="_ERROR_")
                  {
                      alert("ERROR")
                      window.location="empleado.jsp";
                  }
                  else if(a=="_VACIO_")
                      archivos=["vacio"]
                  else     
                    archivos=a.split("@");
            },
            error: function () {
                alert("Hubo un error en el servidor");
            }
          });
          var tabla ="<table><th>Nombre</th>";
          for (var i = 0; i < archivos.length-1; i++) 
          {
              tabla+="<tr><td>"+archivos[i]+"</td><td><button name='"+archivos[i]+"' onclick='BajarArchivo(this);'>Descargar</button> </td></tr>";
          }
          tabla+="</table>";
          document.getElementById("archivos-mostrar").innerHTML=tabla;
}
  
 var arraybuffer;
async function leer(b,key)
{ 
     var archivo= new FileReader(); 
      await archivo.readAsArrayBuffer(b);
   archivo.onload=  async function()
    {
        arraybuffer=  this.result;
        //--------------MAC--------------
        var MAC = pedirMAC(nombreglobal);
        console.log(MAC);
        var llaveMAC = sessionStorage.getItem('MACkey');
        llaveMAC= await importKeyMAC(llaveMAC);
        var Tag= await generarMAC(llaveMAC,arraybuffer);
        if(MAC!==Tag)
        {
            console.log("MAC INCORRECTA")
            alert("MAC incorrecta");
            return;
        }
        else
        {
            console.log("MAC CORRECTO");
        }
        //---------------AES-----------------
        var arch= await decifrar(arraybuffer,key);
        var myBlob= new Blob([arch]);
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(myBlob);
        link.download = nombreglobal;
        link.click();
    } 
}
async function obtenerArchivo(nombre,key)
{
    var formData = new FormData();
    var llave="hola";
    formData.append("llave" , llave);
    formData.append("nombreArchivo" , nombre );
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'Descargar', true);
    xhr.responseType = 'blob';
    xhr.send(formData);

    xhr.onload = function(e) {
      if (this.status == 200) {
        var blob = new Blob([this.response]);
        leer(blob,key);
               
      }
    }; 
}
var nombreglobal;
var iv;
async function BajarArchivo(ev)
{
    var nombre=ev.name;
    nombreglobal=ev.name;
     var keys =generarLlavesRSA();
     var publica =keys[1];
     var privada =keys[0]; 
    var llave2= pedirLLave(nombre , publica);
    var llave3=decifrarRSA(llave2,privada);  
     var llave4=atob(llave3);
    var llave =str2ab(llave4);
   
    var iv64 = pedirIV(nombre);
    var ivString=atob(iv64);
    iv =str2ab(ivString);
   var key=await importarkeyv(llave);
   
   obtenerArchivo(nombre,key);
}
function decifrar(archivo,key)
{
    return window.crypto.subtle.decrypt(
    {
      name: "AES-CBC",
      iv: iv
    },
    key,
    archivo
    ).then(function(result){
        
        return new Uint8Array(result);
    });
    return archivo;
}
function pedirLLave(nombre , publica)
{
      var formdata = new FormData();
      formdata.append("filename" , nombre);
      formdata.append("publicKeyClient" , publica);
    var a;
          $.ajax({
            url: 'PedirLLave2',
            type: 'POST',
            data:  formdata,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
                a=data;
               
            },
            error: function () {
                alert("error");
            }
        });
        return a;
}
function pedirIV(nombre)
{
      var formdata = new FormData();
      formdata.append("nombre" , nombre);
    var a;
          $.ajax({
            url: 'PedirIV',
            type: 'POST',
            data:  formdata,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
                a=data;
            },
            error: function () {
                alert("error");
            }
        });
        return a;
}
function pedirMAC(nombre)
{
      var formdata = new FormData();
      formdata.append("nombre" , nombre);
    var a;
          $.ajax({
            url: 'PedirMAC',
            type: 'POST',
            data:  formdata,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
                a=data;
            },
            error: function () {
                alert("error");
            }
        });
        return a;
}
async function importarkeyv(rawKey)
{
   return await window.crypto.subtle.importKey(
    "raw",
    rawKey,
    "AES-CBC",
    true,
    ["encrypt", "decrypt"]
  );
}
/*Convert a string into an ArrayBuffer
from https://developers.google.com/web/updates/2012/06/How-to-convert-ArrayBuffer-to-and-from-String
*/
function str2ab(str) {
  const buf = new ArrayBuffer(str.length);
  const bufView = new Uint8Array(buf);
  for (let i = 0, strLen = str.length; i < strLen; i++) {
    bufView[i] = str.charCodeAt(i);
  }
  return buf;
}
