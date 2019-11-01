/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Egzon
 */
@Entity
@Table(name = "Pagesat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagesat.findAll", query = "SELECT p FROM Pagesat p")
    , @NamedQuery(name = "Pagesat.findByPagesaID", query = "SELECT p FROM Pagesat p WHERE p.pagesaID = :pagesaID")
    , @NamedQuery(name = "Pagesat.findByEmriPac", query = "SELECT p FROM Pagesat p WHERE p.emriPac = :emriPac")
    , @NamedQuery(name = "Pagesat.findByMbiemriPac", query = "SELECT p FROM Pagesat p WHERE p.mbiemriPac = :mbiemriPac")
    , @NamedQuery(name = "Pagesat.findByDataLindjes", query = "SELECT p FROM Pagesat p WHERE p.dataLindjes = :dataLindjes")
    , @NamedQuery(name = "Pagesat.findByDataPageses", query = "SELECT p FROM Pagesat p WHERE p.dataPageses = :dataPageses")
    , @NamedQuery(name = "Pagesat.findByShumaePaguar", query = "SELECT p FROM Pagesat p WHERE p.shumaePaguar = :shumaePaguar")})
public class Pagesat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Pagesa_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pagesaID;
    @Column(name = "Emri_Pac")
    private String emriPac;
    @Column(name = "Mbiemri_Pac")
    private String mbiemriPac;
    @Column(name = "Data_Lindjes")
    @Temporal(TemporalType.DATE)
    private Date dataLindjes;
    @Column(name = "Data_Pageses")
    @Temporal(TemporalType.DATE)
    private Date dataPageses;
    @Column(name = "Shuma_ePaguar")
    private Integer shumaePaguar;
    @JoinColumn(name = "Pac_ID", referencedColumnName = "P_ID")
    @ManyToOne
    private Pacienti pacID;
    @JoinColumn(name = "Staff_ID", referencedColumnName = "S_ID")
    @ManyToOne
    private Staff staffID;
    @OneToMany(mappedBy = "pagesaID")
    private List<Sherbimet> sherbimetList;

    public Pagesat() {
    }

    public Pagesat(Integer pagesaID) {
        this.pagesaID = pagesaID;
    }

    public Integer getPagesaID() {
        return pagesaID;
    }

    public void setPagesaID(Integer pagesaID) {
        this.pagesaID = pagesaID;
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

    public Date getDataLindjes() {
        return dataLindjes;
    }

    public void setDataLindjes(Date dataLindjes) {
        this.dataLindjes = dataLindjes;
    }

    public Date getDataPageses() {
        return dataPageses;
    }

    public void setDataPageses(Date dataPageses) {
        this.dataPageses = dataPageses;
    }

    public double getShumaePaguar() {
        return shumaePaguar;
    }

    public void setShumaePaguar(Integer shumaePaguar) {
        this.shumaePaguar = shumaePaguar;
    }

    public Pacienti getPacID() {
        return pacID;
    }

    public void setPacID(Pacienti pacID) {
        this.pacID = pacID;
    }

    public Staff getStaffID() {
        return staffID;
    }

    public void setStaffID(Staff staffID) {
        this.staffID = staffID;
    }

    @XmlTransient
    public List<Sherbimet> getSherbimetList() {
        return sherbimetList;
    }

    public void setSherbimetList(List<Sherbimet> sherbimetList) {
        this.sherbimetList = sherbimetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagesaID != null ? pagesaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagesat)) {
            return false;
        }
        Pagesat other = (Pagesat) object;
        if ((this.pagesaID == null && other.pagesaID != null) || (this.pagesaID != null && !this.pagesaID.equals(other.pagesaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Pagesat[ pagesaID=" + pagesaID + " ]";
    }
    
}
