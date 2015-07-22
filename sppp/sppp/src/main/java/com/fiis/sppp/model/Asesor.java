/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Danny
 */
@Entity
@Table(name = "asesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asesor.findAll", query = "SELECT a FROM Asesor a")})
public class Asesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    @Size(max = 100)
    @Column(name = "numero_acuerdo")
    private String numeroAcuerdo;
    @Column(name = "numero_resolucion")
    private String numeroResolucion;
    @Size(max = 25)
    @Column(name = "numero_solicitud")
    private String numeroSolicitud;
    @Size(max = 200)
    @Column(name = "nombre_decano")
    private String nombreDecano;
    @Size(max = 200)
    @Column(name = "nombre_anio")
    private String nombreAnio;
    @JoinColumn(name = "id_practicante", referencedColumnName = "id")
    @ManyToOne
    private Practicante idPracticante;
    @JoinColumn(name = "id_docente", referencedColumnName = "id")
    @ManyToOne
    private Docente idDocente;

    public Asesor() {
    }

    public Asesor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getNumeroAcuerdo() {
        return numeroAcuerdo;
    }

    public void setNumeroAcuerdo(String numeroAcuerdo) {
        this.numeroAcuerdo = numeroAcuerdo;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getNombreDecano() {
        return nombreDecano;
    }

    public void setNombreDecano(String nombreDecano) {
        this.nombreDecano = nombreDecano;
    }

    public String getNombreAnio() {
        return nombreAnio;
    }

    public void setNombreAnio(String nombreAnio) {
        this.nombreAnio = nombreAnio;
    }

    public Practicante getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Practicante idPracticante) {
        this.idPracticante = idPracticante;
    }

    public Docente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Docente idDocente) {
        this.idDocente = idDocente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asesor)) {
            return false;
        }
        Asesor other = (Asesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "www.fitcoders.com.model.Asesor[ id=" + id + " ]";
    }
    
}
