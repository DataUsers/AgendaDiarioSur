/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Daryl Serrano Hipolito
 */
@Entity
public class Usuario implements Serializable {

    public enum tipoUsuario {
        PERIODISTA,
        NORMAL,
        ADMINISTRADOR
    }

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String nombre;
    private String apellidos;
    @Column(nullable = false)
    private String contrasena;
    @Id
    @Column(nullable = false)
    private String email;
    private String dni;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String gustos;
    private String cuentaFacebook;
    private String cuentaTwitter;
    @OneToMany(mappedBy = "usuarios")
    private List<CalificacionEvento> calificaciones;
    @OneToMany
    private List<EntradaEvento> entradas;
    @Enumerated(EnumType.ORDINAL)
    private tipoUsuario tipoUsuario;

    public Usuario(){
        
    }
    
    public Usuario(String nombre, String contrasena, String email, tipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public tipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(tipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGustos() {
        return gustos;
    }

    public void setGustos(String gustos) {
        this.gustos = gustos;
    }

    public String getCuentaFacebook() {
        return cuentaFacebook;
    }

    public void setCuentaFacebook(String cuentaFacebook) {
        this.cuentaFacebook = cuentaFacebook;
    }

    public String getCuentaTwitter() {
        return cuentaTwitter;
    }

    public void setCuentaTwitter(String cuentaTwitter) {
        this.cuentaTwitter = cuentaTwitter;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<CalificacionEvento> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEvento> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<EntradaEvento> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<EntradaEvento> entradas) {
        this.entradas = entradas;
    }

    public void añadirEntrada(EntradaEvento entradaEvento) {
        getEntradas().add(entradaEvento);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }




    @Override
    public String toString() {
        return "uma.informatica.sii.diarioSur.Usuario[ email=" + email + " ]";
    }

}
