
package uma.informatica.sii.diarioSur.beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.entidades.Usuario;

/**
 *
 * @author ismael pineda
 */

@Named(value = "compraEventos")
@SessionScoped
public class compraEventos  implements Serializable {
    
    private Evento evento;              // Variable para datos del evento que se va a comprar
    private Usuario user;               // Mantenemos el usuario que está realizando la compra 
    private List<String> imagenes;      // Imágenes que se utilizarán para la vista 
    private Integer numEntradas;        // Numero de entradas que se van a comprar
    private Integer numTarjeta;         // Numero de la tarjeta que introducirá
    private Integer numSecreto;         // Numero secreto asociado a la tarjeta
    private boolean tarjetaValida;      // Si el número es válido se acepta la compra
    private UIComponent formulario;     // Formulario para los datos de la compra de entradas
    @Inject
    private ControlAutorizacion ctrl;
    
    
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    public Integer getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(Integer numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public Integer getNumSecreto() {
        return numSecreto;
    }

    public void setNumSecreto(Integer numSecreto) {
        this.numSecreto = numSecreto;
    }

    public boolean isTarjetaValida() {
        return tarjetaValida;
    }

    public void setTarjetaValida(boolean tarjetaValida) {
        this.tarjetaValida = tarjetaValida;
    }

    public UIComponent getFormulario() {
        return formulario;
    }

    public void setFormulario(UIComponent formulario) {
        this.formulario = formulario;
    }

    public ControlAutorizacion getCtrl() {
        return ctrl;
    }

    public void setCtrl(ControlAutorizacion ctrl) {
        this.ctrl = ctrl;
    }

  
    
    public String validarCompra() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if(!ctrl.sesionIniciada()){
	    FacesContext context = FacesContext.getCurrentInstance();
	    context.addMessage(formulario.getClientId(), new FacesMessage("No se ha iniciado sesión no se puede realizar la compra","No se ha iniciado sesión no realizar la compra"));
	}
       
        if(numTarjeta== null || numSecreto==null){
           ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Complete todos los campos", "Complete todos los campos"));
        }else if (numTarjeta>12 || numTarjeta < 12){
           ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de tarjeta no válido", "Numero de tarjeta no válido"));
        }else if(numSecreto<3 || numSecreto> 4){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de tarjeta no válido", "Numero de tarjeta no válido"));
        }else{
            evento.setNumero_entradas(evento.getNumero_entradas()-numEntradas);  // Actualizamos el numero de entradas disponibles para un evento
            
            for( int i=1 ;i<=numEntradas; i++){
               ctrl.getUsuario().añadirEntrada(evento.getEntradas().get(evento.getEntradas().size()+1));
            } 
            
           return null;
        }
         return null;
    }

    public String cancelarCompra(){
        //JSF no nos coge bien la url y por ello he tenido que ponerlo manual pero el método para regresar al evento es este 
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = "";
        url=request.getParameter("evento");
       //Metodo para cuando tengamos el evento en La BD         return "evento.xhtml?evento=" + eventoId;
       
       return null;
    }
    
}
