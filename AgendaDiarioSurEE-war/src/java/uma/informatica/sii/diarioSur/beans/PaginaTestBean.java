/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioEvento;
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

    @EJB
    private NegocioEvento negocioEvento;

    private String nombre;
    private String contrasena;
    private String email;
    private String dni;

    private Long id;

    private Evento evento;

    /**
     * Creates a new instance of PaginaTestBean
     */
    public PaginaTestBean() {
    }

    public String crearUsuario() {
        negocio.crearUsuario(nombre, contrasena, email, dni);
        return null;
    }

    public String obtenerUsuario() {
        Usuario user = negocio.obtenerUsuario(id);
        System.out.println("Usuario obtenido: " + user.getNombre());
        System.out.println("Usuario obtenido contrasena: " + user.getContrasena());
        System.out.println("Usuario obtenido email: " + user.getEmail());

        return null;
    }

    public String crearEvento() {
        evento = new Evento();
        evento.setNombre("Placeholder");
        evento.setDescripcion("Placeholder");
        evento.setPrecio(20);
        evento.setGeolocalizacion("36.714040, -4.433475");
        evento.setOrganizador("OrganizadorNombre");
        evento.setURLOrganizador("http://127.0.0.1:8080");

        /*
        calificaciones = new ArrayList<CalificacionEvento>();
        for (int i = 0; i < 5; ++i) {
            CalificacionEvento calificacion = new CalificacionEvento("PACO", "UNA DESCRIPCION", i);
            calificacion.setFavorito(true);
            calificaciones.add(calificacion);
        }
        evento.setCalificaciones(calificaciones);
         */
        // Crear fechas ficticias
        List<Date> fechas = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Date date = new Date(System.currentTimeMillis());
            fechas.add(date);
            //System.out.println("Date: " + date.toLocalDate().toString());
        }
        evento.setFechas(fechas);

        /*
            // Setear marcador del mapa, compobar errores aqui
            String[] coord = evento.getGeolocalizacion().split(",");
            Double latitud = Double.parseDouble(coord[0]);
            Double longitud = Double.parseDouble(coord[1]);
            model.addOverlay(new Marker(new LatLng(latitud, longitud), evento.getNombre()));
            
            imagenes = new ArrayList<>();
            for (int i = 0; i < 5; ++i) {
            imagenes.add("image" + i + ".jpg");
            }
         */
        
        try {

            negocioEvento.insertarEvento(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(PaginaTestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
