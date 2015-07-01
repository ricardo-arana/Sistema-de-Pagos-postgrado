/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@Entity
@Table(name = "profesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p"),
    @NamedQuery(name = "Profesor.findByIdProfesor", query = "SELECT p FROM Profesor p WHERE p.idProfesor = :idProfesor"),
    @NamedQuery(name = "Profesor.findByNomProfesor", query = "SELECT p FROM Profesor p WHERE p.nomProfesor = :nomProfesor"),
    @NamedQuery(name = "Profesor.findByApellidoProfesor", query = "SELECT p FROM Profesor p WHERE p.apellidoProfesor = :apellidoProfesor"),
    @NamedQuery(name = "Profesor.findByDNIprofesor", query = "SELECT p FROM Profesor p WHERE p.dNIprofesor = :dNIprofesor"),
    @NamedQuery(name = "Profesor.findByTelefonoProfesor", query = "SELECT p FROM Profesor p WHERE p.telefonoProfesor = :telefonoProfesor"),
    @NamedQuery(name = "Profesor.findByUsuarioReg", query = "SELECT p FROM Profesor p WHERE p.usuarioReg = :usuarioReg"),
    @NamedQuery(name = "Profesor.findByFechaReg", query = "SELECT p FROM Profesor p WHERE p.fechaReg = :fechaReg")})
public class Profesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProfesor")
    private Integer idProfesor;
    @Column(name = "nomProfesor")
    private String nomProfesor;
    @Column(name = "apellidoProfesor")
    private String apellidoProfesor;
    @Column(name = "DNIprofesor")
    private String dNIprofesor;
    @Column(name = "telefonoProfesor")
    private String telefonoProfesor;
    @Column(name = "UsuarioReg")
    private String usuarioReg;
    @Column(name = "FechaReg")
    @Temporal(TemporalType.DATE)
    private Date fechaReg;

    public Profesor() {
    }

    public Profesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNomProfesor() {
        return nomProfesor;
    }

    public void setNomProfesor(String nomProfesor) {
        this.nomProfesor = nomProfesor;
    }

    public String getApellidoProfesor() {
        return apellidoProfesor;
    }

    public void setApellidoProfesor(String apellidoProfesor) {
        this.apellidoProfesor = apellidoProfesor;
    }

    public String getDNIprofesor() {
        return dNIprofesor;
    }

    public void setDNIprofesor(String dNIprofesor) {
        this.dNIprofesor = dNIprofesor;
    }

    public String getTelefonoProfesor() {
        return telefonoProfesor;
    }

    public void setTelefonoProfesor(String telefonoProfesor) {
        this.telefonoProfesor = telefonoProfesor;
    }

    public String getUsuarioReg() {
        return usuarioReg;
    }

    public void setUsuarioReg(String usuarioReg) {
        this.usuarioReg = usuarioReg;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfesor != null ? idProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.idProfesor == null && other.idProfesor != null) || (this.idProfesor != null && !this.idProfesor.equals(other.idProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Profesor[ idProfesor=" + idProfesor + " ]";
    }
    
}
