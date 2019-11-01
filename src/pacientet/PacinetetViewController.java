/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacientet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dbManagment.DbConnection;
import dbManagment.Klinika;
import dbManagment.KlinikaJpaController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import dbManagment.PacientModel;
import dbManagment.Pacienti;
import dbManagment.PacientiJpaController;
import dbManagment.Staff;
import dbManagment.StaffJpaController;
import dbManagment.Users;
import dbManagment.exceptions.NonexistentEntityException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import homeView.HomeViewController;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import login.Log_in_controller;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class PacinetetViewController implements Initializable {

    /**
     * Initializes the controller class.
     *
     *
     */
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");

    ObservableList<PacientModel> datapac = FXCollections.observableArrayList();
    ObservableList<PacientModel> datapac1 = FXCollections.observableArrayList();
    FilteredList<PacientModel> filteredData = new FilteredList<>(datapac, e -> true);
    @FXML
    private TableView<PacientModel> pac_table;
    private HomeViewController homeController;
    private Log_in_controller logCo;
    @FXML
    private JFXButton clearBtn;
    @FXML
    private JFXTextField search_txt;

    private FXMLLoader loader;
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXTextField emri_txt;

    @FXML
    private JFXTextField mbiemri_txt;

    @FXML
    private JFXTextField vendlindja_txt;

    @FXML
    private JFXTextField emriPrindit_txt;

    @FXML
    private JFXTextField shteti_txt;

    @FXML
    private JFXTextField qyteti_txt;

    @FXML
    private JFXTextField adresa_txt;

    @FXML
    private JFXTextField tel_txt;

    @FXML
    private JFXTextField email_txt;

    @FXML
    private JFXTextArea alergjit_txt;

    @FXML
    private JFXTextArea infoShtes_txt;

    @FXML
    private JFXDatePicker data_picker;

    @FXML
    private JFXComboBox<String> doktori_combobox;

    @FXML
    private JFXComboBox<String> klinika_combobox;

    @FXML
    private JFXComboBox<String> grupi_gjakut;

    @FXML
    private JFXTextArea semundjeKoronike_txt;

    @FXML
    private JFXRadioButton gjinia_M;
    @FXML
    private JFXRadioButton gjinia_F;

    @FXML
    private JFXButton ruajBtn;
    @FXML
    private TableColumn<PacientModel, String> col_emri;

    @FXML
    private TableColumn<PacientModel, String> col_mbiemri;

    @FXML
    private TableColumn<PacientModel, String> col_emriPrindit;

    @FXML
    private TableColumn<PacientModel, String> col_datalindjes;

    @FXML
    private TableColumn<PacientModel, String> col_shteti;

    @FXML
    private TableColumn<PacientModel, String> col_qyteti;

    @FXML
    private TableColumn<PacientModel, String> col_vendlindja;

    @FXML
    private TableColumn<PacientModel, String> col_adresa;

    @FXML
    private TableColumn<PacientModel, String> col_tel;

    @FXML
    private TableColumn<PacientModel, String> col_email;

    @FXML
    private TableColumn<PacientModel, String> col_gjinia;

    @FXML
    private TableColumn<PacientModel, String> col_semKronike;

    @FXML
    private TableColumn<PacientModel, String> col_grupiGjakut;

    @FXML
    private TableColumn<PacientModel, String> col_alergjit;

    @FXML
    private TableColumn<PacientModel, String> col_infoShtes;
    private Integer pacId;

    @FXML
    private Label countLabelPac;
    Users user = null;
    private Integer staffId;
    private DbConnection dc;
    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton fshijBtn;

    public PacinetetViewController(homeView.HomeViewController homeCo, Log_in_controller logCo) {
        this.logCo = logCo;
        homeController = homeCo;
        loader = new FXMLLoader(getClass().getResource("pacinetetView.fxml"));
        loader.setController(this);
    }

    public void showScene() {
        try {
            homeController.holderPane.getChildren().clear();
            homeController.holderPane.getChildren().add(loader.load());
            editBtn.setDisable(true);
        } catch (IOException ex) {
            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            //        try {

            dc = new DbConnection();

            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("Select * from Klinika");
            while (rs.next()) {
                klinika_combobox.getItems().add(rs.getString(2));
            }
            ResultSet rs1 = conn.createStatement().executeQuery("Select * from Staff");
            while (rs1.next()) {
                doktori_combobox.getItems().add(rs1.getString(2) + " " + rs1.getString(3));
            }
            user = logCo.getUserLog();
            System.out.println(user.toString());
            if (user.getPrivilegji().equals("doktor")) {
                Staff doktori = null;
                StaffJpaController dok = new StaffJpaController(emf);
                List<Staff> doks = dok.findStaffEntities();
                for (Staff doki : doks) {
                    if ((doki.getEmri().equals(user.getEmri())) && (doki.getMbiermi().equals(user.getMbiemri()))) {
                        doktori = doki;
                        Integer staffId = doki.getSId();
                        System.out.println();
                        System.out.println(staffId);
                        System.out.println();
                        loadData(staffId, "doktor");
                    }
                }
                if (doktori != null) {
                    Klinika klinika = doktori.getKlinikaID();

                    if (klinika != null) {

                        klinika_combobox.setValue(klinika.getEmriKlinikes());
                        klinika_combobox.setDisable(true);
                    }

                    doktori_combobox.setValue(doktori.getEmri() + " " + doktori.getMbiermi());
                    doktori_combobox.setDisable(true);
                }

            } else if (user.getPrivilegji().equals("admin")) {
                loadData(staffId, "admin");
            }

//        } catch (SQLException ex) {
//            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
            grupi_gjakut.getItems().addAll("A", "B", "AB", "0");

            ruajBtn.setOnAction(event -> {
                try {
                    ruajPacientin();
                } catch (Exception ex) {
                    Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            editBtn.setOnAction(event -> editoPacientin());

            pac_table.setRowFactory(tv -> {
                TableRow<PacientModel> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    PacientModel pac = row.getItem();
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        pacId = Integer.parseInt(pac.getiDPacienti());
                     
                        emri_txt.setText(pac.getEmriPacientit());
                        mbiemri_txt.setText(pac.getMbiemriPacientit());
                        emriPrindit_txt.setText(pac.getEmriPrindit());
                        vendlindja_txt.setText(pac.getVendlindja());
                        String date = pac.getDatelindja();
                        LocalDate localDate = LocalDate.parse(date);
                        data_picker.setValue(localDate);
//               gjinia_F.setSelected(false);
//               gjinia_M.setSelected(false);
                        String gjinia = pac.getGjinia();
                        if (gjinia.equals("M")) {
                            gjinia_M.setSelected(true);
                        } else {
                            gjinia_F.setSelected(true);
                        }

                        shteti_txt.setText(pac.getShteti());
                        qyteti_txt.setText(pac.getQyteti());
                        adresa_txt.setText(pac.getAdresa());
                        tel_txt.setText(pac.getTel());
                        email_txt.setText(pac.geteMail());

                        grupi_gjakut.setValue(pac.getGrupigjakut());
                        infoShtes_txt.setText(pac.getInformataShtesë());

                        semundjeKoronike_txt.setText(pac.getSemundjeKronike());
                        alergjit_txt.setText(pac.getAlergjit());
                        Integer dokiid = Integer.parseInt(pac.getIdStaff());
                        String klinikaid = pac.getIDklinika();

                        KlinikaJpaController kjpa = new KlinikaJpaController(emf);
                        Klinika klinika = kjpa.findKlinika(Integer.parseInt(klinikaid));
                        klinika_combobox.setValue(klinika.getEmriKlinikes());

                        StaffJpaController djpa = new StaffJpaController(emf);
                        Staff doki = djpa.findStaff(dokiid);
                        doktori_combobox.setValue(doki.getEmri()
                                + " " + doki.getMbiermi());
                        tabPane.getSelectionModel().select(0);
                        editBtn.setDisable(false);
                        ruajBtn.setDisable(true);
                    }
                    fshijBtn.setOnAction(evento -> fshijPac(pac));

                });
                return row;
            });

            clearBtn.setOnAction(event -> clearFields());
        } catch (SQLException ex) {
            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        search_txt.setOnKeyPressed(event -> search());

    }

    private void loadData(Integer id, String roli) {
        Connection conn = dc.Connect();
        ResultSet rs = null;
        try {
            if (roli.equals("admin")) {
                rs = conn.createStatement().executeQuery("Select * from Pacienti");

            } else if (roli.equals("doktor")) {
                rs = conn.createStatement().executeQuery("Select * from Pacienti where Staff_ID =" + id);
            }

            int c = 0;
            while (rs.next()) {
                datapac.add(new PacientModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18)));
                c++;

            }
            String numberAsString = new Integer(c).toString();
            countLabelPac.setText(numberAsString);

        } catch (SQLException ex) {
            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        col_emri.setCellValueFactory(cellData -> cellData.getValue().EmriPacientit());
        col_mbiemri.setCellValueFactory(cellData -> cellData.getValue().MbiemriPacientit());
        col_emriPrindit.setCellValueFactory(cellData -> cellData.getValue().EmriPrindit());
        col_datalindjes.setCellValueFactory(cellData -> cellData.getValue().Datelindja());
        col_shteti.setCellValueFactory(cellData -> cellData.getValue().Shteti());
        col_qyteti.setCellValueFactory(cellData -> cellData.getValue().Qyteti());
        col_vendlindja.setCellValueFactory(cellData -> cellData.getValue().Vendlindja());
        col_adresa.setCellValueFactory(cellData -> cellData.getValue().Adresa());
        col_tel.setCellValueFactory(cellData -> cellData.getValue().Tel());
        col_email.setCellValueFactory(cellData -> cellData.getValue().eMail());
        col_gjinia.setCellValueFactory(cellData -> cellData.getValue().Gjinia());
        col_semKronike.setCellValueFactory(cellData -> cellData.getValue().SemundjeKronike());
        col_grupiGjakut.setCellValueFactory(cellData -> cellData.getValue().Grupigjakut());
        col_alergjit.setCellValueFactory(cellData -> cellData.getValue().Alergjit());
        col_infoShtes.setCellValueFactory(cellData -> cellData.getValue().InformataShtesë());

        pac_table.setItems(null);
        pac_table.setItems(datapac);

    }

    @FXML
    void ruajPacientin() throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        PacientiJpaController pacjpa = new PacientiJpaController(emf);
        List<Pacienti> pacat = pacjpa.findPacientiEntities();
        for (Pacienti pac : pacat) {
            if (pac.getEmri().equals(emri_txt.getText()) && pac.getMbiemri().equals(mbiemri_txt.getText())) {
                String datalind = data_picker.getValue().toString();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String pacData = df.format(pac.getDataLindjes());
                if (datalind.equals(pacData)) {
                    alert.setContentText("Pacienti ekziston ne databaze");
                    alert.showAndWait();
                    throw new Exception();

                }
            }
            
        }
        int count = 0;

      
        if(!isValid(emri_txt.getText())){
            alert.setContentText("Emri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Emri i pacientit jo valid");
        }
        if(!isValid(mbiemri_txt.getText())){
            alert.setContentText("Mbiemri i pacientit  jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri i pacientit jo valid");
        }
        if(!isValid(emriPrindit_txt.getText())){
            alert.setContentText("Emri i prindit jo valid");
            alert.showAndWait();
            throw new Exception("Emri i prindit jo valid");
        }
        
        
         
            
                String emri = "";
                String mbiemri = "";
                String emriPrindit = "";
                String vendlindja = "";
              
                    emri = emri_txt.getText();       
                    mbiemri = mbiemri_txt.getText();
                    emriPrindit = emriPrindit_txt.getText();
                    vendlindja = vendlindja_txt.getText();
               
                java.sql.Date datalindjes = null;
                if (data_picker.getValue() != null) {
                    datalindjes = java.sql.Date.valueOf(data_picker.getValue());
                    count++;
                } else {
                    alert.setContentText("Cakto daten e lindjes ");
                    alert.showAndWait();
                    throw new Exception("Cakto daten e lindjes");
                }

                char gjinia;
                if (gjinia_M.isSelected()) {
                    gjinia = 'M';
                } else {
                    gjinia = 'F';
                }
                String tel = "";

                String shteti = shteti_txt.getText();
                String qyteti = qyteti_txt.getText();
                String adresa = adresa_txt.getText();
                if (tel_txt.getText().length() > 2 && tel_txt.getText().matches("[0-9]+")) {
                    tel = tel_txt.getText();
                    count++;
                } else {
                    alert.setContentText("Numri i telefonit jo valid");
                    alert.showAndWait();
                    throw new Exception("Numri i telefonit jo valid");
                }

                String email = email_txt.getText();
                String grupiGjakut = grupi_gjakut.getValue();
                String infoshtes = infoShtes_txt.getText();
                String semundjeKronike = semundjeKoronike_txt.getText();
                String alergjit = alergjit_txt.getText();

                String klinika = klinika_combobox.getValue();
                if (klinika == null) {
                    alert.setContentText("Cakto Kliniken ");
                    alert.showAndWait();
                    throw new Exception("Cakto Kliniken");
                }

                String doktori = doktori_combobox.getValue();
                if (doktori == null) {
                    alert.setContentText("Cakto Doktorin ");
                    alert.showAndWait();
                    throw new Exception("Cakto Doktorin");
                }
                Connection conn = dc.Connect();
                ResultSet rs = null;
                ResultSet rs1 = null;
                String kie = null;
                String kid = null;
                String[] dokName = doktori.split(" ");
                String dokn = null;

                String did = null;
                try {
                    rs = conn.createStatement().executeQuery("Select * from Klinika");
                    rs1 = conn.createStatement().executeQuery("Select * from Staff");
                } catch (SQLException ex) {
                    Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (rs.next()) {
                    kie = rs.getString(2);
                    if (kie.equals(klinika)) {
                        kid = rs.getString(1);
                    }
                }
                while (rs1.next()) {
                    dokn = rs1.getString(2).trim();
                    if (dokn.equals(dokName[0])) {
                        did = rs1.getString(1);
                    }
                }
                

                    if (kid != null && did != null) {

                        Integer i = Integer.parseInt(kid);
                        Integer d = Integer.parseInt(did);

                        Klinika k = new Klinika();
                        Staff doki = new Staff();
                        k.setKId(i);
                        doki.setSId(d);

                        Pacienti pac = new Pacienti();
                        pac.setKlinikaID(k);
                        pac.setStaffID(doki);

                        pac.setEmri(emri);
                        pac.setMbiemri(mbiemri);
                        pac.setEmriPrindit(emriPrindit);
                        pac.setVendlindja(vendlindja);
                        pac.setGjinia(gjinia);
                        pac.setAdresa(adresa);
                        pac.setShteti(shteti);
                        pac.setDataLindjes(datalindjes);
                        pac.setQyteti(qyteti);
                        pac.setTel(tel);
                        pac.setEmail(email);
                        pac.setGrupiGjakut(grupiGjakut);
                        pac.setInfoShtes(infoshtes);
                        pac.setSemundjeKronike(semundjeKronike);
                        pac.setAlergjite(alergjit);

                        PacientiJpaController pjpa = new PacientiJpaController(emf);
                        try {

                            pjpa.create(pac);

                        } catch (Exception ex) {
                            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Alert ShtoAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        ShtoAlert.setTitle("Informacion");

                        ShtoAlert.setContentText("Pacienti : " + emri + " " + mbiemri + "\n U shtua me sukses");
                        ShtoAlert.showAndWait();

//                        clearTable();
//                        loadData(staffId, "doktor");

                        homeController.reLoadPac();
                    } else {
                        Alert ids = new Alert(Alert.AlertType.WARNING);
                        ids.setHeaderText(null);
                        ids.setContentText("Cakto nje Doktor dhe Klinik");
                        ids.showAndWait();

                    }
                
           

        

    }

    @FXML
    public void clearTable() {
        pac_table.getItems().clear();

    }

    @FXML
    void clearFields() {
        emri_txt.clear();
        mbiemri_txt.clear();
        emriPrindit_txt.clear();
        vendlindja_txt.clear();
        shteti_txt.clear();
        qyteti_txt.clear();
        adresa_txt.clear();
        tel_txt.clear();
        email_txt.clear();
        semundjeKoronike_txt.clear();
        alergjit_txt.clear();
        infoShtes_txt.clear();
        data_picker.setValue(null);
        grupi_gjakut.setValue(null);
        if (user.getPrivilegji().equals("admin")) {
            doktori_combobox.setValue(null);
            klinika_combobox.setValue(null);
        }

        editBtn.setDisable(true);
        ruajBtn.setDisable(false);

    }

    public void fshijPac(PacientModel pac) {
        if (pac == null) {
            Alert selektoPac = new Alert(Alert.AlertType.INFORMATION);
            selektoPac.setHeaderText(null);
            selektoPac.setTitle("Informacion");
            selektoPac.setContentText("Selekto nje pacient");
            selektoPac.showAndWait();
        } else {
            Alert fshijAlert = new Alert(Alert.AlertType.CONFIRMATION);
            fshijAlert.setHeaderText(null);
            fshijAlert.setTitle("Informacion");

            fshijAlert.setContentText("Deshironi ta fshini pacientin : " + pac.getEmriPacientit() + " " + pac.getMbiemriPacientit() + "\n nga databaza");
            Optional<ButtonType> option = fshijAlert.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    PacientiJpaController pjpa = new PacientiJpaController(emf);
                    Integer pacId = Integer.parseInt(pac.getiDPacienti());
                    pjpa.destroy(pacId);
                    fshijAlert.setHeaderText(null);
                    fshijAlert.setContentText("Pacienti u fshi nga databaza");
                    fshijAlert.show();
                    loadData(staffId, "doktor");
                    clearTable();
                    loadData(staffId, "doktor");
                    homeController.reLoadPac();

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public void editoPacientin() {
        int count = 0;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (emri_txt.getText().isEmpty() || mbiemri_txt.getText().isEmpty() || emriPrindit_txt.getText().isEmpty() || shteti_txt.getText().isEmpty() || qyteti_txt.getText().isEmpty() || adresa_txt.getText().isEmpty() || adresa_txt.getText().isEmpty()) {
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Ploteso fushat e zbrazta");
            alert.showAndWait();
        } else {
            try {
                Connection conn = dc.Connect();
                ResultSet rs = null;
                rs = conn.createStatement().executeQuery("Select * from Pacienti");
                Integer pacid = null;
//                while (rs.next()) {
//                    if (rs.getString(2).equals(emri_txt.getText()) || rs.getString(11).equals(tel_txt.getText())) {
//                        pacid = Integer.parseInt(rs.getString(1));
//
//                    }
//                }
                String emri = "";
                String mbiemri = "";
                String emriPrindit = "";
                String vendlindja = "";
                if (isValid(emri_txt.getText())) {
                    emri = emri_txt.getText();
                    count++;
                } else {
                    alert.setContentText("Emri jo valid");
                    alert.showAndWait();
                }
                if (isValid(mbiemri_txt.getText())) {
                    mbiemri = mbiemri_txt.getText();
                    count++;
                } else {
                    alert.setContentText("Mbiemri jo valid");
                    alert.showAndWait();
                }
                if (isValid(emriPrindit_txt.getText())) {
                    emriPrindit = emriPrindit_txt.getText();
                    count++;
                } else {
                    alert.setContentText("Emri prindit jo valid");
                    alert.showAndWait();
                }
                if (isValid(vendlindja_txt.getText())) {
                    vendlindja = vendlindja_txt.getText();
                    count++;
                } else {
                    alert.setContentText("Vendlindja jo valide");
                    alert.showAndWait();
                }
                java.sql.Date datalindjes = null;
                if (data_picker.getValue() != null) {
                    datalindjes = java.sql.Date.valueOf(data_picker.getValue());
                    count++;
                } else {
                    alert.setContentText("Data e lindjes e zbrazet!");
                    alert.showAndWait();
                }

                char gjinia;
                if (gjinia_M.isSelected()) {
                    gjinia = 'M';
                } else {
                    gjinia = 'F';
                }
                String tel = "";

                String shteti = shteti_txt.getText();
                String qyteti = qyteti_txt.getText();
                String adresa = adresa_txt.getText();
                if (tel_txt.getText().length() > 2 && tel_txt.getText().matches("[0-9]+")) {
                    tel = tel_txt.getText();
                    count++;
                } else {
                    alert.setContentText("Numri i telefonit jo valid");
                    alert.showAndWait();
                }

                String email = email_txt.getText();
                String grupiGjakut = grupi_gjakut.getValue();
                String infoshtes = infoShtes_txt.getText();
                String semundjeKronike = semundjeKoronike_txt.getText();
                String alergjit = alergjit_txt.getText();

                String klinika = klinika_combobox.getValue();
                String doktori = doktori_combobox.getValue();

                ResultSet rs1 = null;
                ResultSet rs2 = null;
                String kie = null;
                String kid = null;
                String[] dokName = doktori.split(" ");
                String dokn = null;

                String did = null;
                try {
                    rs1 = conn.createStatement().executeQuery("Select * from Klinika");
                    rs2 = conn.createStatement().executeQuery("Select * from Staff");
                } catch (SQLException ex) {
                    Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (rs1.next()) {
                    kie = rs1.getString(2);
                    if (kie.equals(klinika)) {
                        kid = rs1.getString(1);
                    }
                }
                while (rs2.next()) {
                    dokn = rs2.getString(2).trim();
                    if (dokn.equals(dokName[0])) {
                        did = rs2.getString(1);
                    }
                }
                if (count == 6) {
                    if (kid != null && did != null) {

                        Integer i = Integer.parseInt(kid);
                        Integer d = Integer.parseInt(did);

                        Klinika k = new Klinika();
                        Staff doki = new Staff();
                        k.setKId(i);
                        doki.setSId(d);

                        Pacienti pac = new Pacienti();
                        pac.setPId(pacId);
                        pac.setKlinikaID(k);
                        pac.setStaffID(doki);

                        pac.setEmri(emri);
                        pac.setMbiemri(mbiemri);
                        pac.setEmriPrindit(emriPrindit);
                        pac.setVendlindja(vendlindja);
                        pac.setGjinia(gjinia);
                        pac.setAdresa(adresa);
                        pac.setShteti(shteti);
                        pac.setDataLindjes(datalindjes);
                        pac.setQyteti(qyteti);
                        pac.setTel(tel);
                        pac.setEmail(email);
                        pac.setGrupiGjakut(grupiGjakut);
                        pac.setInfoShtes(infoshtes);
                        pac.setSemundjeKronike(semundjeKronike);
                        pac.setAlergjite(alergjit);

                        PacientiJpaController pjpa = new PacientiJpaController(emf);
                        try {

                            pjpa.edit(pac);

                        } catch (Exception ex) {
                            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Alert ShtoAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        ShtoAlert.setTitle("Informacion");

                        ShtoAlert.setContentText("Pacienti : " + emri + " " + mbiemri + "\n U editua me sukses");
                        ShtoAlert.showAndWait();

                        clearTable();
                        loadData(staffId, "doktor");

                        homeController.reLoadPac();
                    } else {
                        Alert ids = new Alert(Alert.AlertType.WARNING);
                        ids.setHeaderText(null);
                        ids.setContentText("Cakto nje Doktor dhe Klinik");
                        ids.showAndWait();

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public boolean isValid(String s) {
        String regex = "[A-Za-z\\s]+";
        return !s.trim().isEmpty() && s.matches(regex)  ;//returns true if input and regex matches otherwise false;
    }

    public void search() {

        search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pacientidetails -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (pacientidetails.getEmriPacientit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (pacientidetails.getMbiemriPacientit().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<PacientModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(pac_table.comparatorProperty());
        pac_table.setItems(sortedData);
    }
    
   

}
