/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Luis Mayo
 */
@Entity
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idEvento;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String gelolocalizacion; 
    private String tipoEvento;
    private Time duracion;
    private String organizador;
    private Byte[][] imagenes;
    private String URLVideos;
    private String URLOrganizador;
    @OneToMany
    private List<CalificacionEvento> calificaciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getGelolocalizacion() {
        return gelolocalizacion;
    }

    public void setGelolocalizacion(String gelolocalizacion) {
        this.gelolocalizacion = gelolocalizacion;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public Byte[][] getImagenes() {
        return imagenes;
    }

    public void setImagenes(Byte[][] imagenes) {
        this.imagenes = imagenes;
    }

    public String getURLVideos() {
        return URLVideos;
    }

    public void setURLVideos(String URLVideos) {
        this.URLVideos = URLVideos;
    }

    public String getURLOrganizador() {
        return URLOrganizador;
    }

    public void setURLOrganizador(String URLOrganizador) {
        this.URLOrganizador = URLOrganizador;
    }
    
    public Integer getId() {
        return idEvento;
    }

    public void setId(Integer id) {
        this.idEvento = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uma.informatica.sii.diarioSur.Evento[ id=" + idEvento + " ]";
    }
    
}
