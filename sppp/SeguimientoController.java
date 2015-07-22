/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.controller.administracion;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import www.fitcoders.com.file.ManejoArchivo;
import www.fitcoders.com.model.Ampliacion;
import www.fitcoders.com.model.Oficio;
import www.fitcoders.com.model.Practicante;
import www.fitcoders.com.model.util.USeguimiento;
import www.fitcoders.com.service.GeneralService;
import www.fitcoders.com.util.Faces;

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
                + "a.idPracticante.idEstado.nombre as nombreEstado from Asesor a";
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
