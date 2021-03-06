/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "tipoartefacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoartefacto.findAll", query = "SELECT t FROM Tipoartefacto t"),
    @NamedQuery(name = "Tipoartefacto.findByIdTipoartefacto", query = "SELECT t FROM Tipoartefacto t WHERE t.idTipoartefacto = :idTipoartefacto"),
    @NamedQuery(name = "Tipoartefacto.findByDescripcion", query = "SELECT t FROM Tipoartefacto t WHERE t.descripcion = :descripcion")})
public class Tipoartefacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipoartefacto")
    private Integer idTipoartefacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoartefacto")
    private List<Artefacto> artefactoList;

    public Tipoartefacto() {
    }

    public Tipoartefacto(Integer idTipoartefacto) {
        this.idTipoartefacto = idTipoartefacto;
    }

    public Tipoartefacto(Integer idTipoartefacto, String descripcion) {
        this.idTipoartefacto = idTipoartefacto;
        this.descripcion = descripcion;
    }

    public Integer getIdTipoartefacto() {
        return idTipoartefacto;
    }

    public void setIdTipoartefacto(Integer idTipoartefacto) {
        this.idTipoartefacto = idTipoartefacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Artefacto> getArtefactoList() {
        return artefactoList;
    }

    public void setArtefactoList(List<Artefacto> artefactoList) {
        this.artefactoList = artefactoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoartefacto != null ? idTipoartefacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoartefacto)) {
            return false;
        }
        Tipoartefacto other = (Tipoartefacto) object;
        if ((this.idTipoartefacto == null && other.idTipoartefacto != null) || (this.idTipoartefacto != null && !this.idTipoartefacto.equals(other.idTipoartefacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.Proyect.Model.Tipoartefacto[ idTipoartefacto=" + idTipoartefacto + " ]";
    }
    
}
