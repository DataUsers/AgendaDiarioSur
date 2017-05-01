/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

    public enum Tipo {
        ACTOSPOLÍTICOS, CERTÁMENES,
        CINES, CONCIERTOS, CONCURSOS, CONMMEMORACIONES, DEBATES, DESFILES,
        ENTREGAPREMIOS, EVENTODEPORTIVO, EVENTOINFANTIL, EXPOSICIONES, FERIAS,
        FIESTASBENÉFICAS, FIESTASNOCTURNAS, HOMENAJES, INAUGURACIONES, MUESTRASDEARTE, PRESENTACIONES,
        AIRE_LIBRE, CAMPEONATOS, CLASESABIERTAS, CONFERENCIAS, CONGRESOS, GASTRONOMICO, MODA, MUSEOS, MUSICAL, TORNEOS
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idEvento;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String geolocalizacion;
    @Enumerated(EnumType.ORDINAL)
    private Tipo tipoEvento;
    @ElementCollection (fetch =  FetchType.EAGER)
    private List<Date> fechas;
    private Time duracion;
    private Integer numero_entradas;
    private String organizador;
    private Byte[][] imagenes;
    private String URLVideos;
    private String URLOrganizador;
    @OneToMany
    private List<CalificacionEvento> calificaciones;
    @OneToMany
    private List<EntradaEvento> entradas;

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

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

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public String getGeolocalizacion() {
		return geolocalizacion;
	}

	public void setGeolocalizacion(String geolocalizacion) {
		this.geolocalizacion = geolocalizacion;
	}

	public Tipo getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(Tipo tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<Date> getFechas() {
		return fechas;
	}

	public void setFechas(List<Date> fechas) {
		this.fechas = fechas;
	}

	public Time getDuracion() {
		return duracion;
	}

	public void setDuracion(Time duracion) {
		this.duracion = duracion;
	}

	public Integer getNumero_entradas() {
		return numero_entradas;
	}

	public void setNumero_entradas(Integer numero_entradas) {
		this.numero_entradas = numero_entradas;
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
