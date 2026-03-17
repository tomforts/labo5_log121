package org.example.labo5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;

public class ImageDocument implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    public void addPerspective(Perspective perspective) {
        perspectives.add(perspective);
    }

    public void removePerspective(Perspective perspective) {
        perspectives.remove(perspective);
    }

}
