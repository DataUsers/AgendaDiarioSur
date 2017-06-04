/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */ 
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Local;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author ismae
 */

@Local
public interface NegocioCompra {
    
    /**
     * Metodo para obtener el evento mediante su id
     * @param idEvento              Pasamos el identificador para encontrar el evento
     * @return evento               Devolvemos el evento que se ha encontrado
     * @throws DiarioSurException   Si no existe el evento se lanza esta excepcion
     */
    Evento obtenerEvento(String idEvento) throws DiarioSurException;  
   
    /**
     * Generamos las entradas y se las asignamos al usuario que haya hecho la compra
     * @param envento
     * @param numEntradasSeleccionadas
     * @param usuario
     * @param formaPago
     */
    void generarEntradas(Evento evento, Integer numEntradasSeleccionadas,Usuario usuario, String formaPago) throws DiarioSurException;
}
