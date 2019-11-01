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
public class UsersModel {

    private final StringProperty userID;
    private final StringProperty emri;
    private final StringProperty mbiemri;
    private final StringProperty dataLindjes;
    private final StringProperty privilegji;
        private final StringProperty userName;
            private final StringProperty password;
            
     public UsersModel(String id,String e,String m,String dL,String p ,String u,String pas){
         userID = new SimpleStringProperty(id);
         emri = new SimpleStringProperty(e);
        mbiemri = new SimpleStringProperty(m);
         dataLindjes = new SimpleStringProperty(dL);
         privilegji = new SimpleStringProperty(p);
         userName = new SimpleStringProperty(u);
         password = new SimpleStringProperty(pas);
     }

    public StringProperty getUserID() {
        return userID;
    }

    public StringProperty getEmri() {
        return emri;
    }

    public StringProperty getMbiemri() {
        return mbiemri;
    }

    public StringProperty getDataLindjes() {
        return dataLindjes;
    }

    public StringProperty getPrivilegji() {
        return privilegji;
    }

    public StringProperty getUserName() {
        return userName;
    }

    public StringProperty getPassword() {
        return password;
    }
     
    
     public String getID(){
         return userID.get();
     }
     
     public String getEm(){
         return emri.get();
     }
     public String getMb(){
         return mbiemri.get();
     }
     public String getDl(){
         return dataLindjes.get();
         
     }
     public String getPR(){
         return privilegji.get();
     }
     public String getUs(){
         return userName.get();
     }
     public String getPas(){
         return password.get();
     }
}
