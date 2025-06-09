package com.mycompany.santarahoteladmin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

public class LaporanPendapatanController {
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
    
    private DecimalFormat stringFormatterSeparator = new DecimalFormat("#,###");
    
    private String queryYear = "YEAR(CURDATE())";
    
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
    private ComboBox comboBox_tahun;
    
    @FXML
    private Label label_tahun;
    
    @FXML
    private void initialize(){
        thisMonthRevenue();
        lastMonthRevenue();
        totalRevenue();
        grafikRevenue();
        initiateComboBox();
        getCurrentYear();
    }
    
    private void getCurrentYear(){
        try {
            Connection connect = ConnectionDB.ConnectDb();
            PreparedStatement prepare = connect.prepareStatement("SELECT YEAR(CURRENT_DATE) AS year");
            ResultSet result = prepare.executeQuery();
            label_tahun.setText(result.getString(1));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void totalRevenue() {
        String query = "SELECT `harga` FROM `customer` WHERE status != 'Belum Dibayar'";
        long bank = 0;
        
        Connection connect = ConnectionDB.ConnectDb();
        try {
            PreparedStatement prepare = connect.prepareStatement(query);

            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                bank += result.getLong("harga");
            }
            
            label_totalPendapatan.setText("Rp. " + stringFormatterSeparator.format(bank).replace(",", ".") + ".000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void thisMonthRevenue() {
        String query = "SELECT `harga` FROM `customer` WHERE `status` != 'Belum Dibayar' AND `tanggal_check_in` >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01') AND `tanggal_check_in` < DATE_FORMAT(DATE_ADD(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m-01');";
        long bank = 0;
        int countPeople = 0;
        
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            
            ResultSet result = prepare.executeQuery();
            
            while(result.next()) {
                bank += result.getLong("harga");
                countPeople++;
            }
            
            label_revenueThisMonth.setText("Rp. " + stringFormatterSeparator.format(bank).replace(",", ".") + ".000");
            label_countPeopleThisMonth.setText(Integer.toString(countPeople) + " Orang");
        } catch (Exception e) {
        }
    }
    
    private void lastMonthRevenue() {
        String query = "SELECT `harga` FROM `customer` WHERE `status` != 'Belum Dibayar' AND `tanggal_check_in` >= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m-01') AND `tanggal_check_in` < DATE_FORMAT(CURRENT_DATE, '%Y-%m-01')";
        long bank = 0;
        int countPeople = 0;
        
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            
            ResultSet result = prepare.executeQuery();
            
            while(result.next()) {
                bank += result.getLong("harga");
                countPeople++;
            }
            
            label_revenueLastMonth.setText("Rp. " + stringFormatterSeparator.format(bank).replace(",", ".") + ".000");
            label_countPeopleLastMonth.setText(Integer.toString(countPeople) + " Orang");
        } catch (Exception e) {
        }
    }
    
    @FXML
    private LineChart<String, Number> lineChart_revenueStream;
    
    private void grafikRevenue() {
        lineChart_revenueStream.getData().clear();
        XYChart.Series salesSeries = new XYChart.Series();
        String stringQuery = "SELECT b.bulan, COALESCE(SUM(p.harga), 0) AS total_penjualan FROM bulan_ref b LEFT JOIN customer p ON MONTH(p.tanggal_check_in) = b.bulan AND YEAR(p.tanggal_check_in) = " + queryYear + " AND p.status != 'Belum Dibayar' WHERE b.bulan <= ( SELECT MAX(MONTH(tanggal_check_in)) FROM customer WHERE YEAR(tanggal_check_in) = " + queryYear + " ) GROUP BY b.bulan ORDER BY b.bulan";
        
        Connection connect = ConnectionDB.ConnectDb();
        
        try {
            PreparedStatement prepare = connect.prepareStatement(stringQuery);
            
            ResultSet result = prepare.executeQuery();
            
            while(result.next()) {
                salesSeries.getData().add(new Data(getMonthName(result.getInt("bulan")),result.getInt("total_penjualan")));
            }
        } catch (Exception e) {
        }
        
        lineChart_revenueStream.getData().add(salesSeries);
    }
    
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
    
    private void initiateComboBox() {
        ObservableList<String> tahun = FXCollections.observableArrayList();
        
        Connection connect = ConnectionDB.ConnectDb();
        String query = "SELECT DISTINCT YEAR(tanggal_check_in) AS tahun FROM customer ORDER BY tahun";
        
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            
            while (result.next()) {                
                tahun.add(result.getString("tahun"));
            }
        } catch (Exception e) {
        }
        comboBox_tahun.setItems(tahun);
    }
    
    @FXML
    private void changeChartYear(ActionEvent event) {
        queryYear = ((ComboBox) event.getSource()).getValue().toString();
        label_tahun.setText(queryYear);
        grafikRevenue();
    }
}
