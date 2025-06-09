/*
 * Class untuk menghandle pergantian page atau scene antar FXML views
 */
package com.mycompany.santarahotel;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author MyBook Hype AMD
 */
public class SwitchScene {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    
    /**
     * Beralih ke tampilan halaman beranda
     * @param event ActionEvent yang memicu metode ini
     * @throws IOException Jika file FXML tidak dapat dimuat
     */
    @FXML
    public void switchSceneHome (ActionEvent event) throws IOException {
        App.setRoot("homePage");
    }

    // inisiasi variabel untuk menu navigasi pilihan kamar    
    private MenuItem Deluxe;
    private MenuItem Premium;
    private MenuItem Standard;
    
    
    /**
     * Beralih ke page reservasi dan menetapkan jenis kamar yang dipilih
     * @param event ActionEvent yang memicu metode ini
     * @param menuStandard Item komponen menu kamar standar
     * @param menuPremium Item komponen menu kamar premium
     * @param menuDeluxe Item komponen menu kamar Deluxe
     * @throws IOException Jika file FXML tidak dapat dimuat
     */
    @FXML
    public void switchSceneReservasi(ActionEvent event, MenuItem menuStandard, MenuItem menuPremium, MenuItem menuDeluxe) throws IOException {
        Deluxe = menuDeluxe; Premium = menuPremium; Standard = menuStandard;
        
        // Menentukan tipe kamar mana yang dipilih dan simpan dalam wadah data
        if (event.getSource() == Standard) {
            DataContainerBetweenScene.setData("Standar");
        }else if (event.getSource() == Premium) {
            DataContainerBetweenScene.setData("Premium");
        } else if (event.getSource() == Deluxe) {
            DataContainerBetweenScene.setData("Deluxe");
        }
        
        App.setRoot("reservasi");
    }
    
    
    // Inisiasi button untuk pemilihan opsi sarapan
    private Button button_tidakTermasukSarapan;
    private Button button_termasukSarapan;
    
    
    /**
     * Beralih ke formulir reservasi dan menetapkan preferensi sarapan
     * @param event ActionEvent yang memicu metode ini
     * @param tidakTermasukSarapan Tombol untuk opsi “tanpa sarapan”
     * @param termasukSarapan Tombol untuk pilihan “dengan sarapan”
     * @throws IOException Jika file FXML tidak dapat dimuat
     */
    @FXML
    public void switchSceneReservasi_IsiData(ActionEvent event, Button tidakTermasukSarapan, Button termasukSarapan) throws IOException {
        button_tidakTermasukSarapan = tidakTermasukSarapan; button_termasukSarapan = termasukSarapan;
        
        if (event.getSource() == button_tidakTermasukSarapan) {
            DataContainerBetweenScene.setVarian("Tidak termasuk sarapan");
        } else if (event.getSource() == button_termasukSarapan) {
            DataContainerBetweenScene.setVarian("Termasuk sarapan");
        }
        
        App.setRoot("reservasi_FormData");
    }
    
    @FXML
    public void switchSceneReservasi_DetailKamar (ActionEvent event) throws IOException {
        App.setRoot("reservasi_DetailKamar");
    }
    
    @FXML
    public void switchSceneCekPesanan(ActionEvent event) throws IOException {
        DataContainerBetweenScene.setSearch(((MenuItem)event.getSource()).getId());
        App.setRoot("cekPesanan");
    }
    
    @FXML
    public void switchSceneAboutCompany (ActionEvent event) throws IOException {
        App.setRoot("aboutCompany");
    }
    
    @FXML
    public void switchSceneBiodata (ActionEvent event) throws IOException {
        App.setRoot("biodata");
    }
    
}
