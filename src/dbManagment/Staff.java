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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s")
    , @NamedQuery(name = "Staff.findBySId", query = "SELECT s FROM Staff s WHERE s.sId = :sId")
    , @NamedQuery(name = "Staff.findByEmri", query = "SELECT s FROM Staff s WHERE s.emri = :emri")
    , @NamedQuery(name = "Staff.findByMbiermi", query = "SELECT s FROM Staff s WHERE s.mbiermi = :mbiermi")
    , @NamedQuery(name = "Staff.findByEmail", query = "SELECT s FROM Staff s WHERE s.email = :email")
    , @NamedQuery(name = "Staff.findByTel", query = "SELECT s FROM Staff s WHERE s.tel = :tel")
    , @NamedQuery(name = "Staff.findByNumriIdentitetit", query = "SELECT s FROM Staff s WHERE s.numriIdentitetit = :numriIdentitetit")
    , @NamedQuery(name = "Staff.findByLloji", query = "SELECT s FROM Staff s WHERE s.lloji = :lloji")
    , @NamedQuery(name = "Staff.findByTeDhenaShtes", query = "SELECT s FROM Staff s WHERE s.teDhenaShtes = :teDhenaShtes")
    , @NamedQuery(name = "Staff.findBySpecializimi", query = "SELECT s FROM Staff s WHERE s.specializimi = :specializimi")
    , @NamedQuery(name = "Staff.findByMbanTermine", query = "SELECT s FROM Staff s WHERE s.mbanTermine = :mbanTermine")})
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "S_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer sId;
    @Column(name = "Emri")
    private String emri;
    @Column(name = "Mbiermi")
    private String mbiermi;
    @Column(name = "Email")
    private String email;
    @Column(name = "Tel")
    private String tel;
    @Column(name = "Numri_Identitetit")
    private String numriIdentitetit;
    @Column(name = "Lloji")
    private String lloji;
    @Column(name = "Te_Dhena_Shtes")
    private String teDhenaShtes;
    @Column(name = "Specializimi")
    private String specializimi;
    @Column(name = "Mban_Termine")
    private Boolean mbanTermine;
    @OneToMany(mappedBy = "staffID")
    private List<Terminet> terminetList;
    @OneToMany(mappedBy = "staffID")
    private List<Pacienti> pacientiList;
    @OneToMany(mappedBy = "staffID")
    private List<Pagesat> pagesatList;
    @JoinColumn(name = "Klinika_ID", referencedColumnName = "K_ID")
    @ManyToOne
    private Klinika klinikaID;
    @OneToMany(mappedBy = "staffID")
    private List<Vizitat> vizitatList;
    @OneToMany(mappedBy = "staffID")
    private List<Sherbimet> sherbimetList;

    public Staff() {
    }

    public Staff(Integer sId) {
        this.sId = sId;
    }

    public Integer getSId() {
        return sId;
    }

    public void setSId(Integer sId) {
        this.sId = sId;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiermi() {
        return mbiermi;
    }

    public void setMbiermi(String mbiermi) {
        this.mbiermi = mbiermi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNumriIdentitetit() {
        return numriIdentitetit;
    }

    public void setNumriIdentitetit(String numriIdentitetit) {
        this.numriIdentitetit = numriIdentitetit;
    }

    public String getLloji() {
        return lloji;
    }

    public void setLloji(String lloji) {
        this.lloji = lloji;
    }

    public String getTeDhenaShtes() {
        return teDhenaShtes;
    }

    public void setTeDhenaShtes(String teDhenaShtes) {
        this.teDhenaShtes = teDhenaShtes;
    }

    public String getSpecializimi() {
        return specializimi;
    }

    public void setSpecializimi(String specializimi) {
        this.specializimi = specializimi;
    }

    public Boolean getMbanTermine() {
        return mbanTermine;
    }

    public void setMbanTermine(Boolean mbanTermine) {
        this.mbanTermine = mbanTermine;
    }

    @XmlTransient
    public List<Terminet> getTerminetList() {
        return terminetList;
    }

    public void setTerminetList(List<Terminet> terminetList) {
        this.terminetList = terminetList;
    }

    @XmlTransient
    public List<Pacienti> getPacientiList() {
        return pacientiList;
    }

    public void setPacientiList(List<Pacienti> pacientiList) {
        this.pacientiList = pacientiList;
    }

    @XmlTransient
    public List<Pagesat> getPagesatList() {
        return pagesatList;
    }

    public void setPagesatList(List<Pagesat> pagesatList) {
        this.pagesatList = pagesatList;
    }

    public Klinika getKlinikaID() {
        return klinikaID;
    }

    public void setKlinikaID(Klinika klinikaID) {
        this.klinikaID = klinikaID;
    }

    @XmlTransient
    public List<Vizitat> getVizitatList() {
        return vizitatList;
    }

    public void setVizitatList(List<Vizitat> vizitatList) {
        this.vizitatList = vizitatList;
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
        hash += (sId != null ? sId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.sId == null && other.sId != null) || (this.sId != null && !this.sId.equals(other.sId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Staff[ sId=" + sId + " ]";
    }
    
}
