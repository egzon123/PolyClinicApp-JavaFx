/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Egzon
 */
public class PagesatModel {
     private final StringProperty PagesaId;
    private final StringProperty emriPac;
    private final StringProperty mbiemriPac;
    private final StringProperty dataLindjes;
    private final StringProperty dataPageses;

    private final StringProperty shumaEpaguar;
    private final StringProperty staff;
    
    public PagesatModel(String pID,String em,String mb,String dl,String dp,String shp,String st){
        emriPac = new SimpleStringProperty(em);
        PagesaId = new SimpleStringProperty(pID);
        mbiemriPac = new SimpleStringProperty(mb);
        dataLindjes = new SimpleStringProperty(dl);
        dataPageses = new SimpleStringProperty(dp);
        shumaEpaguar = new SimpleStringProperty(shp);
        staff = new SimpleStringProperty(st);
   
    }
    
    public StringProperty getPagesaId(){
        return PagesaId;
    }

    public StringProperty getEmriPac() {
        return emriPac;
    }
    

    public StringProperty getMbiemriPac() {
        return mbiemriPac;
    }

    public StringProperty getDataLindjes() {
        return dataLindjes;
    }

    public StringProperty getDataPageses() {
        return dataPageses;
    }

    public StringProperty getShumaEpaguar() {
        return shumaEpaguar;
    }

    public StringProperty getStaff() {
        return staff;
    }
    
    public String getPid(){
        return PagesaId.get();
    }
    
    public String getEP(){
        return emriPac.get();
        
    }
    public String getMP(){
       return mbiemriPac.get();
    }
    public String getDL(){
        return dataLindjes.get();
    }
    public String getDP(){
        return dataPageses.get();

    }
    public String getSHP(){
        return shumaEpaguar.get();
    }
    public String getST(){
        return staff.get();
    }
    
    public LocalDate getOpenDate(){
        LocalDate localDate = LocalDate.parse(dataLindjes.get());
        return localDate;

    }

}
