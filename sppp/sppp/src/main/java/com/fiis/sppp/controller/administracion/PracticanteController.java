/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller.administracion;

import com.fiis.sppp.dao.util.MySQLUtil;
import com.fiis.sppp.model.Asesor;
import com.fiis.sppp.model.Docente;
import com.fiis.sppp.model.ObservacionPracticante;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.model.PracticanteFuncion;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.EmailSenderService;
import com.fiis.sppp.util.Faces;
import com.fiis.sppp.util.IReportManager;
import com.fiis.sppp.util.PATHSPPP;
import com.fiis.sppp.util.SPPPID;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class PracticanteController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    //<editor-fold defaultstate="collapsed" desc=" inicializacion de datos y otros ">
    private List<Practicante> listaPracticantes;
    private Practicante practicante;
    private PracticanteFuncion practicanteFuncion;
    private ObservacionPracticante observacionPracticante;
    private List<Docente> listaDocentes;
    //////////////////////////////////////////

    public void iniciarValoresLista() throws Exception {
        listaPracticantes = generalService.listHQL("from Practicante p where p.idEstado.id in (1,2,3,4)", null, null);
    }

    public void iniciarValorInstancia() {
        practicante = new Practicante();
        practicante.setPracticanteFuncionList(new ArrayList<PracticanteFuncion>());
    }

    public void iniciarInstanciaPracticanteFuncion() {
        practicanteFuncion = new PracticanteFuncion();
    }

    public void instanciarNuevaObservacion() {
        observacionPracticante = new ObservacionPracticante();
    }

    public void eliminarPracticanteFuncion(int index) throws Exception {
        PracticanteFuncion pf = practicante.getPracticanteFuncionList().remove(index);
        if (pf.getId() != null) {
            generalService.delete(pf);
        }
        Faces.addMessage("La función ha sido eliminada!", "", FacesMessage.SEVERITY_WARN);
    }
    
    public void guardarNumeroResolucion() throws Exception {
        if(asesor.getId() != null) {
            generalService.update(asesor);
            Faces.addMessage("EL NÚMERO DE RESOLUCIÓN A SIDO ACTUALIZADO", "", FacesMessage.SEVERITY_INFO);
        } else {
            Faces.addMessage("ASESOR NO HA SIDO ASIGNADO AÚN....!", "", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cargarParaEdicionPracticante(Integer idPracticante) throws Exception {
        System.out.println(idPracticante);
        String hql = "from Asesor a where a.idPracticante.id=" + idPracticante;
        asesor = generalService.getByHQL(hql, null, null, false);
        if(asesor == null) {
            asesor = new Asesor();
        }
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);

        List<PracticanteFuncion> listaPF = generalService.listHQL("from PracticanteFuncion p where p.idPracticante.id=" + practicante.getId(), null, null);
        List<ObservacionPracticante> listaOP = generalService.listHQL("from ObservacionPracticante o where o.idPracticante.id=" + practicante.getId(), null, null);
        practicante.setPracticanteFuncionList(listaPF);
        practicante.setObservacionPracticanteList(listaOP);
    }

    public void guardarObservacionPracticante() throws Exception {
        observacionPracticante.setIdPracticante(practicante);
        practicante.getObservacionPracticanteList().add(observacionPracticante);
        observacionPracticante.setActivo(true);

        if (practicante.getId() != null) {
            Integer idPF = (Integer) generalService.save(observacionPracticante);
            observacionPracticante.setId(idPF);
        }
        Faces.addMessage("NUEVA OBSERVACIÓN AÑADIDA", "", FacesMessage.SEVERITY_INFO);
    }

    public void guardarPracticaAlumno() throws Exception {
        generalService.update(practicante);
        Faces.addMessage("LAS PRÁCTICAS DEL ALUMNO FUERON ACTUALIZADAS...", "", FacesMessage.SEVERITY_INFO);

        iniciarValoresLista();
    }

    public void calcularFinPracticas() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(practicante.getFechaInicio());

        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 3);

        practicante.setFechaFin(cal.getTime());
        System.out.println(practicante.getFechaFin());
    }

    public void buscarPracticanteWS() {
        System.out.println("Buscando prancitcante en WS");
        practicante.setNombreCompleto("NOMBRE DEL PRACTICANTE");
        practicante.setCreditosAprobados(160);
        practicante.setFechaRegistro(new Date());
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" ASIGNACION DE JURADO AL PRACTICANTE ">
    private Asesor asesor;
    private boolean enviarCorreo;

    //datos para el asesor
    public void cargarDatosParaAsesor(Integer idPracticante) throws Exception {
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        listaDocentes = generalService.listHQL("from Docente d", null, null);

        asesor = generalService.getByHQL("from Asesor a where a.idPracticante.id=" + idPracticante, null, null, false);
        if (asesor == null) {
            asesor = new Asesor();
            String nombreAnio = "Año de la Diversificación Productiva y del Fortalecimiento de la Educación";
            String acuerdo = "ACUERDO Nº (id)-(anio)-CPPP-FIIS-UNAS";
            Integer anio = Calendar.getInstance().get(Calendar.YEAR);
            Integer idAsesor = generalService.getByHQL("select max(id) from Asesor", null, null, false);
            if (idAsesor == null) {
                idAsesor = 1;
            } else {
                idAsesor++;
            }

            acuerdo = acuerdo.replace("(id)", completarCeros(idAsesor));
            acuerdo = acuerdo.replace("(anio)", anio + "");

            asesor.setNumeroAcuerdo(acuerdo);
            asesor.setNumeroSolicitud("N° ");
            asesor.setNombreDecano("MSc. Cesar Lindo Pizarro");
            asesor.setNombreAnio(nombreAnio);
        }

    }

    public void guardarAsesorAlPracticante() throws Exception {
        if (asesor.getId() == null) {
            asesor.setIdPracticante(practicante);
            asesor.setFechaRegistro(new Date());
            String changeEstate = "update practicante set id_estado=(idEstado) where id=" + practicante.getId();
            changeEstate = changeEstate.replace("(idEstado)", SPPPID.Practicate.E_ASESOR_ASIGNADO + "");
            generalService.executeQuery(changeEstate, true);
            Integer id = (Integer) generalService.save(asesor);
            asesor.setId(id);
            Faces.addMessage("SE HA ASIGNADO CON ÉXITO EL ASESOR AL PRACTICANTE...!", "", FacesMessage.SEVERITY_INFO);
        } else {
            generalService.update(asesor);
            Faces.addMessage("SE HA ACTUALIZADO LOS DATOS DEL ASESOR DEL PRACTICANTE...!", "", FacesMessage.SEVERITY_INFO);
        }
        if (enviarCorreo) {
            try {
                System.out.println(practicante.getCorreoElectronico());
                System.out.println(asesor.getIdDocente().getEmail());
                EmailSenderService em = new EmailSenderService();
                String[] emails = {practicante.getCorreoElectronico(), asesor.getIdDocente().getEmail(), "danny.lopez@unas.edu.pe"};
                em.setEmails(emails);
                em.setContentHTML("CORREO ELECTRINICO DE PRUEBA DEL SISTEMA DE PPP");
                em.setTitleMessage("SISTEMA DE PRÁCTICAS DE PROFESIONALES");
                em.sendEmail();
                Faces.addMessage("CORREO ENVIADO...!", "", FacesMessage.SEVERITY_INFO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void imprimirReporteAsesor() {
        if (practicante.getId() == null) {
            Faces.addMessage("DEBE GUARDAR LOS CAMBIOS PARA QUE PUEDA VER EL REPORTE!", "", FacesMessage.SEVERITY_ERROR);
        } else {
            try {
                Map<String, Object> param = new HashMap<>();
                param.put("ID_PRACTICANTE", practicante.getId());
                IReportManager reporte = new IReportManager(null);
                reporte.setJasperPrint(PATHSPPP.RP_AUTORIZACION_Y_ASIGNACION_ASESOR, param, MySQLUtil.getJdbcPostGresSQL().getConnection());
                reporte.exportarPDF("AutorizacionAsesor");
                MySQLUtil.getJdbcPostGresSQL().closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    //</editor-fold>

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public List<Practicante> getListaPracticantes() {
        return listaPracticantes;
    }

    public void setListaPracticantes(List<Practicante> listaPracticantes) {
        this.listaPracticantes = listaPracticantes;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public boolean getEnviarCorreo() {
        return enviarCorreo;
    }

    public void setEnviarCorreo(boolean enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }

    public Asesor getAsesor() {
        return asesor;
    }

    public void setAsesor(Asesor asesor) {
        this.asesor = asesor;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public PracticanteFuncion getPracticanteFuncion() {
        return practicanteFuncion;
    }

    public void setPracticanteFuncion(PracticanteFuncion practicanteFuncion) {
        this.practicanteFuncion = practicanteFuncion;
    }

    public List<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public ObservacionPracticante getObservacionPracticante() {
        return observacionPracticante;
    }

    public void setObservacionPracticante(ObservacionPracticante observacionPracticante) {
        this.observacionPracticante = observacionPracticante;
    }
}
