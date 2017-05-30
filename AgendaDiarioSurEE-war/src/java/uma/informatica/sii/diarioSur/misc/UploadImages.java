/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uma.informatica.sii.diarioSur.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import uma.informatica.sii.diarioSur.beans.EventoBean;
import uma.informatica.sii.diarioSur.entidades.Evento;

/**
 *
 * @author luism
 */
public class UploadImages {

    List<UploadedFile> imagenes;

    /**
     * Creates a new instance of UploadImages
     */
    public UploadImages() {
        imagenes = new ArrayList<>();
    }
    
    /**
     * Guarda las imagenes que se han subido en el cuadro
     * @param event
     */
    public void handleImage(FileUploadEvent event) {
        System.out.println("Soy el manejador de imagenes y he sido invocado");
        imagenes.add(event.getFile());
        System.out.println("Como manejador que soy he acabado mi mision uajajajaj " + imagenes.size());
    }

    public void save(Evento event) {
        String[] guardar = new String[imagenes.size()];
        int i = 0;
        for (UploadedFile imagen : imagenes) {
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

                guardar[i++] = dbPath;

            } catch (IOException ex) {
                Logger.getLogger(EventoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        event.setImagenes(guardar);
    }
}
