/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.model.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import www.fitcoders.com.fitdateutil.datetime.MDifferenceDate;

/**
 *
 * @author Danny
 */
public class USeguimiento implements Serializable  {
    
    private Integer idPracticante;
    private String codigoEstudiante;
    private String nombreDocente;
    private String nombrePracticante;
    private String tituloProyecto;
    private String cambioTema;
    private Date fechaInicio;
    private Date fechaFin;
    private Long mesesAmpliacion;
    private String nombreEstado;
    private String nombreEstadoGeneral;

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getNombrePracticante() {
        return nombrePracticante;
    }

    public void setNombrePracticante(String nombrePracticante) {
        this.nombrePracticante = nombrePracticante;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public Integer getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Integer idPracticante) {
        this.idPracticante = idPracticante;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }

    public String getCambioTema() {
        return cambioTema;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public void setCambioTema(String cambioTema) {
        this.cambioTema = cambioTema;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getMesesAmpliacion() {
        return mesesAmpliacion;
    }

    public void setMesesAmpliacion(Long mesesAmpliacion) {
        this.mesesAmpliacion = mesesAmpliacion;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    

    public String getNombreEstadoGeneral() {
        /// ejecucion, informe , anulacion
        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(fechaInicio);
        Calendar calFin = Calendar.getInstance();
        calFin.setTime(fechaFin);
        Calendar calHoy = Calendar.getInstance();
        calFin.add(Calendar.DATE, 1);
        //EJECUCION, INFORME
//        calFin.add(Calendar.MONTH, -1);
        if(calHoy.after(calInicio) && calHoy.before(calFin)) {
            nombreEstadoGeneral = "EJECUCIÓN";
            return nombreEstadoGeneral;
        }
        
        calFin.add(Calendar.MONTH, 1);
        MDifferenceDate.differenceDates(calInicio.getTime(), calFin.getTime());
        int totalMeses = MDifferenceDate.getMoths() - 1;
        
        calInicio.add(Calendar.MONTH, totalMeses);
        
        if(calHoy.after(calInicio) && calHoy.before(calFin)) {
            nombreEstadoGeneral = "INFORME";
            return nombreEstadoGeneral;
        }
//        System.out.println(calFin.getTime() + " _ " + calHoy.getTime() + " {{" + nombrePracticante);
        if(calHoy.after(calFin)) {
            nombreEstadoGeneral= "FIN DE PRÁCTICA";
        }
        
        return nombreEstadoGeneral;
    }

    public void setNombreEstadoGeneral(String nombreEstadoGeneral) {
        this.nombreEstadoGeneral = nombreEstadoGeneral;
    }
    
}
