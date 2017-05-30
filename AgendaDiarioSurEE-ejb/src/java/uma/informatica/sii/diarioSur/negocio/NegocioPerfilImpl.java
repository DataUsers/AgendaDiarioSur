/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

/**
 *
 * @author ismae
 */

import java.sql.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uma.informatica.sii.diarioSur.entidades.Usuario;

@Stateless
public class NegocioPerfilImpl implements NegocioPerfil {
    
    @PersistenceContext
    private EntityManager em;
    
    
    @Override
    public void guardarCambios(String nombre, String apellidos, Date fechaNacimiento, String email, String cuentaTwitter,String cuentaFacebook) throws DiarioSurException{
    
        Usuario usuarioEncontrado = em.find(Usuario.class, email);
        
        if (usuarioEncontrado == null) {
            throw new CuentaInexistenteException();
        }
        
        usuarioEncontrado.setNombre(nombre);
        usuarioEncontrado.setApellidos(apellidos);
        usuarioEncontrado.setFechaNacimiento(fechaNacimiento);
        usuarioEncontrado.setCuentaFacebook(cuentaFacebook);
        usuarioEncontrado.setCuentaTwitter(cuentaTwitter);
        em.persist(usuarioEncontrado);
        em.flush();
    }
    

    @Override
    public void guardarContraseña(String email, String contraseñaActual, String nuevaContraseña) throws DiarioSurException {
        Usuario usuarioEncontrado = em.find(Usuario.class, email);
        
        if (usuarioEncontrado == null) {
            throw new CuentaInexistenteException();
        }else if (!usuarioEncontrado.getContrasena().equals(contraseñaActual)){
            throw new DiarioSurException();
        }
        
        usuarioEncontrado.setContrasena(nuevaContraseña);
        em.persist(usuarioEncontrado);
        em.flush();
    }
    
         

    @Override
    public Usuario obtenerUsuario(String email) throws DiarioSurException {
        Usuario devolver = em.find(Usuario.class, email);
        if (devolver  == null) {
            throw new CuentaInexistenteException();
        }
        return devolver; 
    }
}
     