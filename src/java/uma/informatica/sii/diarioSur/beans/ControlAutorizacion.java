/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import uma.informatica.sii.diarioSur.Usuario;

/**
 *
 * @author francis
 */
@Named(value = "controlAutorizacion")
@SessionScoped
public class ControlAutorizacion implements Serializable {

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String home() {
        String devolver = "index.xhtml?faces-redirect=true";
       

        return devolver;
    }

    public String logout() {
        // Destruye la sesión (y con ello, el ámbito de este bean)
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().invalidateSession();
        usuario = null;
        return "login.xhtml?faces-redirect=true";
    }
	public boolean sesionIniciada(){
		return usuario!=null;
	}

    /**
     * Creates a new instance of ControlAutorizacion
     */
    public ControlAutorizacion() {
		usuario=null;
    }
}
