/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function shareFacebook(id) {
    var shareUrl = "https://www.facebook.com/sharer/sharer.php?u=127.0.0.1:8080/AgendaDiarioSur/faces/evento.xhtml?evento=" + id;
    var title = 'Compartir en Facebook';

    share(shareUrl, title);
    
    return false;
}

function shareTwitter(url) {
    var text = "Echa un vistazo a este evento! " + url;
    var shareUrl = "https://twitter.com/intent/tweet?text=" + text;
    var title = 'Compartir en Twitter';
 
    share(shareUrl, title);

    return false;
}

function share(url, title) {
    // Fix de posicion
    var w = 580;
    var h = 470;

    var dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left;
    var dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 3) - (h / 3)) + dualScreenTop;

    var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    if (newWindow) {
	newWindow.focus();
    }
}
