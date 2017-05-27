/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioEvento;
import uma.informatica.sii.diarioSur.negocio.NegocioLogin;

/**
 *
 * @author darylfed
 */
@Named(value = "paginaTestBean")
@RequestScoped
public class PaginaTestBean {

    @EJB
    private NegocioLogin negocio;

    @EJB
    private NegocioEvento negocioEvento;

    private String nombre;
    private String contrasena;
    private String dni;

    private String email;

    private Evento evento;
    
    private Evento.Tipo[] tiposEvento = { Evento.Tipo.CONCIERTOS, Evento.Tipo.DESFILES, Evento.Tipo.EXPOSICIONES,
					    Evento.Tipo.FERIAS, Evento.Tipo.CINES};

    /**
     * Creates a new instance of PaginaTestBean
     */
    public PaginaTestBean() {
    }

    public String crearUsuario() throws DiarioSurException {
        negocio.crearUsuario(nombre, contrasena, email, dni);
        return null;
    }

    public String obtenerUsuario() throws DiarioSurException {
        Usuario user = negocio.obtenerUsuario(email);
        System.out.println("Usuario obtenido: " + user.getNombre());
        System.out.println("Usuario obtenido contrasena: " + user.getContrasena());
        System.out.println("Usuario obtenido email: " + user.getEmail());

        return null;
    }

    public String crearEvento() {
        Random rnd = new Random(System.currentTimeMillis());
	
	evento = new Evento();
        evento.setTipoEvento(tiposEvento[rnd.nextInt(5)]);
	
	evento.setNombre("Placeholder " + evento.getTipoEvento() + " " + rnd.nextInt(50));
        evento.setDescripcion("Placeholder magico "+ rnd.nextInt(50));
        evento.setPrecio(rnd.nextInt(50));
        evento.setGeolocalizacion("36.714040, -4.433475");
        evento.setOrganizador("OrganizadorNombre " + rnd.nextInt(50));
        evento.setURLOrganizador("http://127.0.0.1:8080");
        
        // Crear fechas ficticias
        List<Date> fechas = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Date date = new Date(System.currentTimeMillis());
            fechas.add(date);
            //System.out.println("Date: " + date.toLocalDate().toString());
        }
        evento.setFechas(fechas);
        
        // Test images
        String[] imagenes = new String[4];
        imagenes[0] = "http://i.imgur.com/93vHumH.png";
        imagenes[1] = "http://i.imgur.com/H1SW1Rj.png";
        imagenes[2] = "http://i.imgur.com/pojHJNc.jpg";
	imagenes[3] = "http://i.imgur.com/cZK9lZP.png";
        
        evento.setImagenes(imagenes);

	System.out.println("Tpo de evento generado: " + evento.getTipoEvento());
	
        /*
            // Setear marcador del mapa, compobar errores aqui
            String[] coord = evento.getGeolocalizacion().split(",");
            Double latitud = Double.parseDouble(coord[0]);
            Double longitud = Double.parseDouble(coord[1]);
            model.addOverlay(new Marker(new LatLng(latitud, longitud), evento.getNombre()));
            
            imagenes = new ArrayList<>();
            for (int i = 0; i < 5; ++i) {
            imagenes.add("image" + i + ".jpg");
            }
         */
        
        try {

            negocioEvento.insertarEvento(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(PaginaTestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
