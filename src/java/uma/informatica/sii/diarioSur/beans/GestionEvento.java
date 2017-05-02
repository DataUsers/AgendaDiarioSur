/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import uma.informatica.sii.diarioSur.Evento;
import uma.informatica.sii.diarioSur.Usuario;

/**
 *
 * @author darylfed
 */
@Named(value = "gestionEvento")
@RequestScoped
public class GestionEvento {

    private List<String> filtrosPredeterminados;
    private List<String> filtros;
    private List<Evento> placeholderEvents;
    private List<Evento> eventosMostrar;

    private String fecha;
    private String tiempo;

    @Inject
    private ControlAutorizacion ctrl;

    /**
     * Creates a new instance of GestionEvento
     */
    public GestionEvento() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String query = request.getParameter("q");
        String filtro = request.getParameter("filtrar");

        // obtener cookies y asignar filtros
        crearFiltroPredeterminado();
        filtros = filtrosPredeterminados;

        // Creacion de eventos placeholder
        crearPlaceholder();

        if (query != null && query.length() != 0) {
            // Busqueda placeholder
            System.out.println("Tiene query String: " + query);
            eventosMostrar = new ArrayList<>();
            Random rnd = new Random(System.currentTimeMillis());

            System.out.println("Numero de eventos placeholder: " + placeholderEvents.size());

            for (Evento evento : placeholderEvents) {
                // busqueda normal por nombre, el query deberia de hacerce con el ejb
                // Busqueda placeholder
                if (filtro != null && filtro.length() != 0) {
                    System.out.println("Tiene un filtro: " + filtro);
                }

                // Comprobar latitud y longitud y obtener por proximidad
                if (rnd.nextBoolean()) {
                    eventosMostrar.add(evento);
                }
            }
        } else {
            eventosMostrar = placeholderEvents.subList(0, 5);
        }

    }

    public boolean comprobarAutorizacion() {
        boolean autorizado = false;

        if (ctrl.sesionIniciada() && (ctrl.getUsuario().getTipoUsuario() == Usuario.tipoUsuario.ADMINISTRADOR
                || ctrl.getUsuario().getTipoUsuario() == Usuario.tipoUsuario.PERIODISTA)) {
            autorizado = true;
        }

        return autorizado;
    }
    
    public boolean comprobarAdmin(){
        boolean esAdmin = false;
        
        esAdmin = ctrl.sesionIniciada() && (ctrl.getUsuario().getTipoUsuario() == Usuario.tipoUsuario.ADMINISTRADOR);
        
        return esAdmin;
    }

    public String guardarCambios(Evento evento) {
        // realizar los cambios en la BD y despues refrescar la pagina
        System.out.println("Modificar evento");
        System.out.println("Nuevo nombre: " + evento.getNombre());
        System.out.println("Fecha: " + fecha);
        System.out.println("Tiempo: " + tiempo);

        return "gestionEvento?faces-redirect=true"; // test
    }
    
    public String borrarEvento(Evento evento){
        // eliminar evento de la base de datos y despues refrescar la pagina
        System.out.println("Se va a borrar un evento");
        System.out.println("Evento nombre: " + evento.getNombre());
        
        return "gestionEvento?faces-redirect=true";
    }

    private void crearFiltroPredeterminado() {
        filtrosPredeterminados = new ArrayList<>();
        filtrosPredeterminados.add("Todos");
        filtrosPredeterminados.add("Lugar");
        filtrosPredeterminados.add("Fecha");
        filtrosPredeterminados.add("Tipos");
        filtrosPredeterminados.add("Duracion");
        filtrosPredeterminados.add("Coste");
    }

    private void crearPlaceholder() {
        Random rnd = new Random(System.currentTimeMillis());
        placeholderEvents = new ArrayList<>();

        // Tipo de Eventos predeterminados para filtrar 
        List<String> tipoEventos = new ArrayList<>();
        tipoEventos.add(Evento.Tipo.CONCIERTOS.name());
        tipoEventos.add(Evento.Tipo.DESFILES.name());
        tipoEventos.add(Evento.Tipo.FERIAS.name());
        tipoEventos.add(Evento.Tipo.EXPOSICIONES.name());
        tipoEventos.add(Evento.Tipo.CINES.name());

        for (int i = 0; i < 20; ++i) {
            Evento eventoPlaceholder = new Evento();
            eventoPlaceholder.setIdEvento(10);
            eventoPlaceholder.setNombre("Placeholder Nombre evento " + i);
            eventoPlaceholder.setPrecio(rnd.nextInt(100));
            eventoPlaceholder.setDescripcion("PLACEHOLDER description del evento" + i);
            eventoPlaceholder.setGeolocalizacion("36.714040, -4.433475");
            eventoPlaceholder.setOrganizador("OrganizadorNombre");
            eventoPlaceholder.setURLOrganizador("http://127.0.0.1:8080");
            eventoPlaceholder.setNumeroVisitas(rnd.nextInt(500));

            // Eleccion random de tipo de evento
            String tipoEventStr = tipoEventos.get(rnd.nextInt(tipoEventos.size()));
            Evento.Tipo tipoEvento = Evento.Tipo.valueOf(Evento.Tipo.class, tipoEventStr);
            eventoPlaceholder.setTipoEvento(tipoEvento);
            // imagenes
            placeholderEvents.add(eventoPlaceholder);
        }
    }

    public String placeholderFecha() {
        Random rnd = new Random(System.currentTimeMillis());

        // Crear dia random
        LocalDateTime date = LocalDateTime.now();
        date =  date.plusDays(rnd.nextInt(10));
        
        // Formatear la salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String outDate = date.format(formatter);

        return outDate;
    }

    public String placeholderTiempo() {
        Random rnd = new Random(System.currentTimeMillis());

        // Crear dia random
        LocalDateTime date = LocalDateTime.now();
        date = date.plusHours(rnd.nextInt(24));

        // Formatear la salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String outTime = date.format(formatter);

        return outTime;
    }

    public List<String> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<String> filtros) {
        this.filtros = filtros;
    }

    public List<Evento> getEventosMostrar() {
        return eventosMostrar;
    }

    public void setEventosMostrar(List<Evento> eventosMostrar) {
        this.eventosMostrar = eventosMostrar;
    }

    public String randomImage() {
        Random rnd = new Random(System.currentTimeMillis());
        return "image" + rnd.nextInt(5) + ".jpg";
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

}
