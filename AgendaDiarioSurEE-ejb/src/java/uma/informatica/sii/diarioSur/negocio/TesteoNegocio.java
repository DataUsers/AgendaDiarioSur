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

/**
 *
 * @author darylfed
 */
@Stateless
public class TesteoNegocio implements TesteoNegocioLocal {
    
    @PersistenceContext(name = "AgendaDiarioSurEEPU")
    private EntityManager em;
    
    @Override
    public void crearUsuario(String nombre, String contrasena, String email, String dni) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContrasena(contrasena);
        usuario.setEmail(email);
        usuario.setTipoUsuario(Usuario.tipoUsuario.NORMAL);
        usuario.setDni(dni);
        em.persist(usuario);
        
        System.out.println(usuario.getId());
        System.out.println("Usuario metido en la BD");
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Usuario obtenerUsuario(long id) {
        Usuario usuario = em.find(Usuario.class, id);
        if(usuario == null){
            System.out.println("No esta en la BD");
        }
        return usuario;
    }
    
}
