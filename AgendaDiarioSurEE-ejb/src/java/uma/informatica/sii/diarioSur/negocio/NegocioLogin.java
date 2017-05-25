/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Local;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author luism
 */


@Local
public interface NegocioLogin {

    public Usuario login(String email, String contraseña) throws DiarioSurException;

    void crearUsuario(String nombre, String contrasena, String email, String dni) throws DiarioSurException;

    Usuario obtenerUsuario(String email) throws DiarioSurException;

    void registrarUsuario(Usuario usuario) throws DiarioSurException;
}

