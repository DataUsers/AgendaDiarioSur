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
public interface NegocioBusqueda {

    /**
     * Realiza una busqueda de eventos en la BD y devuelve una sublista de
     * eventos obtenida a partir de la fila posicion pagina*maxEventos hasta
     * pagina*maxEventos + maxEventos.
     *
     * @param pagina Offset a usar
     * @param maxEventos Numero de eventos a devolver
     * @return La lista de eventos con a lo sumo maxEventos eventos.
     * @throws DiarioSurException
     */
    List obtenerEventos(int pagina, int maxEventos) throws DiarioSurException;

    /**
     * 
     * @param pagina
     * @param maxEventos
     * @param filtros
     * @param query
     * @param filtrarMasVisitados
     * @return
     * @throws DiarioSurException 
     */
    List busquedaEventos(int pagina, int maxEventos, List filtros, String query, boolean filtrarMasVisitados) throws DiarioSurException;

    /**
     * 
     * @param pagina
     * @param maxEventos
     * @param filtros
     * @param query
     * @param latitud
     * @param longitud
     * @param filtrarMasVisitados
     * @return
     * @throws DiarioSurException 
     */
    List busquedaEventos(int pagina, int maxEventos, List filtros, String query, double latitud, double longitud, boolean filtrarMasVisitados) throws DiarioSurException;

}
