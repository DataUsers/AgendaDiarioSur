/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.util.Date;
import javax.ejb.Local;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author ismae
 */

@Local
public interface NegocioPerfil{

    void guardarCambios(String nombre,  String apellidos,  Date fechaNacimiento, String email, String cuentaTwitter, String cuentaFacebook) throws DiarioSurException;

    Usuario obtenerUsuario(String email) throws DiarioSurException;

    void guardarContraseña(String email, String contraseñaActual, String nuevaContraseña) throws DiarioSurException;
}
