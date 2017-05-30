/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import uma.informatica.sii.diarioSur.entidades.Usuario;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioPerfil;



/**
 *
 * @author ismae
 */
 
@Named(value = "perfil")
@RequestScoped
public class PerfilBean {
    
 
    @Inject
    private ControlAutorizacion ctrl;
    
    @EJB
    private NegocioPerfil profile;
 
    private String nombre;
    private String apellidos;
    private String idEmail;                        // Variable para pasarsela a los metodos del EJB pues email está a disabled
    private String email;  
    private String tipoUsuario; 
    private String contrasenaAntigua;
    private String nuevaContrasena;
    private String confirmacionContrasena; 
    private Date nacimiento;
    private java.util.Date fechaAuxiliar;
    private java.util.Date fechaNacimiento;
    private String cuentaTwitter;
    private String cuentaFacebook;
    private final FacesContext ctx = FacesContext.getCurrentInstance();
   
    //Metodo para cambiar los datos del perfil
    public String validarCambiosPerfil() throws DiarioSurException, ParseException  {
      
        if(validarDatos()){  //Si todos los datos introducidos son validos los cambiaremos 
          idEmail = ctrl.getUsuario().getEmail();
          profile.guardarCambios(nombre, apellidos, nacimiento, idEmail , cuentaTwitter, cuentaFacebook);
          ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos cambiados con éxito", "Datos cambiados con éxito"));
          ctrl.setUsuario(profile.obtenerUsuario(idEmail));
          return "perfil.xhtml"; 
          
        }else{
           return null; 
        }  
    }

    
    public String cambiarContrasenia() throws DiarioSurException {
        
        boolean valido = validarContraseña();
        
        if(valido){
            idEmail = ctrl.getUsuario().getEmail();
            try{
            profile.guardarContraseña(idEmail, contrasenaAntigua, nuevaContrasena);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña cambiada con éxito", "Contraseña cambiada con éxito"));
            }catch(DiarioSurException e){
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña cambiada con éxito", "Contraseña cambiada con éxito"));
                return null;
            }
            ctrl.setUsuario(profile.obtenerUsuario(idEmail));
            return "perfil.xhtml";
        }else{
           return null;
        }
    }
    
    
    //Metodo para validar los datos del formulario en caso de que se hayan rellenado
    public Boolean validarDatos() throws ParseException{
        
        boolean valido= false;
           
        if (nombre.length() < 3){
          
           ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR : Introduzca un nombre valido", " ERROR : Introduzca un nombre valido"));          
            
        }else if(apellidos.length()!=0 && apellidos.length() < 5 ){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR : Debe introducir apellidos válidos", " ERROR : Debe introducir apellidos válidos"));          
        
        }else if(cuentaTwitter!= null && cuentaTwitter.length()>0 ){
            if(!cuentaTwitter.matches("((http|https)://)?(www[.])?twitter.com/.+") || !cuentaTwitter.matches("(www[.])?twitter.com/.+") ){
             ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR : Cuenta de Twitter no válida","ERROR : Cuenta de Twitter no válida"));
            }else{
             valido=true;
            } 
            
        }else if(cuentaFacebook!=null && cuentaFacebook.length()>0){
            if(!cuentaFacebook.matches("((http|https)://)?(www[.])?facebook.com/.+") || !cuentaFacebook.matches("(www[.])?facebook.com/.+") ){
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR : Cuenta de Facebook no válida", "ERROR : Cuenta de Facebook no válida"));
            }else{
             valido=true;
            }
        }else if(fechaNacimiento!=null){
          nacimiento = new Date(fechaNacimiento.getTime()); 
          fechaAuxiliar = new java.util.Date();
          if(nacimiento.before(fechaAuxiliar)){
                   valido=true;
          }else{
          ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Introduzca una fecha anterior a la actual " ,  "Introduzca una fecha anterior a la actual " ));
          }

        }else{
           valido=true;
        }
        return valido;
    }  
    
    
  // Metodo para validar los datos de la nueva contraseña     
  public boolean validarContraseña(){
   boolean valido = false;

        if (nuevaContrasena.length() == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR : Introduzca una nueva contraseña válida", "ERROR :Introduzca una nueva contraseña válida"));
           
        }else if (nuevaContrasena.length() < 6) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR : Contraseña demasiado insegura", "ERROR : Contraseña demasiado insegura"));
        
        }else if (!nuevaContrasena.equals(confirmacionContrasena)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR : La confirmacion no coincide con la contraseñas nueva", "ERROR : La confirmacion no coincide con la contraseñas nueva"));

        }else{
            valido = true;
        }
        
   return valido;   
  }

  
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setContrasenaAntigua(String contrasenaAntigua) {
        this.contrasenaAntigua = contrasenaAntigua;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public void setConfirmacionContrasena(String confirmacionContrasena) {
        this.confirmacionContrasena = confirmacionContrasena;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCuentaTwitter(String cuentaTwitter) {
        this.cuentaTwitter = cuentaTwitter;
    }

    public void setCuentaFacebook(String cuentaFacebook) {
        this.cuentaFacebook = cuentaFacebook;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public java.util.Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(java.util.Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
  
  

    public String getNombre() {
       
        return  ctrl.getUsuario().getNombre();
    }

    public String getApellidos() {
       return  ctrl.getUsuario().getApellidos();
    }

    public String getTipoUsuario(){
      return ctrl.getUsuario().getTipoUsuario().toString();
    }
    
    public String getEmail() {
        return ctrl.getUsuario().getEmail();
    }
    
    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

  
    public String getConfirmacionContrasena() {
        return confirmacionContrasena;
    }

    public String getCuentaTwitter() {
        return ctrl.getUsuario().getCuentaTwitter();
    }

    public String getCuentaFacebook() {
        return ctrl.getUsuario().getCuentaFacebook();
    }

    public String getContrasenaAntigua() {
        return contrasenaAntigua;
    }
    
}
