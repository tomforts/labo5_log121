package org.example.labo5.controller.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.controller.PerspectiveClipboard;
/**
 * Interface du patron Stratégie pour le copier/coller entre perspectives.
 * Chaque implémentation définit quels paramètres sont copiés et collés
 * via le PerspectiveClipboard.
 */
public interface CopyPasteStrategy {

    /**
     * Copie les paramètres sélectionnés de la perspective source vers le presse-papiers.
     *
     * @param source    perspective dont les paramètres sont copiés
     * @param clipboard presse-papiers cible
     */
    void copy(Perspective source, PerspectiveClipboard clipboard);

    /**
     * Applique les paramètres du presse-papiers à la perspective cible.
     *
     * @param clipboard presse-papiers source
     * @param target    perspective à modifier
     */
    void paste(PerspectiveClipboard clipboard, Perspective target);

    String getName();
}