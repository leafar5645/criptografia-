/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
async function Upload()
{
    var key=await llavesAES(); 
    console.log(key);
    var importada =await importarKey(key);
    console.log(importada);
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


