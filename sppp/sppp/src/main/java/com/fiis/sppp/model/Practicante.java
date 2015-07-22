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
@Table(name = "practicante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Practicante.findAll", query = "SELECT p FROM Practicante p")})
public class Practicante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Size(max = 100)
    @Column(name = "lu_area_asignada")
    private String luAreaAsignada;
    @Size(max = 20)
    @Column(name = "codigo_alumno")
    private String codigoAlumno;
    @Size(max = 200)
    @Column(name = "lu_website")
    private String luWebsite;
    @Size(max = 150)
    @Column(name = "lu_responsable_area")
    private String luResponsableArea;
    @Size(max = 400)
    @Column(name = "titulo_proyecto")
    private String tituloProyecto;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Size(max = 150)
    @Column(name = "lu_email")
    private String luEmail;
    @Size(max = 25)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "creditos_aprobados")
    private Integer creditosAprobados;
    @Size(max = 150)
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Size(max = 700)
    @Column(name = "resumen_propuesta")
    private String resumenPropuesta;
    @Size(max = 200)
    @Column(name = "lu_razon_social")
    private String luRazonSocial;
    @Size(max = 200)
    @Column(name = "lu_titular")
    private String luTitular;
    @Size(max = 25)
    @Column(name = "lu_telefono")
    private String luTelefono;
    @Size(max = 50)
    @Column(name = "lu_cargo")
    private String luCargo;
    @Size(max = 25)
    @Column(name = "codigo_proyecto")
    private String codigoProyecto;
    @Size(max = 300)
    @Column(name = "cambio_tema")
    private String cambioTema;
    @OneToMany(mappedBy = "idPracticante")
    private List<Historial> historialList;
    @OneToMany(mappedBy = "idPracticante")
    private List<ObservacionPracticante> observacionPracticanteList;
    @OneToMany(mappedBy = "idPracticante")
    private List<Asesor> asesorList;
    @OneToMany(mappedBy = "idPracticante")
    private List<PracticanteFuncion> practicanteFuncionList;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private Estado idEstado;
    @OneToMany(mappedBy = "idPracticante")
    private List<Ampliacion> ampliacionList;
    @OneToMany(mappedBy = "idPracticante")
    private List<Sustentacion> sustentacionList;
    @OneToMany(mappedBy = "idPracticante")
    private List<InformeFinal> informeFinalList;

    public Practicante() {
    }

    public Practicante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getLuAreaAsignada() {
        return luAreaAsignada;
    }

    public void setLuAreaAsignada(String luAreaAsignada) {
        this.luAreaAsignada = luAreaAsignada;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public String getLuWebsite() {
        return luWebsite;
    }

    public void setLuWebsite(String luWebsite) {
        this.luWebsite = luWebsite;
    }

    public String getLuResponsableArea() {
        return luResponsableArea;
    }

    public void setLuResponsableArea(String luResponsableArea) {
        this.luResponsableArea = luResponsableArea;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
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

    public String getLuEmail() {
        return luEmail;
    }

    public void setLuEmail(String luEmail) {
        this.luEmail = luEmail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getCreditosAprobados() {
        return creditosAprobados;
    }

    public void setCreditosAprobados(Integer creditosAprobados) {
        this.creditosAprobados = creditosAprobados;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getResumenPropuesta() {
        return resumenPropuesta;
    }

    public void setResumenPropuesta(String resumenPropuesta) {
        this.resumenPropuesta = resumenPropuesta;
    }

    public String getLuRazonSocial() {
        return luRazonSocial;
    }

    public void setLuRazonSocial(String luRazonSocial) {
        this.luRazonSocial = luRazonSocial;
    }

    public String getLuTitular() {
        return luTitular;
    }

    public void setLuTitular(String luTitular) {
        this.luTitular = luTitular;
    }

    public String getLuTelefono() {
        return luTelefono;
    }

    public void setLuTelefono(String luTelefono) {
        this.luTelefono = luTelefono;
    }

    public String getLuCargo() {
        return luCargo;
    }

    public void setLuCargo(String luCargo) {
        this.luCargo = luCargo;
    }

    public String getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public String getCambioTema() {
        return cambioTema;
    }

    public void setCambioTema(String cambioTema) {
        this.cambioTema = cambioTema;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @XmlTransient
    public List<ObservacionPracticante> getObservacionPracticanteList() {
        return observacionPracticanteList;
    }

    public void setObservacionPracticanteList(List<ObservacionPracticante> observacionPracticanteList) {
        this.observacionPracticanteList = observacionPracticanteList;
    }
    
    @XmlTransient
    public List<InformeFinal> getInformeFinalList() {
        return informeFinalList;
    }

    public void setInformeFinalList(List<InformeFinal> informeFinalList) {
        this.informeFinalList = informeFinalList;
    }

    @XmlTransient
    public List<Asesor> getAsesorList() {
        return asesorList;
    }

    public void setAsesorList(List<Asesor> asesorList) {
        this.asesorList = asesorList;
    }

    @XmlTransient
    public List<PracticanteFuncion> getPracticanteFuncionList() {
        return practicanteFuncionList;
    }

    public void setPracticanteFuncionList(List<PracticanteFuncion> practicanteFuncionList) {
        this.practicanteFuncionList = practicanteFuncionList;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @XmlTransient
    public List<Ampliacion> getAmpliacionList() {
        return ampliacionList;
    }

    public void setAmpliacionList(List<Ampliacion> ampliacionList) {
        this.ampliacionList = ampliacionList;
    }

    @XmlTransient
    public List<Sustentacion> getSustentacionList() {
        return sustentacionList;
    }

    public void setSustentacionList(List<Sustentacion> sustentacionList) {
        this.sustentacionList = sustentacionList;
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
        if (!(object instanceof Practicante)) {
            return false;
        }
        Practicante other = (Practicante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "www.fitcoders.com.model.Practicante[ id=" + id + " ]";
    }
    
}
