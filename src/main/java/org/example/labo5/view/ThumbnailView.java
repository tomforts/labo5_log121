package org.example.labo5.view;

import org.example.labo5.model.Image;
import org.example.labo5.observer.Observer;
import org.example.labo5.observer.Subject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ThumbnailView extends JPanel implements Observer {

    private Image image;

    public ThumbnailView(Image image) {
        this.image = image;
        image.attach(this);
    }

    @Override
    public void update(Subject s) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage img = image.getBufferedImage();
        if (img != null) {
            g.drawImage(img, 0, 0, 150, 150, null);
        }
    }
}