/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.sql.Date;
import javax.ejb.Local;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author ismae
 */

@Local
public interface NegocioPerfil{
    
   /**
     * Actualiza la información del perfil introducida en el formulario donde visualiza sus datos.
     * @param email
     * @param nombre
     * @param apellidos 
     * @param fechaNacimiento
     * @param email
     * @param cuentaTwitter
     * @param cuentaFacebook
     * @throws CuentaInexistenteException Si no encuentra al usuario que tenga asociado el email o este no existe
     */
    void guardarCambios(String nombre,  String apellidos,  Date fechaNacimiento, String email, String cuentaTwitter, String cuentaFacebook) throws DiarioSurException;
        
      /**
     * Encuentra a aquel usuario que tenga asociado el email que tiene como parametro
     * @param email
     * @return Aquel usuario que tiene asociado el email
     * @throws CuentaInexistenteException Si no encuentra al usuario que tenga asociado el email o este no existe
     */
    Usuario obtenerUsuario(String email) throws DiarioSurException;

    /**
     * Actualiza la información introducida en el formulario del perfil.
     * @param email
     * @param contraseñaActual 
     * @param nuevaContraseña  Contraseña que va a reemplazar a la actual. La sesión seguira iniciada pero la contraseña habrá cambiado
     * @throws CuentaInexistenteException  Si no encuentra al usuario que tenga asociado el email o este no existe
     * @throws DiarioSurException Si no coincide la contraseña actual con la que tiene lanzamos esta excepcion.
     */
    void guardarContraseña(String email, String contraseñaActual, String nuevaContraseña) throws DiarioSurException;
}
