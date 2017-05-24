/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
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
@ViewScoped
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
    private String currentURI;
    private boolean validado;
    private String eventId;
    private boolean hasGeo;

    // Para calificaciones
    private CalificacionEvento calificacion;
    private int commentPage;
    private boolean hasMoreComments;
    private final int MAX_CALIFICACIONES = 5;
    private UploadedFile imagen;
    private UIComponent imageComponent;

    /**
     * Creates a new instance of Evento
     */
    public EventoBean() {
        calificacion = new CalificacionEvento();
        hasMoreComments = false;
        commentPage = 0;
    }

    public void onLoad() {

        // Validar si el id del evento existe
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        // Hardcoded
        validado = false;
        eventId = request.getParameter("evento");
        currentUrl = request.getRequestURL().toString();
        currentURI = "evento";

        if (eventId != null) {
            // Crear current url
            currentUrl += "?evento=" + eventId;
            currentURI += "?evento=" + eventId;

            System.out.println("current URL: " + currentUrl);
            System.out.println("current URI: " + currentURI);

            int id = 0;
            try {
                id = Integer.parseInt(eventId);

                evento = negocio.findEvento(id);
                validado = true;

                // Setear marcador del mapa e imagenes placeholder
                // Comprobar si tiene geolocalizacion
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
                calificaciones = negocio.getCalificaciones(commentPage, MAX_CALIFICACIONES, evento);

                if (calificaciones.size() < MAX_CALIFICACIONES) {
                    hasMoreComments = false;
                } else {
                    hasMoreComments = true;
                }

            } catch (NumberFormatException e) {
                validado = false;
            } catch (DiarioSurException e) {
                validado = false;
            }
        }
    }

    public String nextCommentPage() {
        ++commentPage;
        return currentURI + "&commentPage=" + commentPage + "&faces-redirect=true";
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
            nCalificacion = negocio.obtenerNumFav(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(EventoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Long.toString(nCalificacion);
    }

    public String enviarCalificacion() {

        if (ctrl.sesionIniciada()) {
            // Crear calificacion, guardar en la persistencia y asignarlo al evento
            // Guardar en la base de datos y redirigir al evento
            
            if(calificacion.getTitulo().length() == 0 || calificacion.getComentario().length() == 0){
                return null;
            }

            if (imagen != null) {
                System.out.println("Hay una imagen: " + imagen.getFileName());

                try {

                    System.out.println("sitio temporal: " + System.getProperty("java.io.tmpdir"));

                    // Obtener el path
                    InputStream input = imagen.getInputstream();
                    File targetFile = new File("resources" + File.separator + "imagenes" + File.separator + imagen.getFileName());
                    System.out.println("path targetFile: " + targetFile.getAbsolutePath());
                    FileOutputStream targetStream = new FileOutputStream(targetFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = input.read(buffer)) != -1) {
                        targetStream.write(buffer, 0, bytesRead);
                        System.out.println("Leidos " + bytesRead);
                    }

                    // Cerrar los streams???
                    // Settear a la calificacion el path de la imagen
                    String pathImagenCal = targetFile.getAbsolutePath();
                    System.out.println("path imagen en bd: " + pathImagenCal);
                    calificacion.setImagen(pathImagenCal);
                } catch (IOException ex) {
                    Logger.getLogger(EventoBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            calificacion.setEventos(evento);
            calificacion.setUsuarios(ctrl.getUsuario()); // CTRL DEBERIA DE DEVOLVER BIEN AL USER

            // Guardar imagen y path de la imagen
            try {
                negocioCal.insertarCalificacion(calificacion);
            } catch (DiarioSurException ex) {
                Logger.getLogger(EventoBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            currentURI += "&faces-redirect=true";
            System.out.println("currentURL: " + currentURI);

            return currentURI; // hacer feedback al usuario
        } else {
            // Notificar que necesitainiciar sesion
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(formulario.getClientId(), new FacesMessage("Tienes que iniciar sesion para enviar una calificacion"));
            return "login"; // Hacer feedback al usuario
        }

    }

    public String marcarFavorito() {
        // Comprobar sesion, si esta logueado, marcar favorito
        // si no, enviar a la pagina de login

        if (ctrl.sesionIniciada()) {
            String eventId = evento.getIdEvento().toString();

            // Crear calificacion como favorito y guardarlo en la base de datos

            try {
                calificacion = new CalificacionEvento(); // Crear nueva calificacion por si acaso
                calificacion.setEventos(evento);
                calificacion.setUsuarios(ctrl.getUsuario()); // CTRL.GETUSUARIO DEBERIA DE FUNCIONAR
                calificacion.setFavorito(true);

                negocioCal.insertarCalificacion(calificacion);
            } catch (DiarioSurException ex) {
                // CAMBIAR
                Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
                return null; // Refrescar pagina
            }

            currentURI += "&faces-redirect=true";

            return currentURI; // refrescar pagina
        } else {
            System.out.println("NO iniciada sesion");
            // Mostrar que no puede dar a favoritos a menos que este iniciado de sesion
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(favoritos.getClientId(), new FacesMessage("Tienes que iniciar sesion para dar a favoritos"));

            return "login"; // refrescar pagina
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

    public String getCurrentURI() {
        return currentURI;
    }

    public void setCurrentURI(String currentURI) {
        this.currentURI = currentURI;
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

    public boolean isHasMoreComments() {
        return hasMoreComments;
    }

    public void setHasMoreComments(boolean hasMoreComments) {
        this.hasMoreComments = hasMoreComments;
    }

    public boolean isHasGeo() {
        return hasGeo;
    }

    public void setHasGeo(boolean hasGeo) {
        this.hasGeo = hasGeo;
    }

    public UIComponent getImageComponent() {
        return imageComponent;
    }

    public void setImageComponent(UIComponent imageComponent) {
        this.imageComponent = imageComponent;
    }
}
