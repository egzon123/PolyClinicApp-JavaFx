/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homeView;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import polyclinicmanagmentv2.pkg0.PolyClinicManagmentV20;
import login.Log_in_controller;
import models.User;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class DrawerController extends Log_in_controller implements Initializable {
     
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton doctorBtn;
    @FXML
    private JFXButton appointmentBtn;
    @FXML
    private JFXButton paymentBtn;
    @FXML
    private JFXButton logoutBtn;
    @FXML
    private JFXButton exitBtn;
    @FXML
    public JFXButton pac;
        
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("//D:testout.txt"));
//            String s = br.readLine();
//            if(s.equals("admin")){
//                pac.setDisable(true);
//            }
//            
//            //        FXMLLoader loader = new FXMLLoader();
////        loader.setLocation(getClass().getResource("/login/log_in_form.fxml"));
////            try {
////                loader.load();
////            } catch (IOException ex) {
////                Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        Log_in_controller lo = loader.getController();
////        if(lo.getUserName()){
////            pac.setDisable(true);
////        }
////
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
//        }

     
    
    }    
    
    @FXML
    private void logOut(ActionEvent event) {
           try {
            Stage window=(Stage) exitBtn.getScene().getWindow();
            PolyClinicManagmentV20 polyfx=new PolyClinicManagmentV20();
            polyfx.start(new Stage());
            window.close();
        } catch (Exception ex) {
            Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exit(ActionEvent event) {
           Platform.exit();
    }
    
   
    
}
