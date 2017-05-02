/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import uma.informatica.sii.diarioSur.Evento;

/**
 *
 * @author Daryl
 */
@Named(value = "busqueda")
@RequestScoped
public class Busqueda {

    private List<String> filtrosPredeterminados;
    private List<String> filtros;
    private List<Evento> placeholderEvents;
    private List<Evento> eventosMostrar;

    /**
     * Creates a new instance of Busqueda
     */
    public Busqueda() {
        // Obtener query y etiqueta cookies
        // Si no hay query ni filtro ni cookies, cargar etiquetas predeterminadas y filtrar predeterminado
        // Si se ha hecho query, hacer query y filtrar mostrando los resultados
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

    private void crearFiltroPredeterminado() {
        filtrosPredeterminados = new ArrayList<>();
        filtrosPredeterminados.add("Mas Visitados");
        filtrosPredeterminados.add("Conciertos");
        filtrosPredeterminados.add("Desfiles");
        filtrosPredeterminados.add("Exposiciones");
        filtrosPredeterminados.add("Ferias");
        filtrosPredeterminados.add("Cines");
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

    public String construirEnlace(Integer eventoId) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        int index = url.indexOf(uri);
        String newUrl = url.substring(0, index + 1);
        System.out.println(newUrl);
        return newUrl + "AgendaDiarioSur/faces/evento.xhtml?evento=" + eventoId;
    }

    public String placeholderFecha() {
        Random rnd = new Random(System.currentTimeMillis());

        // Crear dia random
        LocalDateTime date = LocalDateTime.now().plusDays(rnd.nextInt(10));
        date.plusHours(rnd.nextInt(24));

        // Formatear la salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String outDate = date.format(formatter);

        return outDate;
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

}
