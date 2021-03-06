package uma.informatica.sii.diarioSur.misc;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author darylfed
 */
public class GeoLocUtils {

    public static void main(String[] args) {
       obtenerCoordenadas("");

    }

    /***
     * Obtiene las coordenadas de la direccion pasada como parametro
     * este metodo solo devuelve el primer resultado de la busqueda por direccion
     * puede llegar a ser impreciso
     * @param direccion usada para buscar en google maps
     * @return GeoLocation objeto con las coordenadas tanto en radianes como en grados
     */
    public static GeoLocation obtenerCoordenadas(String direccion) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json";
        String key = "AIzaSyAyM4z88cdeWuj13fJZ85k8XM6BUm5hi5s";
        double latitud = 0;
        double longitud = 0;
	GeoLocation geolocation = GeoLocation.fromDegrees(0, 0);

        try {
            String queryFormated = URLEncoder.encode(direccion, "UTF-8");
            System.out.println("Query normal: " + direccion + "\nQuery formateada: " + queryFormated);

            System.out.println("Haciendo request a " + url + "?address=" + queryFormated + 
		    "&components=country:ES" +
		    "&key=" + key);

            URL urlObject = new URL(url + "?address=" + queryFormated + "&key=" + key);
            HttpsURLConnection con = (HttpsURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");

            InputStream is = con.getInputStream();
            JsonReader reader = Json.createReader(is);

            System.out.println(con.getResponseMessage());
            if (con.getResponseCode() != 200) {
                System.out.println("Error al hacer request");
                // Tirar excepcion y manejar
            }

            JsonObject res = reader.readObject();

            String statusRequest = res.getString("status");
            System.out.println("Status request: " + statusRequest);
            if (!statusRequest.equals("OK")) {
                System.out.println("Error tras hacer la request");
                // manejar error
            } else {
		
		/* Obtener latitud y longitud en grados */
                JsonArray resultados = res.getJsonArray("results");
                JsonObject firstObj = resultados.getJsonObject(0);
                JsonObject geometry = firstObj.getJsonObject("geometry");
                JsonObject location = geometry.getJsonObject("location");
                latitud = location.getJsonNumber("lat").doubleValue();
                longitud = location.getJsonNumber("lng").doubleValue();
                System.out.println("Latitud: " + latitud + " longitud: " + longitud);
		
		geolocation = GeoLocation.fromDegrees(latitud, longitud);
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GeoLocUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GeoLocUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeoLocUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return geolocation;
    }
}
