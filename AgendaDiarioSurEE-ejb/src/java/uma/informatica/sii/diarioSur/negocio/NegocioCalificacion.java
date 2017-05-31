/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.util.List;
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
     * Guarda una calificacion a la BD, dicha calificación debe de tener el 
     * parámetro eventos apuntando a un evento real guardado en la BD
     * Lanza DiarioSurException si el evento asociado a la calificacion no existe
     * en la BD
     * @param calificacion La calificacion a guardar
     * @throws DiarioSurException 
     */
    void insertarCalificacion(CalificacionEvento calificacion) throws DiarioSurException;
    
    /**
     * Obtiene una lista de calificaciones asociados a el evento pasado como parametro
     * 
     * ESte método devolvera una sublista de a lo sumo maxCalificaciones
     * @param pagina Offset a usar
     * @param maxCalificaciones Numero de calificaciones a devolver
     * @param evento evento al que se le cogeran las calificaciones asociadas
     * @return Lista de las calificaciones asociadas al evento
     * @throws DiarioSurException 
     */
    List getCalificaciones(int pagina, int maxCalificaciones, Evento evento) throws DiarioSurException;
    
}
