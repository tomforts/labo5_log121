package org.example.labo5.view;

import org.example.labo5.model.observer.Observer;
import org.example.labo5.model.observer.Subject;
import org.example.labo5.model.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageView extends JPanel implements Observer {

    private org.example.labo5.model.Image image;

    public ImageView(Image image) {
        this.image = image;
        setBackground(Color.WHITE);
        if (this.image != null) {
            this.image.attach(this);
        }
    }

    @Override
    public void update(Subject subject) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        BufferedImage img = image != null ? image.getBufferedImage() : null;
        if (img == null) {
            return;
        }

        Rectangle bounds = getScaledBounds(img.getWidth(), img.getHeight(), getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }
    /**
     * Calcule les dimensions et la position d'une image redimensionnée
     * afin qu'elle s'adapte à une zone donnée.
     *
     * @param imgW largeur originale de l'image
     * @param imgH hauteur originale de l'image
     * @param boxW largeur du conteneur
     * @param boxH hauteur du conteneur
     * @return un rectangle représentant la position (x, y) et la taille (largeur, hauteur)
     *         de l'image redimensionnée dans le conteneur
     */
    private Rectangle getScaledBounds(int imgW, int imgH, int boxW, int boxH) {
        double scale = Math.min((double) boxW / imgW, (double) boxH / imgH);
        int w = (int) (imgW * scale);
        int h = (int) (imgH * scale);
        int x = (boxW - w) / 2;
        int y = (boxH - h) / 2;
        return new Rectangle(x, y, w, h);
    }
}