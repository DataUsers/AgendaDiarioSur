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
     * Realiza una busqueda de eventos en la BD y devuelve una sublista de
     * eventos obtenida a partir de la fila posicion pagina*maxEventos hasta
     * pagina*maxEventos + maxEventos.
     * 
     * Dicha busqueda de eventos puede ser con o sin filtros, y/o con o sin query string
     * además también tiene la posibilidad de filtrar por los mas visitados. 
     * @param pagina offset a usar
     * @param maxEventos Numero de eventos a devolver
     * @param filtros Filtros a usar en la busqueda
     * @param query Query String a usar en la busqueda
     * @param filtrarMasVisitados booleano que indica si filtrar por los mas visitados
     * @return La lista de eventos con a lo sumo maxEventos eventos.
     * @throws DiarioSurException 
     */
    List busquedaEventos(int pagina, int maxEventos, List filtros, String query, boolean filtrarMasVisitados) throws DiarioSurException;

    /**
     * Realiza una busqueda de eventos en la BD y devuelve una sublista de
     * eventos obtenida a partir de la fila posicion pagina*maxEventos hasta
     * pagina*maxEventos + maxEventos.
     * 
     * Dicha busqueda de eventos puede ser con o sin filtros, y/o con o sin query string
     * además también tiene la posibilidad de filtrar por los mas visitados. 
     * 
     * Además tambien busca por geolocalizacion en un radio de 5km, este metodo puede ser
     * impreciso si se usa desde un portatil o sobremesa, mientras que usando la geolocalizacion
     * del movil puede ser más preciso.
     * 
     * @param pagina offset a usar
     * @param maxEventos Numero de eventos a devolver
     * @param filtros Filtros a usar en la busqueda
     * @param query Query String a usar en la busqueda
     * @param latitud La latitud
     * @param longitud La longitud
     * @param filtrarMasVisitados booleano que indica si filtrar por los mas visitados
     * @return La lista de eventos con a lo sumo maxEventos eventos.
     * @throws DiarioSurException 
     */
    List busquedaEventos(int pagina, int maxEventos, List filtros, String query, double latitud, double longitud, boolean filtrarMasVisitados) throws DiarioSurException;

}
