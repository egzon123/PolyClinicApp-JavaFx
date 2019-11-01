/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import dbManagment.DbConnection;
import dbManagment.Users;
import dbManagment.UsersJpaController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import homeView.HomeViewController;
import java.sql.Connection;
import java.util.List;
import javafx.scene.control.Alert;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import dbManagment.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class Log_in_controller implements Initializable {

    @FXML
    private AnchorPane Mainpane;
    
      @FXML
    private AnchorPane holderPane;
    @FXML
    private Button kyqu_button;
    @FXML
    public JFXTextField userName;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXSpinner loggingProgress;
         private DbConnection dc;
           HomeViewController homeCo;
    private Users userLog;
     
    @FXML
    public JFXPasswordField userPassword;
     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
    
            // TODO
            loggingProgress.setVisible(false);

         userPassword.setOnKeyPressed(
                 event->{
                     switch(event.getCode()){
                         case ENTER:
                             kyqu_button.fire();
                  
                            
                     }
                 }
         );
         
         
     
             
    }   
    
    public String getUserName(){
        return userName.getText();
    }
    public String getUserPassword(){
        return userPassword.getText();
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        loggingProgress.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(3));   
        pauseTransition.setOnFinished(ev -> {
            System.out.println("Complte one");
                completeLogin();
            System.out.println("Complte two");
        });
        pauseTransition.play();

    }
   
    private boolean isValidInput() {
         
       boolean b = (userName.getText().trim().length() > 0) && (userPassword.getText().trim().length() > 0);
       
    dc = new DbConnection();
                 Connection conn = dc.Connect();
         
                UsersJpaController usjpa = new UsersJpaController(emf);
                List<Users> users = usjpa.findUsersEntities();
                int count = 0;
                for(Users user : users){
                  if(user.getUserName().equals(userName.getText()) && user.getUserPassword().equals(userPassword.getText())){
                   
                       userLog = user;
                       break;
                  }
                 count++;
                 if(count == users.size()){
                     loggingProgress.setVisible(false);
                     Alert al= new Alert(Alert.AlertType.ERROR);
                     al.setTitle("Info");
                     al.setContentText("UserName ose password gabim");
                     al.setHeaderText(null);
                     al.show();
                     kyqu_button.setDisable(false);
                     b = false;
                      try {
                          throw new Exception("userName ose password gabim");
                      } catch (Exception ex) {
                          Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                 }
      }
    return b;
 
    }
    
    

    public void completeLogin() {
        loggingProgress.setVisible(false);
        if (isValidInput()) {
        
          
       
            
             homeCo = new HomeViewController(this);
      
                    
            homeCo.showHomeStage();
          
             
//                Stage stage = new Stage();
//                Parent home = FXMLLoader.load(getClass().getResource(Routes.HOMEVIEW));
//                Scene sc = new Scene(home);
//                stage.setScene(sc);
                
                     
                        
                
//                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
//                decorator.setCustomMaximize(false);
//                decorator.setBorder(Border.EMPTY);
//
//                Scene scene = new Scene(decorator);
//                scene.getStylesheets().add(PolyClinicManagmentV20.class.getResource("/styles/styles.css").toExternalForm());
//                stage.initStyle(StageStyle.UNDECORATED);
//                stage.setScene(scene);
//
//                stage.setIconified(false);
//                stage.show();
             
              kyqu_button.getScene().getWindow().hide();
                
        
        
   
    
    

    }  
}
    public Users getUserLog(){
    
        return userLog;
    }
}
