/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.santarahoteladmin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class navslider {
    
    public static void slide_navbar(Pane sidenavbar) {
        if (sidenavbar.isVisible()) {
//            hide navbar
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {

                TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), sidenavbar);
                slideIn.setFromX(0);
                slideIn.setToX(-400);
                slideIn.play();
                
                slideIn.setOnFinished(e -> {
                    sidenavbar.setVisible(false);
                    sidenavbar.setDisable(true);
                    sidenavbar.setPrefWidth(0);
                });

            }));

            timeline.setCycleCount(1);
            timeline.play();
            
        } else {
//           show navbar

            sidenavbar.setVisible(true);
            sidenavbar.setDisable(false);
            sidenavbar.setPrefWidth(328);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {

                TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), sidenavbar);
                slideIn.setFromX(-400);
                slideIn.setToX(0);
                slideIn.play();
                

            }));

            timeline.setCycleCount(1);
            timeline.play();
        }
    }
    
}
