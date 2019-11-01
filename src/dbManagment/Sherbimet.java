/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

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
 * @author Egzon
 */
@Entity
@Table(name = "Sherbimet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sherbimet.findAll", query = "SELECT s FROM Sherbimet s")
    , @NamedQuery(name = "Sherbimet.findBySherbimetID", query = "SELECT s FROM Sherbimet s WHERE s.sherbimetID = :sherbimetID")
    , @NamedQuery(name = "Sherbimet.findByEmriSherbimit", query = "SELECT s FROM Sherbimet s WHERE s.emriSherbimit = :emriSherbimit")
    , @NamedQuery(name = "Sherbimet.findByQmimiSherbimit", query = "SELECT s FROM Sherbimet s WHERE s.qmimiSherbimit = :qmimiSherbimit")})
public class Sherbimet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Sherbimet_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer sherbimetID;
    @Column(name = "Emri_Sherbimit")
    private String emriSherbimit;
    @Column(name = "Qmimi_Sherbimit")
    private Integer qmimiSherbimit;
    @JoinColumn(name = "Pagesa_ID", referencedColumnName = "Pagesa_ID")
    @ManyToOne
    private Pagesat pagesaID;
    @JoinColumn(name = "Staff_ID", referencedColumnName = "S_ID")
    @ManyToOne
    private Staff staffID;

    public Sherbimet() {
    }

    public Sherbimet(Integer sherbimetID) {
        this.sherbimetID = sherbimetID;
    }

    public Integer getSherbimetID() {
        return sherbimetID;
    }

    public void setSherbimetID(Integer sherbimetID) {
        this.sherbimetID = sherbimetID;
    }

    public String getEmriSherbimit() {
        return emriSherbimit;
    }

    public void setEmriSherbimit(String emriSherbimit) {
        this.emriSherbimit = emriSherbimit;
    }

    public Integer getQmimiSherbimit() {
        return qmimiSherbimit;
    }

    public void setQmimiSherbimit(Integer qmimiSherbimit) {
        this.qmimiSherbimit = qmimiSherbimit;
    }

    public Pagesat getPagesaID() {
        return pagesaID;
    }

    public void setPagesaID(Pagesat pagesaID) {
        this.pagesaID = pagesaID;
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
        hash += (sherbimetID != null ? sherbimetID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sherbimet)) {
            return false;
        }
        Sherbimet other = (Sherbimet) object;
        if ((this.sherbimetID == null && other.sherbimetID != null) || (this.sherbimetID != null && !this.sherbimetID.equals(other.sherbimetID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Sherbimet[ sherbimetID=" + sherbimetID + " ]";
    }
    
}
