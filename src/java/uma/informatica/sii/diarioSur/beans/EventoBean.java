/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import uma.informatica.sii.diarioSur.CalificacionEvento;
import uma.informatica.sii.diarioSur.Evento;
import uma.informatica.sii.diarioSur.Publicidad;
import uma.informatica.sii.diarioSur.Usuario;

/**
 *
 * @author darylfed
 */
@Named(value = "evento")
@RequestScoped
public class EventoBean implements Serializable {

    private Publicidad publicidad;
    private Evento evento; // event placeholder
    private List<String> imagenes;
    private List<CalificacionEvento> calificaciones;
    private MapModel model = new DefaultMapModel();
    private String currentUrl;

    //@ManagedProperty("#{param.eventId}")
    private String eventId;

    /**
     * Creates a new instance of Evento
     */
    public EventoBean() {

	// Placeholders
	publicidad = new Publicidad();
	evento = new Evento();
	evento.setIdEvento(10);
	evento.setNombre("Placeholder");
	evento.setDescripcion("Placeholder");
	evento.setPrecio(20);
	evento.setGeolocalizacion("36.714040, -4.433475");
	evento.setOrganizador("OrganizadorNombre");
	evento.setURLOrganizador("http://127.0.0.1:8080");

	calificaciones = new ArrayList<CalificacionEvento>();
	for (int i = 0; i < 5; ++i) {
	    CalificacionEvento calificacion = new CalificacionEvento("PACO", "UNA DESCRIPCION", i);
	    calificacion.setFavorito(true);
	    calificaciones.add(calificacion);
	}
	evento.setCalificaciones(calificaciones);

	// Crear fechas ficticias
	List<Date> fechas = new ArrayList<>();
	for (int i = 0; i < 5; ++i) {
	    Date date = new Date(System.currentTimeMillis());
	    fechas.add(date);
	    //System.out.println("Date: " + date.toLocalDate().toString());
	}
	evento.setFechas(fechas);

	// Setear marcador del mapa, compobar errores aqui
	String[] coord = evento.getGeolocalizacion().split(",");
	Double latitud = Double.parseDouble(coord[0]);
	Double longitud = Double.parseDouble(coord[1]);
	model.addOverlay(new Marker(new LatLng(latitud, longitud), evento.getNombre()));

	imagenes = new ArrayList<>();
	for (int i = 0; i < 5; ++i) {
	    imagenes.add("image" + i + ".jpg");
	}

	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	// Hardcoded
	eventId = request.getParameter("evento");
	currentUrl = request.getRequestURL().toString() + "?evento=" + eventId;
    }

    public void redirect() {
	FacesContext.getCurrentInstance()
		.getApplication()
		.getNavigationHandler()
		.handleNavigation(
			FacesContext.getCurrentInstance(), null, "index.xhtml"
		);
    }

    public boolean validarEvento() {
	boolean validado = false;

	if (eventId != null && eventId.length() > 0) {
	    // Deberia comprobar si el evento esta en la base de datos y asignar el
	    // evento a la variable evento de este bean
	    int id = 0;
	    //System.out.println("Evento id " + eventId);
	    try {
		id = Integer.parseInt(eventId);
		validado = evento.getIdEvento() == id;
	    } catch (NumberFormatException e) {
		validado = false;
	    }
	} else {
	    //System.out.println("Vacio " + eventId);
	}

	return validado;
    }

    public String numeroFavoritos() {
	int nCalificacion = 0;
	for (CalificacionEvento calificacion : calificaciones) {
	    if (calificacion.isFavorito()) {
		++nCalificacion;
	    }
	}

	return Integer.toString(nCalificacion);
    }

    public String marcarFavorito() {
	// Comprobar sesion, si esta logueado, marcar favorito
	// si no, enviar a la pagina de login
	System.out.println("Marcar favorito");

	return "login.xhtml";
    }

    public Publicidad getPublicidad() {
	return publicidad;
    }

    public void setPublicidad(Publicidad publicidad) {
	this.publicidad = publicidad;
    }

    public Evento getEvento() {
	return evento;
    }

    public void setEvento(Evento evento) {
	this.evento = evento;
    }

    public String getEventId() {
	return eventId;
    }

    public void setEventId(String eventId) {
	this.eventId = eventId;
    }

    public List<String> getImagenes() {
	return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
	this.imagenes = imagenes;
    }

    public List<CalificacionEvento> getCalificaciones() {
	return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEvento> calificaciones) {
	this.calificaciones = calificaciones;
    }

    public MapModel getModel() {
	return model;
    }

    public void setModel(MapModel model) {
	this.model = model;
    }

    public String getCurrentUrl() {
	return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
	this.currentUrl = currentUrl;
    }

}
