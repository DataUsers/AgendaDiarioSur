/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validateBasic(){
     var login=document.getElementById('j_idt6:nombre').value;
     var pass=document.getElementById('j_idt6:pass').value;
      if(login.trim().length<=0){
        alert("El nombre de usuario esta vacio");
        document.getElementById('j_idt6:nombre').focus();
        return false;
    }
    
   if(pass.trim().length<=0){
        alert("La password esta vacia");
        document.getElementById('j_idt6:pass').focus();
        return false;
    }
    return true;
}

function validate(){
    var pass=document.getElementById('j_idt6:pass').value;
    var email=document.getElementById('j_idt6:email').value;
    var nombre=document.getElementById('j_idt6:nombre').value;
    var apellido=document.getElementById('j_idt6:apellido').value;
    
       if(nombre.trim().length<=0){
        alert("El campo nombre esta vacio");
        document.getElementById('j_idt6:nombre').focus();
        return false;
    }
      if(apellido.trim().length<=0){
        alert("El campo apellido esta vacio");
        document.getElementById('j_idt6:apellido').focus();
        return false;
    }
   
    
   if(pass.trim().length<=0){
        alert("La password esta vacia");
        document.getElementById('j_idt6:pass').focus();
        return false;
    }

     if(!validateEmail(email)){
        alert("El email no es correcto");
        document.getElementById('j_idt6:email').focus();
        return false;
    }

    return true;
   
}