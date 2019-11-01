/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagesat;

import Settings.exceptions.NonexistentEntityException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import dbManagment.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class SherbimetController implements Initializable {
private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    @FXML
    private JFXTextField sherbimiText;

    @FXML
    private JFXTextField shumaSherbimitText;
    private Stage thisStage;
    @FXML
    private JFXButton shtoSherbiminBtn;
    @FXML
    private JFXListView<String> listaSherbimeve;
    @FXML
    private JFXButton shtoTeListaBtn;

      @FXML
    private JFXListView<String> listaESherbimeveTe;
          @FXML
    private JFXButton fshijeBtn;

    @FXML
    private JFXButton fshijeNgaListaBtn;
    Integer shumaTotale= 0;
    /**
     * Initializes the controller class.
     */
    private PagesatViewController pagCo;
    FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shtoSherbiminBtn.setOnAction(event -> {
            try {
                shenoSherbimTePagesa();
            } catch (Exception ex) {
                Logger.getLogger(SherbimetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        loadSherbimet();
//        listaSherbimeve = new JFXListView<>();
        shtoTeListaBtn.setOnAction(event -> {
            try {
                shtoTeLista();
            } catch (Exception ex) {
                Logger.getLogger(SherbimetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        listaSherbimeve.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {

                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        // change the label text value to the newly selected
                        // item.
//						
                        String[] s = newValue.split(" -> ");
                        sherbimiText.setText(s[0]);
                        String [] sh = s[1].split(" ");
                        shumaSherbimitText.setText(sh[0]);
                    }
                });
  thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
             pagCo.getShtoSherbimBtn().setDisable(false);
          }
      }); 
       fshijeBtn.setOnAction(event->deleteSelectedItems());
          fshijeNgaListaBtn.setOnAction(event->fshijeNgaLista());
    }

    public SherbimetController(PagesatViewController pagCo) {
        this.pagCo = pagCo;

        thisStage = new Stage();

        // Load the FXML file
        loader = new FXMLLoader(getClass().getResource("/pagesat/Sherbimet.fxml"));

        // Set this class as the controller
        loader.setController(this);
    }

    public void showSherbiemet() {
        try {
            thisStage.setScene(new Scene(loader.load()));

            thisStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SherbimetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isOpen() {
        return thisStage.isShowing();
    }

    public void shenoSherbimTePagesa()throws Exception{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        String sherbimi = sherbimiText.getText();
        if(sherbimi.isEmpty()){
           alert.setContentText("Sherbimi jo valid");
           alert.showAndWait();
           throw new Exception("Sherbimi jo valid");
        }
        String shuma = shumaSherbimitText.getText();
        if(!shuma.matches("[0-9]+")){
            alert.setContentText("Shuma jo valide");
            alert.showAndWait();
            throw new Exception("Shuma jo valide");
        }
        ObservableList<String> s = listaESherbimeveTe.getItems();
        shumaTotale+=Integer.parseInt(shuma);
        String euro = "\u20ac";
        String so = sherbimi+" -> "+shuma+" "+euro;
         listaESherbimeveTe.getItems().add(so);
        
        pagCo.getSherbimet(sherbimi, shuma);
       
        sherbimiText.clear();
        shumaSherbimitText.clear();
        pagCo.setTotalShuma();
        

    }

    public void shtoTeLista() throws Exception{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        String sherbimi = sherbimiText.getText();
        if(sherbimi.isEmpty()){
           alert.setContentText("Sherbimi jo valid");
           alert.showAndWait();
           throw new Exception("Sherbimi jo valid");
        }
        String shuma = shumaSherbimitText.getText();
        if(!shuma.matches("[0-9]+")){
            alert.setContentText("Shuma jo valide");
            alert.showAndWait();
            throw new Exception("Shuma jo valide");
        }
        SherbimetJpaController shjpa  = new SherbimetJpaController(emf);
        Sherbimet sh = new Sherbimet();
        sh.setEmriSherbimit(sherbimi);
        sh.setQmimiSherbimit(Integer.parseInt(shuma));
        sh.setStaffID(pagCo.getDoki());
        shjpa.create(sh);
        listaSherbimeve.getItems().clear();
        loadSherbimet();
        sherbimiText.clear();
        shumaSherbimitText.clear();
    }
    
    public void deleteSelectedItems(){
        if(listaESherbimeveTe !=null){
            String itemToRemove = listaESherbimeveTe.getSelectionModel().getSelectedItem();
            listaESherbimeveTe.getItems().remove(itemToRemove);
            
            pagCo.getListaSherbimeveTeKryera().getItems().remove(itemToRemove);
                pagCo.setTotalShuma();
            
        }
       
    }
    
    public void fshijeNgaLista(){
        if(listaSherbimeve !=null){
            String itemToRemove = listaSherbimeve.getSelectionModel().getSelectedItem();
            SherbimetJpaController shjpa = new SherbimetJpaController(emf);
            String [] item = itemToRemove.split(" -> ");
            String emriSh = item[0];
            String [] qmimiSh = item[1].split(" ");
            String qmimi = qmimiSh[0];
            List<Sherbimet> shlist = shjpa.findSherbimetEntities();
            Integer shid = null;
            for(Sherbimet sh : shlist){
                if(sh.getEmriSherbimit().equals(emriSh) && sh.getQmimiSherbimit() == Integer.parseInt(qmimi)){
                    shid = sh.getSherbimetID();
                    break;
                }
            }
            try {
                shjpa.destroy(shid);
               listaSherbimeve.getItems().clear();
               loadSherbimet();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SherbimetController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }
    
    public boolean isStageVisible(){
        return thisStage.isShowing();
    }
    
    public void loadSherbimet(){
        Staff doki = pagCo.getDoki();
           String euro = "\u20ac";
        Integer sid = doki.getSId();
        SherbimetJpaController shjpa = new SherbimetJpaController(emf);
        List<Sherbimet> shlist = shjpa.findSherbimetEntities();
        String lloji = "";
        Integer shuma =0;
          ObservableList<String> lista = (ObservableList<String>) listaSherbimeve.getItems();
         ArrayList<String> list = new ArrayList<>();
        for(Sherbimet sh : shlist){
          
            if( sh.getStaffID() !=null && sh != null ){
                  if(sid == sh.getStaffID().getSId()){
                      
                       lloji = sh.getEmriSherbimit();
                         shuma = sh.getQmimiSherbimit();
                         String go= lloji+" -> "+shuma+" "+euro;
                       
                       list.add(go);
                             
               
            }
            }
          
        }
        Set<String> unique = new HashSet<String>(list);
        for(String s : unique){
          listaSherbimeve.getItems().add(s);
        }
    }
}
