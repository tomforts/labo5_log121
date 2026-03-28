package org.example.labo5.controller;

import org.example.labo5.view.PerspectiveView;

public interface ViewController {

    public void handleZoom(PerspectiveView view, double delta);

    public void handleTranslation(PerspectiveView view, int dx, int dy, int extra);
}
