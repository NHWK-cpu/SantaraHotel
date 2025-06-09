
package com.mycompany.santarahoteladmin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class untuk menangani animasi slide sidebar
 * 
 * Menyediakan efek animasi ketika menampilkan atau menyembunyikan sidebar navigasi
 */

public class navslider {
    
    /**
     * Method untuk menampilkan/menyembunyikan navbar dengan efek slide
     * @param sidenavbar Pane yang akan di-animasikan (sidebar/navbar)
     */
    public static void slide_navbar(Pane sidenavbar) {
        if (sidenavbar.isVisible()) { // Cek apakah navbar sedang aktif
//            hide sidebar (slide ke kiri)
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                // Buat animasi translate (pergerakan)
                TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), sidenavbar);
                
                slideIn.setFromX(0); // Posisi awal (tidak bergeser)
                slideIn.setToX(-400); // Posisi akhir (geser 400px ke kiri)
                slideIn.play(); // Mulai animasi
                
                // Setelah animasi selesai
                slideIn.setOnFinished(e -> {
                    sidenavbar.setVisible(false); // Sembunyikan sidebar
                    sidenavbar.setDisable(true); // Nonaktifkan interaksi
                    sidenavbar.setPrefWidth(0); // Set lebar menjadi 0
                });

            }));

            timeline.setCycleCount(1); // Animasi hanya dijalankan 1 kali
            timeline.play(); // Mulai timeline
            
        } else { // Animasi untuk menampilkan sidebar (slide dari kiri)
//           show navbar

            // Set properti navbar sebelum animasi
            sidenavbar.setVisible(true); // Tampilkan sidebar (transparan)
            sidenavbar.setDisable(false); // Aktifkan interaksi
            sidenavbar.setPrefWidth(328); // Set lebar sidebar ke ukuran normal

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                // Buat animasi translate (pergerakan)
                TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), sidenavbar);
                
                slideIn.setFromX(-400); // Posisi awal (400px di kiri layar)
                slideIn.setToX(0); // Posisi akhir (posisi normal)
                slideIn.play(); // Mulai animasi
                

            }));

            timeline.setCycleCount(1); // Animasi hanya dijalankan 1 kali
            timeline.play(); // Mulai timeline
        }
    }
    
}
