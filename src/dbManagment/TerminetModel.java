/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Egzon
 */
public class TerminetModel {

    private final StringProperty idTermini;
    private final StringProperty emriPac;
    private final StringProperty mbiemriPac;
    private final StringProperty emriDok;
    private final StringProperty dataTerminit;
    private final StringProperty kohaFillimit;
    private final StringProperty kohaPerfundimit;
    private final StringProperty arsyetTerminit;
    
    public TerminetModel(String idT,String eP,String mP,String dT,String kF,String kP,String aT,String eD){
        this.idTermini = new SimpleStringProperty(idT);
        this.emriPac = new SimpleStringProperty(eP);
        this.mbiemriPac = new SimpleStringProperty(mP);
        this.emriDok = new SimpleStringProperty(eD);
        this.dataTerminit = new SimpleStringProperty(dT);
        this.kohaFillimit = new SimpleStringProperty(kF);
        this.kohaPerfundimit = new SimpleStringProperty(kP);
         this.arsyetTerminit = new SimpleStringProperty(aT);
        
    }

    public StringProperty getIdTermini() {
        return idTermini;
    }

    public StringProperty getEmriPac() {
        return emriPac;
    }

    public StringProperty getMbiemriPac() {
        return mbiemriPac;
    }

    public StringProperty getEmriDok() {
        return emriDok;
    }

    public StringProperty getDataTerminit() {
        return dataTerminit;
    }

    public StringProperty getKohaFillimit() {
        return kohaFillimit;
    }

    public StringProperty getKohaPerfundimit() {
        return kohaPerfundimit;
    }

    public StringProperty getArsyetTerminit() {
        return arsyetTerminit;
    }
    
    
    
    public String getTid(){
        return idTermini.get();
    }
    public String getEP(){
        return emriPac.get();
    }
    public String getMP(){
        return mbiemriPac.get();
       
    }
    public String getED(){
       return emriDok.get();
    }
    public LocalDate getDT(){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
      String date =dataTerminit.get();
      LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
    
    public String getDTa(){
        return dataTerminit.get();
    }
    public String getKF(){
        return kohaFillimit.get();
    }
    public String getKP(){
        return kohaPerfundimit.get();
    }
    public String getAT(){
        return arsyetTerminit.get();
    }
}
