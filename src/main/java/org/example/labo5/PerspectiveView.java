package org.example.labo5;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PerspectiveView extends JPanel implements Observer {

    private String viewName;
    private Image image;
    private Perspective perspective;
    private ViewController controller;

    public PerspectiveView(String viewName, Image image, Perspective perspective, ViewController controller) {
        this.viewName = viewName;
        this.image = image;
        this.perspective = perspective;
        this.controller = controller;

        if (this.perspective != null) {
            this.perspective.attach(this);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public Image getImage() {
        return image;
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public ViewController getController() {
        return controller;
    }

    @Override
    public void update(Subject subject) {
        repaint();
    }

    public void display() {
        repaint();
    }

    public void renderPerspective() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null || perspective == null) {
            return;
        }

        BufferedImage source = image.getBufferedImage();
        if (source == null) {
            return;
        }

        double zoom = perspective.getZoomFactor();
        if (zoom <= 0) {
            zoom = 1.0;
        }

        int cropWidth = (int) (source.getWidth() / zoom);
        int cropHeight = (int) (source.getHeight() / zoom);

        cropWidth = Math.max(1, Math.min(cropWidth, source.getWidth()));
        cropHeight = Math.max(1, Math.min(cropHeight, source.getHeight()));

        int centerX = source.getWidth() / 2 + perspective.getOffsetX();
        int centerY = source.getHeight() / 2 + perspective.getOffsetY();

        int x = centerX - cropWidth / 2;
        int y = centerY - cropHeight / 2;

        x = Math.max(0, Math.min(x, source.getWidth() - cropWidth));
        y = Math.max(0, Math.min(y, source.getHeight() - cropHeight));

        BufferedImage cropped = source.getSubimage(x, y, cropWidth, cropHeight);
        g.drawImage(cropped, 0, 0, getWidth(), getHeight(), null);
    }
}