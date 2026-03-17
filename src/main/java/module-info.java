module org.example.labo5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.labo5 to javafx.fxml;
    exports org.example.labo5;
}