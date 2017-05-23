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

    Evento findEvento(Integer idEvento) throws DiarioSurException;

    void insertarEvento(Evento evento) throws DiarioSurException;

    Long obtenerNumFav(Evento evento) throws DiarioSurException;

    List getCalificaciones(int pagina, int maxCalificaciones, Evento evento) throws DiarioSurException;

    Evento obtenerEventos(int id) throws DiarioSurException;

    List queryEventos(String q, String filtro) throws DiarioSurException;
    
}
