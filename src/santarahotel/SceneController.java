package santarahotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void switchSceneReservasi(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Reservasi.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchSceneReservasi_IsiData(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Reservasi_FormData.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchSceneCekPesanan(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Cek_Pesanan.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private Button btn_cekPesanan;

    @FXML
    private Button btn_loginAdmin;

    @FXML
    private Button btn_reservasi;

    @FXML
    private Button btn_tentangKami;

    @FXML
    private CheckBox checkBox_sarapan;

    @FXML
    private CheckBox checkBox_selimutTambahan;

    @FXML
    private Label label_hargaAkhir;

    @FXML
    private Label label_roomType;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TextField textField_namaPemesan;

    @FXML
    private TextField textField_nomorKependudukan;

    @FXML
    private TextField textField_tanggalCheckIn;
    
    @FXML
    private TextField textField_tanggalCheckOut;
    
//    Method untuk form data reservasi
    private int harga;
    private long jumlahHari;
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
    public static long countDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    private void hitungLamaPesanan() {
        // kalkulasi untuk berapa hari pelanggan memesan kamar
        try {
            String[] waktuCheckIn_String = textField_tanggalCheckIn.getText().split("-");
            int[] waktuCheckIn_Integer = new int[3];
            for (int i = 0; i < waktuCheckIn_String.length; i++) {
                waktuCheckIn_Integer[i] = Integer.parseInt(waktuCheckIn_String[i]);
            }

            String[] waktuCheckOut_String = textField_tanggalCheckOut.getText().split("-");
            int[] waktuCheckOut_Integer = new int[3];
            for (int i = 0; i < waktuCheckOut_String.length; i++) {
                waktuCheckOut_Integer[i] = Integer.parseInt(waktuCheckOut_String[i]);
            }

            LocalDate tanggalCheckIn = LocalDate.of(waktuCheckIn_Integer[0], waktuCheckIn_Integer[1], waktuCheckIn_Integer[2]);
            LocalDate tanggalCheckOut = LocalDate.of(waktuCheckOut_Integer[0], waktuCheckOut_Integer[1], waktuCheckOut_Integer[2]);

            // Menghitung hari
            jumlahHari = countDaysBetween(tanggalCheckIn, tanggalCheckOut);
        } catch (Exception e) {
        }
    }
    
    public void pesananTambahan(ActionEvent event) {
        
        hitungLamaPesanan();
        
        if (label_roomType.getText().equals("Fast Staycation")) {
            harga = 275;
        }
        
        if (checkBox_sarapan.isSelected() && checkBox_selimutTambahan.isSelected()) {
            harga = (int) (harga * jumlahHari) + 70;
        } else if (checkBox_sarapan.isSelected()) {
            harga = (int) (harga * jumlahHari) + 40;
        } else if (checkBox_selimutTambahan.isSelected()) {
            harga = (int) ((harga * jumlahHari) + 30);
        } else {
            harga = (int) (harga * jumlahHari);
        }
        
        label_hargaAkhir.setText("Rp. " + stringFormatterSeparator.format(harga).replace(",", ".") + ".000");
    }
    
    public void kalkulasiLamaPesanan() {
        try {
            
            if (textField_tanggalCheckIn.getText().isEmpty() || textField_tanggalCheckOut.getText().isEmpty()) {
                return;
            }

            hitungLamaPesanan();

            if (label_roomType.getText().equals("Fast Staycation")) {
                harga = 275;
            }

            if (checkBox_sarapan.isSelected() && checkBox_selimutTambahan.isSelected()) {
                harga = (int) (harga * jumlahHari) + 70;
            } else if (checkBox_sarapan.isSelected()) {
                harga = (int) (harga * jumlahHari) + 40;
            } else if (checkBox_selimutTambahan.isSelected()) {
                harga = (int) ((harga * jumlahHari) + 30);
            } else {
                harga = (int) (harga * jumlahHari);
            }

            label_hargaAkhir.setText("Rp. " + stringFormatterSeparator.format(harga).replace(",", ".") + ".000");

        } catch (Exception e) {
            return;
        }
    }
    
    private Connection connect = ConnectionDB.ConnectDb();
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    public void pesanKamar() {
        try {
            
            alertMessage alert = new alertMessage();

            // Mengecek apakah ada kolom yang kosong
            if(textField_namaPemesan.getText().isEmpty() || textField_nomorKependudukan.getText().isEmpty() 
                    || textField_tanggalCheckIn.getText().isEmpty() || textField_tanggalCheckOut.getText().isEmpty()) {
                alert.errorMessage("Isi semua kolom yang tersedia sesuai ketentuan");
            } else {
                String insertData = "INSERT INTO customer " 
                        + "(nama, nomor_kependudukan, tanggal_check_in, tanggal_check_out, tipe_kamar, nomor_kamar, status, id_pesanan, harga)"
                        + "VALUES(?,?,?,?,?,?,?,?,?)";
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, textField_namaPemesan.getText());
                prepare.setString(2, textField_nomorKependudukan.getText());
                prepare.setString(3, textField_tanggalCheckIn.getText());
                prepare.setString(4, textField_tanggalCheckOut.getText());
                prepare.setString(5, label_roomType.getText());
                prepare.setString(6, Integer.toString((int)(Math.random() * 100) + 1) );
                prepare.setString(7, "Belum Dibayar");
                prepare.setString(8, "PP" + Integer.toString((int)(Math.random() * 100) + 1) );
                prepare.setString(9, Integer.toString(harga));
                
                prepare.executeUpdate();
                
                alert.successMessage("Data Berhasil Masuk!");
                
                pesanKamarClearForm();

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void pesanKamarClearForm() {
        textField_namaPemesan.setText("");
        textField_nomorKependudukan.setText("");
        textField_tanggalCheckIn.setText("");
        textField_tanggalCheckOut.setText("");
    }
    
}


