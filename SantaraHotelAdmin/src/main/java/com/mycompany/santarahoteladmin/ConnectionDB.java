/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.santarahoteladmin;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionDB {
    public static Connection ConnectDb() {
        Dotenv dotenv = Dotenv.configure().directory(".\\src\\main\\java\\com\\mycompany\\santarahotel\\").load(); // Untuk menyambungkan file .env 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dotenv.get("DATABASE_URL"), dotenv.get("DATABASE_USERNAME"), dotenv.get("DATABASE_PASSWORD"));
            System.out.println("Connected to database!");
            return conn;
        } catch (Exception e) {
            System.out.println("Not Connected to database!");
//            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
