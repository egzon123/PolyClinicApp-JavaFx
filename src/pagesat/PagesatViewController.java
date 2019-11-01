/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagesat;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import dbManagment.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Egzon
 */
public class PagesatViewController implements Initializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PolyClinicManagmentV2.0PU");
    private FXMLLoader loader;
    @FXML
    private JFXTextField emri_field;

    @FXML
    private JFXTextField mbiemri_field;

    @FXML
    private JFXDatePicker dataLindjes_picker;

    @FXML
    private JFXTextField total_shuma_field;

    @FXML
    private JFXButton ruajBtn;

    @FXML
    private JFXButton editoBtn;

    @FXML
    private JFXButton fshijeBtn;
    @FXML
    private JFXDatePicker data_pagPicker;
    @FXML
    private JFXButton clearSherbimetListBtn;

    @FXML
    private TableView<PagesatModel> pagesat_Table;

    @FXML
    private TableColumn<PagesatModel, String> col_emri;

    @FXML
    private TableColumn<PagesatModel, String> col_mbiemri;

    @FXML
    private TableColumn<PagesatModel, String> col_dataLindjes;

    @FXML
    private TableColumn<PagesatModel, String> col_dataPageses;

    @FXML
    private TableColumn<PagesatModel, String> col_sherbimet;

    @FXML
    private TableColumn<PagesatModel, String> col_shumaPaguar;

    @FXML
    private TableColumn<PagesatModel, String> col_doktori;

    @FXML
    private JFXDatePicker data_pagesesPicker;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private TextField search_field;
    @FXML
    private JFXButton kerkoBtn;
    private DbConnection dc;
    private Integer pagesaId;

    @FXML
    private JFXButton shtoSherbimBtn;
    private homeView.HomeViewController homeCo;
    SherbimetController sherCo;
    @FXML
    private JFXListView<String> listaSherbimeveTeKryera;
    private Users user;
    ObservableList<PagesatModel> dataPagesat = FXCollections.observableArrayList();
       ObservableList<PagesatModel> byDate = FXCollections.observableArrayList();
    FilteredList<PagesatModel> filteredData = new FilteredList<>(dataPagesat, e -> true);
    FilteredList<PagesatModel> filteredItems = new FilteredList<>(dataPagesat);
    Staff doktori = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = homeCo.getUser();
        shtoSherbimBtn.setOnAction(event -> openSherbimetView());
  
        Pagesat pagesatF = null;
        pagesat_Table.setRowFactory(tv -> {
            TableRow<PagesatModel> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                PagesatModel pagesa = row.getItem();
                String euro = "\u20ac";
                Pagesat pag = null;
                double shuma = 0;
                ObservableList<String> sherbimetList = FXCollections.observableArrayList();

                if (event.getClickCount() == 1 && (!row.isEmpty())) {

                    PagesatJpaController pjpa = new PagesatJpaController(emf);
                    List<Pagesat> plist = pjpa.findPagesatEntities();
                    for (Pagesat p : plist) {
                        if (p.getEmriPac().equals(pagesa.getEP()) && p.getMbiemriPac().equals(pagesa.getMP())) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String dp = df.format(p.getDataPageses());
                            if (dp.equals(pagesa.getDP())) {
                                pag = p;

                                break;
                            }

                        }
                    }
                   pagesaId = pag.getPagesaID();
                    emri_field.setText(pag.getEmriPac());
                    mbiemri_field.setText(pag.getMbiemriPac());
                    LocalDate dataPageses = convertToLocalDateViaSqlDate(pag.getDataPageses());
                    data_pagPicker.setValue(dataPageses);
                    LocalDate datalindjes = convertToLocalDateViaSqlDate(pag.getDataLindjes());
                    dataLindjes_picker.setValue(datalindjes);

                    List<Sherbimet> shlist = pag.getSherbimetList();

                    for (Sherbimet s : shlist) {
                        sherbimetList.add(s.getEmriSherbimit() + " -> " + s.getQmimiSherbimit() + " " + euro);
                        shuma += s.getQmimiSherbimit();
                    }

                }
                listaSherbimeveTeKryera.setItems(sherbimetList);
                total_shuma_field.setText(Double.toString(shuma));
      
                fshijeBtn.setOnAction(eventio -> {
                    try {
                        fshijePagesen(pagesa);
                    } catch (Exception ex) {
                        Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });

            return row;

        });
        loadPagaest();
        ruajBtn.setOnAction(event -> {
            try {
                shtoPages();
            } catch (Exception ex) {
                Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        editoBtn.setOnAction(event->{
            try {
                editoPagesen();
            } catch (Exception ex) {
                Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        clearSherbimetListBtn.setOnAction(event -> clearSherbimetList());
        search_field.setOnKeyPressed(event -> search());
//        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
//            LocalDate minDate = data_pagesesPicker.getValue();
//            // LocalDate maxDate = data_pagesesPicker.getValue();
//
//            // get final values != null
//            final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
//            // final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;
//
//            // values for openDate need to be in the interval [finalMin, finalMax]
//            // return ti -> !finalMin.isAfter(ti.getOpenDate()) && !finalMax.isBefore(ti.getOpenDate());
//            return ti -> !finalMin.isEqual(ti.getOpenDate());
//        },
//                // data_pagesesPicker.valueProperty(),
//                data_pagesesPicker.valueProperty()));
//
//        pagesat_Table.setItems(filteredItems);
    kerkoBtn.setOnAction(event->{
            try {
                serachByDate(data_pagesesPicker.getValue());
            } catch (Exception ex) {
                Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    refreshBtn.setOnAction(evnet->refreshTable());
    }

    public PagesatViewController(homeView.HomeViewController homeCo) {
        this.homeCo = homeCo;
        loader = new FXMLLoader(getClass().getResource("pagesatView.fxml"));

        // Set this class as the controller
        loader.setController(this);

        // Load the scene
    }

    public void showScene() {
        try {
            homeCo.holderPane.getChildren().clear();
            homeCo.holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void getSherbimet(String sherbimi, String shuma) {
        ObservableList<String> s = listaSherbimeveTeKryera.getItems();

        String euro = "\u20ac";

        String so = sherbimi + " -> " + shuma + " " + euro;
        listaSherbimeveTeKryera.getItems().add(so);

    }

    public void openSherbimetView() {

        sherCo = new SherbimetController(this);
        if (!sherCo.isStageVisible()) {
            shtoSherbimBtn.setDisable(true);
        }

        sherCo.showSherbiemet();

    }

    public JFXListView<String> getListaSherbimeveTeKryera() {
        return listaSherbimeveTeKryera;
    }

    public JFXButton getShtoSherbimBtn() {
        return shtoSherbimBtn;
    }

    public void loadPagaest() {
        dc = new DbConnection();
        
        Connection conn = dc.Connect();
        ResultSet rs = null;
        if(!dataPagesat.isEmpty()){
            dataPagesat.removeAll(dataPagesat);
        }
        try {
            if (user.getPrivilegji().equals("admin")) {

                rs = conn.createStatement().executeQuery("Select * from Pagesat");

            } else {

                StaffJpaController dok = new StaffJpaController(emf);
                List<Staff> doks = dok.findStaffEntities();
                for (Staff doki : doks) {
                    if ((doki.getEmri().equals(user.getEmri())) && (doki.getMbiermi().equals(user.getMbiemri()))) {

                        doktori = doki;
                      
                        
                             rs = conn.createStatement().executeQuery("Select * from Pagesat where Staff_ID=" + doktori.getSId());
                        
                       
                        break;

                    }
                }
            }
            String dokEmri = "";
            if (rs != null) {
                StaffJpaController stjpa = new StaffJpaController(emf);
                List<Staff> stffList = stjpa.findStaffEntities();
                while (rs.next()) {
                    for (Staff staff : stffList) {
                        if (rs.getString("Staff_ID").equals(staff.getSId().toString())) {
                            dokEmri = staff.getEmri() + " " + staff.getMbiermi();
                            break;
                        }
                    }
                    dataPagesat.add(new PagesatModel(rs.getString("Pagesa_ID"), rs.getString("Emri_Pac"), rs.getString("Mbiemri_Pac"), rs.getString("Data_Lindjes"), rs.getString("Data_Pageses"), rs.getString("Shuma_ePaguar"), dokEmri));
                }
            }

            col_emri.setCellValueFactory(cellData -> cellData.getValue().getEmriPac());
            col_mbiemri.setCellValueFactory(cellData -> cellData.getValue().getMbiemriPac());
            col_dataLindjes.setCellValueFactory(cellData -> cellData.getValue().getDataLindjes());
            col_dataPageses.setCellValueFactory(cellData -> cellData.getValue().getDataPageses());
            col_shumaPaguar.setCellValueFactory(cellData -> cellData.getValue().getShumaEpaguar());
            col_doktori.setCellValueFactory(cellData -> cellData.getValue().getStaff());
        } catch (SQLException ex) {
            Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pagesat_Table.setItems(null);
        pagesat_Table.setItems(dataPagesat);

    }

    public void shtoPages() throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);

        String emri = "";

        String mbiemri = "";
        java.sql.Date datalindjes = null;
        java.sql.Date datapageses = null;

        Integer shumaTotale = null;

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

        if (dataLindjes_picker.getValue() != null) {
            datalindjes = java.sql.Date.valueOf(dataLindjes_picker.getValue());
        } else {
            alert.setContentText("Cakto daten e lindjes");
            alert.showAndWait();
            throw new Exception("Cakto daten e lindjes");
        }

        if (data_pagPicker.getValue() != null) {
            datapageses = java.sql.Date.valueOf(data_pagPicker.getValue());
        } else {
            alert.setContentText("Cakto daten e pageses");
            alert.showAndWait();
            throw new Exception("Cakto daten e pageses");
        }

        PagesatJpaController pjpa = new PagesatJpaController(emf);
        List<Pagesat> plist = pjpa.findPagesatEntities();

        for (Pagesat p : plist) {

            if (p.getEmriPac().equals(emri) && p.getMbiemriPac().equals(mbiemri)) {

                if (p.getDataLindjes().equals(datalindjes) && p.getDataPageses().equals(datapageses)) {
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Pacienti :" + p.getEmriPac() + " " + p.getMbiemriPac() + " ka bere nje pages ne kete date"
                            + " \n deshironi te regjistroni pages tjeter");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get() == ButtonType.OK) {
                        break;
                    } else {
                        throw new Exception("Nuk u regjistrua pagesa");
                    }
                }
            }
        }
        StaffJpaController stjpa = new StaffJpaController(emf);
        List<Staff> slist = stjpa.findStaffEntities();

        Pagesat pagesa = new Pagesat();
        pagesa.setEmriPac(emri);
        pagesa.setMbiemriPac(mbiemri);
        pagesa.setDataLindjes(datalindjes);
        pagesa.setDataPageses(datapageses);

        Staff doki = null;
        for (Staff staff : slist) {
            if (staff.getEmri().equals(user.getEmri()) && staff.getMbiermi().equals(user.getMbiemri())) {
                doki = staff;
                break;
            }
        }
        pagesa.setStaffID(doki);

        PagesatJpaController pagjpa = new PagesatJpaController(emf);
        pagjpa.create(pagesa);
        List<Sherbimet> sherbimet = new ArrayList<>();
        SherbimetJpaController shjpa = new SherbimetJpaController(emf);
        List<Sherbimet> shlist = shjpa.findSherbimetEntities();

        ObservableList<String> sherbimetEkryera = (ObservableList<String>) listaSherbimeveTeKryera.getItems();
        for (String sh : sherbimetEkryera) {
            String[] s = sh.split(" -> ");
            String[] pa = s[1].split(" ");
            String llojish = s[0];
            String shumap = pa[0];

            Sherbimet sherbimi = new Sherbimet();
            sherbimi.setEmriSherbimit(llojish);
            sherbimi.setQmimiSherbimit(Integer.parseInt(shumap));
            sherbimi.setPagesaID(pagesa);
            sherbimi.setStaffID(doki);

            if (sherbimi == null) {
                Sherbimet sher = new Sherbimet();
                sher.setEmriSherbimit(llojish);
                sher.setQmimiSherbimit(Integer.parseInt(shumap));
                sher.setPagesaID(pagesa);
                sher.setStaffID(doki);
                shjpa.create(sher);
                sherbimet.add(sher);
            } else {
                shjpa.create(sherbimi);
                sherbimet.add(sherbimi);
            }

        }

        shumaTotale = Integer.parseInt(total_shuma_field.getText());
        pagesa.setShumaePaguar(shumaTotale);
        pagesa.setSherbimetList(sherbimet);
        pagjpa.edit(pagesa);
        alert.setContentText("Pagesa u regjistrua me sukses");
        alert.showAndWait();
        
        loadPagaest();

    }

    public boolean isValid(String s) {
        String regex = "[A-Za-z\\s]+";
        return s.matches(regex) && !s.trim().isEmpty();//returns true if input and regex matches otherwise false;
    }

    public void clearTable() {
        pagesat_Table.getItems().clear();
    }

    public void setTotalShuma() {
        ObservableList<String> sh = listaSherbimeveTeKryera.getItems();
        Integer shuma = 0;
        if (sh != null) {
            for (String s : sh) {
                String[] sherbimi = s.split(" -> ");
                String[] shpjes = sherbimi[1].split(" ");
                shuma += Integer.parseInt(shpjes[0]);
            }
        }
        total_shuma_field.setText(shuma.toString());
    }

    public void clearSherbimetList() {
        emri_field.clear();
        mbiemri_field.clear();
        dataLindjes_picker.setValue(null);
        data_pagPicker.setValue(null);
        total_shuma_field.clear();
        listaSherbimeveTeKryera.getItems().clear();
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        String formattedString = localDate.format(formatter);
        LocalDate localDates = LocalDate.parse(formattedString, formatter);
        data_pagPicker.setValue(localDates);
    }

    public Staff getDoki() {
        return doktori;
    }

    public void fshijePagesen(PagesatModel pagesa) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        if (pagesa.getPid() == null) {
            alert.setContentText("Selekto nje pages");
            alert.showAndWait();
        } else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deshironi ta fshini pagesen nga databaza");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                PagesatJpaController pjpa = new PagesatJpaController(emf);
                pjpa.destroy(Integer.parseInt(pagesa.getPid()));
                alert.setContentText("Pagesa u fshi me sukses");
                alert.showAndWait();
                clearTable();
                loadPagaest();
            }
        }
    }

    public void search() {

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pagesatDetails -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (pagesatDetails.getEP().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<PagesatModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(pagesat_Table.comparatorProperty());
        pagesat_Table.setItems(sortedData);
    }

    public void serachByDate(LocalDate date)throws Exception {
        if(!byDate.isEmpty()){
             byDate.removeAll(byDate);
        }
      
       if(date == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
       alert.setContentText("Cakto nje date");
           alert.showAndWait();
           throw new Exception("cakto nje date");
           
       }
        dc = new DbConnection();
        String data = date.toString();
        Connection conn = dc.Connect();
        ResultSet rs = null;
        try {
            if (user.getPrivilegji().equals("admin")) {

                rs = conn.createStatement().executeQuery("Select * from Pagesat where Data_Pageses = "+"'"+date+"'");

            } else {

                StaffJpaController dok = new StaffJpaController(emf);
                List<Staff> doks = dok.findStaffEntities();
                for (Staff doki : doks) {
                    if ((doki.getEmri().equals(user.getEmri())) && (doki.getMbiermi().equals(user.getMbiemri()))) {

                        doktori = doki;

                        rs = conn.createStatement().executeQuery("Select * from Pagesat where Staff_ID=" + doktori.getSId()+" and Data_Pageses = "+"\'"+date+"\'");
                        break;

                    }
                }
            }
            String dokEmri = "";
            if (rs != null) {
                StaffJpaController stjpa = new StaffJpaController(emf);
                List<Staff> stffList = stjpa.findStaffEntities();
                while (rs.next()) {
                    for (Staff staff : stffList) {
                        if (rs.getString("Staff_ID").equals(staff.getSId().toString())) {
                            dokEmri = staff.getEmri() + " " + staff.getMbiermi();
                            break;
                        }
                    }
                    byDate.add(new PagesatModel(rs.getString("Pagesa_ID"), rs.getString("Emri_Pac"), rs.getString("Mbiemri_Pac"), rs.getString("Data_Lindjes"), rs.getString("Data_Pageses"), rs.getString("Shuma_ePaguar"), dokEmri));
                }
            }
            
          
        } catch (SQLException ex) {
            Logger.getLogger(PagesatViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        


        pagesat_Table.setItems(null);
        pagesat_Table.setItems(byDate);
  
    
}
    
        public void refreshTable(){
            loadPagaest();
        }
        
        public void editoPagesen() throws Exception{
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        if(pagesaId == null){
            alert.setContentText("Selekto nje pages");
            alert.showAndWait();
            throw new Exception("Selekto nje pages");
        }

        String emri = "";

        String mbiemri = "";
        java.sql.Date datalindjes = null;
        java.sql.Date datapageses = null;

        Double shumaTotale = null;

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

        if (dataLindjes_picker.getValue() != null) {
            datalindjes = java.sql.Date.valueOf(dataLindjes_picker.getValue());
        } else {
            alert.setContentText("Cakto daten e lindjes");
            alert.showAndWait();
            throw new Exception("Cakto daten e lindjes");
        }

        if (data_pagPicker.getValue() != null) {
            datapageses = java.sql.Date.valueOf(data_pagPicker.getValue());
        } else {
            alert.setContentText("Cakto daten e pageses");
            alert.showAndWait();
            throw new Exception("Cakto daten e pageses");
        }
        int shuma = 0;
        
            if (!total_shuma_field.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                alert.setContentText("Shuma jo valide");
                alert.showAndWait();
                throw new Exception("Shuma jo valide");
            }else{
                double d = Double.parseDouble(total_shuma_field.getText());
               shuma = (int)d;
            }
         
          
          PagesatJpaController pjpa = new PagesatJpaController(emf);
          
          
          Pagesat pa = pjpa.findPagesat(pagesaId);
       
          pa.setEmriPac(emri);
          pa.setMbiemriPac(mbiemri);
          pa.setDataLindjes(datalindjes);
          pa.setDataPageses(datapageses);
          pa.setShumaePaguar(shuma);
          
          pjpa.edit(pa);
          alert.setContentText("Pagesa u editua me sukses");
          alert.showAndWait();
          clearTable();
          loadPagaest();
    }
        
}