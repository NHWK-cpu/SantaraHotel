package com.mycompany.santarahotel;

/**
 * Class ini bertindak sebagai connector ke database 
 * untuk memudahkan interaksi aplikasi dengan database.
 */

// Import library JDBC untuk koneksi ke database
import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;


public class ConnectionDB {

    
    // Method statis untuk menginisialisasi koneksi ke database
    public static Connection ConnectDb() {
        Dotenv dotenv = Dotenv.configure().directory(".\\src\\main\\java\\com\\mycompany\\santarahotel\\").load(); // Untuk menyambungkan file .env 
        try {
            // Memuat driver JDBC untuk MySQL (dibutuhkan agar bisa menggunakan koneksi JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database 'db_santarahotel' di localhost 
            // data username, password, dan url database diambil dari file .env untuk menjaga keamanan
            Connection conn = DriverManager.getConnection(dotenv.get("DATABASE_URL"), dotenv.get("DATABASE_USERNAME"), dotenv.get("DATABASE_PASSWORD"));

            // Menampilkan pesan ke console jika koneksi berhasil
            System.out.println("Connected to database!");
            return conn;

        } catch (Exception e) {
            // Menampilkan notifikasi error ke console jika koneksi gagal
            alertMessage alert = new alertMessage();
            alert.errorMessage(e.toString());

            return null;
        }
    }
}