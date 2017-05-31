
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject; 
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioCompra;

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
    
    
    private Evento  evento;                     // Variable para datos del evento que se va a comprar
    private String  idEvento;
    private Usuario usuario;                    // Mantenemos el usuario que está realizando la compra 
    private String  nombreEvento;               // Nombre del evento 
    private String  fecha;                      // Fecha en la que se celebra el evento.
    private String  fotoEvento;                 // Imágenes que se utilizarán para la vista 
    private String  descripcionEvento;          // Descripción del evento a mostrar
    private String  tipoEvento;                 // Tipo de evento que mostramos
    private Integer numEntradas;                // Numero de entradas de las que se disponen
    private Integer numEntradasSeleccionadas;   // Numero de entradas seleccionadas para la compra
    private String  numTarjeta;                 // Numero de la tarjeta que introducirá
    private String  numSecreto;                 // Numero secreto asociado a la tarjeta 
    private Integer  precioEntrada;
    
    private final   FacesContext ctx = FacesContext.getCurrentInstance();
   
     
    public void cargarDatos() throws DiarioSurException{
        setUsuario(ctrl.getUsuario());
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idEvento = request.getParameter("evento");
        evento= comprar.obtenerEvento(idEvento);
        setNombreEvento(evento.getNombre());
        setNumEntradas(evento.getNumero_entradas());
        setPrecioEntrada(evento.getPrecio());
        setTipoEvento(evento.getTipoEvento().toString());
        /*
        // Forma de escoger las fechas posteriroes a la actual.
        boolean encontrada = false;
        Date primeraFecha = null;
        for(Date d:evento.getFechas())  {
             if((d.after(new Date())) && !encontrada ) {
                 primeraFecha=d;
                 encontrada=true;
             }
        }*/
        DateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
        setFecha(formato.format(evento.getFechas().get(0)));  
        
    }
    
    
    public String cancelarCompra(){
       return  "evento.xhtml?evento=" + evento.getIdEvento() + "&faces-redirect=true";
    }
    
    public String validarCompra() {
       
       ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, evento.getIdEvento().toString(), evento.getIdEvento().toString()));
       /*evento= comprar.obtenerEvento(id);
       if(validarDatos() && (numEntradas > numEntradasSeleccionadas)){
         comprar.generarEntradas()*/
       /*
      
            //comprar.generarEntradas(evento,numEntradasSeleccionadas,);
            return null;
        }else{
	*/
         return null;
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
    
    public String calcularPrecio(){
      return String.valueOf(numEntradasSeleccionadas*precioEntrada);
    }
    
    public Evento getEvento() throws DiarioSurException {
        return evento ;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        
        return ctrl.getUsuario();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreEvento() {
        return evento.getNombre();
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
   
    //Conseguimos la primera fecha para comprar una entrada para el evento
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFotoEvento() {
        return evento.getImagenes()[0];
    }

    public void setFotoEvento(String fotoEvento) {
        this.fotoEvento = fotoEvento;
    }

    public String getDescripcionEvento() {
        return evento.getDescripcion();
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getNumEntradas() {
        return evento.getNumero_entradas();
    }

    public void setNumEntradas(Integer numEntradas) {
        this.numEntradas = numEntradas;
    }

    public Integer getNumEntradasSeleccionadas() {
        return numEntradasSeleccionadas;
    }

    public void setNumEntradasSeleccionadas (Integer numEntradasSeleccionadas) {
        this.numEntradasSeleccionadas = numEntradasSeleccionadas;
    }

    public String getTipoEvento() {
        return evento.getTipoEvento().toString();
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getNumSecreto() {
        return numSecreto;
    }

    public void setNumSecreto(String numSecreto) {
        this.numSecreto = numSecreto;
    }

    public Integer getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(Integer precioEntrada) {
        this.precioEntrada = precioEntrada;
    } 
    
}
