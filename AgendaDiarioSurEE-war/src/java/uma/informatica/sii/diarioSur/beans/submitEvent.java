/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import uma.informatica.sii.diarioSur.misc.GeoLoc;
import java.sql.Date;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author francis
 */
@Named(value = "submitEvent")
@RequestScoped
public class submitEvent {

	private Evento evento;
	@Inject
	private ControlAutorizacion ctrl;
	private String localizacion;
	private Date fecha;

	/**
	 * Creates a new instance of Login
	 */
	public submitEvent() throws IOException {
		evento = new Evento();
	}

	public Evento getEvento() {
		return evento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public SelectItem[] getGenderValues() {
		SelectItem[] items = new SelectItem[Evento.Tipo.values().length];
		int i = 0;
		for (Evento.Tipo g : Evento.Tipo.values()) {
			items[i++] = new SelectItem(g, g.toString().replace("_", " "));
		}
		return items;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String upload() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		evento.setFechas(new ArrayList<>());
		evento.getFechas().add(fecha);
		evento.setGeolocalizacion(GeoLoc.obtenerDireccion(localizacion));
		if (evento.getNombre().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre es obligatorio", "El nombre es obligatorio"));
			return null;
		}

		return "index.xhtml?faces-redirect=true";
	}

}
