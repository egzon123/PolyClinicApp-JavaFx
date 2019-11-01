/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vizitat;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.lowagie.text.Image;
import dbManagment.DbConnection;
import dbManagment.Staff;
import dbManagment.StaffJpaController;
import dbManagment.Users;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import dbManagment.VizitatModel;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import homeView.HomeViewController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableRow;
import pacientet.PacinetetViewController;
import terminet.TerminetController;
import dbManagment.*;
import dbManagment.exceptions.NonexistentEntityException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class VizitatViewController implements Initializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    @FXML
    private TableView<VizitatModel> vizitat_table;
    @FXML
    private Label pacLabel;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton fshijeBtn;
    @FXML
    private TableColumn<VizitatModel, String> col_emriPac;

    @FXML
    private TableColumn<VizitatModel, String> col_mbiermiPac;

    @FXML
    private TableColumn<VizitatModel, String> col_DataLindjes;

    @FXML
    private TableColumn<VizitatModel, String> col_Doktori;
    @FXML
    private TableView<DatatVizitavModel> table_datat_vizitave;

    @FXML
    private TableColumn<DatatVizitavModel, String> col_data_vizitave;
    @FXML
    private JFXTextField mbiemri_field;
    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton clearBtn;
    @FXML
    private JFXTextField emri_field;

    @FXML
    private JFXDatePicker data_lindjes_picker;

    @FXML
    private JFXDatePicker dataVizites_picker;

    @FXML
    private JFXTextField anekesatPac_field;

    @FXML
    private JFXTextField vlersimiMjekut_field;

    @FXML
    private JFXTextField diagnoza_field;

    @FXML
    private JFXTextField terapia_field;

    @FXML
    private JFXTextField rekomandimi_field;
        @FXML
    private JFXButton reportBtn;
    Integer vid;
    String emripac = "";
    String mbiemripac = "";
    String dataL = "";
    @FXML
    private JFXButton shtoBtn;
    Integer staffId;
    Vizitat vizit = null;

    @FXML
    private JFXTabPane tabPane;
    private HomeViewController homeCo;
    private FXMLLoader loader;
    private DbConnection dc;
    private Users user;
    ObservableList<VizitatModel> dataVizitat = FXCollections.observableArrayList();
    ObservableList<DatatVizitavModel> datatEVizitave = FXCollections.observableArrayList();
    FilteredList<VizitatModel> filteredData = new FilteredList<>(dataVizitat, e -> true);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = homeCo.getUser();
        loadVizitatData();
    LocalDate localDate = LocalDate.now();//For reference
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
String formattedString = localDate.format(formatter);
LocalDate localDates = LocalDate.parse(formattedString, formatter);
dataVizites_picker.setValue(localDates);
        vizitat_table.setRowFactory(tv -> {
            TableRow<VizitatModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                VizitatModel vizita = row.getItem();

                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    cleardatesTable();
                    shfaqListenEVizitave(vizita);
                }

            });

            return row;

        });

        table_datat_vizitave.setRowFactory(tv -> {
            TableRow<DatatVizitavModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                DatatVizitavModel date = row.getItem();
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    VizitatJpaController vjpa = new VizitatJpaController(emf);
                    List<Vizitat> vlist = vjpa.findVizitatEntities();
                    for (Vizitat v : vlist) {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String dl = df.format(v.getDataLindjes());
                        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                        String dv = df.format(v.getDataVizites());
                        if (v.getEmriPacientit().equals(emripac) && v.getMbiemriPacientit().equals(mbiemripac) && dl.equals(dataL) && dv.equals(date.getDV())) {
                            emri_field.setText(v.getEmriPacientit());
                            mbiemri_field.setText(v.getMbiemriPacientit());

                            LocalDate datalindjes = convertToLocalDateViaSqlDate(v.getDataLindjes());
                            data_lindjes_picker.setValue(datalindjes);
                            LocalDate dataVizites = convertToLocalDateViaSqlDate(v.getDataVizites());
                            dataVizites_picker.setValue(dataVizites);
                            anekesatPac_field.setText(v.getAnkesatPacientit());
                            vlersimiMjekut_field.setText(v.getVlersimiMjekut());

                            diagnoza_field.setText(v.getDiagnoza());
                            terapia_field.setText(v.getTerapia());
                            rekomandimi_field.setText(v.getRekomandimi());
                            vizit = v;
                        }
                    }
                }
            });
            return row;
        });
        fshijeBtn.setOnAction(eventk -> {
            try {
                fshijeViziten(vizit);
            } catch (Exception ex) {
                Logger.getLogger(VizitatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        shtoBtn.setOnAction(event -> {
            try {
                shtoVizit();
            } catch (Exception ex) {
                Logger.getLogger(VizitatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        editBtn.setOnAction(eventEdit->{
            try {
                editoViziten(vizit);
            } catch (Exception ex) {
                Logger.getLogger(VizitatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        clearBtn.setOnAction(eventClear->clearFields());
        searchField.setOnKeyPressed(event -> search());
        reportBtn.setOnAction(event->{
            try {
                report();
            } catch (Exception ex) {
                Logger.getLogger(VizitatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public VizitatViewController(HomeViewController homeCo) {
        this.homeCo = homeCo;
        loader = new FXMLLoader(getClass().getResource("vizitatView.fxml"));
        loader.setController(this);
    }

    public void showScene() {
        try {
            homeCo.holderPane.getChildren().clear();
            homeCo.holderPane.getChildren().add(loader.load());

        } catch (IOException ex) {
            Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadVizitatData() {
        try {
            dc = new DbConnection();
            Connection conn = dc.Connect();
            ResultSet rs = null;

            if (user.getPrivilegji().equals("admin") || user.getPrivilegji().equals("recepcionist")) {
                rs = conn.createStatement().executeQuery("Select  Emri_Pacientit ,Mbiemri_Pacientit,Data_Lindjes,Staff_ID\n"
                        + " from Vizitat group by Emri_Pacientit ,Mbiemri_Pacientit,Data_Lindjes,Staff_ID");
            } else {
                Staff doktori = null;

                StaffJpaController dok = new StaffJpaController(emf);
                List<Staff> doks = dok.findStaffEntities();
                for (Staff doki : doks) {
                    if ((doki.getEmri().equals(user.getEmri())) && (doki.getMbiermi().equals(user.getMbiemri()))) {
                        doktori = doki;
                        staffId = doki.getSId();

                        rs = conn.createStatement().executeQuery("Select  Emri_Pacientit ,Mbiemri_Pacientit,Data_Lindjes,Staff_ID\n"
                                + " from Vizitat group by Emri_Pacientit ,Mbiemri_Pacientit,Data_Lindjes,Staff_ID having Staff_ID =" + staffId);
                        break;
                    }
                }
            }

            StaffJpaController stjpa = new StaffJpaController(emf);
            List<Staff> stffList = stjpa.findStaffEntities();
            String dokEmri = "";

            int count = 0;
            if (rs != null) {
                while (rs.next()) {
                    for (Staff staff : stffList) {
                        if(staff.getSId() != null && rs.getString("Staff_ID") !=null){
                              if (rs.getString("Staff_ID").equals(staff.getSId().toString())) {
                            dokEmri = staff.getEmri() + " " + staff.getMbiermi();
                        }
                        }
                      
                    }

                    dataVizitat.add(new VizitatModel(rs.getString("Emri_Pacientit"), rs.getString("Mbiemri_Pacientit"), rs.getString("Data_Lindjes"), dokEmri));

                }
                col_emriPac.setCellValueFactory(cellData -> cellData.getValue().getEmriPac());
                col_mbiermiPac.setCellValueFactory(cellData -> cellData.getValue().getMbiemriPac());
                col_DataLindjes.setCellValueFactory(cellData -> cellData.getValue().getDataLindjes());
                col_Doktori.setCellValueFactory(cellData -> cellData.getValue().getEmriDok());

                vizitat_table.setItems(null);
                vizitat_table.setItems(dataVizitat);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TerminetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void shfaqListenEVizitave(VizitatModel vpac) {
        dc = new DbConnection();
        Connection conn = dc.Connect();

        ResultSet rs = null;
        VizitatJpaController vjpa = new VizitatJpaController(emf);
        List<Vizitat> vizitatList = vjpa.findVizitatEntities();

        for (Vizitat v : vizitatList) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String pacData = df.format(v.getDataLindjes());
            if (v.getEmriPacientit().equals(vpac.getEP()) && v.getMbiemriPacientit().equals(vpac.getMP()) && pacData.equals(vpac.getDL())) {
                emripac = v.getEmriPacientit();
                mbiemripac = v.getMbiemriPacientit();
                pacLabel.setText(emripac + " " + mbiemripac);
                dataL = pacData;
                vid = v.getVId();
            }
        }
        try {

            rs = conn.createStatement().executeQuery("Select Data_Vizites from Vizitat where Emri_Pacientit =" + "'" + emripac + "'" + "and Mbiemri_Pacientit="
                    + "'" + mbiemripac + "'" + " and Data_Lindjes =" + "'" + dataL + "'");

            if (rs != null) {
                while (rs.next()) {
                    datatEVizitave.add(new DatatVizitavModel(rs.getString("Data_Vizites")));

                }
                col_data_vizitave.setCellValueFactory(cellData -> cellData.getValue().getDataVizitave());
                table_datat_vizitave.setItems(null);
                table_datat_vizitave.setItems(datatEVizitave);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VizitatViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabPane.getSelectionModel().select(0);

    }

    public void cleardatesTable() {
        table_datat_vizitave.getItems().clear();
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void shtoVizit() throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);

        String emri = "";

        String mbiemri = "";
        java.sql.Date datalindjes = null;
        java.sql.Date datavizites = null;
        String ankesatPac = "";
        String VlersimiMjekut = "";
        String diagnoza = "";
        String terapia = "";
        String rekomandimi = "";
        Integer dokiId = null;

        if (isValid(emri_field.getText())) {
            emri = emri_field.getText();
        } else {

            alert.setContentText("Emri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Emri i pacientit jo valid");
        }
        if (isValid(mbiemri_field.getText())) {
            mbiemri = mbiemri_field.getText();
        } else {
            alert.setContentText("Mbimeri jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri jo valid");
        }

        if (data_lindjes_picker.getValue() != null) {
            datalindjes = java.sql.Date.valueOf(data_lindjes_picker.getValue());
        } else {
            alert.setContentText("Cakto daten e lindjes");
            alert.showAndWait();
            throw new Exception("Cakto daten e lindjes");
        }

        if (dataVizites_picker.getValue() != null) {
            datavizites = java.sql.Date.valueOf(dataVizites_picker.getValue());
        } else {
            alert.setContentText("Cakto daten e vizites");
            alert.showAndWait();
            throw new Exception("Cakto daten e vizites");
        }

        VizitatJpaController vjpa = new VizitatJpaController(emf);
        List<Vizitat> vlist = vjpa.findVizitatEntities();
        for (Vizitat v : vlist) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dl = df.format(v.getDataLindjes());
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            String dv = df.format(v.getDataVizites());
            LocalDate localDate = data_lindjes_picker.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataLindjes = localDate.format(formatter);
            LocalDate local = dataVizites_picker.getValue();
            DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataVizites = local.format(form);
            if (v.getEmriPacientit().equals(emri_field.getText()) && v.getMbiemriPacientit().equals(mbiemri_field.getText())) {
                if (dl.equals(dataLindjes) && dv.equals(dataVizites)) {
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);

                    al.setContentText("Pacienti ka bere nje vizite ne kete date\n deshironi ta regjistroni edhe nje vizite");
                    Optional<ButtonType> option = al.showAndWait();
                    if (option.get() != ButtonType.OK) {

                        throw new Exception("Vizita nuk u regjistrua");

                    }
                }
            }
        }

        StaffJpaController stjpa = new StaffJpaController(emf);
        
 Staff st = null;
              if(staffId !=null){
                  st = stjpa.findStaff(staffId); 
              }
        Vizitat vizita = new Vizitat();
        vizita.setEmriPacientit(emri);
        vizita.setMbiemriPacientit(mbiemri);
        vizita.setDataLindjes(datalindjes);
        vizita.setDataVizites(datavizites);
        vizita.setAnkesatPacientit(anekesatPac_field.getText());
        vizita.setVlersimiMjekut(vlersimiMjekut_field.getText());
        vizita.setDiagnoza(diagnoza_field.getText());
        vizita.setTerapia(terapia_field.getText());
        vizita.setRekomandimi(rekomandimi_field.getText());
        vizita.setStaffID(st);
        vjpa.create(vizita);
        alert.setContentText("Vizita u shtua me sukses");
        alert.showAndWait();
        loadVizitatData();
        homeCo.reloadVizitat();
    }

    public boolean isValid(String s) {
        String regex = "[A-Za-z\\s]+";
        return s.matches(regex) && !s.trim().isEmpty();//returns true if input and regex matches otherwise false;
    }

 

    public void fshijeViziten(Vizitat v) throws Exception {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        if (v == null) {
            alert.setContentText("Selekto nje vizite");
            alert.showAndWait();
        } else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deshironi ta fshini viziten nga databaza");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    VizitatJpaController vjpa = new VizitatJpaController(emf);

                    vjpa.destroy(v.getVId());

                    alert.setContentText("Vizita u fshi nga databaza");
                    alert.show();
                    homeCo.reloadVizitat();
                    tabPane.getSelectionModel().select(1);

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(PacinetetViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public void editoViziten(Vizitat v) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (v == null) {
            alert.setContentText("Selekto nje vizite");
            alert.showAndWait();
            throw new Exception("Selekto nje vizite");
        }

        String emri = "";

        String mbiemri = "";
        java.sql.Date datalindjes = null;
        java.sql.Date datavizites = null;
        String ankesatPac = "";
        String VlersimiMjekut = "";
        String diagnoza = "";
        String terapia = "";
        String rekomandimi = "";
        Integer dokiId = null;

        if (isValid(emri_field.getText())) {
            emri = emri_field.getText();
        } else {

            alert.setContentText("Emri i pacientit jo valid");
            alert.showAndWait();
            throw new Exception("Emri i pacientit jo valid");
        }
        if (isValid(mbiemri_field.getText())) {
            mbiemri = mbiemri_field.getText();
        } else {
            alert.setContentText("Mbimeri jo valid");
            alert.showAndWait();
            throw new Exception("Mbiemri jo valid");
        }

        if (data_lindjes_picker.getValue() != null) {
            datalindjes = java.sql.Date.valueOf(data_lindjes_picker.getValue());
        } else {
            alert.setContentText("Cakto daten e lindjes");
            alert.showAndWait();
            throw new Exception("Cakto daten e lindjes");
        }

        if (dataVizites_picker.getValue() != null) {
            datavizites = java.sql.Date.valueOf(dataVizites_picker.getValue());
        } else {
            alert.setContentText("Cakto daten e vizites");
            alert.showAndWait();
            throw new Exception("Cakto daten e vizites");
        }
        VizitatJpaController vjpa = new VizitatJpaController(emf);
              StaffJpaController stjpa = new StaffJpaController(emf);
              Staff st = null;
              if(staffId !=null){
                  st = stjpa.findStaff(staffId); 
              }


        Vizitat vizita = v;
        vizita.setEmriPacientit(emri);
        vizita.setMbiemriPacientit(mbiemri);
        vizita.setDataLindjes(datalindjes);
        vizita.setDataVizites(datavizites);
        vizita.setAnkesatPacientit(anekesatPac_field.getText());
        vizita.setVlersimiMjekut(vlersimiMjekut_field.getText());
        vizita.setDiagnoza(diagnoza_field.getText());
        vizita.setTerapia(terapia_field.getText());
        vizita.setRekomandimi(rekomandimi_field.getText());
        vizita.setStaffID(st);
        vjpa.edit(vizita);
        alert.setContentText("Vizita u editua me sukses");
        alert.showAndWait();
      
        homeCo.reloadVizitat();
              
    }
    
    public void clearFields(){
        emri_field.clear();
        mbiemri_field.clear();
        data_lindjes_picker.setValue(null);
        dataVizites_picker.setValue(null);
        anekesatPac_field.clear();
        vlersimiMjekut_field.clear();
        diagnoza_field.clear();
        terapia_field.clear();
        rekomandimi_field.clear();
          LocalDate localDate = LocalDate.now();//For reference
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
String formattedString = localDate.format(formatter);
LocalDate localDates = LocalDate.parse(formattedString, formatter);
dataVizites_picker.setValue(localDates);
        
    }
        public void search() {

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vizitaDetails -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (vizitaDetails.getEP().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } 
                return false;
            });
        });
        SortedList<VizitatModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(vizitat_table.comparatorProperty());
        vizitat_table.setItems(sortedData);
    }
        
        public void report()throws Exception{
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText(null);
           
                 dc = new DbConnection();
            Connection conn = dc.Connect();
            //String rep = "C:\\Users\\Egzon\\Documents\\NetBeansProjects\\PolyClinicManagmentV2.0\\src\\reports\\.VizitaReport.jrxml";
           
        try {
            if(vizit == null){
                alert.setContentText("Selekto nje vizite");
                alert.showAndWait();
               throw new Exception("Selekto nje vizite");
                        
            }
            
             JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream("/reports/VizitaReport.jrxml"));
            String sql = "Select * from Vizitat where V_ID="+"'"+vizit.getVId()+"'";
            JRDesignQuery dq = new JRDesignQuery();
            HashMap param = new HashMap<>();
            Vizitat v = vizit;
            Staff s = v.getStaffID();
            String dok = s.getEmri()+" "+s.getMbiermi();
            
            
            param.put("dokName",dok);
            param.put("tel",s.getTel());
            param.put("Image",getClass().getResource("/images/reportlogo.jpg"));
                 
            dq.setText(sql);
            jd.setQuery(dq);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,param,conn);
            
            JasperViewer.viewReport(jp,false);
        } catch (JRException ex) {
            Logger.getLogger(VizitatViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
}
