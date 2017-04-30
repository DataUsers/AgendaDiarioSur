/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import uma.informatica.sii.diarioSur.Evento;

/**
 *
 * @author Daryl
 */
@Named(value = "busqueda")
@RequestScoped
public class Busqueda {

    private List<String> filtrosPredeterminados;
    private List<String> filtros;
    private List<Evento> placeholderEvents;
    private List<Evento> eventosMostrar;
    
    /**
     * Creates a new instance of Busqueda
     */
    public Busqueda() {
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
    
    
    
}
