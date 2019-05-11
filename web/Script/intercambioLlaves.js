//-------------------------------------------------------------------------
//-----------------------------Funciones RSA----------------------------
////------------------------------------------------------------------------
function pedirLlaveServidor()
{     
    var Key_promise;
    var Key=null;
    var data;
        $.ajax({
            url: 'PedirPublica',
            type: 'POST',
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            async: false,
            success:  function (dat) {
                console.log(dat.toString());
                data=dat;   
            },
            error: function () {
                alert("Archivo invalido");
            }
        });
        var llave= window.atob(data);
         var encrypt = new JSEncrypt();
          encrypt.setPublicKey(llave);
    return encrypt;
}
//retorna un keyObject
function generarLlavesRSA()
{
    var crypt = new JSEncrypt({ default_key_size: 2048 });
    crypt.getKey();
    crypt.getPrivateKey();
    return[crypt.getPrivateKey(),crypt.getPublicKey()]
}
function cifrarRSA(plaintext,key)
{
    var public_key_object=key[1];
    var cifrado;

  //  plaintext=window.atob(plaintext);
    
     var encrypt = new JSEncrypt();
     encrypt.setPublicKey(public_key_object);
     var encrypted = encrypt.encrypt(plaintext);
    cifrado=window.btoa(encrypted);
    return cifrado;
}

function decifrarRSA(cifrado,key)
{
   // cifrado=window.atob(cifrado);
    var decifrado;
     var crypt = new JSEncrypt();
    crypt.setPrivateKey(key);
    decifrado = crypt.decrypt(cifrado);
    return decifrado;
}

function cifrarpublica(a , pass)
{
      var encrypt = new JSEncrypt();
          encrypt.setPublicKey(a);
          var encrypted = encrypt.encrypt(pass);
    return encrypted;
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

//convertir String a ArrayBuffer
//from https://developers.google.com/web/updates/2012/06/How-to-convert-ArrayBuffer-to-and-from-String
function str2ab(str) {
  const buf = new ArrayBuffer(str.length);
  const bufView = new Uint8Array(buf);
  for (let i = 0, strLen = str.length; i < strLen; i++) {
    bufView[i] = str.charCodeAt(i);
  }
  return buf;
}
/*
Convert  an ArrayBuffer into a string
from https://developers.google.com/web/updates/2012/06/How-to-convert-ArrayBuffer-to-and-from-String
*/
function ab2str(buf) {
  return String.fromCharCode.apply(null, new Uint8Array(buf));
}



