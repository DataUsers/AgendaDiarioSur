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

    List obtenerEventos(int pagina, int maxEventos) throws DiarioSurException;

    List busquedaEventos(int pagina, int maxEventos, List filtros, String query) throws DiarioSurException;

    List busquedaEventos(int pagina, int maxEventos, List filtros, String query, double latitud, double longitud) throws DiarioSurException;
    
}
