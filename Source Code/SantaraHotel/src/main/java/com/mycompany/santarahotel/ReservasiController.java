package com.mycompany.santarahotel;

import java.awt.Desktop;
import java.sql.Connection;
import java.io.IOException;
import java.net.URI;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ReservasiController {
    // Scene controller untuk berganti antar page
    private final SwitchScene sceneController = new SwitchScene();
    
    @FXML
    private MenuItem Deluxe;

    @FXML
    private MenuItem Premium;

    @FXML
    private MenuItem Standard;
    
    @FXML
    private Button button_tidakTermasukSarapan;
    
    @FXML
    private Button button_termasukSarapan;
    // Method navigasi untuk beralih antar page
    @FXML
    private void switchToHome (ActionEvent event) throws IOException {
        sceneController.switchSceneHome(event);
    }
    
    @FXML
    private void switchToReservasi(ActionEvent event) throws IOException {
        sceneController.switchSceneReservasi(event, Standard, Premium, Deluxe);
    }
    
    @FXML
    private void switchToReservasiIsiData (ActionEvent event) throws IOException {
        sceneController.switchSceneReservasi_IsiData(event, button_tidakTermasukSarapan, button_termasukSarapan);
    }
    
    @FXML
    private void switchToReservasiDataKamar (ActionEvent event) throws IOException {
        sceneController.switchSceneReservasi_DetailKamar(event);
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
    
    // Inisasi komponen UI
    @FXML
    private Label label_tipeKamar = new Label();
    @FXML
    private ImageView imageView_slider;
    @FXML
    private Pane pane_imageSlider;
    @FXML
    private ImageView imageView_previewKamar;
    @FXML
    private Label label_varianKamar;
    @FXML
    private Text text_beforeDiscount;
    @FXML
    private Label label_hargaAkhir;
    @FXML
    private Text text_hargaDenganSarapan;
    @FXML
    private Text text_hargaDenganSarapan_sebelumDiskon;
    @FXML
    private Text text_hargaTanpaSarapan;
    @FXML
    private Text text_hargaTanpaSarapan_sebelumDiskon;
    @FXML
    private Label label_hargaAsliKamar;
    
    // Controller untuk image slider
    private ImageSliderController imageSliderController = new ImageSliderController();
    private List<Image> image_list = new ArrayList<>();
    private int harga = 275; // inisasi variabel harga
    
    @FXML
    public void initialize() {
        // Mengeset label jenis kamar
        label_tipeKamar.setText("Santara " + DataContainerBetweenScene.getData());
        
        // Inisialisasi image slider jika komponen ada pada page aktif
        if(imageView_slider != null && pane_imageSlider != null) {
            // Menambahkan image untuk slider
            image_list.add(new Image(getClass().getResource("/com/mycompany/santarahotel/image/Kamar.jpeg").toExternalForm()));
            image_list.add(new Image(getClass().getResource("/com/mycompany/santarahotel/image/Kamar2.jpeg").toExternalForm()));
            image_list.add(new Image(getClass().getResource("/com/mycompany/santarahotel/image/KamarStandar.png").toExternalForm()));

            // Memulai image slider
            imageSliderController.slideImage(imageView_slider, pane_imageSlider, image_list, 484, 656);
        }
        
        // Mengatur gambar preview ruangan berdasarkan jenis yang dipilih
        if(imageView_previewKamar != null) {
            if(null != DataContainerBetweenScene.getData()) switch (DataContainerBetweenScene.getData()) {
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
        }
        
        // Mengatur label varian kamar
        if(label_varianKamar != null) {
            label_varianKamar.setText(DataContainerBetweenScene.getData() + " (" + DataContainerBetweenScene.getVarian() + ")");
        }
        
        harga = getHargaBase();
        
        // Mengatur label harga dengan nilai yang diformat
        if (label_hargaAsliKamar != null) {
            label_hargaAsliKamar.setText("Rp. " + stringFormatterSeparator.format(harga).replace(",", ".") + ".000");
        }
        
        // Menetapkan label harga untuk opsi dengan/tanpa sarapan jika komponen yang dibutuhkan ada pada page aktif
        if (text_hargaDenganSarapan != null) {
            text_hargaTanpaSarapan.setText("Rp. " + stringFormatterSeparator.format(harga).replace(",", ".") + ".000");
            text_hargaTanpaSarapan_sebelumDiskon.setText("Rp. " + stringFormatterSeparator.format(harga*2).replace(",", ".") + ".000");
            text_hargaDenganSarapan.setText("Rp. " + stringFormatterSeparator.format(harga + 50).replace(",", ".") + ".000");
            text_hargaDenganSarapan_sebelumDiskon.setText("Rp. " + stringFormatterSeparator.format((harga + 50)*2).replace(",", ".") + ".000");
        }
        
        if (label_hargaAkhir != null) {
            if (DataContainerBetweenScene.getVarian().equals("Termasuk sarapan")) {
                harga += 50;
            }
            label_hargaAkhir.setText("Rp. " + stringFormatterSeparator.format(harga).replace(",", ".") + ".000");
        }
        
        if(text_beforeDiscount != null) {
            text_beforeDiscount.setText("Rp. " + stringFormatterSeparator.format(harga*2).replace(",", ".") + ".000");
        }
    }
 
    // Deklarasi form field
    @FXML
    private CheckBox checkBox_sarapan;
    @FXML
    private CheckBox checkBox_selimutTambahan;
    @FXML
    private Label label_roomType;
    @FXML
    private TextField textField_namaPemesan;
    @FXML
    private TextField textField_nomorKependudukan;
    @FXML
    private TextField textField_nomorHandphone;
    @FXML
    private TextField textField_email;
    @FXML
    private DatePicker datePicker_tanggalCheckIn;
    @FXML
    private DatePicker datePicker_tanggalCheckOut;
    
    // Variabel untuk perhitungan reservasi
    private long jumlahHari;
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
//    Method untuk form data reservasi
    public static long countDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    @FXML
    private void datePicker(ActionEvent event) {
        // Mendapatkan value date
        
        DatePicker tanggal = (DatePicker) event.getSource();
        System.out.println(tanggal.getValue().toString());
        
        if (datePicker_tanggalCheckIn.getValue() == null || datePicker_tanggalCheckOut.getValue() == null) { return; }        
        
        kalkulasiLamaPesanan();
        
    }
    
    private void hitungLamaPesanan(DatePicker tanggalCheckIn, DatePicker tanggalCheckOut) {
        // kalkulasi untuk berapa hari pelanggan memesan kamar
        System.out.println(jumlahHari);
        try {
            // Mengambil tanggal check-in dari field
            String[] waktuCheckIn_String = tanggalCheckIn.getValue().toString().split("-");
            int[] waktuCheckIn_Integer = new int[3];
            for (int i = 0; i < waktuCheckIn_String.length; i++) {
                waktuCheckIn_Integer[i] = Integer.parseInt(waktuCheckIn_String[i]);
            }

            // Mengambil tanggal check-out dari field
            String[] waktuCheckOut_String = tanggalCheckOut.getValue().toString().split("-");
            int[] waktuCheckOut_Integer = new int[3];
            for (int i = 0; i < waktuCheckOut_String.length; i++) {
                waktuCheckOut_Integer[i] = Integer.parseInt(waktuCheckOut_String[i]);
            }

            LocalDate localDate_tanggalCheckIn = LocalDate.of(waktuCheckIn_Integer[0], waktuCheckIn_Integer[1], waktuCheckIn_Integer[2]);
            LocalDate localDate_tanggalCheckOut = LocalDate.of(waktuCheckOut_Integer[0], waktuCheckOut_Integer[1], waktuCheckOut_Integer[2]);

            // Menghitung hari
            jumlahHari = countDaysBetween(localDate_tanggalCheckIn, localDate_tanggalCheckOut);
            
            System.out.println(jumlahHari);
        } catch (Exception e) {
        }
    }
    
    public void kalkulasiLamaPesanan() {
        // Mengkalkulasikan lama pesanan dengan harga kamar
        try {
            
            harga = getHargaBase();
            
            if (DataContainerBetweenScene.getVarian().equals("Termasuk sarapan")) {
                harga += 50;
            }
            
            hitungLamaPesanan(datePicker_tanggalCheckIn, datePicker_tanggalCheckOut);


            harga = (int) (harga * jumlahHari);

            text_beforeDiscount.setText("Rp. " + stringFormatterSeparator.format(harga*2).replace(",", ".") + ".000");
            label_hargaAkhir.setText("Rp. " + stringFormatterSeparator.format(harga).replace(",", ".") + ".000");
            System.out.println(harga);

        } catch (Exception e) {
            return;
        }
    }
    
    private int getHargaBase() {
        // Mengatur base harga
        switch (DataContainerBetweenScene.getData()) {
            case "Premium": return 350;
            case "Deluxe": return 525;
            default: return 275;
        }
    }

    
    // Menyiapkan variabel untuk koneksi ke database
    private Connection connect = ConnectionDB.ConnectDb();
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    private String IDPesanan() {
        // Membuat kombinasi acak untuk ID pesanan
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&-=+?";

        SecureRandom random = new SecureRandom();
        int panjangID = 8;

        return random.ints(panjangID, 0, chars.length())
                     .mapToObj(chars::charAt)
                     .map(Object::toString)
                     .collect(Collectors.joining());
    }
    
    private String nomorKamar() {
        // Pencarian kamar yang tersedia mulai dari 1 hingga 10 (jika kamar tidak tersedia dalam rentan waktu yang diminta maka akan menampilkan error kamar penuh)
        LocalDate reqCheckIn = datePicker_tanggalCheckIn.getValue();
        LocalDate reqCheckOut = datePicker_tanggalCheckOut.getValue();
        alertMessage alert = new alertMessage();

        if (reqCheckIn == null || reqCheckOut == null) {
            alert.errorMessage("Silakan pilih tanggal check-in dan check-out terlebih dahulu.");
            return null;
        }

        // SQL untuk mengecek overlap:
        // Ambil jumlah reservasi untuk kamar X yang tanggalnya overlap
        String sqlOverlap = ""
          + "SELECT COUNT(*) AS jml "
          + "FROM customer "
          + "WHERE nomor_kamar = ? AND tipe_kamar = ?"
          + "  AND NOT (tanggal_check_out <= ? OR tanggal_check_in >= ?)";

        try (PreparedStatement ps = connect.prepareStatement(sqlOverlap)) {
            for (int i = 1; i <= 10; i++) {
                ps.setString(1, Integer.toString(i));
                ps.setString(2, DataContainerBetweenScene.getData());
                ps.setString(3, reqCheckIn.toString());   // out <= reqIn  → no overlap
                ps.setString(4, reqCheckOut.toString());  // in  >= reqOut → no overlap

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt("jml") == 0) {
                        // kamar i bebas bentrok
                        return Integer.toString(i);
                    }
                }
                // kalau jml > 0 → bentrok, lanjut ke kamar berikutnya
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert.errorMessage("Terjadi kesalahan saat pengecekan kamar.");
            return null;
        }

        // semua kamar 1–10 bentrok
        System.out.println("semua kamar penuh di tanggal tersebut");
        alert.errorMessage("Maaf, semua kamar penuh di tanggal tersebut.");
        return null;
    }
    
    // menginisasi object gmailer untuk mengirim pesan melalui email
    private GMailer mailController = new GMailer();

//    Method untuk memesan kamar ketika menekan tombol pesan
    @FXML
    private void pesanKamar() { 
        String ID = IDPesanan(); // mengambil id secara random
        try {
            
            alertMessage alert = new alertMessage();

            // Mengecek apakah ada kolom yang kosong
            if(textField_namaPemesan.getText().isEmpty() || textField_email.getText().isEmpty() || textField_nomorHandphone.getText().isEmpty() 
                    || datePicker_tanggalCheckIn.getValue() == null || datePicker_tanggalCheckOut.getValue() == null) {
                alert.errorMessage("Isi semua kolom yang tersedia sesuai ketentuan");
            } else if (nomorKamar() != null){
                String insertData = "INSERT INTO customer " 
                        + "(nama, email, nomor_telepon, tanggal_check_in, tanggal_check_out, tipe_kamar, nomor_kamar, status, id_pesanan, varian_kamar, harga)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, textField_namaPemesan.getText());
                prepare.setString(2, textField_email.getText());
                prepare.setString(3, textField_nomorHandphone.getText());
                prepare.setString(4, datePicker_tanggalCheckIn.getValue().toString());
                prepare.setString(5, datePicker_tanggalCheckOut.getValue().toString());
                prepare.setString(6, DataContainerBetweenScene.getData());
                prepare.setString(7, nomorKamar() );
                prepare.setString(8, "Belum Dibayar");
                prepare.setString(9, ID );
                prepare.setString(10, DataContainerBetweenScene.getVarian());
                prepare.setString(11, Integer.toString(harga));
                
                prepare.executeUpdate();
                
                mailController.sendMail("(Santara) Pesanan Anda Berhasil Dibuat!", "Data pesanan kamar anda berhasil masuk!\nMohon simpan ID Pesanan Anda: " + ID + "\n\nSelanjutnya konfirmasi pesanan anda secara offline pada resepsionis kami di tanggal check in anda ( " + datePicker_tanggalCheckIn.getValue().toString() + " )\nJika anda tidak datang sampai H+1 tanggal check in maka pesanan hangus secara otomatis" , textField_email.getText());
                alert.successMessage("Data pesanan anda berhasil disimpan\nKami telah mengirim Email untuk ID pesanan anda\nSelanjutnya silahkan lakukan check in \ndan bayar pesanan sesuai tanggal yang anda tentukan");
                
                pesanKamarClearForm();

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void pesanKamarClearForm() {
        textField_namaPemesan.setText("");
        textField_email.setText("");
        textField_nomorHandphone.setText("");
        datePicker_tanggalCheckIn.getEditor().clear();
        datePicker_tanggalCheckOut.getEditor().clear();
        harga = 0;
    }

}
