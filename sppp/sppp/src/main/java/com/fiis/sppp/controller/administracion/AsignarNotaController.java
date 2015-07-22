/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller.administracion;

import com.fiis.sppp.model.Estado;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.model.Sustentacion;
import com.fiis.sppp.model.SustentacionNota;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class AsignarNotaController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    private Practicante practicante;
    private Sustentacion sustentacion;
    private SustentacionNota sustentacionNota;

    private boolean guardar;

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void guardarSustentacionNota() throws Exception {
        if (file != null) {
            if (file.getFileName().equals("")) {
                sustentacionNota.setUrlArchivo(null);
            }
            if (sustentacionNota.getNota() >= 11) {
                sustentacionNota.setEstado("APROBADO");
            } else {
                sustentacionNota.setEstado("DESAPROBADO");
            }
            sustentacionNota.setIdSustentacion(sustentacion);
            Integer idSusNota = (Integer) generalService.save(sustentacionNota);
            sustentacionNota.setId(idSusNota);
            if (sustentacionNota.getNota() >= 11) {
                practicante.setIdEstado(new Estado(9));
                generalService.update(practicante);
            }
            if (!file.getFileName().equals("")) {
                sustentacionNota.setUrlArchivo(idSusNota + file.getFileName());
                copyFile(sustentacionNota.getUrlArchivo(), file.getInputstream());
                generalService.update(sustentacionNota);
            }
            cargarDetallePracticante(practicante.getId());
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        sustentacionNota = new SustentacionNota();
    }

    public void copyFile(String fileName, InputStream in) {
        try {

            String destination = Faces.getRealPath("resources" + File.separatorChar + "file" + File.separatorChar);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + File.separatorChar + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cargarDetallePracticante(Integer idPracticante) throws Exception {

        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);

        sustentacion = generalService.getByHQL("from Sustentacion s where idPracticante.id=" + idPracticante, null, null, false);
        String hql = "select max(s.nota) from SustentacionNota s where s.idSustentacion.id=" + sustentacion.getId();
        Integer totalNota = generalService.getByHQL(hql, null, null, false);
        System.out.println(totalNota);
        if (totalNota == null) {
            totalNota = 0;
        }
        Long totalNotas = generalService.getByHQL("select count(s.id) from SustentacionNota s where s.idSustentacion.id=" + sustentacion.getId(), null, null, false);

        if (totalNotas >= 3) {
            guardar = false;
        } else {
            guardar = true;
            if (totalNota >= 11) {
                guardar = false;
            } else {
                guardar = true;
            }
        }

        sustentacionNota = new SustentacionNota();

        List<SustentacionNota> lista = generalService.listHQL("from SustentacionNota s where s.idSustentacion.id=" + sustentacion.getId(), null, null);
        sustentacion.setSustentacionNotaList(lista);
    }

    private StreamedContent fileDownload;
    private String nombreArchivo;

    public void prepararArchivoParaDescarga(String nombreArchivo) {
        String destination = Faces.getRealPath("resources" + File.separatorChar + "file" );
        destination = destination + File.separatorChar + nombreArchivo;
        
        InputStream stream = Faces.getExternalContext().getResourceAsStream("/resources/file/" + nombreArchivo);
        fileDownload = new DefaultStreamedContent(stream, "image/jpg", "archivo.jpg");
    }
//    public FileDownloadView() {        
//        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/demo/images/optimus.jpg");
//        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
//    }

    public StreamedContent getFileDownload() {
        return fileDownload;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public Sustentacion getSustentacion() {
        return sustentacion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void setSustentacion(Sustentacion sustentacion) {
        this.sustentacion = sustentacion;
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public SustentacionNota getSustentacionNota() {
        return sustentacionNota;
    }

    public void setSustentacionNota(SustentacionNota sustentacionNota) {
        this.sustentacionNota = sustentacionNota;
    }

    public boolean getGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

}
