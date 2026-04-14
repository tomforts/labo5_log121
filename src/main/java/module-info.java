module org.example.labo5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.labo5 to javafx.fxml;
    exports org.example.labo5;
    exports org.example.labo5.model;
    opens org.example.labo5.model to javafx.fxml;
    exports org.example.labo5.controller.command;
    opens org.example.labo5.controller.command to javafx.fxml;
    exports org.example.labo5.controller;
    opens org.example.labo5.controller to javafx.fxml;
    exports org.example.labo5.view;
    opens org.example.labo5.view to javafx.fxml;
    exports org.example.labo5.model.observer;
    opens org.example.labo5.model.observer to javafx.fxml;
    exports org.example.labo5.controller.services;
    opens org.example.labo5.controller.services to javafx.fxml;
}