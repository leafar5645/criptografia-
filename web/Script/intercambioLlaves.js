
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
    var crypt = new JSEncrypt({ default_key_size: 1024 });
    crypt.getKey();
    crypt.getPrivateKey();
    console.log(crypt.getPrivateKey());
    console.log(crypt.getPublicKey());
    return[crypt.getPrivateKey(),crypt.getPublicKey()]
}
async function enviarLlavePublica(key)
{
    var public_key_object = key[1];
        var exportedAsBase64 = public_key_object;
        console.log(exportedAsBase64);//enviar al servidor
}

function cifrarRSA(plaintext,key)
{
    var public_key_object=key[1];
    var cifrado;

    plaintext=window.atob(plaintext);
    
     var encrypt = new JSEncrypt();
     encrypt.setPublicKey(public_key_object);
     var encrypted = encrypt.encrypt(plaintext);
    cifrado=window.btoa(encrypted);
    return cifrado;
}

function decifrarRSA(cifrado,key)
{
    cifrado=window.atob(cifrado);
    var decifrado;
     var crypt = new JSEncrypt();
     console.log(key[0]);
    crypt.setPrivateKey(key[0]);
    decifrado = crypt.decrypt(cifrado);
    return decifrado;
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



