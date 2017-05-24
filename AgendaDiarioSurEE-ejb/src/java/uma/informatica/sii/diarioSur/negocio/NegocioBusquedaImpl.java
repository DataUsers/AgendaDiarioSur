/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author darylfed
 */
@Stateless
public class NegocioBusquedaImpl implements NegocioBusqueda {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List obtenerEventos(int pagina, int maxEventos) throws DiarioSurException {
        
        Query query = em.createQuery("Select e from Evento e");
        
        query.setFirstResult(pagina * maxEventos);
        query.setMaxResults(maxEventos);
        
        return query.getResultList();
    }
    
}
