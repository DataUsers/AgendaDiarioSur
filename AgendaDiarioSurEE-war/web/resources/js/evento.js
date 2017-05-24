/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validate(){
    var title = document.getElementById('comentario:title').length;
    var text = document.getElementById('comentario:texto').length;
    
    if(title.trim() <= 0 || text.trim() <= 0){
        return false;
    }
    
    return true;
    
}

