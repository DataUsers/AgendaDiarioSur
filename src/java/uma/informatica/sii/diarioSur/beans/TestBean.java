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
 * @author Daryl
 */
@Named(value = "testBean")
@RequestScoped
public class TestBean {

    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
    }
    
    public String gotoEvent(){
	System.out.println("Se ha ejecutado");
	
	return "evento.xhtml";
    }
    
    public String gotoIndex(){
	return "index.xhtml";
    }
    
}
