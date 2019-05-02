function verArchivos()
{
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
function BajarArchivo(ev)
{
    var nombre=ev.name;
    console.log(ev.name);
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
        //console.log(blob.size);
        var arch = decifrar(blob);
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(arch);
        link.download = nombre;
        link.click();       
      }
    };
}
function decifrar(archivo)
{
    return archivo;
}