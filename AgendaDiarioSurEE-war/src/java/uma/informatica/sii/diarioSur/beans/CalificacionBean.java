/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.UploadedFile;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author darylfed
 */
@Named(value = "calificacionBean")
@RequestScoped
public class CalificacionBean implements Serializable {

    @Inject
    private ControlAutorizacion ctrl;

    private String titulo;          // Indica el título que el usuario ponga a su valoración
    private Integer puntuacion;      // Indica la puntuación que el usuario establezca al realizar su valoración
    private String comentario;      // Es el propio comentario acerca del evento
    private boolean favorito;        // Si lo marca como favorito aparecerá como True, en caso contrario a False
    private UploadedFile imagen;
    private UIComponent imageComponent;
    //private UIComponent formulario;

    /**
     * Creates a new instance of CalificacionBean
     */
    public CalificacionBean() {
    }

    public String enviarCalificacion(Evento evento) {
        System.out.println("Enviado una calificacion");
        System.out.println("Puntiacion: " + puntuacion);
        System.out.println("Titulo: " + titulo);
        System.out.println("Comentario: " + comentario);
        System.out.println("Evento: " + evento.getNombre());
        if (imagen != null) {
            System.out.println("Hay una imagen");
        }

        if (ctrl.sesionIniciada()) {
            // Crear calificacion, guardar en la persistencia y asignarlo al evento
            // Guardar en la base de datos y redirigir al evento

            System.out.println("Sesion iniciada, enviando calificacion");

            return "evento.xhtml?faces-redirect=true&includeViewParams=true&evento=" + evento.getIdEvento();
        } else {
            // Notificar que necesitainiciar sesion
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(formulario.getClientId(), new FacesMessage("Tienes que iniciar sesion para enviar una calificacion"));
            System.out.println("Sesion no iniciada");
            return "evento.xhtml?faces-redirect=true&includeViewParams=true&evento=" + evento.getIdEvento();
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

            return "evento?faces-redirect=true&includeViewParams=true&evento=" + eventId;
        } else {
            System.out.println("NO iniciad sesion");
            // Mostrar que no puede dar a favoritos a menos que este iniciado de sesion
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(favoritos.getClientId(), new FacesMessage("Tienes que iniciar sesion para dar a favoritos"));

            return "evento?faces-redirect=true&includeViewParams=true&evento=" + eventId;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
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
