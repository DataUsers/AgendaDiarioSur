/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import uma.informatica.sii.diarioSur.misc.GeoLocation;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioEvento;
import uma.informatica.sii.diarioSur.negocio.NegocioLogin;

/**
 *
 * @author darylfed
 */
@Named(value = "paginaTestBean")
@RequestScoped
public class PaginaTestBean {

    @EJB
    private NegocioLogin negocio;

    @EJB
    private NegocioEvento negocioEvento;

    private String nombre;
    private String contrasena;
    private String dni;

    private String email;

    private Evento evento;

    private Integer numEventos;

    private Evento.Tipo[] tiposEvento = {Evento.Tipo.CONCIERTOS, Evento.Tipo.DESFILES, Evento.Tipo.EXPOSICIONES,
        Evento.Tipo.FERIAS, Evento.Tipo.CINES};

    /**
     * Creates a new instance of PaginaTestBean
     */
    public PaginaTestBean() {
    }

    public String crearUsuario() throws DiarioSurException {
        negocio.crearUsuario(nombre, contrasena, email, dni);
        return null;
    }

    public String crearAdmin() throws DiarioSurException {
        Usuario u = new Usuario("admin", "admin", "admin@admin.admin", Usuario.tipoUsuario.ADMINISTRADOR);
        negocio.registrarUsuario(u);
        return null;
    }

