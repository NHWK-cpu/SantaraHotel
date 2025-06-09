package com.mycompany.santarahotel;

// Import library JavaFX untuk aplikasi GUI sebagai file utama
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App utama untuk aplikasi Santara.
 * Ini adalah titik masuk (entry point) utama ketika aplikasi dijalankan.
 */
public class App extends Application {

    // Scene global yang dapat digunakan untuk mengganti tampilan antar halaman
    private static Scene scene;

    // Stage global untuk mengatur window aplikasi
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        // Membuat scene dari file FXML 'homePage.fxml' dengan ukuran 1440x810
        scene = new Scene(loadFXML("homePage"), 1440, 810);

        // Simpan stage utama ke variabel global
        this.stage = stage;

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
        launch();
    }
}
