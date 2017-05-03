/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import uma.informatica.sii.diarioSur.Usuario;

/**
 *
 * @author ismae
 */
@Named(value = "Perfil")
@RequestScoped
public class Perfil {

    private Usuario usuario;
    private String password;
    private String newPassword;
    private String oldPassword;

    @Inject
    private ControlAutorizacion ctrl;

    public String validarCambiosPerfil() {

        FacesContext ctx = FacesContext.getCurrentInstance();
        String devolver = "perfil.xhtml";
        usuario.setNombre(usuario.getNombre().trim());

        if (usuario.getNombre().length() == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe introducir un nombre", "Debe introducir un nombre"));
            devolver = null;

        }
        return devolver;
    }

    public String cambiarContrasenia() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        if (!newPassword.equals(password)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden", "Las contraseñas no coinciden"));

        }
        if (!ctrl.getUsuario().getContrasena().equals(oldPassword)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta no es su contraseña actual", "Esta no es su contraseña actual"));

        }
        if (ctrl.getUsuario().getContrasena().length() == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña inválida", "Contraseña Inválida"));

        }
        if (ctrl.getUsuario().getContrasena().length() > 6) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña demasiado insegura", "Contraseña demasiado insegura"));
        } else {
            ctrl.getUsuario().setContrasena(password);
        }

        return null;
    }
}
