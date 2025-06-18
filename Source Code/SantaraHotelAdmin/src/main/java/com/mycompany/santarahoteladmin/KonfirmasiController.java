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

/**
 * Controller untuk halaman konfirmasi pesanan admin
 */

public class KonfirmasiController {
    // Komponen UI sidebar
    @FXML
    private Pane sidenavbar;
    
    // Method untuk menampilkan/sembunyikan sidebar
    @FXML
    private void navbar() {
        navslider.slide_navbar(sidenavbar);
    }
    
// Method untuk navigasi antar halaman
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
    
    
// Komponen UI field untuk pencarian data
    @FXML
    private ComboBox comboBox_SearchIndex; // Pilihan metode pencarian (ID atau Informasi)
    @FXML
    private ComboBox comboBox_statusPesanan; // Filter status pesanan
    @FXML
    private ComboBox comboBox_tipeKamar; // Filter tipe kamar
    @FXML
    private ComboBox comboBox_statusUpdated; // Pilihan update status
    
// Komponen input tanggal
    @FXML
    private DatePicker datePicker_tanggalCheckIn; 
    @FXML
    private DatePicker datePicker_tanggalCheckOut;
    
// Field input teks
    @FXML
    private TextField textField_ID;
    @FXML
    private TextField textField_email;
    @FXML
    private TextField textField_nama;
    
    // Alert untuk menampilkan pesan error/success
    private alertMessage alert = new alertMessage();

// Inisialisasi komponen saat controller dijalankan
    @FXML
    public void initialize() {
        // Mengisi ComboBox dengan pilihan pencarian
        ObservableList<String> searchIndex = FXCollections.observableArrayList("ID", "Information");
        comboBox_SearchIndex.setItems(searchIndex);
        
        // Mengisi ComboBox dengan status pesanan
        ObservableList<String> statusPesanan = FXCollections.observableArrayList("Belum dibayar", "Terbayar", "Ditempati", "Check out");
        comboBox_statusPesanan.setItems(statusPesanan);
        
        // Mengisi ComboBox dengan tipe kamar
        ObservableList<String> tipeKamar = FXCollections.observableArrayList("standar", "premium", "deluxe");
        comboBox_tipeKamar.setItems(tipeKamar);
        
        // Mengisi ComboBox dengan status yang bisa diupdate
        ObservableList<String> statusUpdate = FXCollections.observableArrayList("Terbayar", "Check In", "Check Out");
        comboBox_statusUpdated.setItems(statusUpdate);
    }

    /**
     * Method untuk mengubah tampilan form pencarian berdasarkan pilihan metode pencarian
     */
    @FXML
    void searchBy(ActionEvent event) {
        if (comboBox_SearchIndex.getValue().equals("ID")) {
            // Mode pencarian dengan ID - sembunyikan field lainnya
            comboBox_statusPesanan.setVisible(false);
            comboBox_tipeKamar.setVisible(false);
            datePicker_tanggalCheckIn.setVisible(false);
            datePicker_tanggalCheckOut.setVisible(false);
            textField_email.setVisible(false);
            textField_nama.setVisible(false);
            
            // Nonaktifkan field yang tidak digunakan
            comboBox_statusPesanan.setDisable(true);
            comboBox_tipeKamar.setDisable(true);
            datePicker_tanggalCheckIn.setDisable(true);
            datePicker_tanggalCheckOut.setDisable(true);
            textField_email.setDisable(true);
            textField_nama.setDisable(true);
            
            // Tampilkan dan aktifkan field ID
            textField_ID.setVisible(true);
            textField_ID.setDisable(false);
        } else {
            // Mode pencarian dengan informasi lengkap - sembunyikan field ID
            textField_ID.setVisible(false);
            textField_ID.setDisable(true);
            
            // Tampilkan field pencarian lengkap
            comboBox_statusPesanan.setVisible(true);
            comboBox_tipeKamar.setVisible(true);
            datePicker_tanggalCheckIn.setVisible(true);
            datePicker_tanggalCheckOut.setVisible(true);
            textField_email.setVisible(true);
            textField_nama.setVisible(true);
            
            // Aktifkan semua field pencarian
            comboBox_statusPesanan.setDisable(false);
            comboBox_tipeKamar.setDisable(false);
            datePicker_tanggalCheckIn.setDisable(false);
            datePicker_tanggalCheckOut.setDisable(false);
            textField_email.setDisable(false);
            textField_nama.setDisable(false);
        }
    }
    
