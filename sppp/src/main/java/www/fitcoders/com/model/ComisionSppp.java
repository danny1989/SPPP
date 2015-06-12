/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Max
 */
@Entity
@Table(name = "comision_sppp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComisionSppp.findAll", query = "SELECT c FROM ComisionSppp c")})
public class ComisionSppp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "periodo_inicial")
    @Temporal(TemporalType.DATE)
    private Date periodoInicial;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "periodo_final")
    @Temporal(TemporalType.DATE)
    private Date periodoFinal;
    @OneToMany(mappedBy = "idComisionSppp")
    private List<MiembroComisionSppp> miembroComisionSpppList;

    public ComisionSppp() {
    }

    public ComisionSppp(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(Date periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(Date periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    @XmlTransient
    public List<MiembroComisionSppp> getMiembroComisionSpppList() {
        return miembroComisionSpppList;
    }

    public void setMiembroComisionSpppList(List<MiembroComisionSppp> miembroComisionSpppList) {
        this.miembroComisionSpppList = miembroComisionSpppList;
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
        if (!(object instanceof ComisionSppp)) {
            return false;
        }
        ComisionSppp other = (ComisionSppp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "www.fitcoders.com.model.ComisionSppp[ id=" + id + " ]";
    }
    
}
