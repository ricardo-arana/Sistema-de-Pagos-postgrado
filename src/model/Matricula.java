/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ricardo
 */
@Entity
@Table(name = "matricula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matricula.findAll", query = "SELECT m FROM Matricula m"),
    @NamedQuery(name = "Matricula.findByIdmatricula", query = "SELECT m FROM Matricula m WHERE m.idmatricula = :idmatricula"),
    @NamedQuery(name = "Matricula.findByNroCiclo", query = "SELECT m FROM Matricula m WHERE m.nroCiclo = :nroCiclo"),
    @NamedQuery(name = "Matricula.findByEstudiante", query = "SELECT m FROM Matricula m WHERE m.estudianteidEstudiante = :estudianteidEstudiante")
})
public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmatricula")
    private Integer idmatricula;
    @Column(name = "nroCiclo")
    private String nroCiclo;
    @JoinColumn(name = "estudiante_idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante estudianteidEstudiante;
    @JoinColumn(name = "ciclo_idCiclo", referencedColumnName = "idCiclo")
    @ManyToOne(optional = false)
    private Ciclo cicloidCiclo;

    public Matricula() {
    }

    public Matricula(Integer idmatricula) {
        this.idmatricula = idmatricula;
    }

    public Integer getIdmatricula() {
        return idmatricula;
    }

    public void setIdmatricula(Integer idmatricula) {
        this.idmatricula = idmatricula;
    }

    public String getNroCiclo() {
        return nroCiclo;
    }

    public void setNroCiclo(String nroCiclo) {
        this.nroCiclo = nroCiclo;
    }

    public Estudiante getEstudianteidEstudiante() {
        return estudianteidEstudiante;
    }

    public void setEstudianteidEstudiante(Estudiante estudianteidEstudiante) {
        this.estudianteidEstudiante = estudianteidEstudiante;
    }

    public Ciclo getCicloidCiclo() {
        return cicloidCiclo;
    }

    public void setCicloidCiclo(Ciclo cicloidCiclo) {
        this.cicloidCiclo = cicloidCiclo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmatricula != null ? idmatricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.idmatricula == null && other.idmatricula != null) || (this.idmatricula != null && !this.idmatricula.equals(other.idmatricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Matricula[ idmatricula=" + idmatricula + " ]";
    }
    
}
