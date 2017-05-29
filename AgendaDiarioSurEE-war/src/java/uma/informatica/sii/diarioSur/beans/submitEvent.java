/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import uma.informatica.sii.diarioSur.entidades.Evento;
import uma.informatica.sii.diarioSur.misc.GeoLocUtils;
import uma.informatica.sii.diarioSur.misc.GeoLocation;
import uma.informatica.sii.diarioSur.negocio.DiarioSurException;
import uma.informatica.sii.diarioSur.negocio.NegocioEvento;

/**
 *
 * @author francis
 */
@Named(value = "submitEvent")
@RequestScoped
public class submitEvent {

    @EJB
    private NegocioEvento negocio;

    private Evento evento;
    @Inject
    private ControlAutorizacion ctrl;
    private String localizacion;
    private java.util.Date fecha;
    private Date fechaReal;
    private UploadedFile imagen;

    /**
     * Creates a new instance of Login
     */
    public submitEvent() throws IOException {
        evento = new Evento();
    }

    public Evento getEvento() {
        return evento;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }
    
    
    
    public SelectItem[] getTipoValues() {
        SelectItem[] items = new SelectItem[Evento.Tipo.values().length];
        int i = 0;
        for (Evento.Tipo g : Evento.Tipo.values()) {
            items[i++] = new SelectItem(g, g.toString().replace("_", " "));
        }
        return items;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String upload() {
        GeoLocation local;
        FacesContext ctx = FacesContext.getCurrentInstance();
        evento.setFechas(new ArrayList<>());
//        evento.getFechas().add(java.sql.Date.valueOf(fecha.toLocalDate()));
        fechaReal = new Date(fecha.getTime());
        evento.getFechas().add(fechaReal);
        //settear geolocalizacion
        local = GeoLocUtils.obtenerCoordenadas(localizacion);
        evento.setLatitud(local.getLatitudeInRadians());
        evento.setLongitud(local.getLongitudeInRadians());
        evento.setDireccion(localizacion);
        evento.setImagenes(new String[1]);

        if (evento.getNombre().trim().length() == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre es obligatorio", "El nombre es obligatorio"));
            return null;
        }

        if (imagen != null) {
            System.out.println("Hay una imagen: " + imagen.getFileName());

            try {

                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

                String path = ec.getRealPath("/") + File.separator + "resources" + File.separator
                        + "imagenes" + File.separator + imagen.getFileName();

                System.out.println("path: " + path);
                String dbPath = "resources" + File.separator + "imagenes" + File.separator + imagen.getFileName();
                System.out.println("dbpath: " + dbPath);

                //String webContentRoot = ec.getRealPath("/");
                //System.out.println(webContentRoot);
                InputStream input = imagen.getInputstream();
                File targetFile = new File(path);

                System.out.println("path targetFile: " + targetFile.getAbsolutePath());
                FileOutputStream targetStream = new FileOutputStream(targetFile);
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = input.read(buffer)) != -1) {
                    targetStream.write(buffer, 0, bytesRead);
                    System.out.println("Leidos " + bytesRead);
                }

                // Cerrar los streams?
                evento.getImagenes()[0] = dbPath;
            } catch (IOException ex) {
                Logger.getLogger(EventoBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            System.out.println("Eeeeeeeeeeeeeeeeeeeeeeees nulo");
        }

        try {
            negocio.insertarEvento(evento);
        } catch (DiarioSurException ex) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Evento existente", "El evento ya existia en la base de datos"));
            return null;
        }
        return "evento.xhtml?evento=" + evento.getIdEvento() + "&faces-redirect=true";
    }

}
