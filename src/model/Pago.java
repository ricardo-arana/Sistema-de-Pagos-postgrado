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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByNroVoucher", query = "SELECT p FROM Pago p WHERE p.nroVoucher = :nroVoucher"),
    @NamedQuery(name = "Pago.findByCuota", query = "SELECT p FROM Pago p WHERE p.cuota = :cuota"),
    @NamedQuery(name = "Pago.findByMonto", query = "SELECT p FROM Pago p WHERE p.monto = :monto"),
    @NamedQuery(name = "Pago.findByFechaPago", query = "SELECT p FROM Pago p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "Pago.findByFechaRegPago", query = "SELECT p FROM Pago p WHERE p.fechaRegPago = :fechaRegPago"),
    @NamedQuery(name = "Pago.listaPagos", query = "SELECT p FROM Pago p WHERE p.estudianteidEstudiante = :idEstudiante and p.maestriaidMaestria = :idMaestria  and p.cicloidCiclo = :idCiclo order by p.cuota,p.fechaPago"),
    @NamedQuery(name = "Pago.PagosGeneral", query = "SELECT p FROM Pago p WHERE p.maestriaidMaestria = :idMaestria and p.cicloidCiclo = :idCiclo order by p.estudianteidEstudiante"),
    @NamedQuery(name = "Pago.findbyPagoMaestriaCuota", query = "SELECT p FROM Pago p WHERE p.maestriaidMaestria = :idMaestria and p.cuota = :cuota order and by p.estudianteidEstudiante")})
public class Pago implements Serializable {
    @Basic(optional = false)
    @Column(name = "montoSaldo")
    private float montoSaldo;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nroVoucher")
    private String nroVoucher;
    @Column(name = "Cuota")
    private String cuota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Float monto;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "fechaRegPago")
    @Temporal(TemporalType.DATE)
    private Date fechaRegPago;
    @JoinColumn(name = "Maestria_idMaestria", referencedColumnName = "idMaestria")
    @ManyToOne(optional = false)
    private Maestria maestriaidMaestria;
    @JoinColumn(name = "Estudiante_idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante estudianteidEstudiante;
    @JoinColumn(name = "Ciclo_idCiclo", referencedColumnName = "idCiclo")
    @ManyToOne(optional = false)
    private Ciclo cicloidCiclo;

    public Pago() {
    }

    public Pago(String nroVoucher) {
        this.nroVoucher = nroVoucher;
    }

    public String getNroVoucher() {
        return nroVoucher;
    }

    public void setNroVoucher(String nroVoucher) {
        this.nroVoucher = nroVoucher;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaRegPago() {
        return fechaRegPago;
    }

    public void setFechaRegPago(Date fechaRegPago) {
        this.fechaRegPago = fechaRegPago;
    }

    public Maestria getMaestriaidMaestria() {
        return maestriaidMaestria;
    }

    public void setMaestriaidMaestria(Maestria maestriaidMaestria) {
        this.maestriaidMaestria = maestriaidMaestria;
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
        hash += (nroVoucher != null ? nroVoucher.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.nroVoucher == null && other.nroVoucher != null) || (this.nroVoucher != null && !this.nroVoucher.equals(other.nroVoucher))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pago[ nroVoucher=" + nroVoucher + " ]";
    }

    public float getMontoSaldo() {
        return montoSaldo;
    }

    public void setMontoSaldo(float montoSaldo) {
        this.montoSaldo = montoSaldo;
    }
    
}
