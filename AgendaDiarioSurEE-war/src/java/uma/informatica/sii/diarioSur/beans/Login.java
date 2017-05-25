/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioLogin;

/**
 *
 * @author francis
 */
@Named(value = "login")
@RequestScoped
public class Login {

    private String usuario;
    private String contrasenia;
    private List<Usuario> usuarios;

    @Inject
    private ControlAutorizacion ctrl;

    @EJB
    NegocioLogin negocio;

    /**
     * Creates a new instance of Login
     *
     * @throws java.io.IOException
     */
    public Login() throws IOException {

    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String autenticar() {
        try {
            Usuario u = negocio.login(usuario, contrasenia);
            ctrl.setUsuario(u);
            return "index.xhtml";
        } catch (DiarioSurException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrectos", "Usuario o contraseña incorrectos"));
            return null;
        }
    }

}
