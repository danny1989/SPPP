/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller.administracion;

import com.fiis.sppp.dao.util.MySQLUtil;
import com.fiis.sppp.model.Docente;
import com.fiis.sppp.model.Estado;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.model.Sustentacion;
import com.fiis.sppp.model.util.USeguimiento;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import com.fiis.sppp.util.IReportManager;
import com.fiis.sppp.util.PATHSPPP;
import com.fiis.sppp.util.SPPPID;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class EvaluacionController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    private List<USeguimiento> listaPracticantes;
    private Practicante practicante;
    private Sustentacion sustentacion;

    private List<Docente> listaJurados;

    public void inicializarLista() throws Exception {
        String hql = "select a.idPracticante.id as idPracticante,"
                + "a.idPracticante.codigoAlumno as codigoEstudiante,"
                + "a.idDocente.nombreCompleto as nombreDocente,"
                + "a.idPracticante.nombreCompleto as nombrePracticante,"
                + "a.idPracticante.tituloProyecto as tituloProyecto,"
                + "a.idPracticante.cambioTema as cambioTema,"
                + "a.idPracticante.fechaInicio as fechaInicio,"
                + "a.idPracticante.fechaFin as fechaFin,"
                + "(select sum(am.numeroMeses) from Ampliacion am where am.idPracticante.id=a.idPracticante.id) as mesesAmpliacion,"
                + "a.idPracticante.idEstado.nombre as nombreEstado from Asesor a where a.idPracticante.idEstado.id in (6,8,9)";
        //ESTADOS: EJECUCIÓN, INFORME, ANULACIÓN.
        listaPracticantes = generalService.listHQL(hql, null, USeguimiento.class);
        System.out.println(listaPracticantes);
    }

    public void getPracticateById(Integer idPracticante) throws Exception {
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);

        sustentacion = new Sustentacion();
        listaJurados = generalService.listHQL("from Docente d", null, null);
        Sustentacion sus = generalService.getByHQL("from Sustentacion s where s.idPracticante.id=" + idPracticante, null, null, false);
        if (sus != null) {
            sustentacion = sus;

        } else {
            String nombreAnio = "Año de la Diversificación Productiva y del Fortalecimiento de la Educación";
            String acuerdo = "ACUERDO Nº (id)-(anio)-CPPP-FIIS-UNAS";
            Integer anio = Calendar.getInstance().get(Calendar.YEAR);
            Integer idAsesor = generalService.getByHQL("select max(id) from Sustentacion", null, null, false);
            if (idAsesor == null) {
                idAsesor = 1;
            } else {
                idAsesor++;
            }

            acuerdo = acuerdo.replace("(id)", completarCeros(idAsesor));
            acuerdo = acuerdo.replace("(anio)", anio + "");

            sustentacion.setNumeroAcuerdo(acuerdo);
            sustentacion.setNombreAnio(Faces.getNombreAnio());
            sustentacion.setNombreDecano(Faces.getNombreDecano());
        }

        Faces.addMessage("DATOS DE PRACTICANTE CARGADOS...", "", FacesMessage.SEVERITY_INFO);
    }

    public void imprimirActa() throws Exception {
        if (sustentacion.getId() != null) {
            IReportManager r = new IReportManager(null);
            Map<String, Object> param = new HashMap<>();
            param.put("id_sustentacion", sustentacion.getId());
            r.setJasperPrint(PATHSPPP.RP_ACTA_SUSTENTACION, param, MySQLUtil.getJdbcPostGresSQL().getConnection());
            MySQLUtil.getJdbcPostGresSQL().closeConnection();
            r.exportarPDF("Acta_de_sustentacion");
        } else {
            Faces.addMessage("DEBE GUARDAR PRIMERO, PARA PODER DESCARGAR EL ACTA", "", FacesMessage.SEVERITY_INFO);
        }
    }
    
    public void imprimirAcuerdo() throws Exception {
        if (sustentacion.getId() != null) {
            IReportManager r = new IReportManager(null);
            Map<String, Object> param = new HashMap<>();
            param.put("id_sustentacion", sustentacion.getId());
            r.setJasperPrint(PATHSPPP.RP_ACUERDO_SUSTENTACION, param, MySQLUtil.getJdbcPostGresSQL().getConnection());
            MySQLUtil.getJdbcPostGresSQL().closeConnection();
            r.exportarPDF("Acuerdo_de_sustentacion");
        } else {
            Faces.addMessage("DEBE GUARDAR PRIMERO, PARA PODER DESCARGAR EL ACUERDO", "", FacesMessage.SEVERITY_INFO);
        }
    }

    private String completarCeros(Integer valor) {
        if (valor < 10) {
            return "00" + valor;
        } else if (valor < 100) {
            return "0" + valor;
        } else {
            return valor + "";
        }
    }

    public void guardarJurados() throws Exception {
        if (sustentacion.getId() == null) {
            sustentacion.setIdPracticante(practicante);
            generalService.save(sustentacion);
            practicante.setIdEstado(new Estado(SPPPID.Practicate.H_JURADOS_ASIGNADOS));

            generalService.update(practicante);
            Faces.addMessage("SE HA GENERADO LOS DATOS PARA LA SUSTENTACIÓN DE LAS PRÁCTICAS", "", FacesMessage.SEVERITY_INFO);
            System.out.println("Guardar Todo");
        } else {
            generalService.update(sustentacion);
            Faces.addMessage("SE HAN ACTUALIZADO LOS DATOS PARA LA SUSTENTACIÓN DE PRÁCTICAS", "", FacesMessage.SEVERITY_INFO);
        }

    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public List<USeguimiento> getListaPracticantes() {
        return listaPracticantes;
    }

    public void setListaPracticantes(List<USeguimiento> listaPracticantes) {
        this.listaPracticantes = listaPracticantes;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public List<Docente> getListaJurados() {
        return listaJurados;
    }

    public void setListaJurados(List<Docente> listaJurados) {
        this.listaJurados = listaJurados;
    }

    public Sustentacion getSustentacion() {
        return sustentacion;
    }

    public void setSustentacion(Sustentacion sustentacion) {
        this.sustentacion = sustentacion;
    }

}
