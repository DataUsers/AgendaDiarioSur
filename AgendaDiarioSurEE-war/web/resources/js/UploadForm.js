/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validate(){
//    alert("Metodo validateBasic invocado");
     var login=document.getElementById('nombre').value;
      if(login.trim().length<=0){
        alert("El nombre de usuario esta vacio");
        document.getElementById('nombre').focus();
        return false;
	}
	return true;
}