/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author Ricardo
 */
@Entity
@Table(name = "maestria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestria.findAll", query = "SELECT m FROM Maestria m"),
    @NamedQuery(name = "Maestria.findByIdMaestria", query = "SELECT m FROM Maestria m WHERE m.idMaestria = :idMaestria"),
    @NamedQuery(name = "Maestria.findByNombreMaestria", query = "SELECT m FROM Maestria m WHERE m.nombreMaestria = :nombreMaestria")})
public class Maestria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMaestria")
    private Integer idMaestria;
    @Column(name = "nombreMaestria")
    private String nombreMaestria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestria")
    private List<Estudiante> estudianteList;

    public Maestria() {
    }

    public Maestria(Integer idMaestria) {
        this.idMaestria = idMaestria;
    }

    public Integer getIdMaestria() {
        return idMaestria;
    }

    public void setIdMaestria(Integer idMaestria) {
        this.idMaestria = idMaestria;
    }

    public String getNombreMaestria() {
        return nombreMaestria;
    }

    public void setNombreMaestria(String nombreMaestria) {
        this.nombreMaestria = nombreMaestria;
    }

    @XmlTransient
    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaestria != null ? idMaestria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestria)) {
            return false;
        }
        Maestria other = (Maestria) object;
        if ((this.idMaestria == null && other.idMaestria != null) || (this.idMaestria != null && !this.idMaestria.equals(other.idMaestria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Maestria[ idMaestria=" + idMaestria + " ]";
    }
    
}
