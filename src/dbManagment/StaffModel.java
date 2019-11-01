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
public class StaffModel {

    private final StringProperty iDStaff;
    private final StringProperty emri;
    private final StringProperty mbiemri;
    private final StringProperty email;
    private final StringProperty tel;
    private final StringProperty nrIdentitetit;
    private final StringProperty lloji;
    private final StringProperty teDhenaShtes;
    private final StringProperty specializimi;
    private final StringProperty mbanTermine;
    private final StringProperty klinika;

    public StaffModel(String iDStaff, String emri, String mbiemri, String email, String tel, String nrIdentitetit, String lloji, String teDhenaShtes, String specializimi, String mbanTermine, String klinika) {
        this.iDStaff = new SimpleStringProperty(iDStaff);
        this.emri = new SimpleStringProperty(emri);
        this.mbiemri = new SimpleStringProperty(mbiemri);
        this.email = new SimpleStringProperty(email);
        this.tel = new SimpleStringProperty(tel);
        this.nrIdentitetit = new SimpleStringProperty(nrIdentitetit);
        this.lloji = new SimpleStringProperty(lloji);
        this.teDhenaShtes = new SimpleStringProperty(teDhenaShtes);
        this.specializimi = new SimpleStringProperty(specializimi);
        this.mbanTermine = new SimpleStringProperty(mbanTermine);
         this.klinika = new SimpleStringProperty(klinika);
          

    }

    public StringProperty getiDStaff() {
        return iDStaff;
    }

    public StringProperty getEmri() {
        return emri;
    }

    public StringProperty getMbiemri() {
        return mbiemri;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getTel() {
        return tel;
    }

    public StringProperty getNrIdentitetit() {
        return nrIdentitetit;
    }

    public StringProperty getLloji() {
        return lloji;
    }

    public StringProperty getTeDhenaShtes() {
        return teDhenaShtes;
    }

    public StringProperty getSpecializimi() {
        return specializimi;
    }

    public StringProperty getMbanTermine() {
        return mbanTermine;
    }

    public StringProperty getKlinika() {
        return klinika;
    }
    
    
    
    public String getSiD(){
        return iDStaff.get();
    }
    public String getE(){
        return emri.get();
    }
    public String getM(){
        return mbiemri.get();
    }
    public String getEma(){
        return email.get();
    }
    public String getT(){
        return tel.get();
    }
    public String getnrI(){
        return nrIdentitetit.get();
    }
    public String getLl(){
       return lloji.get();
    }
    public String getDhenaS(){
        return teDhenaShtes.get();
    }
    public String getS(){
        return specializimi.get();
    }
    public String getmT(){
        return mbanTermine.get();
        
    }
    public String getK(){
        return klinika.get();
    }
    

}
