/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbManagment;

import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Egzon
 */
public class DatatVizitavModel {
       private final StringProperty dataVizitave;
       
       public DatatVizitavModel(String dv){
           dataVizitave = new SimpleStringProperty(dv);
       }

    public StringProperty getDataVizitave() {
        return dataVizitave;
    }
  public String getDV(){
      return dataVizitave.get();
  }
       
      
}
