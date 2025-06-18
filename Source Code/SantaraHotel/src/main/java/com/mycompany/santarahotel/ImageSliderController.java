
package com.mycompany.santarahotel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

// Kelas controller untuk animasi image slider otomatis (berganti gambar secara berkala)
public class ImageSliderController{
    
    // Komponen tampilan yang digunakan untuk menampilkan gambar
    private ImageView imageView;
    
    // Container tempat imageView berada
    private Pane pane_container;
    
    // List yang menyimpan semua gambar yang akan ditampilkan
    private final List<Image> images = new ArrayList<>();
    
    // Index gambar saat ini yang sedang ditampilkan
    private int currentIndex = 0;
    
    // Method utama untuk menjalankan slider gambar
    public void slideImage(ImageView imageViewComponent, Pane Container, List<Image> image_list, double width, double height) {
        // Menyimpan komponen dan data yang dibutuhkan ke variabel class
        setVariable(imageViewComponent, Container, image_list);
        
        // Mengatur ukuran clip agar image tidak keluar dari area yang ditentukan
        pane_container.setClip(new Rectangle(width,height));
        
        // Menampilkan gambar pertama dari list
        imageView.setImage(images.get(currentIndex));

        // Timeline digunakan untuk mengganti gambar setiap 3 detik
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            // Ganti ke index gambar berikutnya (looping kembali ke 0 jika sudah akhir)
            currentIndex = (currentIndex + 1) % images.size();
            Image nextImage = images.get(currentIndex);

            // Efek transisi keluar ke kiri
            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), imageView);
            slideOut.setFromX(0); // Mulai dari posisi awal
            slideOut.setToX(-600); // Bergerak ke kiri sejauh 600px
            
            // Setelah transisi keluar selesai, ganti gambar dan lakukan transisi masuk
            slideOut.setOnFinished(e -> {
                imageView.setTranslateX(600); // Posisi imageView di kanan sebelum masuk
                imageView.setImage(nextImage); // Ganti ke gambar berikutnya

                TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), imageView);
                slideIn.setFromX(600);
                slideIn.setToX(0);
                slideIn.play(); // Mainkan transisi masuk
            });

            slideOut.play(); // Mainkan transisi keluar
        }));

        timeline.setCycleCount(Animation.INDEFINITE); // Timeline diatur untuk berulang terus menerus
        timeline.play(); // Mulai animasi

    }
    
    // Method private untuk menyimpan parameter ke dalam variabel class
    private void setVariable(ImageView imageViewComponent, Pane Container, List<Image> image_list) {
        imageView = imageViewComponent; pane_container = Container; images.addAll(image_list);
    }
    
}
