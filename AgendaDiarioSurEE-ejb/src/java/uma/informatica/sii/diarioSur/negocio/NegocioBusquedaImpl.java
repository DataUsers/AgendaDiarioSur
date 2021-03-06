/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author darylfed
 */
@Stateless
public class NegocioBusquedaImpl implements NegocioBusqueda {

    @PersistenceContext
    private EntityManager em;
    
    private final Evento.Tipo[] tipoEventos = Evento.Tipo.values();
    
    private final double RADIO_TIERRA = 6371.01; // en km
    private final double DISTANCIA = 5; // en km
    
    @Override
    public List obtenerEventos(int pagina, int maxEventos) throws DiarioSurException {

	Query query = em.createNamedQuery("eventosMasVisitados");

	query.setFirstResult(pagina * maxEventos);
	query.setMaxResults(maxEventos+1); // Para la comprobacion de mas paginas

	List results = query.getResultList();

	return results;
    }
    
    @Override
    public List busquedaEventos(int pagina, int maxEventos, List filtros, String query, boolean filtrarMasVisitados) throws DiarioSurException {
	Query q;
        
        if (filtros.isEmpty()) {
	    if (query.length() <= 0) {
		q = em.createNamedQuery("eventosMasVisitados");
                q.setMaxResults(maxEventos+1);
		q.setFirstResult(pagina * maxEventos);
                
		return q.getResultList();//obtenerEventos(pagina, maxEventos);
	    } else {
                
                // Filtrar por mas visitados o no
                if(filtrarMasVisitados){
                    q = em.createNamedQuery("busquedaMasVisitados");
                }else{
                    q = em.createNamedQuery("busqueda");
                }
                
		q.setParameter("query", query);
		q.setMaxResults(maxEventos+1);
		q.setFirstResult(pagina * maxEventos);

		return q.getResultList();
	    }
	} else {
	    String queryString = "select e from Evento e where ";

	    ListIterator<Evento.Tipo> iterator = filtros.listIterator();
	    while (iterator.hasNext()) {
		Evento.Tipo filtro = iterator.next();
		String indexFiltro = obteneInteger(filtro).toString();
		queryString += "e.tipoEvento = " + indexFiltro;

		if (iterator.hasNext()) {
		    queryString += " or ";
		}
	    }

	    if (query.length() > 0) {
		queryString += " and Upper(e.nombre) like Upper(concat('%', concat('" + query + "', '%')))";
	    }
            
            if(filtrarMasVisitados){
                queryString += " order by e.numeroVisitas DESC";
            }

	    // Crear query
	    q = em.createQuery(queryString);
	    q.setMaxResults(maxEventos+1);
	    q.setFirstResult(pagina * maxEventos);

	    return q.getResultList();
	}
    }

    @Override
    public List busquedaEventos(int pagina, int maxEventos, List filtros, String query, double latitud, double longitud, boolean filtrarMasVisitados) throws DiarioSurException {
        GeoLocation localizacion = GeoLocation.fromDegrees(latitud, longitud);
	List eventosObtenidos = filtrarGeolocalizacion(
		busquedaEventos(pagina, maxEventos, filtros, query, filtrarMasVisitados), 
		localizacion
	);
	
	return eventosObtenidos;
    }
    
    private Integer obteneInteger(Evento.Tipo tipoABuscar){
	//O(n), tambien deberia de throw a exception si no lo encuentra
	int index = 0;
	for(;index < tipoEventos.length; ++index){
	    if(tipoEventos[index].equals(tipoABuscar)){
		break;
	    }
	}
	return index;
    }
    
    private List filtrarGeolocalizacion(List eventos, GeoLocation localizacion){
	List<Evento> eventosFiltrados = new ArrayList<>();
	List<Evento> listaEventos = (List<Evento>) eventos;
	
	double latitudBusq = localizacion.getLatitudeInRadians();
	double longitudBusq = localizacion.getLongitudeInRadians();
	
	GeoLocation maxMinCoord[] = localizacion.boundingCoordinates(DISTANCIA, RADIO_TIERRA);
	GeoLocation minCoord = maxMinCoord[0];
	GeoLocation maxCoord = maxMinCoord[1];
	
	double minLat = minCoord.getLatitudeInRadians();
	double maxLat = maxCoord.getLatitudeInRadians();
	double minLon = minCoord.getLongitudeInRadians();
	double maxLon = maxCoord.getLongitudeInRadians();
	
	for(Evento evento : listaEventos){
	    
	    double eventLat = evento.getLatitud();
	    double eventLon = evento.getLongitud();
            
	    if( (eventLat >= minLat && eventLat <= maxLat) &&
		    (eventLon >= minLon && eventLon <= maxLon) &&
		    Math.acos(Math.sin(latitudBusq) * Math.sin(eventLat) 
			    + Math.cos(latitudBusq) * Math.cos(eventLat) 
				    * Math.cos(eventLon - longitudBusq)) <= DISTANCIA/RADIO_TIERRA){
		// Add evento a la lista
		eventosFiltrados.add(evento);
	    }
	}
	
	return eventosFiltrados;
    }

}
