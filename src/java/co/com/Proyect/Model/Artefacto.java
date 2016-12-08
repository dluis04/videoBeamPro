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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan
 */
@Entity
@Table(name = "artefacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artefacto.findAll", query = "SELECT a FROM Artefacto a"),
    @NamedQuery(name = "Artefacto.findByIdArtefacto", query = "SELECT a FROM Artefacto a WHERE a.idArtefacto = :idArtefacto"),
    @NamedQuery(name = "Artefacto.findByHorasenoperacion", query = "SELECT a FROM Artefacto a WHERE a.horasenoperacion = :horasenoperacion"),
    @NamedQuery(name = "Artefacto.findByCiclouso", query = "SELECT a FROM Artefacto a WHERE a.ciclouso = :ciclouso")})
public class Artefacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_artefacto")
    private Integer idArtefacto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horasenoperacion")
    private int horasenoperacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ciclouso")
    private int ciclouso;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private Estado idEstado;
    @JoinColumn(name = "id_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false)
    private Marca idMarca;
    @JoinColumn(name = "id_tipoartefacto", referencedColumnName = "id_tipoartefacto")
    @ManyToOne(optional = false)
    private Tipoartefacto idTipoartefacto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArtefacto")
    private List<Reserva> reservaList;

    public Artefacto() {
    }

    public Artefacto(Integer idArtefacto) {
        this.idArtefacto = idArtefacto;
    }

    public Artefacto(Integer idArtefacto, int horasenoperacion, int ciclouso) {
        this.idArtefacto = idArtefacto;
        this.horasenoperacion = horasenoperacion;
        this.ciclouso = ciclouso;
    }

    public Integer getIdArtefacto() {
        return idArtefacto;
    }

    public void setIdArtefacto(Integer idArtefacto) {
        this.idArtefacto = idArtefacto;
    }

    public int getHorasenoperacion() {
        return horasenoperacion;
    }

    public void setHorasenoperacion(int horasenoperacion) {
        this.horasenoperacion = horasenoperacion;
    }

    public int getCiclouso() {
        return ciclouso;
    }

    public void setCiclouso(int ciclouso) {
        this.ciclouso = ciclouso;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public Tipoartefacto getIdTipoartefacto() {
        return idTipoartefacto;
    }

    public void setIdTipoartefacto(Tipoartefacto idTipoartefacto) {
        this.idTipoartefacto = idTipoartefacto;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArtefacto != null ? idArtefacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artefacto)) {
            return false;
        }
        Artefacto other = (Artefacto) object;
        if ((this.idArtefacto == null && other.idArtefacto != null) || (this.idArtefacto != null && !this.idArtefacto.equals(other.idArtefacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.Proyect.Model.Artefacto[ idArtefacto=" + idArtefacto + " ]";
    }
    
}
