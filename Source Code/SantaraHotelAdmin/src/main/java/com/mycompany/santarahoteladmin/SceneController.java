package com.mycompany.santarahoteladmin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller untuk navigasi antar halaman/scene dalam aplikasi admin
 * 
 * Class ini bertanggung jawab untuk menangani perpindahan antar halaman (scene)
 * pada aplikasi admin Santara Hotel.
 */

public class SceneController {
    
    @FXML
    public static void switchSceneKonfirmasiPesanan (ActionEvent event) throws IOException {
        App.setRoot("KonfirmasiPesanan"); // Memuat file FXML KonfirmasiPesanan.fxml
    }
    
    @FXML
    public static void switchSceneHistoriPesanan (ActionEvent event) throws IOException {
        App.setRoot("historiPesanan"); // Memuat file FXML historiPesanan.fxml
    }
    
    @FXML
    public static void switchSceneLaporanPendapatan (ActionEvent event) throws IOException {
        App.setRoot("LaporanPendapatan"); // Memuat file FXML LaporanPendapatan.fxml
    }
}
