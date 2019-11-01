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
@Table(name = "Vizitat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vizitat.findAll", query = "SELECT v FROM Vizitat v")
    , @NamedQuery(name = "Vizitat.findByVId", query = "SELECT v FROM Vizitat v WHERE v.vId = :vId")
    , @NamedQuery(name = "Vizitat.findByEmriPacientit", query = "SELECT v FROM Vizitat v WHERE v.emriPacientit = :emriPacientit")
    , @NamedQuery(name = "Vizitat.findByMbiemriPacientit", query = "SELECT v FROM Vizitat v WHERE v.mbiemriPacientit = :mbiemriPacientit")
    , @NamedQuery(name = "Vizitat.findByDataLindjes", query = "SELECT v FROM Vizitat v WHERE v.dataLindjes = :dataLindjes")
    , @NamedQuery(name = "Vizitat.findByDataVizites", query = "SELECT v FROM Vizitat v WHERE v.dataVizites = :dataVizites")
    , @NamedQuery(name = "Vizitat.findByAnkesatPacientit", query = "SELECT v FROM Vizitat v WHERE v.ankesatPacientit = :ankesatPacientit")
    , @NamedQuery(name = "Vizitat.findByVlersimiMjekut", query = "SELECT v FROM Vizitat v WHERE v.vlersimiMjekut = :vlersimiMjekut")
    , @NamedQuery(name = "Vizitat.findByDiagnoza", query = "SELECT v FROM Vizitat v WHERE v.diagnoza = :diagnoza")
    , @NamedQuery(name = "Vizitat.findByTerapia", query = "SELECT v FROM Vizitat v WHERE v.terapia = :terapia")
    , @NamedQuery(name = "Vizitat.findByRekomandimi", query = "SELECT v FROM Vizitat v WHERE v.rekomandimi = :rekomandimi")})
public class Vizitat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "V_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer vId;
    @Column(name = "Emri_Pacientit")
    private String emriPacientit;
    @Column(name = "Mbiemri_Pacientit")
    private String mbiemriPacientit;
    @Column(name = "Data_Lindjes")
    @Temporal(TemporalType.DATE)
    private Date dataLindjes;
    @Column(name = "Data_Vizites")
    @Temporal(TemporalType.DATE)
    private Date dataVizites;
    @Column(name = "Ankesat_Pacientit")
    private String ankesatPacientit;
    @Column(name = "Vlersimi_Mjekut")
    private String vlersimiMjekut;
    @Column(name = "Diagnoza")
    private String diagnoza;
    @Column(name = "Terapia")
    private String terapia;
    @Column(name = "Rekomandimi")
    private String rekomandimi;
    @JoinColumn(name = "Pac_ID", referencedColumnName = "P_ID")
    @ManyToOne
    private Pacienti pacID;
    @JoinColumn(name = "Staff_ID", referencedColumnName = "S_ID")
    @ManyToOne
    private Staff staffID;

    public Vizitat() {
    }

    public Vizitat(Integer vId) {
        this.vId = vId;
    }

    public Integer getVId() {
        return vId;
    }

    public void setVId(Integer vId) {
        this.vId = vId;
    }

    public String getEmriPacientit() {
        return emriPacientit;
    }

    public void setEmriPacientit(String emriPacientit) {
        this.emriPacientit = emriPacientit;
    }

    public String getMbiemriPacientit() {
        return mbiemriPacientit;
    }

    public void setMbiemriPacientit(String mbiemriPacientit) {
        this.mbiemriPacientit = mbiemriPacientit;
    }

    public Date getDataLindjes() {
        return dataLindjes;
    }

    public void setDataLindjes(Date dataLindjes) {
        this.dataLindjes = dataLindjes;
    }

    public Date getDataVizites() {
        return dataVizites;
    }

    public void setDataVizites(Date dataVizites) {
        this.dataVizites = dataVizites;
    }

    public String getAnkesatPacientit() {
        return ankesatPacientit;
    }

    public void setAnkesatPacientit(String ankesatPacientit) {
        this.ankesatPacientit = ankesatPacientit;
    }

    public String getVlersimiMjekut() {
        return vlersimiMjekut;
    }

    public void setVlersimiMjekut(String vlersimiMjekut) {
        this.vlersimiMjekut = vlersimiMjekut;
    }

    public String getDiagnoza() {
        return diagnoza;
    }

    public void setDiagnoza(String diagnoza) {
        this.diagnoza = diagnoza;
    }

    public String getTerapia() {
        return terapia;
    }

    public void setTerapia(String terapia) {
        this.terapia = terapia;
    }

    public String getRekomandimi() {
        return rekomandimi;
    }

    public void setRekomandimi(String rekomandimi) {
        this.rekomandimi = rekomandimi;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vId != null ? vId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vizitat)) {
            return false;
        }
        Vizitat other = (Vizitat) object;
        if ((this.vId == null && other.vId != null) || (this.vId != null && !this.vId.equals(other.vId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbManagment.Vizitat[ vId=" + vId + " ]";
    }
    
}
