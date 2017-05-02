/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.IOException;
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
    private String veriCon;
    @Inject
    private ControlAutorizacion ctrl;

    /**
     * Creates a new instance of Login
     */
    public Register() throws IOException {
        usuario = new Usuario("", "", "", Usuario.tipoUsuario.NORMAL);
        veriCon = "";
    }

    public String getVeriCon() {
        return veriCon;
    }

    public void setVeriCon(String veriCon) {
        this.veriCon = veriCon;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String registrar() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String devolver = "index.xhtml";
        usuario.setNombre(usuario.getNombre().trim());
        usuario.setContrasena(usuario.getContrasena().trim());
        usuario.setEmail(usuario.getEmail().trim());
        if (usuario.getNombre().length() == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe introducir un nombre", "Debe introducir un nombre"));
            devolver = null;
        }
        if (usuario.getContrasena().length() == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe introducir una contraseña", "Debe introducir una contraseña"));
            devolver = null;
        }
        if (!usuario.getContrasena().equals(veriCon.trim())) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden", "Las contraseñas no coinciden"));
            devolver = null;
        }
        if (devolver != null) {
            ctrl.setUsuario(usuario);
        }
        return devolver;
    }
}
