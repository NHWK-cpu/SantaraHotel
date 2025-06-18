
package com.mycompany.santarahotel;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

/**
 * Class ini digunakan untuk mengirim email menggunakan SMTP Gmail.
 * Menggunakan JavaMail API.
 */

public class GMailer {
    // Method untuk mengirim email
    // Parameter:
    // - subject : subject/judul email
    // - messageString : isi pesan email
    // - recipient  : alamat email tujuan
    public void sendMail(String subject, String messageString, String recipient) {
        Dotenv dotenv = Dotenv.configure().directory(".\\src\\main\\java\\com\\mycompany\\santarahotel\\").load(); // Untuk menyambungkan file .env 
        
        final String username = dotenv.get("GMAIL_USER"); // Email yang digunakan sebagai pengirim
        final String password = dotenv.get("GMAIL_AUTH"); // Password autentikasi

        // Konfigurasi SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Aktifkan autentikasi SMTP
        props.put("mail.smtp.starttls.enable", "true");  // Aktifkan autentikasi SMTP
        props.put("mail.smtp.host", "smtp.gmail.com"); // Host SMTP Gmail
        props.put("mail.smtp.port", "587"); // Port untuk TLS

        // Session dengan authentikasi email pengirim
        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                // Kredensial email pengirim (gunakan app password untuk Gmail)
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Buat pesan email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(dotenv.get("GMAIL_USER")));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipient)
            );
            message.setSubject(subject);
            message.setText(messageString);

            // Kirim email
            Transport.send(message);

            System.out.println("Email berhasil dikirim.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
