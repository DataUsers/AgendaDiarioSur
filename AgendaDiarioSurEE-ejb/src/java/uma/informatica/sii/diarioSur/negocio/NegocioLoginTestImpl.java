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
public class NegocioLoginTestImpl implements NegocioLoginTest {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Usuario obtenerCuenta(long id) throws DiarioSurException {
        Usuario userFound = em.find(Usuario.class, id);
        
        if(userFound == null){
            throw new CuentaInexistenteException();
        }
        
        return userFound;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
