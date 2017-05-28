/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author darylfed
 */
@Stateless
public class NegocioEventoImpl implements NegocioEvento {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Evento findEvento(Integer idEvento) throws DiarioSurException {
        Evento eventFound = em.find(Evento.class, idEvento);

        if (eventFound == null) {
            throw new EventoNoEncException();
        }

        return eventFound;
    }

    @Override
    public void insertarEvento(Evento evento) throws DiarioSurException {

        try {
            em.persist(evento);
            System.out.println("EVento nuevo creado id: " + evento.getIdEvento());
        } catch (EntityExistsException e) {
            throw new EventoExistenteException();
        }

        System.out.println("Evento guardado en la BD");
    }

    @Override
    public Long obtenerNumFav(Evento evento) throws DiarioSurException {
        Evento eventoFound = em.find(Evento.class, evento.getIdEvento());

        if (eventoFound == null) {
            throw new EventoNoEncException();
        }

        Query query = em.createNamedQuery("findFavoritos");
        query.setParameter("idEvento", evento.getIdEvento());

        //query.setFirstResult(0) // ESTO PUEDE SER UTIL 
        long count = (Long) query.getSingleResult();

        return count;
    }

    @Override
    public List getCalificaciones(int pagina, int maxCalificaciones, Evento evento) throws DiarioSurException {

        Evento eventoFound = em.find(Evento.class, evento.getIdEvento());

        if (eventoFound == null) {
            throw new EventoNoEncException();
        }

        Query query = em.createNamedQuery("findCalificaciones");
        query.setParameter("idEvento", evento.getIdEvento());

        query.setFirstResult(pagina * maxCalificaciones);
        query.setMaxResults(maxCalificaciones);

        return query.getResultList();
    }

    @Override
    public List obtenerEventos(int maxResult) throws DiarioSurException {

        Query query = em.createQuery("SELECT e from Evento e");
        query.setMaxResults(maxResult);

        return query.getResultList();
    }

    @Override
    public List queryEventos(String q, String filtro, int pagina, int maxResult) throws DiarioSurException {

        Query query = em.createNamedQuery("queryBusqueda");
        query.setParameter("query", q);
        query.setParameter("filtro", filtro);

        query.setMaxResults(maxResult);
        query.setFirstResult(pagina * maxResult);

        return query.getResultList();
    }

    @Override
    public List queryEventos(String q, String filtro, String latitud, String longitud, int pagina, int maxResult) throws DiarioSurException {
        
        Query query = em.createNamedQuery("queryBusquedaGeo");
        query.setParameter("query", q);
        query.setParameter("filtro", filtro);

        query.setMaxResults(maxResult);
        query.setFirstResult(pagina * maxResult);

        return query.getResultList();
    }

}
