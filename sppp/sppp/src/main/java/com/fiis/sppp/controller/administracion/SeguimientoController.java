/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller.administracion;

import com.fiis.sppp.model.Ampliacion;
import com.fiis.sppp.model.Estado;
import com.fiis.sppp.model.Oficio;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.model.util.USeguimiento;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import com.fiis.sppp.util.SPPPID;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class SeguimientoController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    private List<USeguimiento> listaPracticantes;
    private Practicante practicante;

    private Ampliacion ampliacion;

    public void inicializarListaPracticas() throws Exception {
        //Nombre Practicante
        //nombre Asesor
        //Tema
        //cambio de tema
        //inicio de practicas - Fin de practicas
        //Ampliacion
        //total meses 
        //Estado
        //EXISTEN 3 ESTADOS PARA HACER LAS PRACTICAS EN GENERAL.
        String hql = "select a.idPracticante.id as idPracticante,"
                + "a.idPracticante.codigoAlumno as codigoEstudiante,"
                + "a.idDocente.nombreCompleto as nombreDocente,"
                + "a.idPracticante.nombreCompleto as nombrePracticante,"
                + "a.idPracticante.tituloProyecto as tituloProyecto,"
                + "a.idPracticante.cambioTema as cambioTema,"
                + "a.idPracticante.fechaInicio as fechaInicio,"
                + "a.idPracticante.fechaFin as fechaFin,"
                + "(select sum(am.numeroMeses) from Ampliacion am where am.idPracticante.id=a.idPracticante.id) as mesesAmpliacion,"
                + "a.idPracticante.idEstado.nombre as nombreEstado from Asesor a where a.idPracticante.idEstado.id in (5)";
        //ESTADOS: EJECUCIÓN, INFORME, ANULACIÓN.
        listaPracticantes = generalService.listHQL(hql, null, USeguimiento.class);
        System.out.println(listaPracticantes);
    }

    public void getPracticanteById(Integer idPracticante) throws Exception {
              
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        ampliacion = new Ampliacion();
        
        ampliacion.setNombreDecano(Faces.getNombreDecano());
        ampliacion.setNumeroMeses(1);
        ampliacion.setFechaAmpliacion(new Date());
    }

    public void cambiarInformeEntregado(Integer idPracticante) throws Exception {
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        practicante.setIdEstado(new Estado(SPPPID.Practicate.F_INFORME_ENTREGADO));
        
        generalService.update(practicante);
        inicializarListaPracticas();
        Faces.addMessage("SE HA ENTREGADO EL INFORME DEL PRACTICANTE...!", "", FacesMessage.SEVERITY_INFO);
        
    }
    
    public void anulacionDePractica(Integer idPracticante) throws Exception {
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        practicante.setIdEstado(new Estado(SPPPID.Practicate.G_PRACTICA_ANULADA));
        
        generalService.update(practicante);
        inicializarListaPracticas();
        Faces.addMessage("SE HA ANULADO LA PRÁCTICA....!", "", FacesMessage.SEVERITY_WARN);
    }
    
    public void guardarAmpliacion() throws Exception {
        Long totalAmpliacion = generalService.getByHQL("select sum(a.numeroMeses) from Ampliacion a where a.idPracticante.id=" + practicante.getId(), null, null, false);
        if (totalAmpliacion == null) {
            totalAmpliacion = Long.valueOf(0);
        }
        totalAmpliacion = totalAmpliacion + ampliacion.getNumeroMeses() + 3;
        //sdfsdf
        if (totalAmpliacion <= 6) {
            if (ampliacion.getId() == null) {
                ampliacion.setFechaRegistro(new Date());
                Oficio of = new Oficio();
                Integer idOficio = (Integer) generalService.save(of);
                of.setId(idOficio);
                ampliacion.setIdOficio(of);
                ampliacion.setIdPracticante(practicante);

                Calendar calFin = Calendar.getInstance();
                calFin.setTime(practicante.getFechaFin());
                calFin.add(Calendar.MONTH, ampliacion.getNumeroMeses());

                practicante.setFechaFin(calFin.getTime());
                generalService.save(ampliacion);
                generalService.update(practicante);
 //Hola
                Faces.addMessage("LAS PRÁCTICAS FUERON AMPLIADAS CON ÉXITO", "", FacesMessage.SEVERITY_INFO);
            }
        } else {
            Faces.addMessage("NO PUEDE AMPLIAR A MÁS DE 6 MESES.", "Ud. esta tratando de ampliar las prácticas hasta " + totalAmpliacion + " meses", FacesMessage.SEVERITY_ERROR);
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

    public Ampliacion getAmpliacion() {
        return ampliacion;
    }

    public void setAmpliacion(Ampliacion ampliacion) {
        this.ampliacion = ampliacion;
    }

}
