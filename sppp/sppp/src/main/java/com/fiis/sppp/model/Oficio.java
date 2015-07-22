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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Danny
 */
@Entity
@Table(name = "oficio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oficio.findAll", query = "SELECT o FROM Oficio o")})
public class Oficio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "auto")
    private Integer auto;
    @Column(name = "nombre_oficio")
    private String nombreOficio;
    @OneToMany(mappedBy = "idOficio")
    private List<Ampliacion> ampliacionList;
    @OneToMany(mappedBy = "idOficio")
    private List<InformeFinal> informeFinalList;

    public Oficio() {
    }

    public Oficio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuto() {
        return auto;
    }

    public void setAuto(Integer auto) {
        this.auto = auto;
    }

    public String getNombreOficio() {
        return nombreOficio;
    }

    public void setNombreOficio(String nombreOficio) {
        this.nombreOficio = nombreOficio;
    }

    @XmlTransient
    public List<Ampliacion> getAmpliacionList() {
        return ampliacionList;
    }

    public void setAmpliacionList(List<Ampliacion> ampliacionList) {
        this.ampliacionList = ampliacionList;
    }

    @XmlTransient
    public List<InformeFinal> getInformeFinalList() {
        return informeFinalList;
    }

    public void setInformeFinalList(List<InformeFinal> informeFinalList) {
        this.informeFinalList = informeFinalList;
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
        if (!(object instanceof Oficio)) {
            return false;
        }
        Oficio other = (Oficio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Oficio[ id=" + id + " ]";
    }
}
