package org.example.labo5.model;

/**
 * Représente un état d'une perspective.
 * Ce memento permet de sauvegarder et restaurer les valeurs
 * de zoom, de translation et de rotation.
 */
public class PerspectiveMemento {

    private final double zoomFactor;
    private final int offsetX;
    private final int offsetY;
    private final int angle;

    public PerspectiveMemento(double zoomFactor, int offsetX, int offsetY, int angle) {
        this.zoomFactor = zoomFactor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.angle=angle;
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

    public int getAngle(){return angle;}
}