package org.example.labo5.model;

/**
 * Représente un instantané de l'état d'une perspective.
 * Ce memento permet de sauvegarder et restaurer les valeurs
 * de zoom et de translation.
 */
public class PerspectiveMemento {

    private final double zoomFactor;
    private final int offsetX;
    private final int offsetY;

    public PerspectiveMemento(double zoomFactor, int offsetX, int offsetY) {
        this.zoomFactor = zoomFactor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}