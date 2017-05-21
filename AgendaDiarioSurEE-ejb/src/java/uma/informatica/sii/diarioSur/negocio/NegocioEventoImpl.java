/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author darylfed
 */
@Stateless
public class NegocioEventoImpl implements NegocioEvento {

    @PersistenceContext
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Evento findEvento(long idEvento) throws DiarioSurException{
        Evento eventFound = em.find(Evento.class, idEvento);
        
        if(eventFound == null){
            throw new EventoNoEncException();
        }
        
        return eventFound;
    }
    
}
