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
public class VizitatModel {
//    private final StringProperty iDvizitat;
    private final StringProperty emriPac;
    private final StringProperty mbiemriPac;
    private final StringProperty dataLindjes;
    private final StringProperty emriDok;
    
    public VizitatModel(String emriPac,String mbiemriPac,String dataLindjes,String emriDok){
//        this.iDvizitat = new SimpleStringProperty(iDvizitat);
        this.emriPac = new SimpleStringProperty(emriPac);
        this.mbiemriPac = new SimpleStringProperty(mbiemriPac);
        this.dataLindjes = new SimpleStringProperty(dataLindjes);
        this.emriDok = new SimpleStringProperty(emriDok);
    }

//    public StringProperty getiDvizitat() {
//        return iDvizitat;
//    }

    public StringProperty getEmriPac() {
        return emriPac;
    }

    public StringProperty getMbiemriPac() {
        return mbiemriPac;
    }

    public StringProperty getDataLindjes() {
        return dataLindjes;
    }

    public StringProperty getEmriDok() {
        return emriDok;
    }
    
    
//    public String getidV(){
//        return iDvizitat.get();
//    }
    
    public String getEP(){
        return emriPac.get();
    }
    public String getMP(){
        return mbiemriPac.get();
    }
    public String getDL(){
        return dataLindjes.get();
    }
    
    public String getED(){
        return emriDok.get();
    }
            
}
