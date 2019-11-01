/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Egzon
 */
@Entity
@Table(name = "Terminet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terminet.findAll", query = "SELECT t FROM Terminet t")
    , @NamedQuery(name = "Terminet.findByTId", query = "SELECT t FROM Terminet t WHERE t.tId = :tId")
    , @NamedQuery(name = "Terminet.findByEmriPac", query = "SELECT t FROM Terminet t WHERE t.emriPac = :emriPac")
    , @NamedQuery(name = "Terminet.findByMbiemriPac", query = "SELECT t FROM Terminet t WHERE t.mbiemriPac = :mbiemriPac")
    , @NamedQuery(name = "Terminet.findByDataTerminit", query = "SELECT t FROM Terminet t WHERE t.dataTerminit = :dataTerminit")
    , @NamedQuery(name = "Terminet.findByKohaFillimit", query = "SELECT t FROM Terminet t WHERE t.kohaFillimit = :kohaFillimit")
    , @NamedQuery(name = "Terminet.findByKohaPerfundimit", query = "SELECT t FROM Terminet t WHERE t.kohaPerfundimit = :kohaPerfundimit")
    , @NamedQuery(name = "Terminet.findByArsyetTerminit", query = "SELECT t FROM Terminet t WHERE t.arsyetTerminit = :arsyetTerminit")})
public class Terminet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "T_ID")
        @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer tId;
    @Column(name = "Emri_Pac")
    private String emriPac;
    @Column(name = "Mbiemri_Pac")
    private String mbiemriPac;
    @Column(name = "Data_Terminit")
    @Temporal(TemporalType.DATE)
    private Date dataTerminit;
    @Column(name = "Koha_Fillimit")
    @Temporal(TemporalType.TIME)
    private Date kohaFillimit;
    @Column(name = "Koha_Perfundimit")
    @Temporal(TemporalType.TIME)
    private Date kohaPerfundimit;
    @Column(name = "Arsyet_Terminit")
    private String arsyetTerminit;
    @JoinColumn(name = "Staff_ID", referencedColumnName = "S_ID")
    @ManyToOne
    private Staff staffID;

    public Terminet() {
    }

    public Terminet(Integer tId) {
        this.tId = tId;
    }

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public String getEmriPac() {
        return emriPac;
    }

    public void setEmriPac(String emriPac) {
        this.emriPac = emriPac;
    }

    public String getMbiemriPac() {
        return mbiemriPac;
    }

    public void setMbiemriPac(String mbiemriPac) {
        this.mbiemriPac = mbiemriPac;
    }

    public Date getDataTerminit() {
        return dataTerminit;
    }

    public void setDataTerminit(Date dataTerminit) {
        this.dataTerminit = dataTerminit;
    }

    public Date getKohaFillimit() {
        return kohaFillimit;
    }

    public void setKohaFillimit(Date kohaFillimit) {
        this.kohaFillimit = kohaFillimit;
    }

    public Date getKohaPerfundimit() {
        return kohaPerfundimit;
    }

    public void setKohaPerfundimit(Date kohaPerfundimit) {
        this.kohaPerfundimit = kohaPerfundimit;
    }

    public String getArsyetTerminit() {
        return arsyetTerminit;
    }

    public void setArsyetTerminit(String arsyetTerminit) {
        this.arsyetTerminit = arsyetTerminit;
    }

    public Staff getStaffID() {
        return staffID;
    }

    public void setStaffID(Staff staffID) {
        this.staffID = staffID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tId != null ? tId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terminet)) {
            return false;
        }
        Terminet other = (Terminet) object;
        if ((this.tId == null && other.tId != null) || (this.tId != null && !this.tId.equals(other.tId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Terminet[ tId=" + tId + " ]";
    }
    
}
