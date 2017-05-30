/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Stateless;
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
public class NegocioCalificacionImpl implements NegocioCalificacion {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void insertarCalificacion(CalificacionEvento calificacion) throws DiarioSurException {
        
        Evento eventoBusc = em.find(Evento.class, calificacion.getEventos().getIdEvento());
        
        if(eventoBusc == null){
            throw new EventoNoEncException();
        }
        
        // Guardar calificacion
        em.persist(calificacion);
        
        // Actualizar las relaciones
        // Usuario
        Usuario userFound = em.find(Usuario.class, calificacion.getUsuarios().getEmail());
        userFound.getCalificaciones().add(calificacion);
        em.merge(userFound);
        
        // Evento
        eventoBusc.getCalificaciones().add(calificacion);
        em.merge(eventoBusc);
        
    }
    
}
