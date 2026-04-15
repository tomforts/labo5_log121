package org.example.labo5.model;

import org.example.labo5.model.observer.Subject;
/**
 * Modèle d'une perspective sur une image : zoom, translation et rotation.
 * Étend Subject pour notifier les vues à chaque modification.
 * Supporte le patron Memento via PerspectiveMemento.
 */
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

    /**
     * Applique un delta au facteur de zoom courant.
     *
     * @param delta valeur ajoutée au zoom (peut être négative)
     */
    public void zoom(double delta) {
        setZoomFactor(zoomFactor + delta);
    }

    /**
     * Translate l'image en tenant compte de l'angle de rotation courant.
     * Sans effet si le zoom est ≤ 1.
     *
     * @param dx déplacement horizontal en pixels
     * @param dy déplacement vertical en pixels
     */
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

    /** @return instantané de l'état courant */
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
