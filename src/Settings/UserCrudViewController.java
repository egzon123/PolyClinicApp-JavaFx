/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import dbManagment.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class UserCrudViewController implements Initializable {
    private final Stage thisStage;
      FXMLLoader loader;
    private SettingsViewController setCo;
     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    
    @FXML
    private JFXTextField Emr_txt;

    @FXML
    private JFXTextField Mbiemri_txt;
    @FXML
    private JFXDatePicker dataPicker;

    @FXML
    private JFXTextField userName_txt;

    @FXML
    private JFXTextField password_txt;

    @FXML
    private JFXButton ruajUserBtn;

    @FXML
    private JFXButton editoUserBtn;

    @FXML
    private JFXComboBox<String> combo_privilegji;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
             setCo.getUserIRiBtn().setDisable(false);
          }
      });
        combo_privilegji.getItems().addAll("doktor","admin","recepcionist");
        // TODO
        ruajUserBtn.setOnAction(event->{
            try {
                shtoUser();
            } catch (Exception ex) {
                Logger.getLogger(UserCrudViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    public UserCrudViewController(SettingsViewController settingsController){
      thisStage = new Stage();
      setCo = settingsController;
         try {
            loader = new FXMLLoader(getClass().getResource("/Settings/UserCrudView.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
          

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showStage(){
       thisStage.showAndWait();
    }
       public boolean isSageVisible(){
        return thisStage.isShowing();
    }
    String emri = null;
    String mbiemri = null;
    String privilegji = null;
    String userName = null;
    String password = null;
   
    public void shtoUser()throws Exception{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        
        if(isValid(Emr_txt.getText())){
           emri = Emr_txt.getText();
           
        }else{
         alert.setContentText("Emri jo valid");
         alert.showAndWait();
         throw new Exception("Emri jo valid");
        }
        if(isValid(Mbiemri_txt.getText())){
         mbiemri = Mbiemri_txt.getText();
        }else{
            alert.setContentText("Mbiemri jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri jo valid");
        }
        if(combo_privilegji.getValue() != null)
        {
             privilegji = combo_privilegji.getValue().toString();
        }else{
            alert.setContentText("Cakto privilegjin");
            alert.showAndWait();
            throw new Exception("Cakto privilegjin");
        }
        
        if(isValid(userName_txt.getText())){
           userName = userName_txt.getText();
        }else{
            alert.setContentText("user name jo valid");
            alert.showAndWait();
            throw new Exception("user name jo valid");
        }
        if(!password_txt.getText().isEmpty()){
           password = password_txt.getText();
        }else{
            alert.setContentText("password jo valid");
            alert.showAndWait();
            throw new Exception("password jo valid");
        }
          java.sql.Date datalindjes = null;
                if (dataPicker.getValue() != null) {
                    datalindjes = java.sql.Date.valueOf(dataPicker.getValue());
                  
                }
        Users user = new Users();
        user.setEmri(emri);
        user.setMbiemri(mbiemri);
        user.setDataLindjes(datalindjes);
        user.setPrivilegji(privilegji);
        user.setUserName(userName);
        user.setUserPassword(password);
        UsersJpaController ujpa = new UsersJpaController(emf);
        ujpa.create(user);
        alert.setContentText("User i ri u regjistrua");
        alert.showAndWait();
       ruajUserBtn.getScene().getWindow().hide();
       setCo.getHomeCo().reLoadUsers();
    }
    
    public boolean isValid(String s) {
        String regex = "[A-Za-z\\s]+";
        return s.matches(regex) && !s.trim().isEmpty();//returns true if input and regex matches otherwise false;
    }

}
