/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.util.List;
import javax.ejb.Local;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author darylfed
 */
@Local
public interface NegocioEvento {

    /**
     * 
     * @param idEvento
     * @return
     * @throws DiarioSurException 
     */
    Evento findEvento(Integer idEvento) throws DiarioSurException;

    /**
     * 
     * @param evento
     * @throws DiarioSurException 
     */
    void insertarEvento(Evento evento) throws DiarioSurException;

    /**
     * 
     * @param evento
     * @return
     * @throws DiarioSurException 
     */
    Long obtenerNumFav(Evento evento) throws DiarioSurException;

    /**
     * 
     * @param pagina
     * @param maxCalificaciones
     * @param evento
     * @return
     * @throws DiarioSurException 
     */
    List getCalificaciones(int pagina, int maxCalificaciones, Evento evento) throws DiarioSurException;

    /**
     * 
     * @param maxResult
     * @return
     * @throws DiarioSurException 
     */
    List obtenerEventos(int maxResult) throws DiarioSurException;
    
    /**
     * 
     * @param evento
     * @throws DiarioSurException 
     */
    void eliminarEvento(Evento evento) throws DiarioSurException;

    /**
     * 
     * @param evento
     * @throws DiarioSurException 
     */
    void modificarEvento(Evento evento) throws DiarioSurException;
    
}
