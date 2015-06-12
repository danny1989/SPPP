/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.controller.administracion;

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
public class ConfiguracionController implements Serializable{
    
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
    
    
    
}
