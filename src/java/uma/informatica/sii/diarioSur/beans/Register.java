/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import uma.informatica.sii.diarioSur.Usuario;

/**
 *
 * @author francis
 */
@Named(value = "register")
@RequestScoped
public class Register {

	private Usuario usuario;

	@Inject
	private ControlAutorizacion ctrl;

	/**
	 * Creates a new instance of Login
	 */
	public Register() throws IOException {
	}

	@PostConstruct
	public void close() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException ex) {
			Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
