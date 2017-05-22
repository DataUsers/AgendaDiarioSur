/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author darylfed
 */
@Stateless
public class CalificacionNegocioImpl implements CalificacionNegocio {

    @PersistenceContext
    private EntityManager em;
    
    // Inyectar Bean de control de usuario
    
    @Override
    public void insertarCalificacion(CalificacionEvento calificacion) throws DiarioSurException {
        
        compruebaLogin(calificacion.getUsuarios()); // QUITAR DESPUES
        
        Evento eventoBusc = em.find(Evento.class, calificacion.getEventos().getIdEvento());
        
        if(eventoBusc == null){
            throw new EventoNoEncException();
        }
        
        em.persist(calificacion);
    }
    
    // QUITAR DESPUES
    @Override
    public void compruebaLogin(Usuario usuario) throws DiarioSurException {
        // TEST, QUITAR DESPUES
        
        Usuario userFind = em.find(Usuario.class, usuario.getId());
        
        if(userFind == null){
            throw new DiarioSurException();
        }
        
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
