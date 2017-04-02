/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ismael
 */
@Entity
public class Publicidad implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPublicidad;       // Es el identificador de cada objeto de la clase Publicidad
    private String ubicacionWeb;     // Indica la URL a la que se debería redirigir si un usuario accede a él.
    private Byte[][] imagen;         // Si es una imagen estática se guardará como tal
    private String video;            // Si es una video el anuncio se guardará el enlace a la plataforma de video.
    public enum Tipo {ANIMOLUCRO, INSTITUCIONAL, MARCA, NEGOCIO, PRODUCTOLOCAL, SERVICIOPUBLICO };   // Distinguimos según el tipo de publicidad 
    private Tipo tipo;

   
    public Long getIdPublicidad() {
        return idPublicidad;
    }
    
    public void setIdPublicidad(Long idPublicidad) {
        this.idPublicidad = idPublicidad;
    }

    public String getUbicacionWeb() {
        return ubicacionWeb;
    }

    public void setUbicacionWeb(String ubicacion) {
        this.ubicacionWeb = ubicacion;
    }

    public Byte[][] getImagen() {
        return imagen;
    }

    public void setImagen(Byte[][] imagen) {
        this.imagen = imagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPublicidad != null ? idPublicidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publicidad)) {
            return false;
        }
        Publicidad other = (Publicidad) object;
        return !((this.idPublicidad == null && other.idPublicidad != null) || (this.idPublicidad != null && !this.idPublicidad.equals(other.idPublicidad)));
    }

    @Override
    public String toString() {
        return "uma.informatica.sii.diarioSur.Usuario[ idPublicidad=" + idPublicidad + " ]";
    }
     
    
}