/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String apellidos;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String dni;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String gustos;
    private String cuentaFacebook;
    private String cuentaTwitter;
    @OneToMany
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uma.informatica.sii.diarioSur.Usuario[ id=" + id + " ]";
    }

}
