
package com.mycompany.santarahotel;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/*
* Controller sederhana untuk menghandle tugas navigasi perpindahan page
* serta tugas untuk menampilkan komponen ketika tombol ditekan
*/

public class homeController {
    @FXML
    private Pane Background_container;
    private SwitchScene sceneHandler = new SwitchScene();
    
    @FXML
    private void initialize() {
    }
    
    private ReservasiController rsvpControl = new ReservasiController();
    
    @FXML
    private MenuItem Deluxe;

    @FXML
    private MenuItem Premium;

    @FXML
    private MenuItem Standard;
    
    @FXML
    private void switchToReservasi(ActionEvent event) throws IOException {
        sceneHandler.switchSceneReservasi(event, Standard, Premium, Deluxe);
    }
    
    @FXML
    private void switchToCekPesanan(ActionEvent event) throws IOException {
        sceneHandler.switchSceneCekPesanan(event);
    }
    
    @FXML
    private void swithToAboutCompany (ActionEvent event) throws IOException {
        sceneHandler.switchSceneAboutCompany(event);
    }
    
    @FXML
    private void switchToBiodata (ActionEvent event) throws IOException {
        sceneHandler.switchSceneBiodata(event);
    }
    
    @FXML
    private StackPane pane_guidance;
    
    @FXML
    private void showGuidance(ActionEvent event) {
        System.out.println("Show");
        pane_guidance.setVisible(true);
        pane_guidance.setDisable(false);
    }
    
    @FXML
    private void hideGuidance(ActionEvent event) {
        pane_guidance.setVisible(false);
        pane_guidance.setDisable(true);
    }
    
}
