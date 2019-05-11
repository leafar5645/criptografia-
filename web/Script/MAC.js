function generarMAC(key)
{
    var file= arraybuffer;
    return window.crypto.subtle.sign(
        "HMAC",
        key,
        file
      ).then(function(result){
        var buffer= new Uint8Array(result);
        buffer= ab2str(buffer); 
        return btoa(buffer);
    
    });
}
function generarLlaveMac()
{
   return window.crypto.subtle.generateKey(
    {
      name: "HMAC",
      hash: {name: "SHA-256"}
    },
    true,
    ["sign", "verify"]
   ).then(function(result){
        return result;
    });
}
function importKeyMAC(key)
{
    key=atob(key);
    key= str2ab(key); 
    return window.crypto.subtle.importKey(
    "raw",
    key,
    {name: 'HMAC', hash: 'SHA-256'},
    true,
    ["sign", "verify"]
  ).then(function(result){
      console.log(result);
        return result;
    });
}

async function exportarKeyMAC(key)
{
    const exported = await window.crypto.subtle.exportKey(
    "raw",
    key
  );
  var exportedKeyBuffer = new Uint8Array(exported);
  var exportedAsString = ab2str(exportedKeyBuffer);
  var exportedAsBase64 = await btoa(exportedAsString);

  return exportedAsBase64;
}

function verificarMAC(key,firma,file)
{
    firma=atob(firma);
    firma= str2ab(firma); 
    return window.crypto.subtle.verify(
    "HMAC",
    key,
    firma,
    file
  );
}

