/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.model;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fiis.sppp.util.SPPPID;

/**
 *
 * @author Danny
 */
@Entity
@Table(name = "docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d")})
public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "paterno")
    private String paterno;
    @Column(name = "materno")
    private String materno;
    @Column(name = "grado")
    private String grado;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "email")
    private String email;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "id_area")
    private Integer idArea;
    @Column(name = "id_especialidad")
    private Integer idEspecialidad;
    @OneToMany(mappedBy = "idDocente")
    private List<Asesor> asesorList;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;
    @OneToMany(mappedBy = "idPresidente")
    private List<Sustentacion> sustentacionList;
    @OneToMany(mappedBy = "idSecretario")
    private List<Sustentacion> sustentacionList1;
    @OneToMany(mappedBy = "idVocalUno")
    private List<Sustentacion> sustentacionList2;
    @OneToMany(mappedBy = "idVocalDos")
    private List<Sustentacion> sustentacionList3;

    @Transient
    private String nombreArea;
    
    @Transient 
    private String nombreAreaDocente;
    
    public Docente() {
    }

    public Docente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreArea() {
        if(idArea == SPPPID.IdArea.SISTEMAS) {
            nombreArea = "Sistemas";
        } else if(idArea == SPPPID.IdArea.CIENCIAS_BASICAS) {
            nombreArea = "Ciencias Básicas";
        } else if(idArea == SPPPID.IdArea.INFORMATICA) {
            nombreArea = "Informática";
        }
        return nombreArea;
    }

    public String getNombreAreaDocente() {
        if(idArea == SPPPID.IdArea.SISTEMAS) {
            nombreAreaDocente = "Sistemas - " + nombreCompleto;
        } else if(idArea == SPPPID.IdArea.CIENCIAS_BASICAS) {
            nombreAreaDocente = "Ciencias Básicas - " + nombreCompleto;
        } else if(idArea == SPPPID.IdArea.INFORMATICA) {
            nombreAreaDocente = "Informática - " + nombreCompleto;
        }
        return nombreAreaDocente;
    }

    public void setNombreAreaDocente(String nombreAreaDocente) {
        this.nombreAreaDocente = nombreAreaDocente;
    }

    
    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    @XmlTransient
    public List<Asesor> getAsesorList() {
        return asesorList;
    }

    public void setAsesorList(List<Asesor> asesorList) {
        this.asesorList = asesorList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    @XmlTransient
    public List<Sustentacion> getSustentacionList() {
        return sustentacionList;
    }

    public void setSustentacionList(List<Sustentacion> sustentacionList) {
        this.sustentacionList = sustentacionList;
    }

    @XmlTransient
    public List<Sustentacion> getSustentacionList1() {
        return sustentacionList1;
    }

    public void setSustentacionList1(List<Sustentacion> sustentacionList1) {
        this.sustentacionList1 = sustentacionList1;
    }

    @XmlTransient
    public List<Sustentacion> getSustentacionList2() {
        return sustentacionList2;
    }

    public void setSustentacionList2(List<Sustentacion> sustentacionList2) {
        this.sustentacionList2 = sustentacionList2;
    }

    @XmlTransient
    public List<Sustentacion> getSustentacionList3() {
        return sustentacionList3;
    }

    public void setSustentacionList3(List<Sustentacion> sustentacionList3) {
        this.sustentacionList3 = sustentacionList3;
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
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "www.fitcoders.com.model.Docente[ id=" + id + " ]";
    }
    
}
