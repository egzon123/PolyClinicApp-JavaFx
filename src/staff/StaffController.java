/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import dbManagment.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import dbManagment.*;
import dbManagment.exceptions.NonexistentEntityException;
import homeView.HomeViewController;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pacientet.PacinetetViewController;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class StaffController implements Initializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    @FXML
    private JFXComboBox<String> comboSpecialization;
    @FXML
    private JFXComboBox<String> comboDepartment;
    @FXML
    private JFXTextField search_txt;
    @FXML
    private ToggleGroup q;
    @FXML
    private JFXTextField emri_txt;

    @FXML
    private JFXTextField mbiemri_txt;

    @FXML
    private JFXTextField email_txt;

    @FXML
    private JFXTextField tel_txt;

    @FXML
    private JFXTextField numriIdentitetit_txt;

    @FXML
    private JFXTextArea teDhenaShtes_txt;

    @FXML
    private JFXComboBox<String> lloji_combo;

    @FXML
    private JFXComboBox<String> specializimi_combo;

    @FXML
    private JFXComboBox<String> klinika_combo;

    @FXML
    private JFXToggleButton mbanTermine_toggle;

    @FXML
    private JFXButton editoBtn;

    @FXML
    private JFXButton ruajBtn;

    @FXML
    private TableView<StaffModel> staf_table;

    @FXML
    private TableColumn<StaffModel, String> col_emri;

    @FXML
    private TableColumn<StaffModel, String> col_mbiemri;

    @FXML
    private TableColumn<StaffModel, String> col_email;

    @FXML
    private TableColumn<StaffModel, String> col_tel;

    @FXML
    private TableColumn<StaffModel, String> col_nrI;

    @FXML
    private TableColumn<StaffModel, String> col_lloji;

    @FXML
    private TableColumn<StaffModel, String> col_specializimi;

    @FXML
    private TableColumn<StaffModel, String> col_klinika;

    @FXML
    private TableColumn<StaffModel, String> col_mbanTermine;
    private HomeViewController homeController;
    @FXML
    private JFXButton editBtn2;
    @FXML
    private JFXButton editoBtn1;

    @FXML
    private JFXButton fshijBtn;
    @FXML
    private Label nrIden_label;

    @FXML
    private Label lloji_label;

    @FXML
    private Label emri_label;

    @FXML
    private Label mbiemri_label;

    @FXML
    private Label tel_label;

    @FXML
    private Label email_label;

    @FXML
    private Label specializimi_label;

    @FXML
    private Label klinika_label;

    @FXML
    private Label mbanTermine_label;
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXButton clearBtn;
    private Integer staffId;

    @FXML
    private TableColumn<StaffModel, String> col_teDhenaShtes;
    private FXMLLoader loader;
    private DbConnection dc;
    ObservableList<StaffModel> datastaff = FXCollections.observableArrayList();
    FilteredList<StaffModel> filteredData = new FilteredList<>(datastaff, e -> true);

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    public StaffController(HomeViewController homeCo) {
        homeController = homeCo;
        loader = new FXMLLoader(getClass().getResource("StaffView.fxml"));
        loader.setController(this);
    }

    public void showScene() {
        try {
            homeController.holderPane.getChildren().clear();
            homeController.holderPane.getChildren().add(loader.load());

        } catch (IOException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        lloji_label = new Label();
//        emri_label = new Label();
//        mbiemri_label = new Label();
//        email_label = new Label();
//        tel_label = new Label();
//        specializimi_label = new Label();
//        klinika_label = new Label();
//        mbanTermine_label = new Label();

        loadStaffData();
        editoBtn1.setDisable(true);
        search_txt.setOnKeyPressed(event -> search());
        lloji_combo.getItems().addAll("Doktor", "Recepcionist", "Staff Teknik");
        specializimi_combo.getItems().addAll("Dr", "Msc", "Bsc", "ska", "tjeter");
        KlinikaJpaController kjpa = new KlinikaJpaController(emf);
        List<Klinika> klist = kjpa.findKlinikaEntities();
        for (Klinika k : klist) {
            klinika_combo.getItems().add(k.getEmriKlinikes());
        }
        ruajBtn.setOnAction(event -> {
            try {
                ruajStaff();
            } catch (Exception ex) {
                Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        });
         
                  editoBtn1.setOnAction(eventEdit -> {
            try { 
                editStaff();
            } catch (Exception ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
                });
             
          
        staf_table.setRowFactory(tv -> {
            TableRow<StaffModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                StaffModel staffModel = row.getItem();
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    lloji_label.setText(staffModel.getLl());
                    emri_label.setText(staffModel.getE());
                    mbiemri_label.setText(staffModel.getM());
                    email_label.setText(staffModel.getEma());
                    tel_label.setText(staffModel.getT());
                    specializimi_label.setText(staffModel.getS());
                    klinika_label.setText(staffModel.getK());
                    mbanTermine_label.setText(staffModel.getmT());
                    nrIden_label.setText(staffModel.getnrI());
                }

                editBtn2.setOnAction(eventEdit -> setDataToEdit(staffModel));

                fshijBtn.setOnAction(evento -> fshijStaff(staffModel));
                clearBtn.setOnAction(eventClear -> clearFields());
              
            });
            return row;
        });
    }

    public void loadStaffData() {
        try {
            dc = new DbConnection();
            Connection conn = dc.Connect();
            ResultSet rs = null;
            rs = conn.createStatement().executeQuery("Select * from Staff");
            KlinikaJpaController kjpa = new KlinikaJpaController(emf);
            List<Klinika> klinikaList = kjpa.findKlinikaEntities();
            String mbanTermine = "";

            String klinikaEmri = "";
            while (rs.next()) {

                for (Klinika k : klinikaList) {
                    if (rs.getString(11) == null) {
                        klinikaEmri = "";
                        break;
                    }
                    if (k.getKId().toString().equals(rs.getString(11))) {
                        klinikaEmri = k.getEmriKlinikes();

                    }
                }
                if (rs.getString(10).toString().equals("1")) {
                    mbanTermine = "po";
                } else {
                    mbanTermine = "jo";
                }
                datastaff.add(new StaffModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), mbanTermine, klinikaEmri));

            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);

        }
        col_emri.setCellValueFactory(cellData -> cellData.getValue().getEmri());
        col_mbiemri.setCellValueFactory(cellData -> cellData.getValue().getMbiemri());
        col_email.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        col_tel.setCellValueFactory(cellData -> cellData.getValue().getTel());
        col_nrI.setCellValueFactory(cellData -> cellData.getValue().getNrIdentitetit());
        col_lloji.setCellValueFactory(cellData -> cellData.getValue().getLloji());
        col_teDhenaShtes.setCellValueFactory(cellData -> cellData.getValue().getTeDhenaShtes());
        col_specializimi.setCellValueFactory(cellData -> cellData.getValue().getSpecializimi());
        col_mbanTermine.setCellValueFactory(cellData -> cellData.getValue().getMbanTermine());
        col_klinika.setCellValueFactory(cellData -> cellData.getValue().getKlinika());
        staf_table.setItems(null);
        staf_table.setItems(datastaff);

    }

    public void search() {

        search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(staffditails -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (staffditails.getE().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (staffditails.getM().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<StaffModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(staf_table.comparatorProperty());
        staf_table.setItems(sortedData);
    }

    public void ruajStaff() throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        StaffJpaController stafjpa = new StaffJpaController(emf);
        List<Staff> staffList = stafjpa.findStaffEntities();
        for (Staff s : staffList) {
            if (s.getNumriIdentitetit().equals(numriIdentitetit_txt.getText())) {
                alert.setContentText("Punetori ekziston ne databaze");
                alert.showAndWait();
                throw new Exception("Punetori ekziston ne databaze");
            }
        }
        String emri = "";
        String mbiemri = "";
        String email = "";
        String tel = "";
        String nrIdentifikimit = "";
        String lloji = "";
        String specializimi = "";
        Klinika klinika = null;

        if (isValid(emri_txt.getText())) {
            emri = emri_txt.getText();
        } else {
            alert.setContentText("Emri jo valid");
            alert.showAndWait();
            throw new Exception("Emri jo valid");
        }
        if (isValid(mbiemri_txt.getText())) {
            mbiemri = mbiemri_txt.getText();
        } else {
            alert.setContentText("Mbiemri jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri jo valid");
        }
        if (email_txt.getText().trim().isEmpty() || !email_txt.getText().contains("@") || !email_txt.getText().contains(".")){
           
                alert.setContentText("Email jo vlaid");
            alert.showAndWait();
            throw new Exception("Email jo valid");
        } else {
         email = email_txt.getText();
        }
        if (tel_txt.getText().length() > 2 && tel_txt.getText().matches("[0-9]+")) {
            tel = tel_txt.getText();

        } else {
            alert.setContentText("Numri i telefonit jo valid");
            alert.showAndWait();
            throw new Exception("Numri i telefonit jo valid");
        }
        if (numriIdentitetit_txt.getText().length() == 10 && numriIdentitetit_txt.getText().matches("[0-9]+")) {
            nrIdentifikimit = numriIdentitetit_txt.getText();
        } else {
            alert.setContentText("Numri i identifikimit jo valid");
            alert.showAndWait();
            throw new Exception("Numri i identifikimit jo valid");
        }
        String dhenaShtes = teDhenaShtes_txt.getText();
        if (lloji_combo.getValue() != null) {
            lloji = lloji_combo.getValue().toString();
        } else {
            alert.setContentText("Cakto llojin");
            alert.showAndWait();
            throw new Exception("Cakto llojin");
        }

        if (specializimi_combo.getValue() != null) {
            specializimi = specializimi_combo.getValue();
        } else {
            alert.setContentText("Cakto specializimin");
            alert.showAndWait();
            throw new Exception("Cakto specializimin");
        }

        if (klinika_combo.getValue() != null) {
            KlinikaJpaController kjpa = new KlinikaJpaController(emf);
            List<Klinika> klist = kjpa.findKlinikaEntities();
            for (Klinika k : klist) {
                if (k.getEmriKlinikes().toString().equals(klinika_combo.getValue().toString())) {
                    klinika = k;
                }
            }
        } else {
            alert.setContentText("Cakto Kliniken");
            alert.showAndWait();
            throw new Exception("Cakto Kliniken");
        }

        Staff staff = new Staff();
        staff.setEmri(emri);
        staff.setMbiermi(mbiemri);
        staff.setEmail(email);
        staff.setTel(tel);
        staff.setNumriIdentitetit(nrIdentifikimit);
        staff.setTeDhenaShtes(dhenaShtes);
        staff.setLloji(lloji);
        staff.setSpecializimi(specializimi);
        staff.setKlinikaID(klinika);
        boolean mbanTermine = false;
        if (mbanTermine_toggle.isSelected()) {
            mbanTermine = true;
        }
        staff.setMbanTermine(mbanTermine);
        stafjpa.create(staff);
        alert.setContentText("Punetori :" + staff.getEmri() + " " + staff.getMbiermi() + "\n u shtua me sukses ne databaze");
        alert.showAndWait();
        clearTable();
        loadStaffData();

    }

    public boolean isValid(String s) {
        String regex = "[A-Za-z\\s]+";
        return !s.trim().isEmpty() && s.matches(regex);  //returns true if input and regex matches otherwise false;
    }

    @FXML
    public void clearTable() {
        staf_table.getItems().clear();

    }

    public void fshijStaff(StaffModel staff) {
        if (staff == null) {
            Alert selektoPac = new Alert(Alert.AlertType.INFORMATION);
            selektoPac.setHeaderText(null);
            selektoPac.setTitle("Informacion");
            selektoPac.setContentText("Selekto nje anetar");
            selektoPac.showAndWait();
        } else {
            Alert fshijAlert = new Alert(Alert.AlertType.CONFIRMATION);
            fshijAlert.setHeaderText(null);
            fshijAlert.setTitle("Informacion");

            fshijAlert.setContentText("Deshironi ta fshini anetarin : " + staff.getE() + " " + staff.getM() + "\n nga databaza");
            Optional<ButtonType> option = fshijAlert.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    StaffJpaController sjpa = new StaffJpaController(emf);
                    Integer staffId = Integer.parseInt(staff.getSiD());
                    sjpa.destroy(staffId);
                    fshijAlert.setHeaderText(null);
                    fshijAlert.setContentText("Anetari u fshi nga databaza");
                    fshijAlert.show();
                    loadStaffData();
                    clearTable();
                    loadStaffData();
                    homeController.reLoadStaff();

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public void setDataToEdit(StaffModel staffModel) {
        if (staffModel != null) {
            staffId =Integer.parseInt(staffModel.getSiD());
            
            lloji_combo.setValue(staffModel.getLl());

            emri_txt.setText(staffModel.getE());

            mbiemri_txt.setText(staffModel.getM());

            email_txt.setText(staffModel.getEma());

            tel_txt.setText(staffModel.getT());

            specializimi_combo.setValue(staffModel.getS());

            klinika_combo.setValue(staffModel.getK());
            boolean mbanTermine = false;
            if (staffModel.getmT().equals("po")) {
                mbanTermine = true;
            }
            mbanTermine_toggle.setSelected(mbanTermine);

            numriIdentitetit_txt.setText(staffModel.getnrI());
            tabPane.getSelectionModel().select(0);
            editoBtn1.setDisable(false);
            ruajBtn.setDisable(true);
        }

    }

    public void clearFields() {
        lloji_combo.setValue(null);

        emri_txt.setText("");

        mbiemri_txt.setText("");

        email_txt.setText("");

        tel_txt.setText("");

        specializimi_combo.setValue(null);

        klinika_combo.setValue(null);

        mbanTermine_toggle.setSelected(false);

        numriIdentitetit_txt.setText("");

        editoBtn1.setDisable(true);
        ruajBtn.setDisable(false);
        
        
    }

    public void editStaff() throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        StaffJpaController stafjpa = new StaffJpaController(emf);

        String emri = "";
        String mbiemri = "";
        String email = "";
        String tel = "";
        String nrIdentifikimit = "";
        String lloji = "";
        String specializimi = "";
        Klinika klinika = null;

        if (isValid(emri_txt.getText())) {
            emri = emri_txt.getText();
        } else {
            alert.setContentText("Emri jo valid");
            alert.showAndWait();
            throw new Exception("Emri jo valid");
        }
        if (isValid(mbiemri_txt.getText())) {
            mbiemri = mbiemri_txt.getText();
        } else {
            alert.setContentText("Mbiemri jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri jo valid");
        }
        if (email_txt.getText().trim().isEmpty() || !email_txt.getText().contains("@") || !email_txt.getText().contains(".")){
           
                alert.setContentText("Email jo vlaid");
            alert.showAndWait();
            throw new Exception("Email jo valid");
        } else {
         email = email_txt.getText();
        }
        if (tel_txt.getText().length() > 2 && tel_txt.getText().matches("[0-9]+")) {
            tel = tel_txt.getText();

        } else {
            alert.setContentText("Numri i telefonit jo valid");
            alert.showAndWait();
            throw new Exception("Numri i telefonit jo valid");
        }
        if (numriIdentitetit_txt.getText().length() == 10 && numriIdentitetit_txt.getText().matches("[0-9]+")) {
            nrIdentifikimit = numriIdentitetit_txt.getText();
        } else {
            alert.setContentText("Numri i identifikimit jo valid");
            alert.showAndWait();
            throw new Exception("Numri i identifikimit jo valid");
        }
        String dhenaShtes = teDhenaShtes_txt.getText();
        if (lloji_combo.getValue() != null) {
            lloji = lloji_combo.getValue().toString();
        } else {
            alert.setContentText("Cakto llojin");
            alert.showAndWait();
            throw new Exception("Cakto llojin");
        }

        if (specializimi_combo.getValue() != null) {
            specializimi = specializimi_combo.getValue();
        } else {
            alert.setContentText("Cakto specializimin");
            alert.showAndWait();
            throw new Exception("Cakto specializimin");
        }

        if (klinika_combo.getValue() != null) {
            KlinikaJpaController kjpa = new KlinikaJpaController(emf);
            List<Klinika> klist = kjpa.findKlinikaEntities();
            for (Klinika k : klist) {
                if (k.getEmriKlinikes().toString().equals(klinika_combo.getValue().toString())) {
                    klinika = k;
                }
            }
        } else {
            alert.setContentText("Cakto Kliniken");
            alert.showAndWait();
            throw new Exception("Cakto Kliniken");
        }
//        List<Staff> staffList =stafjpa.findStaffEntities();
//        Integer staffId = null;
//        for(Staff s :staffList){
//            if(s.getNumriIdentitetit().equals(nrIdentifikimit)){
//                staffId = s.getSId();
//            }
//        }
        Staff staff = new Staff();
        staff.setSId(staffId);
        staff.setEmri(emri);
        staff.setMbiermi(mbiemri);
        staff.setEmail(email);
        staff.setTel(tel);
        staff.setNumriIdentitetit(nrIdentifikimit);
        staff.setTeDhenaShtes(dhenaShtes);
        staff.setLloji(lloji);
        staff.setSpecializimi(specializimi);
        staff.setKlinikaID(klinika);
        boolean mbanTermine = false;
        if (mbanTermine_toggle.isSelected()) {
            mbanTermine = true;
        }
        staff.setMbanTermine(mbanTermine);
        stafjpa.edit(staff);
        alert.setContentText("Punetori :" + staff.getEmri() + " " + staff.getMbiermi() + "\n u editua me sukses ");
        alert.showAndWait();
        clearTable();
        loadStaffData();
    }
}
