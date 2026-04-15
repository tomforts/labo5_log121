package org.example.labo5.controller.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.controller.PerspectiveClipboard;
/**
 * Stratégie ne copiant aucun paramètre.
 * Le collage est sans effet ; permet à l'utilisateur de vider
 * intentionnellement le presse-papiers.
 */
public class CopyNothingStrategy implements CopyPasteStrategy {

    @Override
    public void copy(Perspective source, PerspectiveClipboard clipboard) {
        clipboard.setContent(
                null,
                null,
               null,
                null,
                this
        );
    }

    @Override
    public void paste(PerspectiveClipboard clipboard, Perspective target) {}

    @Override
    public String getName() {
        return "Rien";
    }
}