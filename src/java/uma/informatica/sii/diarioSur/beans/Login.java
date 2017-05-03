/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
@Named(value = "login")
@RequestScoped
public class Login {

    private String usuario;
    private String contrasenia;
    private List<Usuario> usuarios;

    @Inject
    private ControlAutorizacion ctrl;

    /**
     * Creates a new instance of Login
     *
     * @throws java.io.IOException
     */
    public Login() throws IOException {
        usuarios = new ArrayList<>();

        usuarios.add(new Usuario("pepe", "asdf", "pepe@hotmail.com", Usuario.tipoUsuario.NORMAL));
        usuarios.add(new Usuario("manolo", "qwer", "manolo@gmail.com", Usuario.tipoUsuario.PERIODISTA));
        usuarios.add(new Usuario("marisa", "kirisame", "a", Usuario.tipoUsuario.PERIODISTA));
        usuarios.add(new Usuario("manolo", "qwer", "a", Usuario.tipoUsuario.PERIODISTA));
        usuarios.add(new Usuario("eiki", "shiki", "a", Usuario.tipoUsuario.ADMINISTRADOR));
    
    }

    @PostConstruct
    public void close() {
        /*
		try {
			if (ctrl.sesionIniciada()) {
				System.out.println("Sesion YA iniciada");
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			}else{
				System.out.println(ctrl.getUsuario().getNombre());
			}
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
         */
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
        // Implementar este método
        FacesContext ctx = FacesContext.getCurrentInstance();
        String devolver = null;
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(usuario)) {
                if (u.getContrasena().equals(contrasenia)) {
                    ctrl.setUsuario(u);
                    devolver = ctrl.home();
                }
                break;
            }
        }
        if (devolver == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrectos", "Usuario o contraseña incorrectos"));
        }
        return devolver;
    }

}
