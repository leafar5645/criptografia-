//retorna un keyObject
function generarLlavesRSA()
{
    var private_key_object = null; 
    var public_key_object = null; 
    var key_object=[];
    var promise_key = null;
    var crypto = window.crypto || window.msCrypto;
    
    if(crypto.subtle)
    {
        //Parameters:
        //1. Asymmetric Encryption algorithm name and its requirements
        //2. Boolean indicating extractable. which indicates whether or not the raw keying material may be exported by the application (http://www.w3.org/TR/WebCryptoAPI/#dfn-CryptoKey-slot-extractable)
        //3. Usage of the keys. (http://www.w3.org/TR/WebCryptoAPI/#cryptokey-interface-types)
        promise_key = crypto.subtle.generateKey({name: "RSA-OAEP", modulusLength: 2048, publicExponent: new Uint8Array([0x01, 0x00, 0x01]), hash: {name: "SHA-256"}}, false, ["encrypt", "decrypt"]);

        promise_key.then(function(key){
            key_object=[key.privateKey, key.publicKey];
            private_key_object = key.privateKey;
            public_key_object = key.publicKey;
        });
        promise_key.catch = function(e){
            console.log(e.message);
        }
    }
    else
    {
        alert("Cryptography API not Supported");
    }
    return key_object;
}

