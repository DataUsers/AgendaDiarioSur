
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject; 
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import uma.informatica.sii.diarioSur.entidades.CalificacionEvento;
import uma.informatica.sii.diarioSur.entidades.EntradaEvento;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Publicidad;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.NegocioCompra;
import uma.informatica.sii.diarioSur.negocio.NegocioPerfil;

/**
 *
 * @author ismael pineda
 */

@Named(value = "compra")
@SessionScoped
public class compraEventosBean  implements Serializable {
    
    @Inject
    private ControlAutorizacion ctrl;
    
    @EJB
    private NegocioCompra comprar;
    
    
    private Evento evento;                     // Variable para datos del evento que se va a comprar
    private Usuario user;                      // Mantenemos el usuario que está realizando la compra 
    private String nombreEvento;               // Nombre del evento 
    private List<String> imagenes;             // Imágenes que se utilizarán para la vista 
    private Integer numEntradas;               // Numero de entradas que se van a comprar
    private Integer numEntradasSeleccionadas;  //
    private String numTarjeta;                 // Numero de la tarjeta que introducirá
    private String numSecreto;                 // Numero secreto asociado a la tarjeta 
    private String precioEntrada;
    private String currentUrl;                 //
    private String currentURI;
    private String eventId;
    private final FacesContext ctx = FacesContext.getCurrentInstance();
   
   
    
    
      
    public String cancelarCompra(){
       
       return  "evento.xhtml?evento=" + evento.getIdEvento() + "&faces-redirect=true";
    }
    
    public String validarCompra() {
       /*
       HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       String id=request.getParameter("evento");
       ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, id,id));
       evento= comprar.obtenerEvento(id);
       if(validarDatos() && (numEntradas > numEntradasSeleccionadas)){
         comprar.generarEntradas()*/
        

       /*
      
            //comprar.generarEntradas(evento,numEntradasSeleccionadas,);
            return null;
        }else{
	*/
         return null;
       }
  
    public void generarEntradas(Evento envento, Integer numEntradasSeleccionadas){
        evento.setNumero_entradas(evento.getNumero_entradas()-numEntradasSeleccionadas);
       List<EntradaEvento> entradas = evento.getEntradas();
           for(int num=0; num<numEntradasSeleccionadas; num++){
                EntradaEvento nuevaEntrada = new EntradaEvento();
                nuevaEntrada.setEvento(evento);
                nuevaEntrada.setFechaCompra((java.sql.Date) new Date());
                /*nuevaEntrada.setFechaValidez(fechaSeleccionada);
                nuevaEntrada.setFormaPago(nombreEvento);
                Long idEntrada = 0;
                for(EntradaEvento n:entradas){
                 if(n.getFechaValidez().equals(fechaSeleccionada)){
                     idEntrada++;
                 }
                }
                nuevaEntrada.setIdEntrada( idEntrada+1);
                */
                nuevaEntrada.setIdEntrada( (long)  entradas.size()+1);
                Integer precio= Integer.parseInt(precioEntrada);
                nuevaEntrada.setPrecio(numEntradasSeleccionadas*precio );
                nuevaEntrada.setUsuario(ctrl.getUsuario());
                entradas.add(nuevaEntrada);
           }
             evento.setEntradas(entradas);
        
    }
    
    public boolean validarDatos(){
       boolean valido = false;
       
       if(numTarjeta== null || numSecreto==null){
           ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Complete todos los campos", "Complete todos los campos"));
        }else if (numTarjeta.length()>12 || numTarjeta.length() < 12){
           ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de tarjeta no válido", "Numero de tarjeta no válido"));
        }else if(numSecreto.length()<3 || numSecreto.length()> 4){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de tarjeta no válido", "Numero de tarjeta no válido"));
        }else{    
           valido= true;
        }
       return valido;
    }
    
}
