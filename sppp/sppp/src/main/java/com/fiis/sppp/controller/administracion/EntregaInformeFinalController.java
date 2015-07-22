/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.controller.administracion;

import com.fiis.sppp.dao.util.MySQLUtil;
import com.fiis.sppp.model.Estado;
import com.fiis.sppp.model.InformeFinal;
import com.fiis.sppp.model.Oficio;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import com.fiis.sppp.util.IReportManager;
import com.fiis.sppp.util.PATHSPPP;
import com.fiis.sppp.util.SPPPID;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class EntregaInformeFinalController implements Serializable {
    
    @ManagedProperty("#{generalService}")
    GeneralService generalService;
    
    private Practicante practicante;
    private InformeFinal informeFinal;
    
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }
    
    public void getPracticateById(Integer idPracticante) throws Exception {
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        
        informeFinal = new InformeFinal();
        informeFinal.setIdOficio(new Oficio());
        informeFinal.setIdPracticante(practicante);
        informeFinal.setNombreAnio(Faces.getNombreAnio());
        informeFinal.setNombreDecano(Faces.getNombreDecano());
    }

    public void imprimirInforme( ) throws Exception {
        String notaFinal = "select max(s.nota) from SustentacionNota s "
                + "where s.idSustentacion.idPracticante.id="+practicante.getId();
        
        Integer notaFin = generalService.getByHQL(notaFinal, null, null, false);
        String paramNota = "";
        if(notaFin >= 11){
            paramNota = notaFin + " " + nombreNota(notaFin) + " (APROBADA)";
        } else {
            paramNota = notaFin + " " + nombreNota(notaFin) + " (DESAPROBADA)";
        }
        System.out.println(paramNota);   
        System.out.println(informeFinal.getId());
        if (informeFinal.getId() != null) {
            IReportManager r = new IReportManager(null);
            Map<String, Object> param = new HashMap<>();
            param.put("nota", paramNota);
            param.put("id_informe_final", informeFinal.getId());
            r.setJasperPrint(PATHSPPP.RP_REMISION_PRACTICAS_PPP, param, MySQLUtil.getJdbcPostGresSQL().getConnection());
            MySQLUtil.getJdbcPostGresSQL().closeConnection();
            r.exportarPDF("Remision_de_Informe");
        } else {
            Faces.addMessage("DEBE GUARDAR PRIMERO, PARA PODER DESCARGAR EL ACTA", "", FacesMessage.SEVERITY_INFO);
        }
    }
    
    public void guardarInformeFinal() throws Exception {
        informeFinal.setFechaRegistro(new Date());
        if (file != null) {
            if (file.getFileName().equals("")) {
                informeFinal.setUrlArchivo(null);
            }
            ///////AGINACION DE NOMBRE DE OFICIO
            int anio = Calendar.getInstance().get(Calendar.YEAR);
            Integer idOficio = (Integer)generalService.save(informeFinal.getIdOficio());
            informeFinal.getIdOficio().setId(idOficio);
            String nombreOf = "Oficio Nº {{id}}-{{anio}}-CPPP-FIIS-UNAS";
            nombreOf = nombreOf.replace("{{id}}", idOficio + "");
            nombreOf = nombreOf.replace("{{anio}}", anio + "");
            informeFinal.getIdOficio().setNombreOficio(nombreOf);
            generalService.update(informeFinal.getIdOficio());
            ///////////////////
            
            Integer idInf = (Integer)generalService.save(informeFinal);
            informeFinal.setId(idInf);
            
            
            if (!file.getFileName().equals("")) {
                informeFinal.setUrlArchivo(idInf + file.getFileName());
                copyFile(informeFinal.getUrlArchivo(), file.getInputstream());
                generalService.update(informeFinal);
            }
            
            practicante.setIdEstado(new Estado(SPPPID.Practicate.J_PRACTICA_CULMINADA));
            generalService.update(practicante);
            
            FacesMessage message = new FacesMessage("Los datos fueron cargados con éito", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            
        }
    }
    
    public void copyFile(String fileName, InputStream in) {
        try {

            String destination = Faces.getRealPath("resources" + File.separatorChar + "biblioteca" + File.separatorChar);
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
    
    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public InformeFinal getInformeFinal() {
        return informeFinal;
    }

    public void setInformeFinal(InformeFinal informeFinal) {
        this.informeFinal = informeFinal;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public String nombreNota(Integer nota) {
        if(nota == 1) {
            return "UNO";
        } else if(nota == 2) {
            return "DOS";
        } else if(nota == 3) {
            return "TRES";
        } else if(nota == 4) {
            return "CUATRO";
        } else if(nota == 5) {
            return "CINCO";
        } else if(nota == 6) {
            return "SEIS";
        } else if(nota == 7) {
            return "SIETE";
        } else if(nota == 8) {
            return "OCHO";
        } else if(nota == 9) {
            return "NUEVE";
        } else if(nota == 10) {
            return "DIEZ";
        } else if(nota == 11) {
            return "ONCE";
        } else if(nota == 12) {
            return "DOCE";
        } else if(nota == 13) {
            return "TRECE";
        } else if(nota == 14) {
            return "CATORCE";
        } else if(nota == 15) {
            return "QUINCE";
        } else if(nota == 16) {
            return "DIEZ Y SEIS";
        } else if(nota == 17) {
            return "DIEZ Y SIETE";
        } else if(nota == 18) {
            return "DIEZ Y OCHO";
        } else if(nota == 19) {
            return "DIEZ Y NUEVE";
        }else if(nota == 20) {
            return "VEINTE";
        } else {
            return "";
        }
    }
}
