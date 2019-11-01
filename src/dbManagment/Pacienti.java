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
@Table(name = "Pacienti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pacienti.findAll", query = "SELECT p FROM Pacienti p")
    , @NamedQuery(name = "Pacienti.findByPId", query = "SELECT p FROM Pacienti p WHERE p.pId = :pId")
    , @NamedQuery(name = "Pacienti.findByEmri", query = "SELECT p FROM Pacienti p WHERE p.emri = :emri")
    , @NamedQuery(name = "Pacienti.findByMbiemri", query = "SELECT p FROM Pacienti p WHERE p.mbiemri = :mbiemri")
    , @NamedQuery(name = "Pacienti.findByEmriPrindit", query = "SELECT p FROM Pacienti p WHERE p.emriPrindit = :emriPrindit")
    , @NamedQuery(name = "Pacienti.findByVendlindja", query = "SELECT p FROM Pacienti p WHERE p.vendlindja = :vendlindja")
    , @NamedQuery(name = "Pacienti.findByShteti", query = "SELECT p FROM Pacienti p WHERE p.shteti = :shteti")
    , @NamedQuery(name = "Pacienti.findByQyteti", query = "SELECT p FROM Pacienti p WHERE p.qyteti = :qyteti")
    , @NamedQuery(name = "Pacienti.findByAdresa", query = "SELECT p FROM Pacienti p WHERE p.adresa = :adresa")
    , @NamedQuery(name = "Pacienti.findByTel", query = "SELECT p FROM Pacienti p WHERE p.tel = :tel")
    , @NamedQuery(name = "Pacienti.findByEmail", query = "SELECT p FROM Pacienti p WHERE p.email = :email")
    , @NamedQuery(name = "Pacienti.findByDataLindjes", query = "SELECT p FROM Pacienti p WHERE p.dataLindjes = :dataLindjes")
    , @NamedQuery(name = "Pacienti.findByGrupiGjakut", query = "SELECT p FROM Pacienti p WHERE p.grupiGjakut = :grupiGjakut")
    , @NamedQuery(name = "Pacienti.findByGjinia", query = "SELECT p FROM Pacienti p WHERE p.gjinia = :gjinia")
    , @NamedQuery(name = "Pacienti.findBySemundjeKronike", query = "SELECT p FROM Pacienti p WHERE p.semundjeKronike = :semundjeKronike")
    , @NamedQuery(name = "Pacienti.findByAlergjite", query = "SELECT p FROM Pacienti p WHERE p.alergjite = :alergjite")
    , @NamedQuery(name = "Pacienti.findByInfoShtes", query = "SELECT p FROM Pacienti p WHERE p.infoShtes = :infoShtes")})
public class Pacienti implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "P_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pId;
    @Column(name = "Emri")
    private String emri;
    @Column(name = "Mbiemri")
    private String mbiemri;
    @Column(name = "Emri_Prindit")
    private String emriPrindit;
    @Column(name = "Vendlindja")
    private String vendlindja;
    @Column(name = "Shteti")
    private String shteti;
    @Column(name = "Qyteti")
    private String qyteti;
    @Column(name = "Adresa")
    private String adresa;
    @Column(name = "Tel")
    private String tel;
    @Column(name = "Email")
    private String email;
    @Column(name = "Data_Lindjes")
    @Temporal(TemporalType.DATE)
    private Date dataLindjes;
    @Column(name = "Grupi_Gjakut")
    private String grupiGjakut;
    @Column(name = "Gjinia")
    private Character gjinia;
    @Column(name = "Semundje_Kronike")
    private String semundjeKronike;
    @Column(name = "Alergjite")
    private String alergjite;
    @Column(name = "Info_Shtes")
    private String infoShtes;
    @JoinColumn(name = "Klinika_ID", referencedColumnName = "K_ID")
    @ManyToOne
    private Klinika klinikaID;
    @JoinColumn(name = "Staff_ID", referencedColumnName = "S_ID")
    @ManyToOne
    private Staff staffID;
    @OneToMany(mappedBy = "pacID")
    private List<Pagesat> pagesatList;
    @OneToMany(mappedBy = "pacID")
    private List<Vizitat> vizitatList;

    public Pacienti() {
    }

    public Pacienti(Integer pId) {
        this.pId = pId;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public String getEmriPrindit() {
        return emriPrindit;
    }

    public void setEmriPrindit(String emriPrindit) {
        this.emriPrindit = emriPrindit;
    }

    public String getVendlindja() {
        return vendlindja;
    }

    public void setVendlindja(String vendlindja) {
        this.vendlindja = vendlindja;
    }

    public String getShteti() {
        return shteti;
    }

    public void setShteti(String shteti) {
        this.shteti = shteti;
    }

    public String getQyteti() {
        return qyteti;
    }

    public void setQyteti(String qyteti) {
        this.qyteti = qyteti;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataLindjes() {
        return dataLindjes;
    }

    public void setDataLindjes(Date dataLindjes) {
        this.dataLindjes = dataLindjes;
    }

    public String getGrupiGjakut() {
        return grupiGjakut;
    }

    public void setGrupiGjakut(String grupiGjakut) {
        this.grupiGjakut = grupiGjakut;
    }

    public Character getGjinia() {
        return gjinia;
    }

    public void setGjinia(Character gjinia) {
        this.gjinia = gjinia;
    }

    public String getSemundjeKronike() {
        return semundjeKronike;
    }

    public void setSemundjeKronike(String semundjeKronike) {
        this.semundjeKronike = semundjeKronike;
    }

    public String getAlergjite() {
        return alergjite;
    }

    public void setAlergjite(String alergjite) {
        this.alergjite = alergjite;
    }

    public String getInfoShtes() {
        return infoShtes;
    }

    public void setInfoShtes(String infoShtes) {
        this.infoShtes = infoShtes;
    }

    public Klinika getKlinikaID() {
        return klinikaID;
    }

    public void setKlinikaID(Klinika klinikaID) {
        this.klinikaID = klinikaID;
    }

    public Staff getStaffID() {
        return staffID;
    }

    public void setStaffID(Staff staffID) {
        this.staffID = staffID;
    }

    @XmlTransient
    public List<Pagesat> getPagesatList() {
        return pagesatList;
    }

    public void setPagesatList(List<Pagesat> pagesatList) {
        this.pagesatList = pagesatList;
    }

    @XmlTransient
    public List<Vizitat> getVizitatList() {
        return vizitatList;
    }

    public void setVizitatList(List<Vizitat> vizitatList) {
        this.vizitatList = vizitatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pId != null ? pId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacienti)) {
            return false;
        }
        Pacienti other = (Pacienti) object;
        if ((this.pId == null && other.pId != null) || (this.pId != null && !this.pId.equals(other.pId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Pacienti[ pId=" + pId + " ]";
    }
    
}
