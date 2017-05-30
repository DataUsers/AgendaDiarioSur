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

    /**
     * Intenta iniciar sesion con el email y la contraseña especificados
     * @param email
     * @param contraseña
     * @return el usuario que ha iniciado la sesión
     * @throws LoginIncorrectoException cuando la contraseña es incorrecta
     * @throws CuentaInexistenteException si la contraseña no existe
     */
    public Usuario login(String email, String contraseña) throws DiarioSurException;

    /**
     * Registra un usuario con los parametros aportados
     * @param nombre
     * @param contrasena
     * @param email
     * @param dni
     * @throws CuentaYaExistenteException si ya existe un usuario con ese email
     */
    void crearUsuario(String nombre, String contrasena, String email, String dni) throws DiarioSurException;
    
    /**
     * Busca el usuario con ese email
     * @param email
     * @return El usuario encontrado
     * @throws CuentaInexistenteException si el usuario no existe
     */
    Usuario obtenerUsuario(String email) throws DiarioSurException;
    
    /**
     * Registra un usuario en la base de datos
     * @param usuario Objeto ususario con todos los datos del ususario a registrar
     * @throws CuentaYaExistenteException si ya existe un usuario con el mismo email
     */
    void registrarUsuario(Usuario usuario) throws DiarioSurException;
}

