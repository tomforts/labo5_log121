package org.example.labo5.controller;

import org.example.labo5.view.PerspectiveView;
/**
 * Interface des contrôleurs de vue.
 * Définit les interactions de zoom et de translation sur une PerspectiveView.
 */
public interface ViewController {

    /**
     * Applique un zoom sur la perspective de la vue.
     *
     * @param view  vue cible
     * @param delta variation de zoom (positif = zoom avant, négatif = zoom arrière)
     */
    public void handleZoom(PerspectiveView view, double delta);

    /**
     * Applique une translation sur la perspective de la vue.
     *
     * @param view vue cible
     * @param dx   déplacement horizontal en pixels
     * @param dy   déplacement vertical en pixels
     */
    public void handleTranslation(PerspectiveView view, int dx, int dy);
}
