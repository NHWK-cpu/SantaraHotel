module com.mycompany.santarahotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.sql;
    requires java.desktop;
    requires java.base;
    requires jakarta.mail;
    requires io.github.cdimascio.dotenv.java;

    opens com.mycompany.santarahotel to javafx.fxml;
    exports com.mycompany.santarahotel;
}
