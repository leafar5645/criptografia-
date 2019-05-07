var keyPublic;
function verArchivos()
{
    pruebas();//solo es para pruebas BORRAR AL TERMINAR
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
                console.log(a);
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
async function leer(b)
{ 
    
     var archivo= new FileReader(); 
   console.log("entre");
      await archivo.readAsArrayBuffer(b);
   archivo.onload=  function()
    {
        arraybuffer=  this.result;
        
        continuar();
    }
   console.log("llegue");
 
}
async function continuar()
{
   console.log(arraybuffer);
    var key = await importarkeyv(arraybuffer);
    console.log(key);
    console.log(nombreglobal);
    var formData = new FormData();
    var llave="hola";
    formData.append("llave" , llave);
    formData.append("nombreArchivo" , nombreglobal );
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'Descargar', true);
    xhr.responseType = 'blob';
    xhr.send(formData);

    xhr.onload = function(e) {
      if (this.status == 200) {
        var blob = new Blob([this.response]);
        //console.log(blob.size);
        var arch = decifrar(blob);
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(arch);
        link.download = nombreglobal;
        link.click();       
      }
    }; 
}
var nombreglobal;
async function BajarArchivo(ev)
{
    var nombre=ev.name;
    nombreglobal=ev.name;
    var llave2= await pedirLLave(nombre);
    console.log(llave2)
    var llave3=atob(llave2);
   // llave2 =str2ab(llave2);
    
    console.log(llave3);
    var b = new Blob([llave3]);
    console.log(b);
   var buffer=await leer(b); 
   
}
function decifrar(archivo)
{
    return archivo;
}
function pedirLLave(nombre)
{
    console.log("entre");
      var formdata = new FormData();
      formdata.append("nombre" , nombre);
    var a;
          $.ajax({
            url: 'PedirLLave',
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

//-----------------------PRUEBAS---------------
function pruebas()
{
    /*
    var keyP=null;
    var encript= pedirLlaveServidor();
    */

    var key= generarLlavesRSA();
    console.log("llaveC"+key[0]);
    console.log("llaveC"+key[1]);
    enviarLlavePublica(key);
    var prueba="hola";
    prueba=window.btoa(prueba);
    prueba= cifrarRSA(prueba,key);
    console.log("cifrado: "+prueba);
    prueba= decifrarRSA(prueba,key);
     console.log("decifrado: "+prueba);
     
}
function str2ab(str) {
  var buf = new ArrayBuffer(str.length*2); // 2 bytes for each char
  var bufView = new Uint8Array(buf);
  for (var i=0, strLen=str.length; i < strLen; i++) {
    bufView[i] = str.charCodeAt(i);
  }
  return buf;
}