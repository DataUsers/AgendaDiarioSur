/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.UploadedFile;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.negocio.CalificacionNegocio;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;

/**
 *
 * @author darylfed
 */
@Named(value = "calificacionBean")
@RequestScoped
public class CalificacionBean implements Serializable {

    @Inject
    private ControlAutorizacion ctrl;
    
    @EJB
    private CalificacionNegocio negocio; 
    
    private CalificacionEvento calificacion;
    
    private UploadedFile imagen;
    private UIComponent imageComponent;
    //private UIComponent formulario;

    /**
     * Creates a new instance of CalificacionBean
     */
    public CalificacionBean() {
        calificacion = new CalificacionEvento();
    }

    public String enviarCalificacion(Evento evento) {
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

    public String marcarFavorito(Evento evento) {
        // Comprobar sesion, si esta logueado, marcar favorito
        // si no, enviar a la pagina de login
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
                
                negocio.insertarCalificacion(calificacion);
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

    /*
    public UIComponent getFormulario() {
        return formulario;
    }

    public void setFormulario(UIComponent formulario) {
        this.formulario = formulario;
    }
     */
}
