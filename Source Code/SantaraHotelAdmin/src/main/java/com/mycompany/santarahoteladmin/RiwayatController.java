package com.mycompany.santarahoteladmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Controller untuk halaman riwayat/histori pemesanan admin
 */

public class RiwayatController {
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
    
    
// Fungsi utama riwayat
    
    // Format untuk menampilkan angka dengan separator
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
    // Alert untuk menampilkan pesan error/success
    private alertMessage alert = new alertMessage();
    
    // Komponen UI untuk pencarian data
    @FXML
    private ComboBox comboBox_SearchIndex;
    @FXML
    private ComboBox comboBox_statusPesanan;
    @FXML
    private ComboBox comboBox_tipeKamar;
    @FXML
    private DatePicker datePicker_tanggalCheckIn;
    @FXML
    private DatePicker datePicker_tanggalCheckOut;
    @FXML
    private TableView<Customer> table_dataDisplay;
    @FXML
    private TextField textField_ID;
    @FXML
    private TextField textField_email;
    @FXML
    private TextField textField_nama;
    
    // Kolom-kolom tabel
    @FXML
    private TableColumn<Customer, String> colNama;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private TableColumn<Customer, String> colNoTelp;
    @FXML
    private TableColumn<Customer, String> colCheckIn;
    @FXML
    private TableColumn<Customer, String> colCheckOut;
    @FXML
    private TableColumn<Customer, String> colTipe;
    @FXML
    private TableColumn<Customer, String> colNoKamar;
    @FXML
    private TableColumn<Customer, String> colStatus;
    @FXML
    private TableColumn<Customer, String> colId;
    @FXML
    private TableColumn<Customer, String> colVarian;
    @FXML
    private TableColumn<Customer, String> colHarga;
    
