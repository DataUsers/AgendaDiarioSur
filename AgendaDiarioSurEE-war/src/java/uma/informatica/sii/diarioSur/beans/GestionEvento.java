/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioBusqueda;

/**
 *
 * @author darylfed
 */
@Named(value = "gestionEvento")
@ViewScoped
public class GestionEvento implements Serializable{

    @EJB
    private NegocioBusqueda negocio;
    
    @Inject
    private ControlAutorizacion ctrl;

    private List<String> filtrosPredeterminados;
    private List<String> filtros;
    private List<Evento> eventosMostrar;

    private String queryString;
    private Double latitud;
    private Double longitud;

    private int currentPage;
    private final int MAX_EVENTO = 5;
    private boolean hasNextPage;
    private boolean hasPrevPage;
    private String currentURI;
    private List<String> filtrosQuery;

    private String fecha;
    private String tiempo;

    

    /**
     * Creates a new instance of GestionEvento
     */
    public GestionEvento() {
        // Obtener query y etiqueta cookies
	// Si no hay query ni filtro ni cookies, cargar etiquetas predeterminadas y filtrar predeterminado
	// Si se ha hecho query, hacer query y filtrar mostrando los resultados

	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String queryString = request.getQueryString();
	System.out.println("query String en constructor: " + queryString);
	System.out.println("uri on constructor: " + request.getRequestURI());

	// obtener cookies y asignar filtros
	crearFiltroPredeterminado();
	filtros = filtrosPredeterminados;

	currentURI = "gestionEvento";

    }
    
    public void onLoad() {
	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String[] filtrosBusq = request.getParameterValues("filtrar");
	filtrosQuery = new ArrayList<>();

	System.out.println("current page: " + currentPage);
	System.out.println("latitud: " + latitud);
	System.out.println("longitud: " + longitud);
	System.out.println("query String en onload: " + queryString);
	System.out.println("current uri: " + currentURI);

	try {

	    // preparar filtros
	    List<Evento.Tipo> filtros = new ArrayList<>();
	    boolean filtrarMasVisitados = false;

	    if (filtrosBusq != null) {
		// busqueda por varios filtros
		for (String filtro : filtrosBusq) {
		    System.out.println(filtro);
		    if (filtro.equals("Mas Visitados")) {
			filtrarMasVisitados = true;
			System.out.println("tiene mas visitados");
		    } else {
			try {
			    filtrosQuery.add(filtro);
			    String filterRes = filtro.replace(" ", "_");
			    filterRes = filterRes.toUpperCase();
			    Evento.Tipo eventType = Evento.Tipo.valueOf(filterRes);
			    System.out.println("eventype: " + eventType);
			    System.out.println("filtro res: " + filterRes);
			    filtros.add(eventType);
			} catch (IllegalArgumentException e) {
			    // IGNORAR ERROR
			    Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, e);
			}
		    }
		}
	    }

	    // preparar query String
	    if (queryString == null) {
		queryString = "";
	    }

	    // Si tiene latitud y longitud
	    if (latitud != null && longitud != null) {
		// busqueda con latitud y longitud en grados
		eventosMostrar = negocio.busquedaEventos(currentPage, MAX_EVENTO, filtros, queryString, latitud, longitud);
	    } else {
		// si no tiene latitud y longitud
		eventosMostrar = negocio.busquedaEventos(currentPage, MAX_EVENTO, filtros, queryString);
	    }

	} catch (DiarioSurException ex) {
	    Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
	}

	if (eventosMostrar.size() <= MAX_EVENTO) {
	    hasNextPage = false;
	} else {
	    hasNextPage = true;
            eventosMostrar.remove(eventosMostrar.size()-1); // COmprobar
	}
	System.out.println("Has next page: " + hasNextPage);
        
