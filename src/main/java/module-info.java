module org.example.labo5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.labo5 to javafx.fxml;
    exports org.example.labo5;
    exports org.example.labo5.model;
    opens org.example.labo5.model to javafx.fxml;
    exports org.example.labo5.command;
    opens org.example.labo5.command to javafx.fxml;
    exports org.example.labo5.controller;
    opens org.example.labo5.controller to javafx.fxml;
    exports org.example.labo5.view;
    opens org.example.labo5.view to javafx.fxml;
    exports org.example.labo5.observer;
    opens org.example.labo5.observer to javafx.fxml;
    exports org.example.labo5.services;
    opens org.example.labo5.services to javafx.fxml;
}