    // Label untuk menampilkan hasil pencarian
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
    
    // Format untuk menampilkan harga
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
    /**
     * Method untuk mencari data pesanan berdasarkan parameter yang dipilih
     */
    @FXML
    void cariData(ActionEvent event) {
        // Query dasar untuk mencari data
        String stringQuery = "SELECT `nama`, `tipe_kamar`, `nomor_kamar`, `varian_kamar`, `harga`, `status` FROM `customer` WHERE 1=1 ";
        
        // Membuka koneksi database
        Connection connect = ConnectionDB.ConnectDb();
        
        // Pencarian berdasarkan informasi lengkap
        if(textField_nama.isVisible()) {
            // Validasi input
            if(textField_email.getText().isEmpty() || textField_nama.getText().isEmpty() || datePicker_tanggalCheckIn.getValue() == null 
                || datePicker_tanggalCheckOut.getValue() == null || comboBox_statusPesanan.getSelectionModel().isEmpty() || comboBox_tipeKamar.getSelectionModel().isEmpty()) {
                alert.errorMessage("Tolong isi semua kolom yang disediakan");
                return;
            }
            
            // Menyusun query dengan parameter
            stringQuery += "AND `nama` = ? AND `email` = ? AND `tanggal_check_in` = ? AND `tanggal_check_out` = ? AND `tipe_kamar` = ?";
            if(!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                stringQuery += " AND `status` = ?";
            }
            try {
                // Eksekusi query
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
                
                // Jika data ditemukan, update tampilan
                if(result.next()){
                    updateDisplay(result);
                }
            } catch (Exception e) {
            }
        // Pencarian berdasarkan ID
        } else if (textField_ID.isVisible()) {
            // Validasi input
            if(textField_ID.getText().isEmpty()) {
                alert.errorMessage("Tolong isi semua kolom yang disediakan");
                return;
            }
            
            stringQuery += "AND `id_pesanan` = ?";
            
            try {
                // Eksekusi query
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, textField_ID.getText());
                
                ResultSet result = prepare.executeQuery();
                
                // Jika data ditemukan, update tampilan
                if(result.next()) {
                    updateDisplay(result);
                }
            } catch (Exception e) {
            }
        }
    }
    
    /**
     * Method untuk memperbarui tampilan dengan data hasil pencarian
     * @param result ResultSet dari query database
     */
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
    
    /**
     * Method untuk mengubah status pesanan
     */
    @FXML
    private void ubahStatus(ActionEvent event) {
        
        // Validasi pilihan status baru
        if(comboBox_statusUpdated.getSelectionModel().isEmpty()){
            alert.errorMessage("Tolong pilih status terbaru");
            return;
        }
        
        String stringQuery = "UPDATE `customer` SET `status` = ? WHERE 1=1 ";
        
        Connection connect = ConnectionDB.ConnectDb();
        
        // Update berdasarkan informasi lengkap
        if(textField_nama.isVisible()) {
            stringQuery += "AND `nama` = ? AND `email` = ? AND `tanggal_check_in` = ? AND `tanggal_check_out` = ? AND `tipe_kamar` = ?";
            if(!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                stringQuery += " AND `status` = ?";
            }
            try {
                // Eksekusi query update
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
                
                prepare.executeUpdate();
                
                // Tampilkan pesan sukses
                alert.successMessage("Status berhasil diubah!");
            } catch (Exception e) {
                System.out.println(e);
            }
        // Update berdasarkan ID
        } else if (textField_ID.isVisible()) {
            stringQuery += "AND `id_pesanan` = ?";
            
            try {
                // Eksekusi query update
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, comboBox_statusUpdated.getValue().toString());
                prepare.setString(2, textField_ID.getText());
                
                prepare.executeUpdate();
                
                // Tampilkan pesan sukses
                alert.successMessage("Status berhasil diubah!");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
