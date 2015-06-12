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
 * @author Max
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
    @Size(max = 300)
    @Column(name = "lugar_sustentacion")
    private String lugarSustentacion;
    @Column(name = "nota_final")
    private Integer notaFinal;
    @Size(max = 600)
    @Column(name = "comentarios")
    private String comentarios;
    @Size(max = 100)
    @Column(name = "codigo_documento")
    private String codigoDocumento;
    @OneToMany(mappedBy = "idSustentacion")
    private List<Jurado> juradoList;
    @JoinColumn(name = "id_practicante", referencedColumnName = "id")
    @ManyToOne
    private Practicante idPracticante;

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

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    @XmlTransient
    public List<Jurado> getJuradoList() {
        return juradoList;
    }

    public void setJuradoList(List<Jurado> juradoList) {
        this.juradoList = juradoList;
    }

    public Practicante getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Practicante idPracticante) {
        this.idPracticante = idPracticante;
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
        return "www.fitcoders.com.model.Sustentacion[ id=" + id + " ]";
    }
    
}
