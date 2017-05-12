//TODO


package uma.informatica.sii.diarioSur.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 *
 * @author Ismael Pineda
 */
@Entity
public class CalificacionEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCalificacion;  // Es el identificador de cada objeto de la clase Publicidad
    private String  titulo;          // Indica el título que el usuario ponga a su valoración
    private Integer puntuacion;      // Indica la puntuación que el usuario establezca al realizar su valoración
    private String  comentario;      // Es el propio comentario acerca del evento
    private boolean favorito;        // Si lo marca como favorito aparecerá como True, en caso contrario a False
    private byte[] imagen;
    
    @JoinColumn(nullable = false)
    @ManyToOne                       // Modelamos la relación muchos a uno con la entidad Evento
    private Evento eventos;
    
    @JoinColumn(nullable = false)
    @ManyToOne                       // Modelamos la relación muchos a uno con la entidad Usuario
    private Usuario usuarios;

    public CalificacionEvento(){
        
    }
    
    public CalificacionEvento(String titulo, String comentario, Integer puntuacion){
        setTitulo(titulo);
        setComentario(comentario);
        setPuntuacion(puntuacion);
    }
    
    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Evento getEventos() {
        return eventos;
    }

    public void setEventos(Evento eventos) {
        this.eventos = eventos;
    }

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public byte[] getImagen() {
	return imagen;
    }

    public void setImagen(byte[] imagen) {
	this.imagen = imagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalificacion != null ? idCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalificacionEvento)) {
            return false;
        }
        CalificacionEvento other = (CalificacionEvento) object;
        if ((this.idCalificacion == null && other.idCalificacion != null) || (this.idCalificacion != null && !this.idCalificacion.equals(other.idCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uma.informatica.sii.diarioSur.CalificacionEvento[ idCalificacion=" + idCalificacion + " ]";
    }
    
}
