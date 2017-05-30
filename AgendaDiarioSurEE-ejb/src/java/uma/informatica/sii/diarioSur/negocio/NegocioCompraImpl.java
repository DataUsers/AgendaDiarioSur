/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

/**
 *
 * @author ismae
 */


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;

@Stateless
public class NegocioCompraImpl implements NegocioCompra {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Evento obtenerEvento(Integer id) throws DiarioSurException {
        Evento evento = em.find(Evento.class, id);
        if (evento == null) {
            throw new EventoNoEncException();
        }
        
        return evento;
    }

}