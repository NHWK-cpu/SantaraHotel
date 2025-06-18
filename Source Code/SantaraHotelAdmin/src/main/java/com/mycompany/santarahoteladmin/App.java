package com.mycompany.santarahoteladmin;

// Import dependency yang akan digunakan
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/*
 * File ini akan menjadi pintu utama (main java file) yang menghandle manipulasi window/stage 
 * untuk menampung scene yang akan ditampilkan di layar
*/

public class App extends Application {

    private static Scene scene; // Scene global yang dapat digunakan untuk mengganti tampilan antar halaman
    private static Stage stage; // Stage global untuk mengatur window aplikasi

    @Override
    public void start(Stage stage) throws IOException {
        // Method pertama yang akan dijalankan saat project dieksekusi
        scene = new Scene(loadFXML("homePage"), 1440, 870); // Membuat scene dari file FXML 'homePage.fxml' dengan ukuran 1440x810
        
        this.stage = stage; // Simpan stage utama ke variabel global
        
        // Atur scene utama ke stage dan tampilkan dalam mode fullscreen
        this.stage.setScene(scene);
        this.stage.setFullScreen(true);
        this.stage.show();
    }

    // Mengganti root tampilan scene dengan FXML lain (mengubah page)
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    // Mengembalikan stage utama untuk mengambil referensi kebutuhan stage di controller
    static Stage getStage() {
        return App.stage;
    }

    // Memuat file FXML berdasarkan nama file (tanpa ekstensi)
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // Menjalankan aplikasi JavaFX
        launch(args);
    }

}