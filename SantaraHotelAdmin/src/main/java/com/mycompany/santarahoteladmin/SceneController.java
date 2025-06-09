package com.mycompany.santarahoteladmin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SceneController {
    @FXML
    public static void switchSceneKonfirmasiPesanan (ActionEvent event) throws IOException {
        App.setRoot("KonfirmasiPesanan");
    }
    
    @FXML
    public static void switchSceneHistoriPesanan (ActionEvent event) throws IOException {
        App.setRoot("historiPesanan");
    }
    
    @FXML
    public static void switchSceneLaporanPendapatan (ActionEvent event) throws IOException {
        App.setRoot("LaporanPendapatan");
    }
}
