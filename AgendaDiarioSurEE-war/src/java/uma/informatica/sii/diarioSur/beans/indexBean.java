/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioEvento;

/**
 *
 * @author pablo
 */
@Named(value = "index")
@RequestScoped
public class indexBean {
    
    @EJB
    private NegocioEvento negocio;
    
    
    private List<Evento> eventos;
    
    @PostConstruct
    public void init() {
        
        try {
            eventos = negocio.obtenerEventos(7);
            
        } catch (DiarioSurException ex) {
            Logger.getLogger(indexBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
    
    
}
