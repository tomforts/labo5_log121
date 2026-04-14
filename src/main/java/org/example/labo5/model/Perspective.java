package org.example.labo5.model;

import org.example.labo5.model.observer.Subject;

public class Perspective extends Subject implements java.io.Serializable{
    private static final double MIN_ZOOM = 0.1;

    private double zoomFactor;
    private int offsetX;
    private int offsetY;
    private int angle;

    public Perspective(String id) {
        this.zoomFactor = 1.0;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void zoom(double delta) {
        setZoomFactor(zoomFactor + delta);
    }

    public void translate(int dx, int dy) {
        if (zoomFactor <= 1.0) {
            return;
        }
        //on veut garder la même manière de bouger l'image, peu importe la rotation.
        switch(angle) {
            case 0: setOffsets(offsetX + dx, offsetY + dy); break;
            case 90 : setOffsets(offsetX + dy, offsetY - dx); break;
            case 180 : setOffsets(offsetX - dx, offsetY - dy); break;
            case 270 : setOffsets(offsetX - dy, offsetY + dx); break;
            default:break;
        }
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = Math.max(MIN_ZOOM, zoomFactor);
        notifyObservers();
    }

    public void setOffsets(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
        notifyObservers();
    }

    public void setAngle(int angle) {
        this.angle = angle;
        notifyObservers();
    }

    public int getAngle() {
        return angle;
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

    public PerspectiveMemento saveToMemento() {
        return new PerspectiveMemento(zoomFactor, offsetX, offsetY,angle);
    }

    /**
     * Restaure l’état de la perspective à partir d’un memento.
     *
     * Cette méthode permet de remettre la perspective dans un état précédent,
     * en restaurant ses paramètres de zoom, de translation et de rotation.
     * Elle est utilisée dans le cadre du undo/redo.
     *
     * @param memento le memento contenant l’état à restaurer
     */
    public void restoreFromMemento(PerspectiveMemento memento) {
        if (memento == null) {
            return;
        }
        setOffsets(memento.getOffsetX(), memento.getOffsetY());
        setZoomFactor(memento.getZoomFactor());
        setAngle(memento.getAngle());
    }
    public void rotate(int degrees){
        int newAngle = (this.angle + degrees) % 360;
        setAngle(newAngle);
    }
}
