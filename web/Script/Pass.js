/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function CambioPass()
{
    var pass1=document.getElementById("pass1").value;
    var passn=document.getElementById("passn").value;
    var passn2=document.getElementById("passn2").value;
    console.log("fdfsd" +passn);
    console.log(passn2);
    if(passn===passn2)
    {
    var a=pedirPublicaGeneral2();
    var pass1c=cifrarpublica(a , pass1);
    var passnc=cifrarpublica(a , passn);
    var result=cambioPassPeticion(pass1c , passnc);
    if(result==="Bien")
    {
        alert("Cambio realizado");
        window.location="empleado.jsp";
    }
    else
    {
        alert("Contaseña incorrecta")
    }
    }
    else
    {
        alert("las contraseñas no coinciden");
    }
}
function cambioPassPeticion(pass1c , passnc)
{
     var a;
     var formData = new FormData();
     formData.append("pass1", pass1c);
     formData.append("passn", passnc);
     $.ajax({
            url: 'CambiarPass',
            type: 'POST',
            data: formData,
            async:false,
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            success: function (data) {
               a=data;
            },
            error: function () {
                alert("Archivo invalido");
            }
        });
        return a;
}
function pedirPublicaGeneral2()
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
                alert("error-publica");
            }
        });
       return a; 
}
