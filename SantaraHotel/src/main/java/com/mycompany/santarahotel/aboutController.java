package com.mycompany.santarahotel;

/*
* Class controller ini hanya digunakan untuk navigasi perpindahan page
*/

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class aboutController {
    
    SwitchScene sceneController = new SwitchScene();
    
    @FXML
    private MenuItem Deluxe;

    @FXML
    private MenuItem Premium;

    @FXML
    private MenuItem Standard;
    
    @FXML
    private void switchToReservasi(ActionEvent event) throws IOException {
        sceneController.switchSceneReservasi(event, Standard, Premium, Deluxe);
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
}
