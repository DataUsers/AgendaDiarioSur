/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import uma.informatica.sii.diarioSur.Evento;

/**
 *
 * @author darylfed
 */
@Named(value = "calificacionBean")
@RequestScoped
public class CalificacionBean implements Serializable{

    @Inject 
    private EventoBean evento;
    
    private String  titulo;          // Indica el título que el usuario ponga a su valoración
    private Integer puntuacion;      // Indica la puntuación que el usuario establezca al realizar su valoración
    private String  comentario;      // Es el propio comentario acerca del evento
    private boolean favorito;        // Si lo marca como favorito aparecerá como True, en caso contrario a False

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
    
    public String enviarCalificacion(Evento evento){
        System.out.println("Enviado una calificacion");
        System.out.println("Puntiacion: " + puntuacion);
        System.out.println("Titulo: " + titulo);
        System.out.println("Comentario: " + comentario);
        System.out.println("Evento: " + evento.getNombre());
        
        // Crear calificacion, guardar en la persistencia y asignarlo al evento
        // refrescar la pagina
        
        return null;
    }
    
    /**
     * Creates a new instance of CalificacionBean
     */
    public CalificacionBean() {
    }
    
}
