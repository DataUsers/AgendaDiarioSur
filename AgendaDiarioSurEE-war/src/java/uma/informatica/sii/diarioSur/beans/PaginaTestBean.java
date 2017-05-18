/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.TesteoNegocioLocal;

/**
 *
 * @author darylfed
 */
@Named(value = "paginaTestBean")
@RequestScoped
public class PaginaTestBean {

    @EJB
    private TesteoNegocioLocal negocio;
    
    private String nombre;
    private String contrasena;
    private String email;
    private String dni;
    
    private Long id;
    
    /**
     * Creates a new instance of PaginaTestBean
     */
    public PaginaTestBean() {
    }
    
    public String crearUsuario(){
        negocio.crearUsuario(nombre, contrasena, email, dni);
        return null;
    }
    
    public String obtenerUsuario(){
        Usuario user = negocio.obtenerUsuario(id);
        System.out.println("Usuario obtenido: " + user.getNombre());
        System.out.println("Usuario obtenido contrasena: " + user.getContrasena());
        System.out.println("Usuario obtenido email: " + user.getEmail());
        
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
}
