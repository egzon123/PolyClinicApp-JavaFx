/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Egzon
 */
@Entity
@Table(name = "Klinika")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klinika.findAll", query = "SELECT k FROM Klinika k")
    , @NamedQuery(name = "Klinika.findByKId", query = "SELECT k FROM Klinika k WHERE k.kId = :kId")
    , @NamedQuery(name = "Klinika.findByEmriKlinikes", query = "SELECT k FROM Klinika k WHERE k.emriKlinikes = :emriKlinikes")
    , @NamedQuery(name = "Klinika.findByNrpunetoreve", query = "SELECT k FROM Klinika k WHERE k.nrpunetoreve = :nrpunetoreve")})
public class Klinika implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "K_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer kId;
    @Column(name = "Emri_Klinikes")
    private String emriKlinikes;
    @Column(name = "Nr_punetoreve")
    private Integer nrpunetoreve;
    @OneToMany(mappedBy = "klinikaID")
    private List<Pacienti> pacientiList;
    @OneToMany(mappedBy = "klinikaID")
    private List<Staff> staffList;

    public Klinika() {
    }

    public Klinika(Integer kId) {
        this.kId = kId;
    }

    public Integer getKId() {
        return kId;
    }

    public void setKId(Integer kId) {
        this.kId = kId;
    }

    public String getEmriKlinikes() {
        return emriKlinikes;
    }

    public void setEmriKlinikes(String emriKlinikes) {
        this.emriKlinikes = emriKlinikes;
    }

    public Integer getNrpunetoreve() {
        return nrpunetoreve;
    }

    public void setNrpunetoreve(Integer nrpunetoreve) {
        this.nrpunetoreve = nrpunetoreve;
    }

    @XmlTransient
    public List<Pacienti> getPacientiList() {
        return pacientiList;
    }

    public void setPacientiList(List<Pacienti> pacientiList) {
        this.pacientiList = pacientiList;
    }

    @XmlTransient
    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kId != null ? kId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Klinika)) {
            return false;
        }
        Klinika other = (Klinika) object;
        if ((this.kId == null && other.kId != null) || (this.kId != null && !this.kId.equals(other.kId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Klinika[ kId=" + kId + " ]";
    }
    
}
