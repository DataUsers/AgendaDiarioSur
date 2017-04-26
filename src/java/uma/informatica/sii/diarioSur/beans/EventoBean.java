/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import uma.informatica.sii.diarioSur.Evento;
import uma.informatica.sii.diarioSur.Publicidad;

/**
 *
 * @author darylfed
 */
@Named(value = "evento")
@RequestScoped
public class EventoBean implements Serializable{

    private Publicidad publicidad;
    private Evento evento; // event placeholder
    private List<String> imagenes;
    
    //@ManagedProperty("#{param.eventId}")
    private String eventId;

    /**
     * Creates a new instance of Evento
     */
    public EventoBean() {

        // Placeholders
        publicidad = new Publicidad();
        evento = new Evento();
        evento.setId(10);
        evento.setNombre("Placeholder");
        evento.setDescripcion("Placeholder");
        evento.setPrecio(20);
        
        imagenes = new ArrayList<>();
        for(int i = 0; i < 5; ++i){
            imagenes.add("image" + i + ".jpg");
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

    public void redirect() {
        FacesContext.getCurrentInstance()
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(
                        FacesContext.getCurrentInstance(), null, "index.xhtml"
                );
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public boolean validarEvento() {
        boolean validado = false;

        if (eventId != null && eventId.length() > 0) {
            // Deberia comprobar si el evento esta en la base de datos y asignar el
            // evento a la variable evento de este bean
            int id = 0;
            System.out.println("Evento id " + eventId);
            try {
                id = Integer.parseInt(eventId);
                validado = evento.getId() == id;
            } catch (NumberFormatException e) {
                validado = false;
            }
        }else{
            System.out.println("Vacio " + eventId);
        }

        return validado;
    }

}
