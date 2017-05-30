/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Local;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author darylfed
 */
@Local
public interface NegocioCalificacion {

    /**
     * 
     * @param calificacion
     * @throws DiarioSurException 
     */
    void insertarCalificacion(CalificacionEvento calificacion) throws DiarioSurException;
    
}
