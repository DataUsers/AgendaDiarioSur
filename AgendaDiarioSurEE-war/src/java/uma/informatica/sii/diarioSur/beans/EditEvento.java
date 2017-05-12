/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.misc.GeoLoc;

/**
 *
 * @author darylfed
 */
@Named(value = "editEvento")
@RequestScoped
public class EditEvento {

    private Evento evento;
    @Inject
    private ControlAutorizacion ctrl;
    private String localizacion;
    private Date fecha;
    private boolean valido = false;

    /**
     * Creates a new instance of EditEvento
     */
    public EditEvento() {
        crearEventoPlaceholder();
        
        // obtener id de query param
        // comprobar aqui si esta el evento en la bd
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String eventoId = request.getParameter("evento");
        if(eventoId != null && eventoId.length() > 0 && Integer.parseInt(eventoId) == 10){ // id evento hardcodeado
            valido = true;
        }
        
        // si no esta en la bd, mostrar error, si lo esta, cogerlo de la bd
    }
    
    public void crearEventoPlaceholder(){
        evento = new Evento();
        evento.setIdEvento(10);
        evento.setNombre("PLACEHOLDER NOMBRE");
        evento.setDescripcion("PLACEHOLDER DESCRIPCION");
        evento.setPrecio(80);
        localizacion = "Avenida Plutarco";
        evento.setTipoEvento(Evento.Tipo.TORNEOS);
        fecha = Date.valueOf(LocalDate.now());
        evento.setNumero_entradas(400);
        evento.setOrganizador("ORGANIZADOR");
        evento.setURLOrganizador("URL ORGANIZADOR");
        evento.setURLVideos("URL VIDEO");
    }

    public String edit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        evento.setFechas(new ArrayList<>());
        evento.getFechas().add(fecha);
        evento.setGeolocalizacion(GeoLoc.obtenerCoordenada(localizacion));
        if (evento.getNombre().trim().length() == 0) {
            System.out.println("Hay un error");
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre es obligatorio", "El nombre es obligatorio"));
            return null;
        }

        System.out.println("No hay error");
        
        return "gestionEvento.xhtml?faces-redirect=true";
    }
    
    public boolean validarEvento(){
        return ctrl.sesionIniciada() && valido;
    }

    public SelectItem[] getGenderValues() {
        SelectItem[] items = new SelectItem[Evento.Tipo.values().length];
        int i = 0;
        for (Evento.Tipo g : Evento.Tipo.values()) {
            items[i++] = new SelectItem(g, g.toString().replace("_", " "));
        }
        return items;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

}
