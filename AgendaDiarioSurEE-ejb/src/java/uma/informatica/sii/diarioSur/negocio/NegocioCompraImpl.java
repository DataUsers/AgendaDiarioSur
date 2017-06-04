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
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uma.informatica.sii.diarioSur.entidades.EntradaEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;

@Stateless
public class NegocioCompraImpl implements NegocioCompra {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void generarEntradas(Evento evento, Integer numEntradasSeleccionadas, Usuario usuario, String formaPago) throws DiarioSurException {
        Evento eventFound = em.find(Evento.class, evento.getIdEvento());
        if (eventFound == null) {
            throw new EventoNoEncException();
        }

        eventFound.setNumero_entradas(eventFound.getNumero_entradas() - numEntradasSeleccionadas);
        List<EntradaEvento> entradas = eventFound.getEntradas();
        for (int num = 0; num < numEntradasSeleccionadas; num++) {
            EntradaEvento nuevaEntrada = new EntradaEvento();
            nuevaEntrada.setEvento(evento);
            nuevaEntrada.setFechaCompra(new Date());
            nuevaEntrada.setFechaValidez(evento.getFechas().get(0));
            nuevaEntrada.setFormaPago(formaPago);
            nuevaEntrada.setPrecio(numEntradasSeleccionadas * evento.getPrecio());
            nuevaEntrada.setUsuario(usuario);
            nuevaEntrada.setEvento(eventFound);
            entradas.add(nuevaEntrada);
            em.persist(nuevaEntrada);
        }
        eventFound.setEntradas(entradas);
        em.merge(eventFound);
    }

    @Override
    public Evento obtenerEvento(String id) throws DiarioSurException {

        Evento evento = em.find(Evento.class, Integer.parseInt(id));
        if (evento == null) {
            throw new EventoNoEncException();
        }

        return evento;
    }

}
