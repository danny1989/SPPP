/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.controller.administracion;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import www.fitcoders.com.file.ManejoArchivo;
import www.fitcoders.com.model.util.Configuracion;
import www.fitcoders.com.util.Faces;

/**
 *
 * @author Max
 */
@ManagedBean
@SessionScoped
public class ConfiguracionController implements Serializable {

    private Configuracion configuracion;

    public void inicializarDatosDeConfiguracion() throws Exception {
        configuracion = ManejoArchivo.setFileJsonToClass(Configuracion.class, "/resources/configuracion/configuracion.json");
        System.out.println(configuracion.getAnio());
        System.out.println(configuracion.getDecano());
    }

    public void guardarDatosConfiguracion() throws Exception {
        ManejoArchivo.setClassToFileJson(configuracion, "/resources/configuracion/configuracion.json");
        Faces.addMessage("CONFIGURACIÓN GUARDADA CON ÉXITO...!", "", FacesMessage.SEVERITY_INFO);
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    private String nombreDecano;
    private String nombreAnio;

    public void inicializarConfiguracionGeneral() {
        nombreDecano = "";
        nombreAnio = "";
        String archivo = ManejoArchivo.getTextoOfFile(Faces.getRealPath(File.separatorChar + "resources" + File.separatorChar + "file" + File.separatorChar + "config.txt"));
        System.out.println(archivo + "......");
        if (archivo == null) {
            archivo = "";
        }

        if (!archivo.equals("")) {
            String[] files = archivo.split("=");
            nombreDecano = files[0];
            nombreAnio = files[1];
        }

    }

    public void guardarConfiguracionGeneralDelSistema() throws IOException {

        ManejoArchivo.escribirTextoArchivo(Faces.getRealPath(File.separatorChar + "resources" + File.separatorChar + "file" + File.separatorChar + "config.txt"), nombreDecano + "=" + nombreAnio);
        Faces.addMessage("SE HA GUARDADO LA CONFIGURACIÓN GENERAL.", "", FacesMessage.SEVERITY_INFO);
    }
    
    public String getNombreDecano() {
        return nombreDecano;
    }

    public void setNombreDecano(String nombreDecano) {
        this.nombreDecano = nombreDecano;
    }

    public String getNombreAnio() {
        return nombreAnio;
    }

    public void setNombreAnio(String nombreAnio) {
        this.nombreAnio = nombreAnio;
    }

}
