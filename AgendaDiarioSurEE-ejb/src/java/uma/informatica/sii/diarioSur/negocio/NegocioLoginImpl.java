/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uma.informatica.sii.diarioSur.entidades.Usuario;

@Stateless
public class NegocioLoginImpl implements NegocioLogin {

    @PersistenceContext
    private EntityManager em;

    public Usuario obtenerCuenta(String email) throws DiarioSurException {
        Usuario userFound = em.find(Usuario.class, email);

        if (userFound == null) {
            throw new CuentaInexistenteException();
        }

        return userFound;
    }

    @Override
    public Usuario login(String email, String contraseña) throws DiarioSurException{
        Usuario u = obtenerUsuario(email);
        if (!u.getContrasena().equals(contraseña)) {
            throw new LoginIncorrectoException("Contraseña incorrecta");
        }
        return u;
    }
    

    @Override
    public void crearUsuario(String nombre, String contrasena, String email, String dni) throws DiarioSurException {
        Usuario u = new Usuario(nombre, contrasena, email, Usuario.tipoUsuario.NORMAL);
        u.setDni(dni);
        registrarUsuario(u);
    }

    @Override
    public Usuario obtenerUsuario(String email) throws DiarioSurException {
        Usuario devolver = em.find(Usuario.class, email);
        if (devolver == null) {
            throw new CuentaInexistenteException();
        }
        return devolver;      
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws DiarioSurException {
        Usuario u = em.find(Usuario.class, usuario.getEmail());
        if(u!=null){
            throw new CuentaYaExistenteException();
        }
        em.persist(usuario);
    }
}
