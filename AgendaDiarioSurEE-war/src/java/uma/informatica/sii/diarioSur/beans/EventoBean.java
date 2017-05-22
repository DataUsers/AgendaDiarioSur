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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Publicidad;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.EventoNoEncException;
import uma.informatica.sii.diarioSur.negocio.NegocioCalificacion;
import uma.informatica.sii.diarioSur.negocio.NegocioEvento;

/**
 *
 * @author darylfed
 */
@Named(value = "evento")
@RequestScoped
public class EventoBean implements Serializable {

    // Dependencias
    @Inject
    private ControlAutorizacion ctrl;

    @EJB
    private NegocioEvento negocio;

    @EJB
    private NegocioCalificacion negocioCal;

    // Para evento
    private Publicidad publicidad;
    private Evento evento;
    private List<String> imagenes;
    private List<CalificacionEvento> calificaciones;
    private MapModel model = new DefaultMapModel();
    private String currentUrl;
    private boolean validado;
    private String eventId;

    // Para calificaciones
    private CalificacionEvento calificacion;
    private int commentPage;
    private final int MAX_CALIFICACIONES = 5;
    private UploadedFile imagen;
    private UIComponent imageComponent;

    /**
     * Creates a new instance of Evento
     */
    public EventoBean() {
        calificacion = new CalificacionEvento();
        System.out.println("Se ha creado el calificacion bean");
    }

    public void onLoad() {

        // Validar si el id del evento existe
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        // Hardcoded
        validado = false;
        eventId = request.getParameter("evento");
        currentUrl = request.getRequestURL().toString();
        if (eventId != null) {
            // Crear current url
            currentUrl += "?evento=" + eventId;
            int id = 0;
            System.out.println("Evento id " + eventId);
            try {
                id = Integer.parseInt(eventId);

                if (negocio == null) {
                    System.out.println("Negocio a null");
                }

                evento = negocio.findEvento(id);
                System.out.println("ENCONTRADO");
                validado = true;

                // Setear marcador del mapa e imagenes placeholder
                String[] coord = evento.getGeolocalizacion().split(",");
                Double latitud = Double.parseDouble(coord[0]);
                Double longitud = Double.parseDouble(coord[1]);
                model.addOverlay(new Marker(new LatLng(latitud, longitud), evento.getNombre()));

                imagenes = new ArrayList<>();
                if (evento.getImagenes().length > 0) {
                    for (String img : evento.getImagenes()) {
                        imagenes.add(img);
                    }
                }

                // Settear calificaciones
                calificaciones = negocio.getCalificaciones(0, MAX_CALIFICACIONES, evento);

            } catch (NumberFormatException e) {
                /* TODO http://stackoverflow.com/questions/2451154/invoke-jsf-managed-bean-action-on-page-load*/

                System.out.println("NO ENCONTRADO, FORMAT NUMBER");
                validado = false;
            } catch (DiarioSurException e) {
                System.out.println("no ENCONTRADO, DIARIOSUR ");
                validado = false;
            }
        }
    }

    private void createPlaceholders() {
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

        /*
        calificaciones = new ArrayList<CalificacionEvento>();
        for (int i = 0; i < 5; ++i) {
            CalificacionEvento calificacion = new CalificacionEvento("PACO", "UNA DESCRIPCION", i);
            calificacion.setFavorito(true);
            calificaciones.add(calificacion);
        }
        evento.setCalificaciones(calificaciones);
         */
 /*
        // Crear fechas ficticias
        List<Date> fechas = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Date date = new Date(System.currentTimeMillis());
            fechas.add(date);
            //System.out.println("Date: " + date.toLocalDate().toString());
        }
        evento.setFechas(fechas);
         */
        // Setear marcador del mapa, compobar errores aqui
        String[] coord = evento.getGeolocalizacion().split(",");
        Double latitud = Double.parseDouble(coord[0]);
        Double longitud = Double.parseDouble(coord[1]);
        model.addOverlay(new Marker(new LatLng(latitud, longitud), evento.getNombre()));

        imagenes = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            imagenes.add("image" + i + ".jpg");
        }
    }

    public String numeroFavoritos() {
        long nCalificacion = 0;

        try {
            // TODO
            nCalificacion = negocio.obtenerNumFav(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(EventoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Long.toString(nCalificacion);
    }
    
    public String enviarCalificacion() {
        /*
        System.out.println("Enviado una calificacion");
        System.out.println("Puntiacion: " + puntuacion);
        System.out.println("Titulo: " + titulo);
        System.out.println("Comentario: " + comentario);
        System.out.println("Evento: " + evento.getNombre());
        */
        if (imagen != null) {
            System.out.println("Hay una imagen");
        }

        if (ctrl.sesionIniciada()) {
            // Crear calificacion, guardar en la persistencia y asignarlo al evento
            // Guardar en la base de datos y redirigir al evento

            calificacion.setEventos(evento);
            calificacion.setUsuarios(ctrl.getUsuario()); // CTRL DEBERIA DE DEVOLVER BIEN AL USER
            
            System.out.println("Sesion iniciada, enviando calificacion");

            return null; // hacer feedback al usuario
        } else {
            // Notificar que necesitainiciar sesion
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(formulario.getClientId(), new FacesMessage("Tienes que iniciar sesion para enviar una calificacion"));
            System.out.println("Sesion no iniciada");
            return null; // Hacer feedback al usuario
        }

    }
    
    public String marcarFavorito() {
        // Comprobar sesion, si esta logueado, marcar favorito
        // si no, enviar a la pagina de login
        if(evento == null){
            System.out.println("Esta a null");
            System.out.println("id: " + eventId);
        }
        String eventId = evento.getIdEvento().toString();
        System.out.println("Marcar favorito al evento: " + eventId);   

        if (ctrl.sesionIniciada()) {
            // Crear calificacion como favorito y guardarlo en la base de datos
            System.out.println("Sesion iniciada, se va a marcar favorito");
            
            try {
                calificacion = new CalificacionEvento(); // Crear nueva calificacion por si acaso
                calificacion.setEventos(evento);
                calificacion.setUsuarios(ctrl.getUsuario()); // CTRL.GETUSUARIO DEBERIA DE FUNCIONAR
                calificacion.setFavorito(true);
                
                negocioCal.insertarCalificacion(calificacion);
            } catch (DiarioSurException ex) {
                // CAMBIAR
                Logger.getLogger(CalificacionBean.class.getName()).log(Level.SEVERE, null, ex);
                return null; // Refrescar pagina
            }

            return null; // refrescar pagina
        } else {
            System.out.println("NO iniciada sesion");
            // Mostrar que no puede dar a favoritos a menos que este iniciado de sesion
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(favoritos.getClientId(), new FacesMessage("Tienes que iniciar sesion para dar a favoritos"));

            return null; // refrescar pagina
        }
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

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public int getCommentPage() {
        return commentPage;
    }

    public void setCommentPage(int commentPage) {
        this.commentPage = commentPage;
    }

    public CalificacionEvento getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(CalificacionEvento calificacion) {
        this.calificacion = calificacion;
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }

    public UIComponent getImageComponent() {
        return imageComponent;
    }

    public void setImageComponent(UIComponent imageComponent) {
        this.imageComponent = imageComponent;
    }
}
