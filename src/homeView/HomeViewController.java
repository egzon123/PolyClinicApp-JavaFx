/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homeView;

import Settings.SettingsViewController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import dbManagment.DbConnection;
import helpers.Routes;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.Log_in_controller;
import pagesat.PagesatViewController;
     import pacientet.PacinetetViewController;
import dbManagment.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import staff.*;
import terminet.*;
import vizitat.VizitatViewController;


/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class HomeViewController implements Initializable {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    @FXML
    private Log_in_controller loginController;
        @FXML
    private PieChart pie;
    @FXML
    public JFXHamburger hamburger;
    @FXML
    private Label txtCurrentWindow;
        @FXML
    private Label termineTeCaktuara;

    @FXML
    public AnchorPane holderPane;
        @FXML
    private Circle circle1;

    @FXML
    public JFXDrawer drawer;
   
    @FXML
    public AnchorPane homePane;
     private DbConnection dc;
       @FXML
    private Label profileName;
       private  PacinetetViewController pacCo;
       private FXMLLoader loader;

     /**
     * Initializes the controller class.
     */
       
       public HomeViewController(Log_in_controller logCo){
           loginController = logCo;
          
           
           loader = new FXMLLoader(getClass().getResource("homeView.fxml"));
           loader.setController(this);
       }
       
       public void showHomeStage()
       {
        try {
            Stage stage = new Stage();
             stage.resizableProperty().setValue(Boolean.FALSE);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
       public void setTermineText(String s){
           termineTeCaktuara.setText(s);
       }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
            transition.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();
                
                if (!drawer.isClosed()) {
                    drawer.close();
                } else {
                    drawer.open();
                }

            });
            try {
                VBox sidePane = FXMLLoader.load(getClass().getResource("/homeView/Drawer.fxml"));
                
                AnchorPane login = FXMLLoader.load(getClass().getResource(Routes.LOGINVIEW));
                AnchorPane pacView = FXMLLoader.load(getClass().getResource(Routes.PACIENTATVIEW));
                AnchorPane staffView = FXMLLoader.load(getClass().getResource(Routes.STAFFVIEW));
                AnchorPane tertView = FXMLLoader.load(getClass().getResource(Routes.TERMINETVIEW));
                AnchorPane vizitatView = FXMLLoader.load(getClass().getResource(Routes.VIZITATVIEW));
                AnchorPane raportet = FXMLLoader.load(getClass().getResource(Routes.RAPORTETVIEW));
                AnchorPane pagesatView = FXMLLoader.load(getClass().getResource(Routes.PAGESATVIEW));

                
                Pane paneDrawer = (Pane) sidePane.getChildren().get(0);
                
                drawer.setSidePane(sidePane);
                Alert homeAlert = new Alert(Alert.AlertType.WARNING);
                homeAlert.setHeaderText(null);
                
                for (Node node : paneDrawer.getChildren()) {
                    if (node.getAccessibleText() != null) {
                        node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ev) -> {
                            switch (node.getAccessibleText()) {
                                case "homeView":
//                                    Log_in_controller log = new Log_in_controller();
//                                    log.completeLogin();
                                                   AnchorPane homeView;
                            try {
                                homeView = FXMLLoader.load(getClass().getResource(Routes.HOMEVIEW));
                                AnchorPane home = (AnchorPane) homeView.getChildren().get(1);
                                  setNode(home);
                            } catch (IOException ex) {
                                Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                  
                                    break;
                                case "logOut":
                                    setNode(login);
                                    break;
                                case "pacientet":
                                    
                                    drawer.close();
                                 pacCo   = new PacinetetViewController(this,loginController);
                                   pacCo.showScene();
                              
                                    break;
                                case "staff":
                                    if(!loginController.getUserLog().getPrivilegji().equals("admin")){
                                        homeAlert.setContentText("Duhet te keni rolin admin \n per te vazhduar qasjen ne staff");
                                        homeAlert.showAndWait();
                                        
                                    }else{
                                    StaffController stafc = new StaffController(this);
                                    stafc.showScene();
                                    drawer.close();
                                    }
                                 
                                    break;
                                case "terminet":
                                    drawer.close();
                                   TerminetController terCo = new TerminetController(this);
                                   terCo.showScene();
                                    break;
                                case "vizitatView":
                                    drawer.close();
                                    VizitatViewController vizCo = new VizitatViewController(this);
                                    vizCo.showScene();
                                    break;
                                case "raportet":
                                    drawer.close();
                                    setNode(raportet);
                                    break;
                                case "pagesat":
                                    drawer.close();
//                                    setNode(pagesatView);
                                   PagesatViewController pagCon = new PagesatViewController(this);
                                   pagCon.showScene();
                                  
                                    break;
                                case "settings":
                                    if(!loginController.getUserLog().getPrivilegji().equals("admin")){
                                        homeAlert.setContentText("Duhet te keni privilegjin \n admin per te vazhduar");
                                        homeAlert.showAndWait();
                                  
                                    }      else
                                        {
                                                  drawer.close();
//                                    setNode(settingsView);
                                    SettingsViewController settings = new SettingsViewController(this,loginController);
                                    settings.showScene();
                                    break;
                                                }
                                  
                            }
                        });
                    }
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

     
    }
  
    public void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
    }
    
    public void setProfileUser(String s){
        profileName.setText("User : "+s);
    }
    
    
    
    public void reLoadPac(){
            drawer.close();
             pacCo   = new PacinetetViewController(this,loginController);
             pacCo.showScene();
    }
    public void reLoadTermient(){
        drawer.close();
        TerminetController terC = new TerminetController(this);
        terC.showScene();
    }
    public void reloadVizitat(){
        drawer.close();
        VizitatViewController v = new VizitatViewController(this);
        v.showScene();
    }
    public void reLoadStaff(){
        drawer.close();
        StaffController staffCo = new StaffController(this);
        staffCo.showScene();
    }
    public void reLoadUsers(){
        drawer.close();
        SettingsViewController s = new SettingsViewController(this, loginController);
        s.showScene();
    }
    public Users getUser(){
       return loginController.getUserLog();
    }
}