        if(currentPage > 0){
            hasPrevPage = true;
        }

    }
    
    private String obtenerURIActual(){
        String res = currentURI + "?";
	try {
	    // latitud y longitud
	    if (latitud != null && longitud != null) {
		res += "latitud=" + latitud + "&longitud=" + longitud;
	    }

	    // query string
	    if (queryString != null) {
		res += "&q=" + URLEncoder.encode(queryString, "UTF-8");
	    }

	    if (filtrosQuery != null) {
		System.out.println("filtros no a nulo");
		if (filtrosQuery.isEmpty()) {
		    System.out.println("filtro vacio");
		}
		for (String filtro : filtrosQuery) {
		    System.out.println(filtro);
		    res += "&filtrar=" + URLEncoder.encode(filtro, "UTF-8");
		}
	    } else {
		System.out.println("filtros nulo");
	    }

	    System.out.println("res final de siguiente pagina: " + res);

	} catch (UnsupportedEncodingException ex) {
	    Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
	}
        
        return res;
    }

    public String paginaSiguiente() {
        ++currentPage;
	String res = obtenerURIActual();
	return res + "&commentPage=" + currentPage + "&faces-redirect=true";
    }
    
    public String paginaAnterior(){
        --currentPage;
        String res = obtenerURIActual();
	return res + "&commentPage=" + currentPage + "&faces-redirect=true";
    }

    public boolean comprobarAutorizacion() {
        boolean autorizado = false;

        if (ctrl.sesionIniciada() && (ctrl.getUsuario().getTipoUsuario() == Usuario.tipoUsuario.ADMINISTRADOR
                || ctrl.getUsuario().getTipoUsuario() == Usuario.tipoUsuario.PERIODISTA)) {
            autorizado = true;
        }

        return autorizado;
    }
    
    public boolean comprobarAdmin(){
        boolean esAdmin = false;
        
        esAdmin = ctrl.sesionIniciada() && (ctrl.getUsuario().getTipoUsuario() == Usuario.tipoUsuario.ADMINISTRADOR);
        
        return esAdmin;
    }

    public String guardarCambios(Evento evento) {
        // realizar los cambios en la BD y despues refrescar la pagina
        System.out.println("Modificar evento");
        System.out.println("Nuevo nombre: " + evento.getNombre());
        System.out.println("Fecha: " + fecha);
        System.out.println("Tiempo: " + tiempo);
        
        try {
            negocio.modificarEvento(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(GestionEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String res = obtenerURIActual();

        return res + "&faces-redirect=true"; // test
    }
    
    public String borrarEvento(Evento evento){
        // eliminar evento de la base de datos y despues refrescar la pagina
        System.out.println("Se va a borrar un evento");
        System.out.println("Evento nombre: " + evento.getNombre());
        
        try {
            negocio.eliminarEvento(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(GestionEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String res = obtenerURIActual();
        
        return res + "&faces-redirect=true";
    }

    private void crearFiltroPredeterminado() {
        filtrosPredeterminados = new ArrayList<>();
        filtrosPredeterminados.add("Todos");
        filtrosPredeterminados.add("Lugar");
        filtrosPredeterminados.add("Fecha");
        filtrosPredeterminados.add("Tipos");
        filtrosPredeterminados.add("Duracion");
        filtrosPredeterminados.add("Coste");
    }

    public String placeholderFecha() {
        Random rnd = new Random(System.currentTimeMillis());

        // Crear dia random
        LocalDateTime date = LocalDateTime.now();
        date =  date.plusDays(rnd.nextInt(10));
        
        // Formatear la salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String outDate = date.format(formatter);

        return outDate;
    }

    public String placeholderTiempo() {
        Random rnd = new Random(System.currentTimeMillis());

        // Crear dia random
        LocalDateTime date = LocalDateTime.now();
        date = date.plusHours(rnd.nextInt(24));

        // Formatear la salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String outTime = date.format(formatter);

        return outTime;
    }

    public List<String> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<String> filtros) {
        this.filtros = filtros;
    }

    public List<Evento> getEventosMostrar() {
        return eventosMostrar;
    }

    public void setEventosMostrar(List<Evento> eventosMostrar) {
        this.eventosMostrar = eventosMostrar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrevPage() {
        return hasPrevPage;
    }

    public void setHasPrevPage(boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }
    
    

}
