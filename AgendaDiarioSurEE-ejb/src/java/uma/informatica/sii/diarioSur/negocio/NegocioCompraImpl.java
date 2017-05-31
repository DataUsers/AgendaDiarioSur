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
    public void generarEntradas(Evento evento, Integer numEntradasSeleccionadas, Usuario usuario){
       evento.setNumero_entradas(evento.getNumero_entradas()-numEntradasSeleccionadas);
       List<EntradaEvento> entradas = evento.getEntradas();
           for(int num=0; num<numEntradasSeleccionadas; num++){
                EntradaEvento nuevaEntrada = new EntradaEvento();
                nuevaEntrada.setEvento(evento);
                nuevaEntrada.setFechaCompra((java.sql.Date) new Date());
                /*nuevaEntrada.setFechaValidez(fechaSeleccionada);
                nuevaEntrada.setFormaPago(nombreEvento);
                Long idEntrada = 0;
                for(EntradaEvento n:entradas){
                 if(n.getFechaValidez().equals(fechaSeleccionada)){
                     idEntrada++;
                 }
                }
                nuevaEntrada.setIdEntrada( idEntrada+1);
                */
                nuevaEntrada.setIdEntrada( (long)  entradas.size()+1);
                nuevaEntrada.setPrecio(numEntradasSeleccionadas * evento.getPrecio() );
                nuevaEntrada.setUsuario(usuario);
                entradas.add(nuevaEntrada);
           }
             evento.setEntradas(entradas);
        
    }
    @Override
    public Evento obtenerEvento(Integer id) throws DiarioSurException {
        Evento evento = em.find(Evento.class, id);
        if (evento == null) {
            throw new EventoNoEncException();
        }
        
        return evento;
    }

}