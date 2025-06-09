package com.mycompany.santarahoteladmin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class KonfirmasiController {
    @FXML
    private Pane sidenavbar;
    
    @FXML
    private void navbar() {
        navslider.slide_navbar(sidenavbar);
    }
    
    @FXML
    private void switchToKonfirmasiPesanan(ActionEvent event) throws IOException {
        SceneController.switchSceneKonfirmasiPesanan(event);
    }
    
    @FXML
    private void switchToHistoriPesanan(ActionEvent event) throws IOException {
        SceneController.switchSceneHistoriPesanan(event);
    }
    
    @FXML
    private void switchToLaporanPendapatan(ActionEvent event) throws IOException {
        SceneController.switchSceneLaporanPendapatan(event);
    }
    
    
    @FXML
    private ComboBox comboBox_SearchIndex;

    @FXML
    private ComboBox comboBox_statusPesanan;

    @FXML
    private ComboBox comboBox_tipeKamar;
    
    @FXML
    private ComboBox comboBox_statusUpdated;

    @FXML
    private DatePicker datePicker_tanggalCheckIn;

    @FXML
    private DatePicker datePicker_tanggalCheckOut;

    @FXML
    private TextField textField_ID;

    @FXML
    private TextField textField_email;

    @FXML
    private TextField textField_nama;
    
    private alertMessage alert = new alertMessage();

    @FXML
    public void initialize() {
        ObservableList<String> searchIndex = FXCollections.observableArrayList("ID", "Information");
        comboBox_SearchIndex.setItems(searchIndex);
        
        ObservableList<String> statusPesanan = FXCollections.observableArrayList("Belum dibayar", "Terbayar", "Ditempati", "Check out");
        comboBox_statusPesanan.setItems(statusPesanan);
        
        ObservableList<String> tipeKamar = FXCollections.observableArrayList("standar", "premium", "deluxe");
        comboBox_tipeKamar.setItems(tipeKamar);
        
        ObservableList<String> statusUpdate = FXCollections.observableArrayList("Terbayar", "Check In", "Check Out");
        comboBox_statusUpdated.setItems(statusUpdate);
    }

    @FXML
    void searchBy(ActionEvent event) {
        if (comboBox_SearchIndex.getValue().equals("ID")) {
            comboBox_statusPesanan.setVisible(false);
            comboBox_tipeKamar.setVisible(false);
            datePicker_tanggalCheckIn.setVisible(false);
            datePicker_tanggalCheckOut.setVisible(false);
            textField_email.setVisible(false);
            textField_nama.setVisible(false);
            comboBox_statusPesanan.setDisable(true);
            comboBox_tipeKamar.setDisable(true);
            datePicker_tanggalCheckIn.setDisable(true);
            datePicker_tanggalCheckOut.setDisable(true);
            textField_email.setDisable(true);
            textField_nama.setDisable(true);
            
            textField_ID.setVisible(true);
            textField_ID.setDisable(false);
        } else {
            textField_ID.setVisible(false);
            textField_ID.setDisable(true);
            
            comboBox_statusPesanan.setVisible(true);
            comboBox_tipeKamar.setVisible(true);
            datePicker_tanggalCheckIn.setVisible(true);
            datePicker_tanggalCheckOut.setVisible(true);
            textField_email.setVisible(true);
            textField_nama.setVisible(true);
            comboBox_statusPesanan.setDisable(false);
            comboBox_tipeKamar.setDisable(false);
            datePicker_tanggalCheckIn.setDisable(false);
            datePicker_tanggalCheckOut.setDisable(false);
            textField_email.setDisable(false);
            textField_nama.setDisable(false);
        }
    }
    
    @FXML
    private Label label_harga;

    @FXML
    private Label label_jenisKamar;

    @FXML
    private Label label_namaPemesan;

    @FXML
    private Label label_status;

    @FXML
    private Label label_varian;
    
    @FXML
    private Label label_noKamar;
    
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
    @FXML
    void cariData(ActionEvent event) {
        String stringQuery = "SELECT `nama`, `tipe_kamar`, `nomor_kamar`, `varian_kamar`, `harga`, `status` FROM `customer` WHERE 1=1 ";
        
        Connection connect = ConnectionDB.ConnectDb();
        
        if(textField_nama.isVisible()) {
            
            if(textField_email.getText().isEmpty() || textField_nama.getText().isEmpty() || datePicker_tanggalCheckIn.getValue() == null 
                || datePicker_tanggalCheckOut.getValue() == null || comboBox_statusPesanan.getSelectionModel().isEmpty() || comboBox_tipeKamar.getSelectionModel().isEmpty()) {
                alert.errorMessage("Tolong isi semua kolom yang disediakan");
                return;
            }
            
            stringQuery += "AND `nama` = ? AND `email` = ? AND `tanggal_check_in` = ? AND `tanggal_check_out` = ? AND `tipe_kamar` = ?";
            if(!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                stringQuery += " AND `status` = ?";
            }
            try {
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, textField_nama.getText());
                prepare.setString(2, textField_email.getText());
                prepare.setString(3, datePicker_tanggalCheckIn.getValue().toString());
                prepare.setString(4, datePicker_tanggalCheckOut.getValue().toString());
                prepare.setString(5, comboBox_tipeKamar.getValue().toString());
                
                if(!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                    prepare.setString(6, comboBox_statusPesanan.getValue().toString());
                }
                
                ResultSet result = prepare.executeQuery();
                
                if(result.next()){
                    updateDisplay(result);
                }
            } catch (Exception e) {
            }
        } else if (textField_ID.isVisible()) {
            
            if(textField_ID.getText().isEmpty()) {
                alert.errorMessage("Tolong isi semua kolom yang disediakan");
                return;
            }
            
            stringQuery += "AND `id_pesanan` = ?";
            
            try {
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, textField_ID.getText());
                
                ResultSet result = prepare.executeQuery();
                
                if(result.next()) {
                    updateDisplay(result);
                }
            } catch (Exception e) {
            }
        }
    }
    
    private void updateDisplay(ResultSet result) {
        try {
            label_namaPemesan.setText(result.getString("nama"));
            label_jenisKamar.setText(result.getString("tipe_kamar"));
            label_varian.setText(result.getString("varian_kamar"));
            label_harga.setText("Rp. " + stringFormatterSeparator.format(result.getLong("harga")).replace(",", ".") + ".000");
            label_status.setText(result.getString("status"));
            label_noKamar.setText(result.getString("nomor_kamar"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @FXML
    private void ubahStatus(ActionEvent event) {
        
        if(comboBox_statusUpdated.getSelectionModel().isEmpty()){
            alert.errorMessage("Tolong pilih status terbaru");
            return;
        }
        
        String stringQuery = "UPDATE `customer` SET `status` = ? WHERE 1=1 ";
        
        Connection connect = ConnectionDB.ConnectDb();
        
        if(textField_nama.isVisible()) {
            stringQuery += "AND `nama` = ? AND `email` = ? AND `tanggal_check_in` = ? AND `tanggal_check_out` = ? AND `tipe_kamar` = ?";
            if(!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                stringQuery += " AND `status` = ?";
            }
            try {
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, comboBox_statusUpdated.getValue().toString());
                prepare.setString(2, textField_nama.getText());
                prepare.setString(3, textField_email.getText());
                prepare.setString(4, datePicker_tanggalCheckIn.getValue().toString());
                prepare.setString(5, datePicker_tanggalCheckOut.getValue().toString());
                prepare.setString(6, comboBox_tipeKamar.getValue().toString());
                
                if(!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                    prepare.setString(7, comboBox_statusPesanan.getValue().toString());
                }
                
                ResultSet result = prepare.executeQuery();
                
                if(result.next()){
                    label_namaPemesan.setText(result.getString("nama"));
                    label_jenisKamar.setText(result.getString("tipe_kamar"));
                    label_varian.setText(result.getString("varian_kamar"));
                    label_harga.setText(result.getString("harga"));
                    label_status.setText(result.getString("status"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (textField_ID.isVisible()) {
            stringQuery += "AND `id_pesanan` = ?";
            
            try {
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, comboBox_statusUpdated.getValue().toString());
                prepare.setString(2, textField_ID.getText());
                
                prepare.executeUpdate();
                
                alert.successMessage("Status berhasil diubah!");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
