
package com.mycompany.santarahotel;

// Import library untuk koneksi database, logging, format angka, dan JavaFX UI
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CekPesananController {
    // Objek modular untuk berpindah antar scene
    private SwitchScene sceneController = new SwitchScene();

// Mengambil komponen MenuItem yang mungkin digunakan untuk memilih tipe kamar (komponen sudah terinisialisasi pada file fxml)
    @FXML
    private MenuItem Deluxe;
    @FXML
    private MenuItem Premium;
    @FXML
    private MenuItem Standard;
    
// Method untuk navigasi perpindahan page pada navbar (tiap method akan dipasang pada button untuk pemanggilan saat event klik)
    @FXML
    private void switchToHome (ActionEvent event) throws IOException { 
        sceneController.switchSceneHome(event);
    }
    
    @FXML
    private void switchToReservasi(ActionEvent event) throws IOException {
        sceneController.switchSceneReservasi(event, Standard, Premium, Deluxe);
    }
    
    @FXML
    private void switchToCekPesanan (ActionEvent event) throws IOException {
        sceneController.switchSceneCekPesanan(event);
    }
    
    @FXML
    private void swithToAboutCompany (ActionEvent event) throws IOException {
        sceneController.switchSceneAboutCompany(event);
    }
    
    @FXML
    private void switchToBiodata (ActionEvent event) throws IOException {
        sceneController.switchSceneBiodata(event);
    }
    
// Mengambil komponen Pane untuk dimanipulasi pada method initialize (komponen sudah terinisialisasi pada file fxml)
    @FXML
    private Pane pane_search_id_field;
    @FXML
    private Pane pane_search_name_field;

    @FXML
    public void initialize() { // method akan dieksekusi pertama kali saat scene yang menggunakan controller ini ditampilkan
        // pada awal tampil controller akan menampilkan index pencarian sesuai pilihan pengguna
        if (DataContainerBetweenScene.getSearch().equals("MenuSearchID")) { 
            // jika tombol yang dipencet adalah SearchByID maka akan tampil field untuk pencarian berdasarkan ID
            pane_search_id_field.setVisible(true);
            pane_search_id_field.setDisable(false);
            
            pane_search_name_field.setVisible(false);
            pane_search_name_field.setDisable(true);
        } else if (DataContainerBetweenScene.getSearch().equals("MenuSearchName")) {
            // jika tombol yang dipencet adalah SearchByName maka akan tampil field untuk pencarian berdasarkan Informasi Pesanan
            pane_search_name_field.setVisible(true);
            pane_search_name_field.setDisable(false);
            
            pane_search_id_field.setVisible(false);
            pane_search_id_field.setDisable(true);
        }
    }
    
// Inisiasi pengambilan semua komponen yang akan dimanipulasi selama method controller dieksekusi
    @FXML
    private Pane pane_searched;
    @FXML
    private TextField textField_searchIndex_id;
    @FXML
    private TextField textField_searchIndex_nama;
    @FXML
    private TextField textField_searchIndex_nomorKependudukan;
    @FXML
    private Label label_cekPesanan_harga;
    @FXML
    private Label label_cekPesanan_id;
    @FXML
    private Label label_cekPesanan_jenisKamar;
    @FXML
    private Label label_cekPesanan_layananTambahan;
    @FXML
    private Label label_cekPesanan_nama;
    @FXML
    private Label label_cekPesanan_nomorKamar;
    @FXML
    private Label label_cekPesanan_statusPesanan;
    @FXML
    private Label label_cekPesanan_tanggalCheckIn;
    @FXML
    private Label label_cekPesanan_tanggalCheckOut;
    @FXML
    private TextField textField_searchIndex_tanggalCheckIn;
    @FXML
    private ImageView imageView_previewKamar;

// Inisasi object tools yang akan digunakan
    private Connection connect; // Koneksi ke database melalui file ConnectionDB.java
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###"); // Format ribuan untuk pelabelan harga

// Method pencarian berdasarkan ID pesanan (method akan dipasang pada button untuk pemanggilan saat event klik)
    @FXML
    private void SearchByID(ActionEvent event) {
        alertMessage alert = new alertMessage();
        
        if (textField_searchIndex_id.getText().isEmpty()) { // memvalidasi form field yang disediakan
            alert.errorMessage("Tolong isi kolom yang disediakan");
            return;
        }
        
        String selectData = "SELECT * FROM `customer` WHERE id_pesanan = ?";
        
        connect = ConnectionDB.ConnectDb(); 
        
        
        try {
            
            prepare = connect.prepareStatement(selectData); // menyiapkan query sebelum dieksekusi dengan menyisipkan variabel search value
            prepare.setString(1, textField_searchIndex_id.getText()); // mengambil teks yang dimasukkan user pada field lalu dimasukkan sebagai value di query SQL
            result = prepare.executeQuery(); // menangkap hasil eksekusi dari perintah query yang telah dibuat
            
            if (result.next()) {
                alert.successMessage("Data ditemukan!");
                // Set data hasil query ke komponen label yang akan ditampilkan ke pengguna
                label_cekPesanan_nama.setText(result.getString("nama"));
                label_cekPesanan_id.setText(result.getString("id_pesanan"));
                label_cekPesanan_jenisKamar.setText(result.getString("tipe_kamar"));
                label_cekPesanan_nomorKamar.setText(result.getString("nomor_kamar"));
                label_cekPesanan_tanggalCheckIn.setText(result.getString("tanggal_check_in"));
                label_cekPesanan_tanggalCheckOut.setText(result.getString("tanggal_check_out"));
                label_cekPesanan_layananTambahan.setText(result.getString("varian_kamar"));
                label_cekPesanan_statusPesanan.setText(result.getString("status"));
                label_cekPesanan_harga.setText("Rp. " + stringFormatterSeparator.format(Integer.parseInt(result.getString("harga"))).replace(",", ".") + ".000");
            } else {
                alert.errorMessage("Data tidak ditemukan");
                
                return;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(imageView_previewKamar != null) {
            try {
                // menentukan gambar yang akan ditampilkan sesuai jenis kamar yang dipilih
                switch (result.getString("tipe_kamar")) {
                    case "Standar":
                        imageView_previewKamar.setImage(new Image(getClass().getResource("/com/mycompany/santarahotel/image/KamarStandar.png").toExternalForm()));
                        break;
                    case "Premium":
                        imageView_previewKamar.setImage(new Image(getClass().getResource("/com/mycompany/santarahotel/image/KamarPremium.png").toExternalForm()));
                        break;
                    case "Deluxe":
                        imageView_previewKamar.setImage(new Image(getClass().getResource("/com/mycompany/santarahotel/image/KamarDeluxe.png").toExternalForm()));
                        break;
                    default:
                        break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(CekPesananController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // menampilkan hasil pencarian yang sudah ditulis pada label
        pane_searched.setDisable(false);
        pane_searched.setVisible(true);
    }

// Fungsi pencarian berdasarkan nama, nomor kependudukan (email), dan tanggal check-in
    public void SearchByName() {
        alertMessage alert = new alertMessage();

        if(textField_searchIndex_nama.getText().isEmpty() || textField_searchIndex_nomorKependudukan.getText().isEmpty() || textField_searchIndex_tanggalCheckIn.getText().isEmpty()) {
            // Validasi field
            alert.errorMessage("Tolong isi semua kolom yang disediakan");
            return;
        }
        
        String selectData = "SELECT * FROM `customer` WHERE nama = ? AND email = ? AND tanggal_check_in = ?";
        
        connect = ConnectionDB.ConnectDb();
        
        
        try {
            // menyiapkan query sebelum dieksekusi dengan menyisipkan variabel search value
            prepare = connect.prepareStatement(selectData);
            prepare.setString(1, textField_searchIndex_nama.getText());
            prepare.setString(2, textField_searchIndex_nomorKependudukan.getText());
            prepare.setString(3, textField_searchIndex_tanggalCheckIn.getText() + " 00:00:00");
            
            result = prepare.executeQuery(); // menyimpan hasil eksekusi query
            
            if (result.next()) {
                alert.successMessage("Data ditemukan!");
                // Set data hasil query ke label
                label_cekPesanan_nama.setText(String.format("%.12s", result.getString("nama")));
                label_cekPesanan_id.setText(result.getString("id_pesanan"));
                label_cekPesanan_jenisKamar.setText(result.getString("tipe_kamar"));
                label_cekPesanan_nomorKamar.setText(result.getString("nomor_kamar"));
                label_cekPesanan_tanggalCheckIn.setText(result.getString("tanggal_check_in"));
                label_cekPesanan_tanggalCheckOut.setText(result.getString("tanggal_check_out"));
                label_cekPesanan_layananTambahan.setText(result.getString("varian_kamar"));
                label_cekPesanan_statusPesanan.setText(result.getString("status"));
                label_cekPesanan_harga.setText("Rp. " + stringFormatterSeparator.format(Integer.parseInt(result.getString("harga"))).replace(",", ".") + ".000");
            } else {
                alert.errorMessage("Data tidak ditemukan");
                
                return;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        pane_searched.setDisable(false);
        pane_searched.setVisible(true);
    }
    
    @FXML
    private Button btn_switch_to_search_by_id;

    @FXML
    private Button btn_switch_to_search_by_name;

    // Mengatur tampilan form pencarian: berdasarkan ID atau nama
    public void switchSearch(ActionEvent event) {
        if(event.getSource() == btn_switch_to_search_by_name) {
            pane_search_id_field.setDisable(true);
            pane_search_id_field.setVisible(false);
        
            pane_search_name_field.setDisable(false);
            pane_search_name_field.setVisible(true);
        } else if (event.getSource() == btn_switch_to_search_by_id) {
            pane_search_name_field.setDisable(true);
            pane_search_name_field.setVisible(false);
        
            pane_search_id_field.setDisable(false);
            pane_search_id_field.setVisible(true);
        }
    }

}
