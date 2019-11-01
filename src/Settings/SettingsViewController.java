/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import pagesat.PagesatViewController;
import login.Log_in_controller;
import dbManagment.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class SettingsViewController implements Initializable {
    
    @FXML
    private TableView<UsersModel> users_table;

    @FXML
    private TableColumn<UsersModel,String> col_emri;

    @FXML
    private TableColumn<UsersModel,String> col_mbiemri;

    @FXML
    private TableColumn<UsersModel,String> col_dataLindjes;

    @FXML
    private TableColumn<UsersModel,String> col_privilegji;

    @FXML
    private TableColumn<UsersModel,String> col_userName;

    @FXML
    private TableColumn<UsersModel,String> col_password;
        private homeView.HomeViewController homeCon;
        private FXMLLoader loader;
        private Stage thisStage;
        private Log_in_controller logCo;
         ObservableList<UsersModel> usersData = FXCollections.observableArrayList();
         private DbConnection dc;
        
     @FXML
    private JFXButton userRiBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       userRiBtn.setOnAction(event->openUserView());
       loadUsers();
    }    
    
    
    public SettingsViewController(homeView.HomeViewController homeCo,Log_in_controller logCo){
        this.logCo = logCo;
         this.homeCon = homeCo;
        loader = new FXMLLoader(getClass().getResource("/Settings/settingsView.fxml"));
           loader.setController(this);
    }
    
      public void showScene() {
        try {
            homeCon.holderPane.getChildren().clear();
            homeCon.holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      public void openUserView(){
          UserCrudViewController userView = new UserCrudViewController(this);
          if(!userView.isSageVisible()){
              userRiBtn.setDisable(true);
          }
          userView.showStage();
          
      }
      public homeView.HomeViewController getHomeCo(){
          return homeCon;
      }
      
      public JFXButton getUserIRiBtn(){
          return userRiBtn;
      }
      
      public void loadUsers(){
            dc = new DbConnection();
        
        Connection conn = dc.Connect();
        ResultSet rs = null;
        if(!usersData.isEmpty()){
            usersData.removeAll(usersData);
        }
        
            try {
                rs = conn.createStatement().executeQuery("Select * from Users");
                while(rs.next()){
                    usersData.add(new UsersModel(rs.getString("UserID"),rs.getString("Emri"), rs.getString("Mbiemri"), rs.getString("Data_Lindjes"), rs.getString("Privilegji"), rs.getString("UserName"), rs.getString("UserPassword")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SettingsViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            col_emri.setCellValueFactory(cellData -> cellData.getValue().getEmri());
            col_mbiemri.setCellValueFactory(cellData -> cellData.getValue().getMbiemri());
            col_dataLindjes.setCellValueFactory(cellData -> cellData.getValue().getDataLindjes());
            col_privilegji.setCellValueFactory(cellData -> cellData.getValue().getPrivilegji());
            col_userName.setCellValueFactory(cellData -> cellData.getValue().getUserName());
            col_password.setCellValueFactory(cellData -> cellData.getValue().getPassword());
            users_table.setItems(null);
            users_table.setItems(usersData);
      }
    
    

}
