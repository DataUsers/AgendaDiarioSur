/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
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

        calificaciones = new ArrayList<CalificacionEvento>();
        for (int i = 0; i < 5; ++i) {
            calificaciones.add(new CalificacionEvento("PACO", "UNA DESCRIPCION", i));
        }
        evento.setCalificaciones(calificaciones);
        
        // Crear fechas ficticias
        List<Date> fechas = new ArrayList<>();
        for(int i = 0; i < 5; ++i){
            Date date = new Date(System.currentTimeMillis());
            fechas.add(date);
            System.out.println("Date: " + date.toLocalDate().toString());
        }
        evento.setFechas(fechas);
        
        imagenes = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
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

    public List<CalificacionEvento> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEvento> calificaciones) {
        this.calificaciones = calificaciones;
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
                validado = evento.getIdEvento() == id;
            } catch (NumberFormatException e) {
                validado = false;
            }
        } else {
            System.out.println("Vacio " + eventId);
        }

        return validado;
    }

}
