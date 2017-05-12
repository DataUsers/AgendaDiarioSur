/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var httpRequest;

function getGeo() {
    if ("geolocation" in navigator) {
        // llamada a la api de geolocalizacion
        navigator.geolocation.getCurrentPosition(geo_success, geo_error);
    } else {
        console.log('No funciona la geolocalizacion en este navegador');
        window.alert('No funciona la geolocalizacion en este navegador');
    }
}

function geo_success(position) {
    var latitudInput = document.getElementById('latitud');
    var longitudInput = document.getElementById('longitud');

    latitudInput.value = position.coords.latitude;
    longitudInput.value = position.coords.longitude;

    console.log("Posicion obtenida: latitud=" + latitudInput.value + ",longitud=" + longitudInput.value);
    // Transformar coordenada en nombre de calle
    get_geo_formated(position.coords.latitude, position.coords.longitude);
}

function geo_error(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            console.log("Usuario ha denegado la peticion de geolocalizacion");
            break;
        case error.POSITION_UNAVAILABLE:
            console.log("Informacion de geolocalizacion no disponible");
            break;
        case error.TIMEOUT:
            console.log("Peticion de geolocalizacion expirada");
            break;
        case error.UNKNOWN_ERROR:
            console.log("Error inesperado");
            break;
    }
}

function get_geo_formated(latitud, longitud) {
    httpRequest = new XMLHttpRequest();

    var apilink = 'https://maps.googleapis.com/maps/api/geocode/json';
    var key = 'AIzaSyAyM4z88cdeWuj13fJZ85k8XM6BUm5hi5s';
    var latlng = latitud + ',' + longitud;

    if (!httpRequest) {
        alert('No se puede hacer un ajax request');
        console.log('No se puede hacer un ajax request');
        return false;
    }

    var link = apilink + '?latlng=' + latlng + '&key=' + key;
    console.log('Haciendo ajax request a ' + link);

    httpRequest.onreadystatechange = actualizar_geo;
    httpRequest.open('GET', link);
    httpRequest.send();
}

function actualizar_geo() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status === 200) {
            var datos = JSON.parse(httpRequest.responseText);
            if (datos.status === "OK") {

                var direccion = datos.results[0].formatted_address;
                var elementoDireccion = document.getElementById('localizacion');
                elementoDireccion.innerHTML = direccion;

            } else {
                alert('Hubo un problema al parsear a JSON el request ajax');
                console.log('Hubo un problema al parsear a JSON el el request ajax');
            }
        } else {
            alert('Hubo un problema con request ajax');
            console.log('Hubo un problema con request ajax');
        }
    }
}