    private void createEvent(Evento.Tipo tipo, int i, String nombre, String descripcion, String imagen) {
        Random rnd = new Random();
        imagen = "resources/imagenes/" + imagen;
        GeoLocation[] coordenadas = {
            GeoLocation.fromDegrees(36.843593, -4.876234),
            GeoLocation.fromDegrees(36.511921, -4.880657),
            GeoLocation.fromDegrees(36.589181, -4.546647),
            GeoLocation.fromDegrees(36.722345, -4.479399),
            GeoLocation.fromDegrees(36.721339, -4.478111),
            GeoLocation.fromDegrees(36.722345, -4.479399),
            GeoLocation.fromDegrees(36.721339, -4.478111),
            GeoLocation.fromDegrees(36.722345, -4.479399),
            GeoLocation.fromDegrees(36.721339, -4.478111),
            GeoLocation.fromDegrees(36.722345, -4.479399),
            GeoLocation.fromDegrees(36.721339, -4.478111),
            GeoLocation.fromDegrees(36.722345, -4.479399),
            GeoLocation.fromDegrees(36.721339, -4.478111),
            GeoLocation.fromDegrees(36.721339, -4.478111),
            GeoLocation.fromDegrees(36.724111, -4.475944)
        };

        Evento evento = new Evento();
        evento.setTipoEvento(tipo);

        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        evento.setPrecio(rnd.nextInt(50));
        // Latitud y longitud
        GeoLocation loc = coordenadas[i];
        System.out.println("Latitud Degree: " + loc.getLatitudeInDegrees() + " longitud Degree: " + loc.getLongitudeInDegrees());
        System.out.println("latitud radians: " + loc.getLatitudeInRadians() + " longitud radians: " + loc.getLongitudeInRadians());
        evento.setLatitud(loc.getLatitudeInRadians());
        evento.setLongitud(loc.getLongitudeInRadians());

        evento.setOrganizador("DiarioSur");
        evento.setURLOrganizador("http://127.0.0.1:8080");

        // Crear fechas ficticias
        List<Date> fechas = new ArrayList<>();
        for (int j = 0; j < 5; ++j) {
            Date date = new Date(System.currentTimeMillis());
            fechas.add(date);
            //System.out.println("Date: " + date.toLocalDate().toString());
        }
        evento.setFechas(fechas);

        // Test images
        String[] imagenes = new String[1];
        imagenes[0] = imagen;

        evento.setImagenes(imagenes);

        System.out.println("Tpo de evento generado: " + evento.getTipoEvento());

        try {
            negocioEvento.insertarEvento(evento);
        } catch (DiarioSurException ex) {
            Logger.getLogger(PaginaTestBean.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public String pleaseCallMe() {
        System.out.println("Thanks");
        return "hola";
    }

    public String genera() throws DiarioSurException {
        if (negocioEvento.obtenerEventos(1).isEmpty()) {
            System.out.println(negocio);
            try {
                System.out.println("Generador generando generativamente");
                int i = 0;
                createEvent(Evento.Tipo.CONCIERTOS, i++, "Concierto Alejandro Sanz", "Disfruta mucho en este concierto de Alejandro Sanz que llega a Málaga como parte de la gira para su disco \"Sirope\"", "concierto1.jpg");
                createEvent(Evento.Tipo.CONCIERTOS, i++, "Skunk DF en Málaga", "El grupo Skunk DF acturá en Málaga. La apertura de puertas será a las 21:00h.", "concierto2.jpg");
                createEvent(Evento.Tipo.CONCIERTOS, i++, "Festival de tributos MASTERS OF ROCK ",
                        "Más de tres horas para disfrutar de las mejores bandas de la historia del ROCK de la mano de sus más impresionantes bandas tributo.",
                        "concierto3.jpg");
                createEvent(Evento.Tipo.DESFILES, i++, "Moncler Gamme Rouge",
                        "Desfile de moda en el centro de la ciudad",
                        "desfiles1.jpg");
                createEvent(Evento.Tipo.DESFILES, i++, "Esteban Cortazar",
                        "Desfile de moda en el centro de la ciudad",
                        "desfiles2.jpg");
                createEvent(Evento.Tipo.DESFILES, i++, "Valentin Yudashkin",
                        "Disfrute de las innovaciones en el terreno de la moda",
                        "desfiles3.jpg");
                createEvent(Evento.Tipo.EXPOSICIONES, i++, "Museo Picasso",
                        "Disfrute de las obras de este autor malagueño",
                        "expo1.jpg");
                createEvent(Evento.Tipo.EXPOSICIONES, i++, "Exposición de arte",
                        "Disfrute de las obras de diferentes autores que se exponen hoy aquí",
                        "expo2.jpg");
                createEvent(Evento.Tipo.EXPOSICIONES, i++, "Exposición de arte medieval",
                        "Disftute de estas obras de tematica medieval",
                        "expo3.jpg");
                createEvent(Evento.Tipo.FERIAS, i++, "Feria del libro",
                        "Disftute de la lectura en esta feria",
                        "fair1.jpg");
                createEvent(Evento.Tipo.FERIAS, i++, "Feria de Málaga",
                        "Disfruta de todos los entretenimientos que ofrece la feria de Málaga",
                        "fair2.jpg");
                createEvent(Evento.Tipo.FERIAS, i++, "Feria",
                        "Pase el rato en esta entretenida feria",
                        "fair3.jpg");
                createEvent(Evento.Tipo.CINES, i++, "Cine al aire libre",
                        "Interesante proyección de peliculas al aire libre",
                        "cine1.jpg");
                createEvent(Evento.Tipo.CINES, i++, "AutoCine",
                        "Cine para asistir con su vehiculo",
                        "cine2.jpg");
                createEvent(Evento.Tipo.CINES, i++, "Inauguración cine",
                        "Se inaugura una nueva sala de cine en Málaga",
                        "cine3.jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String crearUsuarioPrivilegiado() {
        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setContrasena(contrasena);
        user.setEmail(email);
        user.setDni(dni);
        user.setTipoUsuario(Usuario.tipoUsuario.ADMINISTRADOR);

        try {
            negocio.registrarUsuario(user);
        } catch (DiarioSurException ex) {
            Logger.getLogger(PaginaTestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String obtenerUsuario() throws DiarioSurException {
        Usuario user = negocio.obtenerUsuario(email);
        System.out.println("Usuario obtenido: " + user.getNombre());
        System.out.println("Usuario obtenido contrasena: " + user.getContrasena());
        System.out.println("Usuario obtenido email: " + user.getEmail());

        return null;
    }

    public String crearEvento() {
        Random rnd = new Random(System.currentTimeMillis());

        GeoLocation[] coordenadas = {
            GeoLocation.fromDegrees(36.510304, -4.882464),
            GeoLocation.fromDegrees(36.511983, -4.880888),
            GeoLocation.fromDegrees(36.589181, -4.546646),
            GeoLocation.fromDegrees(36.722377, -4.479303),
            GeoLocation.fromDegrees(36.721336, -4.478155),
            GeoLocation.fromDegrees(36.724182, -4.475945)
        };

        for (int i = 0; i < numEventos; ++i) {
            evento = new Evento();
            evento.setTipoEvento(tiposEvento[rnd.nextInt(5)]);

            evento.setNombre("Placeholder " + evento.getTipoEvento() + " " + rnd.nextInt(50));
            evento.setDescripcion("Placeholder magico " + rnd.nextInt(50));
            evento.setPrecio(rnd.nextInt(50));
            // Latitud y longitud
            GeoLocation loc = coordenadas[rnd.nextInt(coordenadas.length)];
            System.out.println("Latitud Degree: " + loc.getLatitudeInDegrees() + " longitud Degree: " + loc.getLongitudeInDegrees());
            System.out.println("latitud radians: " + loc.getLatitudeInRadians() + " longitud radians: " + loc.getLongitudeInRadians());
            evento.setLatitud(loc.getLatitudeInRadians());
            evento.setLongitud(loc.getLongitudeInRadians());

            evento.setOrganizador("OrganizadorNombre " + rnd.nextInt(50));
            evento.setURLOrganizador("http://127.0.0.1:8080");

            // Crear fechas ficticias
            List<Date> fechas = new ArrayList<>();
            for (int j = 0; j < 5; ++j) {
                Date date = new Date(System.currentTimeMillis());
                fechas.add(date);
                //System.out.println("Date: " + date.toLocalDate().toString());
            }
            evento.setFechas(fechas);

            // Test images
            String[] imagenes = new String[6];
            imagenes[0] = "https://i.imgur.com/JDkxeA8.gif";
            imagenes[1] = "http://i.imgur.com/vh64DDX.gif";
            imagenes[2] = "http://i.imgur.com/93vHumH.png";
            imagenes[3] = "http://i.imgur.com/H1SW1Rj.png";
            imagenes[4] = "http://i.imgur.com/pojHJNc.jpg";
            imagenes[5] = "http://i.imgur.com/cZK9lZP.png";

            evento.setImagenes(imagenes);

            System.out.println("Tpo de evento generado: " + evento.getTipoEvento());

            try {

                negocioEvento.insertarEvento(evento);
            } catch (DiarioSurException ex) {
                Logger.getLogger(PaginaTestBean.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getNumEventos() {
        return numEventos;
    }

    public void setNumEventos(Integer numEventos) {
        this.numEventos = numEventos;
    }

}
