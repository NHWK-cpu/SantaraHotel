package com.mycompany.santarahoteladmin;

import javafx.application.Platform;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;

/**
 * Class notifikasi alert khusus yang menampilkan dialog pemblokiran dengan efek overlay
 * Menggunakan komponen Swing untuk menampilkan peringatan di atas aplikasi JavaFX
 */

public class alertMessage {
    // Komponen Static Swing untuk overlay dan dialog
    private static JWindow overlayWindow; // Fullscreen overlay window
    private static JDialog alertDialog; // Alert dialog box

    /**
     * Menampilkan dialog pesan kesalahan dengan ikon merah
     * @param message adalah pesan kesalahan yang akan ditampilkan
     */
    public void errorMessage(String message) {
        showBlockingAlert(message, "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Menampilkan dialog pesan kesalahan dengan ikon info
     * @param message adalah pesan kesalahan yang akan ditampilkan
     */
    public void successMessage(String message) {
        showBlockingAlert(message, "Success Message", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Method private helper untuk membuat dan menampilkan dialog notifikasi
    private void showBlockingAlert(String message, String title, int messageType) {
        try{
            Stage mainStage = App.getStage();
        // 1. Dapatkan bounds stage utama di thread JavaFX
            Rectangle bounds = new Rectangle(
                (int) mainStage.getX(),
                (int) mainStage.getY(),
                (int) mainStage.getWidth(),
                (int) mainStage.getHeight()
            );

            SwingUtilities.invokeLater(() -> {
                // 2. Buat fullscreen overlay putih
                overlayWindow = new JWindow();
                overlayWindow.setBackground(new Color(255, 255, 255));
                overlayWindow.setBounds(bounds);
                overlayWindow.setAlwaysOnTop(true);
                overlayWindow.setAlwaysOnTop(false);

                // 3. Buat dialog alert
                JOptionPane pane = new JOptionPane(
                    message,
                    messageType,
                    JOptionPane.DEFAULT_OPTION
                );

                alertDialog = pane.createDialog(overlayWindow, title);
                alertDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                alertDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                // 4. Posisikan dialog di tengah overlay
                alertDialog.setLocationRelativeTo(overlayWindow);

                // 5. Handler saat dialog ditutup
                alertDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        overlayWindow.dispose();
                        Platform.runLater(() -> {
                            mainStage.toFront();
                            mainStage.requestFocus();
                        });
                    }
                });

                // 6. Tampilkan secara berurutan
                overlayWindow.setVisible(true);
                alertDialog.setVisible(true);
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
     }
}

