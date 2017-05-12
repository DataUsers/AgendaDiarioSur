/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author darylfed
 */
@Named(value = "test")
@RequestScoped
public class Test {

    /**
     * Creates a new instance of Test
     */
    public Test() {
    }
    
    public String obtenerPrueba(){
        return "Prueba";
    }
    
}
