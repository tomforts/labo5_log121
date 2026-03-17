package org.example.labo5;

import javax.swing.*;

public class PerspectiveView extends JPanel implements Observer {

    private String viewName;
    private Image image;
    private Perspective perspective;
    private ViewController controller;

    public PerspectiveView(String viewName, Image image, Perspective perspective, ViewController controller) {
        this.viewName = viewName;
        this.image = image;
        this.perspective = perspective;
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    public Image getImage() {
        return image;
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public ViewController getController() {
        return controller;
    }

    @Override
    public void update(Subject subject) {}

    public void display() {}

    public void renderPerspective() {}
}
