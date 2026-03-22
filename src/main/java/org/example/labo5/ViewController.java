package org.example.labo5;

public interface ViewController {

    public void handleZoom(PerspectiveView view, double delta);

    public void handleTranslation(PerspectiveView view, int dx, int dy);
}
