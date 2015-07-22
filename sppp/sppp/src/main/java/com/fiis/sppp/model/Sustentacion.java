/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Danny
 */
@Entity
@Table(name = "sustentacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sustentacion.findAll", query = "SELECT s FROM Sustentacion s")})
public class Sustentacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_sustentacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSustentacion;
    @Column(name = "lugar_sustentacion")
    private String lugarSustentacion;
    @Column(name = "nota_final")
    private Integer notaFinal;
    @Column(name = "comentarios")
    private String comentarios;
    @Column(name = "nombre_anio")
    private String nombreAnio;
    @Column(name = "numero_acuerdo")
    private String numeroAcuerdo;
    @Column(name = "nombre_decano")
    private String nombreDecano;
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Column(name = "fecha_emision_dos")
    @Temporal(TemporalType.DATE)
    private Date fechaEmisionDos;
    @Column(name = "numero_solicitud")
    private String numeroSolicitud;
    @OneToMany(mappedBy = "idSustentacion")
    private List<SustentacionNota> sustentacionNotaList;
    @JoinColumn(name = "id_practicante", referencedColumnName = "id")
    @ManyToOne
    private Practicante idPracticante;
    @JoinColumn(name = "id_presidente", referencedColumnName = "id")
    @ManyToOne
    private Docente idPresidente;
    @JoinColumn(name = "id_secretario", referencedColumnName = "id")
    @ManyToOne
    private Docente idSecretario;
    @JoinColumn(name = "id_vocal_uno", referencedColumnName = "id")
    @ManyToOne
    private Docente idVocalUno;
    @JoinColumn(name = "id_vocal_dos", referencedColumnName = "id")
    @ManyToOne
    private Docente idVocalDos;

    public Sustentacion() {
    }

    public Sustentacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaSustentacion() {
        return fechaSustentacion;
    }

    public void setFechaSustentacion(Date fechaSustentacion) {
        this.fechaSustentacion = fechaSustentacion;
    }

    public String getLugarSustentacion() {
        return lugarSustentacion;
    }

    public void setLugarSustentacion(String lugarSustentacion) {
        this.lugarSustentacion = lugarSustentacion;
    }

    public Integer getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Integer notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNombreAnio() {
        return nombreAnio;
    }

    public void setNombreAnio(String nombreAnio) {
        this.nombreAnio = nombreAnio;
    }

    public String getNumeroAcuerdo() {
        return numeroAcuerdo;
    }

    public void setNumeroAcuerdo(String numeroAcuerdo) {
        this.numeroAcuerdo = numeroAcuerdo;
    }

    public String getNombreDecano() {
        return nombreDecano;
    }

    public void setNombreDecano(String nombreDecano) {
        this.nombreDecano = nombreDecano;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaEmisionDos() {
        return fechaEmisionDos;
    }

    public void setFechaEmisionDos(Date fechaEmisionDos) {
        this.fechaEmisionDos = fechaEmisionDos;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    @XmlTransient
    public List<SustentacionNota> getSustentacionNotaList() {
        return sustentacionNotaList;
    }

    public void setSustentacionNotaList(List<SustentacionNota> sustentacionNotaList) {
        this.sustentacionNotaList = sustentacionNotaList;
    }

    public Practicante getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Practicante idPracticante) {
        this.idPracticante = idPracticante;
    }

    public Docente getIdPresidente() {
        return idPresidente;
    }

    public void setIdPresidente(Docente idPresidente) {
        this.idPresidente = idPresidente;
    }

    public Docente getIdSecretario() {
        return idSecretario;
    }

    public void setIdSecretario(Docente idSecretario) {
        this.idSecretario = idSecretario;
    }

    public Docente getIdVocalUno() {
        return idVocalUno;
    }

    public void setIdVocalUno(Docente idVocalUno) {
        this.idVocalUno = idVocalUno;
    }

    public Docente getIdVocalDos() {
        return idVocalDos;
    }

    public void setIdVocalDos(Docente idVocalDos) {
        this.idVocalDos = idVocalDos;
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
        if (!(object instanceof Sustentacion)) {
            return false;
        }
        Sustentacion other = (Sustentacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Sustentacion[ id=" + id + " ]";
    }
}
