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
@Table(name = "ampliacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ampliacion.findAll", query = "SELECT a FROM Ampliacion a")})
public class Ampliacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_ampliacion")
    private Date fechaAmpliacion;
    @Column(name = "nombre_decano")
    private String nombreDecano;
    @Column(name = "numero_meses")
    private Integer numeroMeses;
    @JoinColumn(name = "id_practicante", referencedColumnName = "id")
    @ManyToOne
    private Practicante idPracticante;
    @JoinColumn(name = "id_oficio", referencedColumnName = "id")
    @ManyToOne
    private Oficio idOficio;

    public Ampliacion() {
    }

    public Ampliacion(Integer id) {
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

    public Date getFechaAmpliacion() {
        return fechaAmpliacion;
    }

    public void setFechaAmpliacion(Date fechaAmpliacion) {
        this.fechaAmpliacion = fechaAmpliacion;
    }

    public String getNombreDecano() {
        return nombreDecano;
    }

    public void setNombreDecano(String nombreDecano) {
        this.nombreDecano = nombreDecano;
    }

    public Integer getNumeroMeses() {
        return numeroMeses;
    }

    public void setNumeroMeses(Integer numeroMeses) {
        this.numeroMeses = numeroMeses;
    }

    public Practicante getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Practicante idPracticante) {
        this.idPracticante = idPracticante;
    }

    public Oficio getIdOficio() {
        return idOficio;
    }

    public void setIdOficio(Oficio idOficio) {
        this.idOficio = idOficio;
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
        if (!(object instanceof Ampliacion)) {
            return false;
        }
        Ampliacion other = (Ampliacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Ampliacion[ id=" + id + " ]";
    }
    
}
