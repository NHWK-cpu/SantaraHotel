
package com.mycompany.santarahoteladmin;

import java.io.IOException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * Class controller untuk menangani navigasi yang dilakukan pada scene home atau landing page saat aplikasi dijalankan pertamakali
 */
public class homeController {
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
    
}
