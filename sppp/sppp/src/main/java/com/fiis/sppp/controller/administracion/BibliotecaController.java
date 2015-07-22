/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.controller.administracion;

import com.fiis.sppp.model.InformeFinal;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class BibliotecaController implements Serializable {
    
    @ManagedProperty("#{generalService}")
    GeneralService generalService;
    private StreamedContent fileDownload;
    
    private List<InformeFinal> listaInformes;

    public void cargarBiblioteca() throws Exception {
        listaInformes = generalService.listHQL("from InformeFinal e", null, null);
    }
    
    public void prepararArchivoParaDescarga(String nombreArchivo) {
//        String destination = Faces.getRealPath("resources" + File.separatorChar + "biblioteca" );
//        destination = destination + File.separatorChar + nombreArchivo;
        System.out.println(nombreArchivo);
        InputStream stream = Faces.getExternalContext().getResourceAsStream("/resources/biblioteca/" + nombreArchivo);
        fileDownload = new DefaultStreamedContent(stream, "application/pdf", nombreArchivo);
    }
    
    public List<InformeFinal> getListaInformes() {
        return listaInformes;
    }

    public void setListaInformes(List<InformeFinal> listaInformes) {
        this.listaInformes = listaInformes;
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public StreamedContent getFileDownload() {
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }
    
    
    
}
