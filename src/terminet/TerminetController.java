/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import homeView.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import staff.StaffController;
import dbManagment.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class TerminetController implements Initializable {
    
    @FXML
    private JFXProgressBar PROG;
    private HomeViewController homeCo;
    private FXMLLoader loader;
    private DbConnection dc;
    private Users user;
    @FXML
    private JFXTextField emri_txt;

    @FXML
    private JFXTextField mbiemri_txt;

    @FXML
    private JFXComboBox<String> emri_dok;

    @FXML
    private JFXDatePicker data_terminit;

    @FXML
    private JFXTimePicker koha_fillimit;

    @FXML
    private JFXTimePicker koha_perfundimit;

    @FXML
    private JFXTextField arsyetVizites_txt;
    @FXML
    private TextField kerko_field;

    @FXML
    private JFXButton kerko_btn;

    @FXML
    private JFXCheckBox dataTerminit_checkbox;

    @FXML
    private JFXDatePicker date_search;

    @FXML
    private JFXCheckBox emriDok_checbox;

    @FXML
    private JFXCheckBox emriPac_checkbox;

    @FXML
    private JFXButton fshijeBtn;

    @FXML
    private JFXButton editoBtn;

    @FXML
    private JFXButton shtoBtn;
    
    @FXML
    private JFXButton kerkoBtn;
   
    @FXML
    private TableView<TerminetModel> terminet_table;
    @FXML
    private TableColumn<TerminetModel, String> col_emri;

    @FXML
    private TableColumn<TerminetModel, String> col_mbiemri;

    @FXML
    private TableColumn<TerminetModel, String> col_dok;

    @FXML
    private TableColumn<TerminetModel, String> col_data;

    @FXML
    private TableColumn<TerminetModel, String> col_kohaFillimit;

    @FXML
    private TableColumn<TerminetModel, String> col_kohaPerfundimit;

    @FXML
    private JFXDatePicker data_fillimit_search;
    
    @FXML
    private JFXDatePicker data_perfundimit_search;
    @FXML
    private JFXButton clearBtn;
    private Integer terminiId;
    @FXML
    private TableColumn<TerminetModel, String> col_arsyet;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    ObservableList<TerminetModel> dataTerminet = FXCollections.observableArrayList();
    ObservableList<TerminetModel> dataTerminet1 = FXCollections.observableArrayList();
    FilteredList<TerminetModel> filteredItems = new FilteredList<>(dataTerminet);
    
    FilteredList<TerminetModel> filteredData = new FilteredList<>(dataTerminet, e -> true);

    /**
     * Initializes the controller class.
     *
     */
    public TerminetController(HomeViewController homeCo) {
        this.homeCo = homeCo;
        loader = new FXMLLoader(getClass().getResource("TerminetVeiw.fxml"));
        loader.setController(this);
    }

    public void showScene() {
        try {
            homeCo.holderPane.getChildren().clear();
            homeCo.holderPane.getChildren().add(loader.load());

        } catch (IOException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = homeCo.getUser();
        loadTerminetData();
      LocalDate localDate = LocalDate.now();//For reference
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
String formattedString = localDate.format(formatter);
LocalDate localDates = LocalDate.parse(formattedString, formatter);
data_fillimit_search.setValue(localDates);
        data_fillimit_search.setDisable(true);
       
       dataTerminit_checkbox.setOnAction(eventio->
               isSelectedDate(dataTerminit_checkbox.isSelected())
              );
        
        editoBtn.setDisable(true);
        data_terminit.setValue(LocalDate.now());
        StaffJpaController stjpa = new StaffJpaController(emf);
        List<Staff> slist = stjpa.findStaffEntities();
        for (Staff s : slist) {
            emri_dok.getItems().add(s.getEmri() + " " + s.getMbiermi());
        }
        clearBtn.setOnAction(eventio->clearFields());
        shtoBtn.setOnAction(event -> {
            try {
                shtoTermin();
            } catch (Exception ex) {
                Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        terminet_table.setRowFactory(tv -> {
            TableRow<TerminetModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                TerminetModel terminetModel = row.getItem();
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    terminiId = Integer.parseInt(terminetModel.getTid());

                    emri_txt.setText(terminetModel.getEP());
                    mbiemri_txt.setText(terminetModel.getMP());
                    emri_dok.setValue(terminetModel.getED());
                    LocalDate dataT = LocalDate.parse(terminetModel.getDTa());
                    data_terminit.setValue(dataT);
                    LocalTime startTime = LocalTime.parse(terminetModel.getKF());
                    koha_fillimit.setValue(startTime);
                    LocalTime endTime = LocalTime.parse(terminetModel.getKP());
                    koha_perfundimit.setValue(endTime);
                    arsyetVizites_txt.setText(terminetModel.getAT());
                    editoBtn.setDisable(false);
                }

                fshijeBtn.setOnAction(evento -> fshijeTerminin(terminetModel));
                editoBtn.setOnAction(eventEdit -> {
                    try {
                        editoTerminin();
                    } catch (Exception ex) {
                        Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
            return row;
        });
       
        kerkoBtn.setOnAction(eventiono->{
            try {
               kerkoMeDate(data_fillimit_search.getValue());
            } catch (Exception ex) {
                Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
           kerko_field.setOnKeyPressed(event -> kerko());
           data_fillimit_search.setOnAction(eventData->{
           
        });
          

    }
    
    public void isSelectedDate(boolean b){
       if(b == true){
           kerko_field.setDisable(true);
           data_fillimit_search.setDisable(false);
       }else{
         TerminetController t = new TerminetController(homeCo);
         t.showScene();
         kerko_field.setDisable(false);
             data_fillimit_search.setDisable(true);
            
       }
    }

    public void loadTerminetData() {
        try {
            dc = new DbConnection();
            Connection conn = dc.Connect();
            ResultSet rs = null;

            if (user.getPrivilegji().equals("admin") || user.getPrivilegji().equals("recepcionist")) {
                rs = conn.createStatement().executeQuery("Select * from Terminet");
            } else {
                Staff doktori = null;

                emri_dok.setDisable(true);
                StaffJpaController dok = new StaffJpaController(emf);
                List<Staff> doks = dok.findStaffEntities();
                for (Staff doki : doks) {
                    if ((doki.getEmri().equals(user.getEmri())) && (doki.getMbiermi().equals(user.getMbiemri()))) {
                        doktori = doki;
                        Integer staffId = doki.getSId();
                        emri_dok.setValue(doktori.getEmri() + " " + doktori.getMbiermi());
                        rs = conn.createStatement().executeQuery("Select * from Terminet where Staff_ID =" + staffId);
                        break;
                    }
                }
            }

            StaffJpaController stjpa = new StaffJpaController(emf);
            List<Staff> stffList = stjpa.findStaffEntities();
            String dokEmri = "";
            String kf = "";
            String kp = "";
            while (rs.next()) {
                for (Staff staff : stffList) {
                    if (rs.getString("Staff_ID").equals(staff.getSId().toString())) {
                        dokEmri = staff.getEmri() + " " + staff.getMbiermi();
                    }
                }
                String[] kfv = rs.getString("Koha_Fillimit").toString().split(":");
                String[] kpv = rs.getString("Koha_Perfundimit").toString().split(":");
                String skf = kfv[0] + ":" + kfv[1];
                String skp = kpv[0] + ":" + kpv[1];
                dataTerminet.add(new TerminetModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), skf, skp, rs.getString(7), dokEmri));

            }

        } catch (SQLException ex) {
            Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        col_emri.setCellValueFactory(cellData -> cellData.getValue().getEmriPac());
        col_mbiemri.setCellValueFactory(cellData -> cellData.getValue().getMbiemriPac());
        col_dok.setCellValueFactory(cellData -> cellData.getValue().getEmriDok());
        col_data.setCellValueFactory(cellData -> cellData.getValue().getDataTerminit());
        col_kohaFillimit.setCellValueFactory(cellData -> cellData.getValue().getKohaFillimit());
        col_kohaPerfundimit.setCellValueFactory(cellData -> cellData.getValue().getKohaPerfundimit());
        col_arsyet.setCellValueFactory(cellData -> cellData.getValue().getArsyetTerminit());
        
        terminet_table.setItems(null);
        terminet_table.setItems(dataTerminet);
    }

    public void shtoTermin() throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        String emriPac = "";
        if (isValid(emri_txt.getText())) {
            emriPac = emri_txt.getText();
        } else {
            alert.setContentText("Emri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Emri i pacientit jo valid");
        }
        String mbiemriPac = "";
        if (isValid(mbiemri_txt.getText())) {
            mbiemriPac = mbiemri_txt.getText();
        } else {
            alert.setContentText("Mbiemri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri i pacientit jo valid");
        }
        String emriDoktorit = emri_dok.getValue();
        if (emriDoktorit == null) {
            alert.setContentText("Cakto doktorin");
            alert.showAndWait();
            throw new Exception("Cakto doktorin");
        }
        java.sql.Date dataTerminit = null;
        if (data_terminit.getValue() != null) {
            dataTerminit = java.sql.Date.valueOf(data_terminit.getValue());
        } else {
            alert.setContentText("Cakto daten e Terminit");
            alert.showAndWait();
        }
        LocalTime kF = koha_fillimit.getValue();
        if (kF == null) {
            alert.setContentText("Cakto kohen e fillimit");
            alert.showAndWait();
            throw new Exception("Cakto kohen e fillimit");
        }

        LocalTime kP = koha_perfundimit.getValue();
        if (kP == null) {
            alert.setContentText("Cakto kohen e perfundimit");
            alert.showAndWait();
            throw new Exception("Cakto kohen e perfundimit");
        }
        java.sql.Time kohaFillimit = toSqlTime(koha_fillimit.getValue());

        java.sql.Time kohaPerfundimit = toSqlTime(koha_perfundimit.getValue());
         int c = koha_fillimit.getValue().compareTo(koha_perfundimit.getValue());
        if (c == 1 || c == 0) {
            alert.setContentText("Koha e perfundimit duhet te jet me vone se koha e fillimit");
            alert.showAndWait();
            throw new Exception("Koha e perfundimit duhet te jet me vone se koha e fillimit");
        }
        StaffJpaController stafjpa = new StaffJpaController(emf);
        List<Staff> staffLsit = stafjpa.findStaffEntities();
        String staffEmri = "";
        Staff doki = null;

        for (Staff s : staffLsit) {
            staffEmri = s.getEmri() + " " + s.getMbiermi();
            if (staffEmri.equals(emriDoktorit)) {
                doki = s;

            }
        }
        if (kaTerminTeLire(dataTerminit,kohaFillimit, doki.getSId())) {
            alert.setContentText("Ka termin tjeter ne kete kohe");
            alert.showAndWait();
            throw new Exception("Ka termin tjeter ne kete kohe");
        }

        String arsyetEterminit = arsyetVizites_txt.getText();

        Terminet terminiIri = new Terminet();
        terminiIri.setEmriPac(emriPac);
        terminiIri.setMbiemriPac(mbiemriPac);
        terminiIri.setDataTerminit(dataTerminit);
        terminiIri.setStaffID(doki);
        terminiIri.setKohaFillimit(kohaFillimit);
        terminiIri.setKohaPerfundimit(kohaPerfundimit);
        terminiIri.setArsyetTerminit(arsyetEterminit);
        TerminetJpaController terJpa = new TerminetJpaController(emf);
        terJpa.create(terminiIri);
        alert.setContentText("Termini u shtua me sukses");
        alert.showAndWait();

        clearTable();
        loadTerminetData();
    }

    public boolean isValid(String s) {
        String regex = "[A-Za-z\\s]+";
        return !s.trim().isEmpty() && s.matches(regex);  //returns true if input and regex matches otherwise false;
    }

    @FXML
    public void clearTable() {
        terminet_table.getItems().clear();

    }

    public static java.sql.Time toSqlTime(LocalTime localTime) {

        return java.sql.Time.valueOf(localTime);
    }

    public boolean kaTerminTeLire(java.sql.Date data,java.sql.Time koha, Integer idDok) {
        StaffJpaController stafjpa = new StaffJpaController(emf);
        Staff doki = stafjpa.findStaff(idDok);
        List<Terminet> terminetDok = doki.getTerminetList();
        for (Terminet t : terminetDok) {
            if (data.equals(t.getDataTerminit()) && koha.equals(t.getKohaFillimit()) ) {
                return true;
            }
        }
        return false;
    }

    public void fshijeTerminin(TerminetModel termini) {
        if (termini == null) {
            Alert selektoTer = new Alert(Alert.AlertType.INFORMATION);
       
            selektoTer.setTitle("Informacion");
            selektoTer.setContentText("Selekto nje termin");
            selektoTer.showAndWait();
        } else {
            Alert fshijAlert = new Alert(Alert.AlertType.CONFIRMATION);
            fshijAlert.setHeaderText(null);
            fshijAlert.setTitle("Informacion");

            fshijAlert.setContentText("Deshironi ta fshini terminin per pacientin : " + termini.getEP() + " " + termini.getMP() + "\n nga databaza");
            Optional<ButtonType> option = fshijAlert.showAndWait();
            if (option.get() == ButtonType.OK) {
                TerminetJpaController terjpa = new TerminetJpaController(emf);
                Integer terId = Integer.parseInt(termini.getTid());
                try {
                    terjpa.destroy(terId);
                } catch (Settings.exceptions.NonexistentEntityException ex) {
                    Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
                }
                fshijAlert.setHeaderText(null);
                fshijAlert.setContentText("Termini u fshi nga databaza");
                fshijAlert.show();
                clearTable();
                loadTerminetData();

            }
        }
    }

    public void editoTerminin() throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (terminiId == null) {
            alert.setContentText("Selekto nje Termin");
            alert.showAndWait();
            throw new Exception("Selekto nje Termin");
        }
        String emriPac = "";
        if (isValid(emri_txt.getText())) {
            emriPac = emri_txt.getText();
        } else {
            alert.setContentText("Emri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Emri i pacientit jo valid");
        }
        String mbiemriPac = "";
        if (isValid(mbiemri_txt.getText())) {
            mbiemriPac = mbiemri_txt.getText();
        } else {
            alert.setContentText("Mbiemri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri i pacientit jo valid");
        }
        String emriDoktorit = emri_dok.getValue();
        if (emriDoktorit == null) {
            alert.setContentText("Cakto doktorin");
            alert.showAndWait();
            throw new Exception("Cakto doktorin");
        }
        java.sql.Date dataTerminit = null;
        if (data_terminit.getValue() != null) {
            dataTerminit = java.sql.Date.valueOf(data_terminit.getValue());
        } else {
            alert.setContentText("Cakto daten e Terminit");
            alert.showAndWait();
        }
        LocalTime kF = koha_fillimit.getValue();
        if (kF == null) {
            alert.setContentText("Cakto kohen e fillimit");
            alert.showAndWait();
            throw new Exception("Cakto kohen e fillimit");
        }

        LocalTime kP = koha_perfundimit.getValue();
        if (kP == null) {
            alert.setContentText("Cakto kohen e perfundimit");
            alert.showAndWait();
            throw new Exception("Cakto kohen e perfundimit");
        }
        java.sql.Time kohaFillimit = toSqlTime(koha_fillimit.getValue());

        java.sql.Time kohaPerfundimit = toSqlTime(koha_perfundimit.getValue());
       int c = koha_fillimit.getValue().compareTo(koha_perfundimit.getValue());
        if (c == 1 || c == 0) {
            alert.setContentText("Koha e perfundimit duhet te jet me vone se koha e fillimit");
            alert.showAndWait();
            throw new Exception("Koha e perfundimit duhet te jet me vone se koha e fillimit");
        }
        StaffJpaController stafjpa = new StaffJpaController(emf);
        List<Staff> staffLsit = stafjpa.findStaffEntities();
        String staffEmri = "";
        Staff doki = null;

        for (Staff s : staffLsit) {
            staffEmri = s.getEmri() + " " + s.getMbiermi();
            if (staffEmri.equals(emriDoktorit)) {
                doki = s;

            }
        }

        TerminetJpaController terJpa = new TerminetJpaController(emf);
        Terminet termini = terJpa.findTerminet(terminiId);
        List<Terminet> terList = stafjpa.findStaff(doki.getSId()).getTerminetList();
        if(!kohaFillimit.equals(termini.getKohaFillimit())){
              if (kaTerminTeLire(dataTerminit,kohaFillimit, doki.getSId())) {
                    alert.setContentText("Ka termin tjeter ne kete kohe");
                    alert.showAndWait();
                    throw new Exception("Ka termin tjeter ne kete kohe");
                }
        }
 
          
        

        String arsyetEterminit = arsyetVizites_txt.getText();
        
        Terminet terminiedit = new Terminet();
        terminiedit.setTId(terminiId);
        terminiedit.setEmriPac(emriPac);
        terminiedit.setMbiemriPac(mbiemriPac);
        terminiedit.setDataTerminit(dataTerminit);
        terminiedit.setStaffID(doki);
        terminiedit.setKohaFillimit(kohaFillimit);
        terminiedit.setKohaPerfundimit(kohaPerfundimit);
        terminiedit.setArsyetTerminit(arsyetEterminit);

        terJpa.edit(terminiedit);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);
        al.setContentText("Termini u editua me sukses");
        al.showAndWait();
        homeCo.reLoadTermient();
        
    }
    
    public void clearFields(){
        emri_txt.clear();
        mbiemri_txt.clear();
       data_terminit.setValue(LocalDate.now());
       koha_fillimit.setValue(null);
       koha_perfundimit.setValue(null);
       arsyetVizites_txt.clear();
        
    }
    
    public void kerko(){
      

        kerko_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(terminetDetails -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
             if (terminetDetails.getEP().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
               }
                  else if (terminetDetails.getED().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } 
                  
                return false;
            });
        });
        SortedList<TerminetModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(terminet_table.comparatorProperty());
        terminet_table.setItems(sortedData);
    }

    private void kerkoMeDate(LocalDate value) throws Exception{
        Alert alert = new Alert(Alert.AlertType.WARNING);
      
      java.sql.Date date = java.sql.Date.valueOf(value);
        String data = date.toString();
        if(value == null){
              alert.setContentText("Cakto daten");
        alert.showAndWait();
        throw new Exception("Cakto daten");
        }
          try {
            dc = new DbConnection();
            Connection conn = dc.Connect();
            ResultSet rs = null;

            if (user.getPrivilegji().equals("admin") || user.getPrivilegji().equals("recepcionist")) {
                rs = conn.createStatement().executeQuery("Select * from Terminet where Data_Terminit="+"\'"+data+"\'");
            } else {
                Staff doktori = null;

                emri_dok.setDisable(true);
                StaffJpaController dok = new StaffJpaController(emf);
                List<Staff> doks = dok.findStaffEntities();
                for (Staff doki : doks) {
                    if ((doki.getEmri().equals(user.getEmri())) && (doki.getMbiermi().equals(user.getMbiemri()))) {
                        doktori = doki;
                        Integer staffId = doki.getSId();
                        emri_dok.setValue(doktori.getEmri() + " " + doktori.getMbiermi());
                        rs = conn.createStatement().executeQuery("Select * from Terminet where Staff_ID =" + staffId+" and Data_Terminit="+"\'"+data+"\'");
                        break;
                    }
                }
            }

            StaffJpaController stjpa = new StaffJpaController(emf);
            List<Staff> stffList = stjpa.findStaffEntities();
            String dokEmri = "";
            String kf = "";
            String kp = "";
            while (rs.next()) {
                for (Staff staff : stffList) {
                    if (rs.getString("Staff_ID").equals(staff.getSId().toString())) {
                        dokEmri = staff.getEmri() + " " + staff.getMbiermi();
                    }
                }
                String[] kfv = rs.getString("Koha_Fillimit").toString().split(":");
                String[] kpv = rs.getString("Koha_Perfundimit").toString().split(":");
                String skf = kfv[0] + ":" + kfv[1];
                String skp = kpv[0] + ":" + kpv[1];
                dataTerminet1.add(new TerminetModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), skf, skp, rs.getString(7), dokEmri));

            }

        } catch (SQLException ex) {
            Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       // clearTable();
        terminet_table.setItems(null);
        terminet_table.setItems(dataTerminet1);
        
    }
    
    public void kerkoMeDate(){
         filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
        LocalDate minDate = data_fillimit_search.getValue();
        LocalDate maxDate = data_perfundimit_search.getValue();

        // get final values != null
        final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
        final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

        // values for openDate need to be in the interval [finalMin, finalMax]
        return ti -> !finalMin.isAfter(ti.getDT()) && !finalMax.isBefore(ti.getDT());
    },
    data_fillimit_search.valueProperty(),
    data_perfundimit_search.valueProperty()));
           clearTable();
terminet_table.setItems(filteredItems);
    }

    
}
