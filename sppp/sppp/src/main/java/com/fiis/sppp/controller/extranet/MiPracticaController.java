/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller.extranet;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import com.fiis.sppp.dao.util.MySQLUtil;
import com.fiis.sppp.model.Estado;
import com.fiis.sppp.model.ObservacionPracticante;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.model.PracticanteFuncion;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import com.fiis.sppp.util.IReportManager;
import com.fiis.sppp.util.PATHSPPP;
import com.fiis.sppp.util.SPPPID;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class MiPracticaController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    private Practicante practicante;
    private PracticanteFuncion practicanteFuncion;

    public void cargarMisDatos() throws Exception {
        practicante = (Practicante) Faces.getSessionAttribute(Faces.ATTRIBUTE_PRACTICA);
        practicante = generalService.getByHQL("from Practicante p where p.id=" + practicante.getId(), null, null, false);
        System.out.println(practicante);

        List<PracticanteFuncion> listaPF = generalService.listHQL("from PracticanteFuncion p where p.idPracticante.id=" + practicante.getId(), null, null);
        List<ObservacionPracticante> listaOP = generalService.listHQL("from ObservacionPracticante o where o.idPracticante.id=" + practicante.getId(), null, null);
        practicante.setPracticanteFuncionList(listaPF);
        practicante.setObservacionPracticanteList(listaOP);
    }

    public void imprimirFormatoFUno() {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("ID_PRACTICANTE", practicante.getId());
            IReportManager reporte = new IReportManager(null);
            reporte.setJasperPrint(PATHSPPP.RP_FORMATO_F1, param, MySQLUtil.getJdbcPostGresSQL().getConnection());
            reporte.exportarPDF("FormatoF1");
            MySQLUtil.getJdbcPostGresSQL().closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void guardarFuncionPracticante() throws Exception {
        practicanteFuncion.setIdPracticante(practicante);
        practicante.getPracticanteFuncionList().add(practicanteFuncion);
        if (practicante.getId() != null) {
            Integer idPF = (Integer) generalService.save(practicanteFuncion);
            practicanteFuncion.setId(idPF);
        }
        Faces.addMessage("NUEVA FUNCIÓN AÑADIDA", "", FacesMessage.SEVERITY_INFO);
    }

    public void onDateSelect(SelectEvent event) {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime((Date) event.getObject());
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(Calendar.YEAR, fecha.get(Calendar.YEAR));
        fechaFin.set(Calendar.MONTH, fecha.get(Calendar.MONTH) + 3);
        fechaFin.set(Calendar.DATE, fecha.get(Calendar.DATE));

        practicante.setFechaFin(fechaFin.getTime());

    }

    public void eliminarPracticanteFuncion(int index) throws Exception {
        PracticanteFuncion pf = practicante.getPracticanteFuncionList().remove(index);
        if (pf.getId() != null) {
            generalService.delete(pf);
        }
        Faces.addMessage("La función ha sido eliminada!", "", FacesMessage.SEVERITY_WARN);
    }

    public void iniciarInstanciaPracticanteFuncion() {
        practicanteFuncion = new PracticanteFuncion();
    }

    public void guardarActualizarMiPractica() throws Exception {
        practicante.setIdEstado(new Estado(SPPPID.Practicate.A_PRACTICA_REGISTRADA));
        String updateObserva = "update observacion_practicante set activo=false where id=" + practicante.getId();
        generalService.executeQuery(updateObserva, true);
        generalService.update(practicante);
        cargarMisDatos();
        Faces.addMessage("PRACTICA ACTUALIZADA CON ÉXITO...!", "", FacesMessage.SEVERITY_INFO);
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public Practicante getPracticante() {
        return practicante;
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

}
