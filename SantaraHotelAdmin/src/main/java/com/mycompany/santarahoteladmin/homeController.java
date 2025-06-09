/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author MyBook Hype AMD
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
