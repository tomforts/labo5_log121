module org.example.labo5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.labo5 to javafx.fxml;
    exports org.example.labo5;
}