    // Inisialisasi komponen saat controller dimuat
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
        
        
        // Inisialisasi kolom tabel dengan properti Customer
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNoTelp.setCellValueFactory(new PropertyValueFactory<>("noTelp"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colTipe.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        colNoKamar.setCellValueFactory(new PropertyValueFactory<>("noKamar"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVarian.setCellValueFactory(new PropertyValueFactory<>("varian"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
    }
    
    /**
     * Method untuk mengubah tampilan form pencarian berdasarkan pilihan metode pencarian
     */
    @FXML
    private void searchBy(ActionEvent event) {
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
    
    
    /**
     * Method untuk mencari data pesanan berdasarkan parameter yang dipilih
     */
    @FXML
    private void cariData(ActionEvent event) {
        String stringQuery = "SELECT * FROM `customer` WHERE 1=1";
        Connection connect = ConnectionDB.ConnectDb();
        
        // Pencarian berdasarkan ID
        if (!(textField_ID.isDisable() && textField_ID.getText().isEmpty())) {
            // cari berdasarkan id
            
            stringQuery += " AND id_pesanan = ?";
            
            try {
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                prepare.setString(1, textField_ID.getText());
                
                ResultSet result = prepare.executeQuery();
                ObservableList<Customer> data = FXCollections.observableArrayList();
                
                // Memproses hasil query
                while (result.next()) {
                    String nama = result.getString("nama");
                    String email = result.getString("email");
                    String no_telepon = result.getString("nomor_telepon");
                    String tgl_checkIn = result.getString("tanggal_check_in");
                    String tgl_checkOut = result.getString("tanggal_check_out");
                    String tipe = result.getString("tipe_kamar");
                    String no_kamar = result.getString("nomor_kamar");
                    String status = result.getString("status");
                    String id = result.getString("id_pesanan");
                    String varian = result.getString("varian_kamar");
                    String harga = "Rp. " + stringFormatterSeparator.format(Integer.parseInt(result.getString("harga"))).replace(",", ".") + ".000";
                    
                    // Membuat objek Customer dari hasil query
                    Customer customer = new Customer(nama, email, harga, no_telepon, tgl_checkIn, tgl_checkOut, tipe, no_kamar, status, id, varian);
                    data.add(customer);
                }
                table_dataDisplay.setItems(data);  // Menampilkan data di TableView
                
            } catch (Exception e) {
                System.out.println(e);
            }
        
        // Pencarian berdasarkan kriteria lain
        } else if (!(textField_email.getText().isEmpty()) || !(textField_nama.getText().isEmpty()) || datePicker_tanggalCheckIn.getValue() != null 
                || datePicker_tanggalCheckOut.getValue() != null || !(comboBox_statusPesanan.getSelectionModel().isEmpty()) || !(comboBox_tipeKamar.getSelectionModel().isEmpty())) {
            // cari berdasarkan kriteria
            List<Object> parameters = new ArrayList<>();
            
        // Membangun query dinamis berdasarkan parameter yang diisi
            if (!(textField_nama.getText().isEmpty())) {
                stringQuery += " AND nama LIKE ?";
                parameters.add("%" + textField_nama.getText() + "%");
            }
            
            if (!(textField_email.getText().isEmpty())) {
                stringQuery += " AND email = ?";
                parameters.add(textField_email.getText());
            }
            
            if (datePicker_tanggalCheckIn.getValue() != null) {
                stringQuery += " AND tanggal_check_in = ?";
                parameters.add(java.sql.Date.valueOf(datePicker_tanggalCheckIn.getValue()));
            }
            
            if (datePicker_tanggalCheckOut.getValue() != null) {
                stringQuery += " AND tanggal_check_out = ?";
                parameters.add(java.sql.Date.valueOf(datePicker_tanggalCheckOut.getValue()));
            }
            
            if (!(comboBox_tipeKamar.getSelectionModel().isEmpty())) {
                stringQuery += " AND tipe_kamar = ?";
                parameters.add(comboBox_tipeKamar.getValue().toString());
            }
            
            if (!(comboBox_statusPesanan.getSelectionModel().isEmpty())) {
                stringQuery += " AND status = ?";
                parameters.add(comboBox_statusPesanan.getValue().toString());
            }
            
            try {
                PreparedStatement prepare = connect.prepareStatement(stringQuery);
                
                // Mengisi parameter query
                for (int i = 0; i < parameters.size(); i++) {
                    prepare.setObject(i +1, parameters.get(i));
                }
                
                ResultSet result = prepare.executeQuery();
                ObservableList<Customer> data = FXCollections.observableArrayList();
                
                // Memproses hasil query
                while (result.next()) {
                    String nama = result.getString("nama");
                    String email = result.getString("email");
                    String no_telepon = result.getString("nomor_telepon");
                    String tgl_checkIn = result.getString("tanggal_check_in");
                    String tgl_checkOut = result.getString("tanggal_check_out");
                    String tipe = result.getString("tipe_kamar");
                    String no_kamar = result.getString("nomor_kamar");
                    String status = result.getString("status");
                    String id = result.getString("id_pesanan");
                    String varian = result.getString("varian_kamar");
                    String harga = "Rp. " + stringFormatterSeparator.format(Integer.parseInt(result.getString("harga"))).replace(",", ".") + ".000";
                    
                    // Membuat objek Customer dari hasil query
                    Customer customer = new Customer(nama, email, harga, no_telepon, tgl_checkIn, tgl_checkOut, tipe, no_kamar, status, id, varian);
                    data.add(customer);
                }
                table_dataDisplay.setItems(data);  // Menampilkan data di TableView
            } catch (Exception e) {
                System.out.println(e);
            }
        } 
    }
    
    /**
     * Method untuk mereset form pencarian
     */
    @FXML
    private void clearIndex(ActionEvent event) throws IOException {
        switchToHistoriPesanan(event);
    }
    
    /**
     * Method untuk mengekspor seluruh data tabel database ke Excel
     */
    @FXML
    private void downloadEntireTable(ActionEvent event) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Customer");
        
        // Membuat header Excel
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nama");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("No Telepon");
        header.createCell(3).setCellValue("Tanggal Check In");
        header.createCell(4).setCellValue("Tanggal Check Out");
        header.createCell(5).setCellValue("Tipe Kamar");
        header.createCell(6).setCellValue("Nomor Kamar");
        header.createCell(7).setCellValue("Status");
        header.createCell(8).setCellValue("ID Pesanan");
        header.createCell(9).setCellValue("Varian");
        header.createCell(10).setCellValue("Harga");
        
        // Query untuk mengambil semua data
        String stringQuery = "SELECT * FROM `customer`";
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(stringQuery);
            ResultSet result = prepare.executeQuery();
            int index = 1;
            
            // Mengisi data ke Excel
            while(result.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(result.getString("nama"));
                row.createCell(1).setCellValue(result.getString("email"));
                row.createCell(2).setCellValue(result.getString("nomor_telepon"));
                row.createCell(3).setCellValue(result.getString("tanggal_check_in"));
                row.createCell(4).setCellValue(result.getString("tanggal_check_out"));
                row.createCell(5).setCellValue(result.getString("tipe_kamar"));
                row.createCell(6).setCellValue(result.getString("nomor_kamar"));
                row.createCell(7).setCellValue(result.getString("status"));
                row.createCell(8).setCellValue(result.getString("id_pesanan"));
                row.createCell(9).setCellValue(result.getString("varian_kamar"));
                row.createCell(10).setCellValue("Rp. " + stringFormatterSeparator.format(Integer.parseInt(result.getString("harga"))).replace(",", ".") + ".000");
                index++;
            }
        
            // Menyimpan file Excel
            String fileName = LocalDate.now() + " DataLengkap_TabelPesanan.xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        
        } catch (Exception ex) {
            Logger.getLogger(RiwayatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        alert.successMessage("Seluruh Data Tabel Berhasil Diekspor");
    }
    
    /**
     * Method untuk mengekspor data yang sudah difilter dan tambil pada TableView ke Excel
     */
    @FXML
    private void downloadFilteredTable(ActionEvent event) {
        // Validasi jika tabel kosong
        if (table_dataDisplay.getItems().isEmpty()) {
            alert.errorMessage("Belum ada data yang terfilter");
            return;
        }

        String fileName = LocalDate.now() + " DataFiltered_TabelPesanan.xlsx";

        try {
            // Menggunakan ExcelExporter untuk mengekspor data
            ExcelExporter.exportTableViewToExcel(table_dataDisplay, fileName);
        } catch (IOException ex) {
            Logger.getLogger(RiwayatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        alert.successMessage("Data Tabel Terfilter Berhasil Diekspor");
    }
    
    /**
     * Method untuk mengimpor data dari Excel
     */
    @FXML
    private void importData(ActionEvent event) {
        // Membuka dialog pemilihan file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));
        File selectedFile = fileChooser.showOpenDialog(App.getStage());

        if (selectedFile != null) {
            // Jika file terpilih, proses untuk memasukkan data ke database
            processExcelFile(selectedFile);
        }
    }
    
    
    /**
     * Method untuk memproses file Excel dan mengimpor datanya
     * @param file File Excel yang akan diproses
     */
    private void processExcelFile(File file) {

        try (Connection connection = ConnectionDB.ConnectDb()) {
            // Membaca file Excel
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Query untuk insert data
            String query = "INSERT INTO `customer` (nama, email, nomor_telepon, tanggal_check_in, tanggal_check_out, tipe_kamar, nomor_kamar, status, id_pesanan, varian_kamar, harga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            // Memproses setiap baris data
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Lewati header

                // Membaca data dari Excel
                String nama = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();
                String no_telepon = row.getCell(2).getStringCellValue();
                String tgl_check_in = row.getCell(3).getStringCellValue();
                String tgl_check_out = row.getCell(4).getStringCellValue();
                String tipe_kamar = row.getCell(5).getStringCellValue();
                int no_kamar = Integer.parseInt(row.getCell(6).getStringCellValue());
                String status = row.getCell(7).getStringCellValue();
                String id = row.getCell(8).getStringCellValue();
                String varian = row.getCell(9).getStringCellValue();
                int hargaInt = getIntHarga(row);

                // Cek apakah data dengan ID yang sama sudah ada
                PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM `customer` WHERE id_pesanan = ?");
                checkStmt.setString(1, id);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                // Jika data belum ada, lakukan insert
                if (count == 0) {
                    // Insert data
                    statement.setString(1, nama);
                    statement.setString(2, email);
                    statement.setString(3, no_telepon);
                    statement.setString(4, tgl_check_in);
                    statement.setString(5, tgl_check_out);
                    statement.setString(6, tipe_kamar);
                    statement.setInt(7, no_kamar);
                    statement.setString(8, status);
                    statement.setString(9, id);
                    statement.setString(10, varian);
                    statement.setInt(11, hargaInt);
                    statement.executeUpdate();
                } else {
                    System.out.println("Data ID " + id + " sudah ada. Lewati.");
                }
            }

            workbook.close();
            inputStream.close();

            alert.successMessage("Data berhasil diimpor");

        } catch (Exception e) {
            e.printStackTrace();
            alert.errorMessage("Terjadi kesalahan saat mengimpor data.");
        }
    }
    
    /**
     * Method untuk mengkonversi format harga dari string ke integer
     * @param row Baris data dari Excel
     * @return Harga dalam bentuk integer
     */
    private int getIntHarga(Row row) {
        String hargaString = (row.getCell(10).getStringCellValue()).replace(".", "").trim();
        int hargaInt = Integer.parseInt(hargaString.substring(3, hargaString.length() - 3));
        return hargaInt;
    }
}
