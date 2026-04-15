package org.example.labo5.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
/**
 * Agrège une Image et ses Perspectives associées.
 * Constitue l'unité de sauvegarde/chargement de l'application.
 */
public class ImageDocument implements Serializable {
    private Image image;
    private List<Perspective> perspectives;

    public ImageDocument(Image image) {
        this.image = image;
        this.perspectives = new ArrayList<>();
    }

    public Image getImage() {
        return image;
    }

    public List<Perspective> getPerspectives() {
        return perspectives;
    }

    /** @param perspective perspective à ajouter au document */
    public void addPerspective(Perspective perspective) {
        perspectives.add(perspective);
    }
}
