package com.mycompany.santarahoteladmin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Controller untuk halaman laporan pendapatan admin
 */

public class LaporanPendapatanController {
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
    
    // Format untuk menampilkan angka dengan separator
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
    // Variabel untuk menyimpan query tahun (default tahun sekarang)
    private String queryYear = "YEAR(CURDATE())";
    
    // Komponen Label untuk menampilkan data
    @FXML
    private Label label_totalPendapatan;
    @FXML
    private Label label_countPeopleLastMonth;
    @FXML
    private Label label_countPeopleThisMonth;
    @FXML
    private Label label_revenueLastMonth;
    @FXML
    private Label label_revenueThisMonth;
    @FXML
    private ComboBox comboBox_tahun; // ComboBox untuk memilih tahun
    @FXML
    private Label label_tahun;
    
    // Inisialisasi komponen saat controller dimuat
    @FXML
    private void initialize(){
        thisMonthRevenue(); // Hitung pendapatan bulan ini
        lastMonthRevenue(); // Hitung pendapatan bulan lalu
        totalRevenue(); // Hitung total pendapatan
        grafikRevenue(); // Buat grafik pendapatan
        initiateComboBox(); // Isi ComboBox tahun
        getCurrentYear(); // Ambil tahun sekarang
    }
    
// Method untuk mendapatkan tahun sekarang
    private void getCurrentYear(){
        try {
            int tahunSekarang = LocalDate.now().getYear();
            label_tahun.setText(Integer.toString(tahunSekarang));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Method untuk menghitung total pendapatan keseluruhan dari semua transaksi di database
     */
    private void totalRevenue() {
        String query = "SELECT `harga` FROM `customer` WHERE status != 'Belum Dibayar'";
        long bank = 0; // Variabel untuk menyimpan total
        
        Connection connect = ConnectionDB.ConnectDb();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);

            // Jumlahkan semua pendapatan
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                bank += result.getLong("harga");
            }
            
            // Format dan tampilkan hasil
            label_totalPendapatan.setText("Rp. " + stringFormatterSeparator.format(bank).replace(",", ".") + ".000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method untuk menghitung pendapatan bulan ini
     */
    private void thisMonthRevenue() {
        // Query untuk pendapatan bulan berjalan
        String query = "SELECT `harga` FROM `customer` WHERE `status` != 'Belum Dibayar' AND `tanggal_check_in` >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01') AND `tanggal_check_in` < DATE_FORMAT(DATE_ADD(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m-01');";
        long bank = 0; // Total pendapatan
        int countPeople = 0; // Jumlah tamu yang check-in atau membayar
        
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            
            ResultSet result = prepare.executeQuery();
            
            // Hitung jumlah tamu dan total pendapatan
            while(result.next()) {
                bank += result.getLong("harga");
                countPeople++;
            }
            
            // Format dan tampilkan hasil
            label_revenueThisMonth.setText("Rp. " + stringFormatterSeparator.format(bank).replace(",", ".") + ".000");
            label_countPeopleThisMonth.setText(Integer.toString(countPeople) + " Orang");
        } catch (Exception e) {
        }
    }
    
    /**
     * Method untuk menghitung pendapatan bulan lalu
     */
    private void lastMonthRevenue() {
        // Query untuk pendapatan bulan sebelumnya
        String query = "SELECT `harga` FROM `customer` WHERE `status` != 'Belum Dibayar' AND `tanggal_check_in` >= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m-01') AND `tanggal_check_in` < DATE_FORMAT(CURRENT_DATE, '%Y-%m-01')";
        long bank = 0; // Total pendapatan
        int countPeople = 0; // Jumlah tamu
        
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            
            ResultSet result = prepare.executeQuery();
            
            // Hitung jumlah tamu dan total pendapatan
            while(result.next()) {
                bank += result.getLong("harga");
                countPeople++;
            }
            
            // Format dan tampilkan hasil
            label_revenueLastMonth.setText("Rp. " + stringFormatterSeparator.format(bank).replace(",", ".") + ".000");
            label_countPeopleLastMonth.setText(Integer.toString(countPeople) + " Orang");
        } catch (Exception e) {
        }
    }
    
    // Komponen LineChart untuk menampilkan grafik line chart
    @FXML
    private LineChart<String, Number> lineChart_revenueStream;
    
    /**
     * Method untuk membuat grafik pendapatan
     */
    private void grafikRevenue() {
        lineChart_revenueStream.getData().clear(); // Bersihkan data grafik sebelumnya
        XYChart.Series salesSeries = new XYChart.Series(); // Buat series baru untuk grafik
        
        // Query untuk data grafik per bulan
        String stringQuery = "SELECT b.bulan, COALESCE(SUM(p.harga), 0) AS total_penjualan FROM bulan_ref b LEFT JOIN customer p ON MONTH(p.tanggal_check_in) = b.bulan AND YEAR(p.tanggal_check_in) = " + queryYear + " AND p.status != 'Belum Dibayar' WHERE b.bulan <= ( SELECT MAX(MONTH(tanggal_check_in)) FROM customer WHERE YEAR(tanggal_check_in) = " + queryYear + " ) GROUP BY b.bulan ORDER BY b.bulan";
        
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(stringQuery);
            
            ResultSet result = prepare.executeQuery();
            
            // Tambahkan data ke series grafik
            while(result.next()) {
                salesSeries.getData().add(new Data(getMonthName(result.getInt("bulan")),result.getInt("total_penjualan")));
            }
        } catch (Exception e) {
        }
        
        // Tambahkan series ke grafik
        lineChart_revenueStream.getData().add(salesSeries);
    }
    
    /**
     * Method untuk mengubah angka bulan menjadi nama bulan
     * @param monthNumber angka bulan (1-12)
     * @return nama bulan dalam Bahasa Inggris
     */
    private String getMonthName(int monthNumber) {
        switch (monthNumber) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "Augustust";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                throw new AssertionError();
        }
    }
    
    /**
     * Method untuk mengisi ComboBox dengan tahun-tahun yang tersedia
     */
    private void initiateComboBox() {
        ObservableList<String> tahun = FXCollections.observableArrayList();
        
        Connection connect = ConnectionDB.ConnectDb();
        // Query untuk mendapatkan tahun unik dari database
        String query = "SELECT DISTINCT YEAR(tanggal_check_in) AS tahun FROM customer ORDER BY tahun";
        
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            
            // Tambahkan tahun ke ComboBox
            while (result.next()) {                
                tahun.add(result.getString("tahun"));
            }
        } catch (Exception e) {
        }
        comboBox_tahun.setItems(tahun);
    }
    
    /**
     * Method untuk mengubah grafik berdasarkan tahun yang dipilih
     * @param event event dari ComboBox
     */
    @FXML
    private void changeChartYear(ActionEvent event) {
        // Update tahun yang dipilih
        queryYear = ((ComboBox) event.getSource()).getValue().toString();
        label_tahun.setText(queryYear);
        
        // Perbarui grafik
        grafikRevenue();
    }
}
