/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Egzon
 */
public class PacientModel {
        private final StringProperty iDPacienti;
    private final StringProperty emriPacientit;
    private final StringProperty mbiemriPacientit;
    private final StringProperty emriPrindit;
    private final StringProperty datelindja;
    private final StringProperty shteti;
    private final StringProperty qyteti;
    private final StringProperty vendlindja;
    private final StringProperty adresa;
    private final StringProperty tel;
    private final StringProperty eMail;
    private final StringProperty gjinia;
    private final StringProperty semundjeKronike;
    private final StringProperty grupigjakut;
    private final StringProperty alergjit;
    private final StringProperty informataShtesë;
    private final StringProperty IDklinia;
    private final StringProperty IDStaff;
    
       public PacientModel(String iDPacienti, String emriPacientit, String mbiemriPacientit, String emriPrindit, String vendlindja, String shteti, String qyteti, String adresa, String tel, String email, String datelindja, String grupigjakut, String  gjinia, String  semundjeKronike, String  alergjit, String  infoshtes, String idstaff,String idklinika) {
        this.iDPacienti = new SimpleStringProperty(iDPacienti);
        this.adresa = new SimpleStringProperty(adresa);
        this.alergjit = new SimpleStringProperty(alergjit);
        this.datelindja = new SimpleStringProperty(datelindja);
        this.eMail = new SimpleStringProperty(email);
        this.emriPacientit = new SimpleStringProperty(emriPacientit);
        this.emriPrindit = new SimpleStringProperty(emriPrindit);
        this.gjinia = new SimpleStringProperty(gjinia);
        this.grupigjakut = new SimpleStringProperty(grupigjakut);
        this.mbiemriPacientit = new SimpleStringProperty(mbiemriPacientit);
        this.IDklinia = new SimpleStringProperty(idklinika);
        this.IDStaff = new SimpleStringProperty(idstaff);
        this.shteti = new SimpleStringProperty(shteti);
        this.qyteti = new SimpleStringProperty(qyteti);
        this.vendlindja = new SimpleStringProperty(vendlindja);
        this.tel = new SimpleStringProperty(tel);
        this.semundjeKronike = new SimpleStringProperty(semundjeKronike);
        this.informataShtesë = new SimpleStringProperty(infoshtes);

    }
       
        public StringProperty iDPacienti() {
        return iDPacienti;
    }

    public StringProperty EmriPacientit() {
        return emriPacientit;
    }

    public StringProperty MbiemriPacientit() {
        return mbiemriPacientit;
    }

    public StringProperty EmriPrindit() {
        return emriPrindit;
    }

    public StringProperty Datelindja() {
        return datelindja;
    }

    public StringProperty Shteti() {
        return shteti;
    }

    public StringProperty Qyteti() {
        return qyteti;
    }

    public StringProperty Vendlindja() {
        return vendlindja;
    }

    public StringProperty Adresa() {
        return adresa;
    }

    public StringProperty Tel() {
        return tel;
    }

    public StringProperty eMail() {
        return eMail;
    }

    public StringProperty Gjinia() {
        return gjinia;
    }

    public StringProperty SemundjeKronike() {
        return semundjeKronike;
    }

    public StringProperty Grupigjakut() {
        return grupigjakut;
    }

    public StringProperty Alergjit() {
        return alergjit;
    }

    public StringProperty InformataShtesë() {
        return informataShtesë;
    }
    public StringProperty idStaff() {
        return IDStaff;
    }
    
    
    
    
    
       
           public String getiDPacienti() {
        return iDPacienti.get();
    }

    public String getEmriPacientit() {
        return emriPacientit.get();
    }

    public String getMbiemriPacientit() {
        return mbiemriPacientit.get();
    }

    public String getEmriPrindit() {
        return emriPrindit.get();
    }

    public String getDatelindja() {
        return datelindja.get();
    }

    public String getShteti() {
        return shteti.get();
    }

    public String getQyteti() {
        return qyteti.get();
    }

    public String getVendlindja() {
        return vendlindja.get();
    }

    public String getAdresa() {
        return adresa.get();
    }

    public String getTel() {
        return tel.get();
    }

    public String geteMail() {
        return eMail.get();
    }

    public String getGjinia() {
        return gjinia.get();
    }

    public String getSemundjeKronike() {
        return semundjeKronike.get();
    }

    public String getGrupigjakut() {
        return grupigjakut.get();
    }

    public String getAlergjit() {
        return alergjit.get();
    }

    public String getInformataShtesë() {
        return informataShtesë.get();
    }

    public String getIdStaff() {
        return IDStaff.get();
    }

    public String getIDklinika() {
        return IDklinia.get();
    }
    
    
    
     public void setiDPacienti(String value) {
        iDPacienti.set(value);
    }

    public void setIDStaff(String value) {
        IDStaff.set(value);
    }

    public void setIDklinika(String value) {
        IDklinia.set(value);
    }

    public void setInformataShtese(String value) {
        informataShtesë.set(value);
    }

    public void setInformataShtesë(String value) {
        informataShtesë.set(value);
    }

    public void setAlergjit(String value) {
        alergjit.set(value);
    }

    public void setGrupigjakut(String value) {
        grupigjakut.set(value);
    }

    public void setSemundjeKronike(String value) {
        grupigjakut.set(value);
    }

    public void setGjinia(String value) {
        gjinia.set(value);
    }

    public void seteMail(String value) {
        eMail.set(value);
    }

    public void setTel(String value) {
        tel.set(value);
    }

    public void setAdresa(String value) {
        adresa.set(value);
    }

    public void setShteti(String value) {
        shteti.set(value);

    }

    public void setVendlindja(String value) {
        vendlindja.set(value);
    }
    
    
    public void setDatelindja(String value) {
        datelindja.set(value);
    }
    
    public void setEmriPrindit(String value) {
        emriPrindit.set(value);
    }
    
      public void setMbiemriPacientit(String value) {
        mbiemriPacientit.set(value);
    }

        public void setEmriPacientit(String value) {
        emriPacientit.set(value);
    }
        
          @Override
    public String toString() {
        return "PacientDetails{" + "iDPacienti=" + iDPacienti + ", emriPacientit=" + emriPacientit + ", mbiemriPacientit=" + mbiemriPacientit + ", emriPrindit=" + emriPrindit + ", datelindja=" + datelindja + ", gjinia=" + gjinia + ", Shteti=" +shteti + ", qyteti" + qyteti + ", vendlinja=" + vendlindja + ", adrenas=" + adresa + ", tel=" + tel + ", email=" + eMail + ", semundje kronike=" + semundjeKronike + ", grupi gjakut =" + grupigjakut + ", alergjit =" + alergjit + ", infoshtes=" + informataShtesë + ",  id klinika=" + IDklinia + ", id staff=" + IDStaff+'}';
    }

    public String getKlinikaID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      

}
