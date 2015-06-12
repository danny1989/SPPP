/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.model;

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
 * @author Max
 */
@Entity
@Table(name = "jurado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jurado.findAll", query = "SELECT j FROM Jurado j")})
public class Jurado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    @Size(max = 50)
    @Column(name = "posicion")
    private String posicion;
    @Column(name = "id_practicante")
    private Integer idPracticante;
    @JoinColumn(name = "id_sustentacion", referencedColumnName = "id")
    @ManyToOne
    private Sustentacion idSustentacion;
    @JoinColumn(name = "id_docente", referencedColumnName = "id")
    @ManyToOne
    private Docente idDocente;

    public Jurado() {
    }

    public Jurado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Integer getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Integer idPracticante) {
        this.idPracticante = idPracticante;
    }

    public Sustentacion getIdSustentacion() {
        return idSustentacion;
    }

    public void setIdSustentacion(Sustentacion idSustentacion) {
        this.idSustentacion = idSustentacion;
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
        if (!(object instanceof Jurado)) {
            return false;
        }
        Jurado other = (Jurado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "www.fitcoders.com.model.Jurado[ id=" + id + " ]";
    }
    
}
