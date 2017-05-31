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
     * Busca un evento en la base de datos
     * @param idEvento del evento en cuestion a buscar 
     * @return El evento encontrado
     * @throws DiarioSurException 
     */
    Evento findEvento(Integer idEvento) throws DiarioSurException;

    /**
     * Guarda el evento en la BD
     * 
     * Lanza EventoExistenteException si el evento ya existe en la BD
     * @param evento El evento a guardar
     * @throws DiarioSurException 
     */
    void insertarEvento(Evento evento) throws DiarioSurException;

    /**
     * Obtiene el numero de favortios que tiene el evento
     * 
     * Lanza EventoNoEncException si no existe eñ evento
     * @param evento El evento al que al que obtener su numero de favoritos
     * @return El numero de favoritos del evento
     * @throws DiarioSurException 
     */
    Long obtenerNumFav(Evento evento) throws DiarioSurException;

    /**
     * Obtener una lista arbitraria de eventos
     * @param maxResult Numero maximo de eventos
     * @return Una lista de eventos con a lo sumo maxResult
     * @throws DiarioSurException 
     */
    List obtenerEventos(int maxResult) throws DiarioSurException;
    
    /**
     * Elimina de la BD el evento pasado como parametro
     * 
     * Lanza EventoExistenteException si no existe el evento
     * @param evento El evento a eliminar
     * @throws DiarioSurException 
     */
    void eliminarEvento(Evento evento) throws DiarioSurException;

    /**
     * Modifica el evento de la BD con los valores de los atributos del evento
     * pasado como parametro.
     * 
     * Lanza EventoExistenteException si no existe el evento
     * @param evento
     * @throws DiarioSurException 
     */
    void modificarEvento(Evento evento) throws DiarioSurException;
    
}
