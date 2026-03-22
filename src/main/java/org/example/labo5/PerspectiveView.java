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

        setBackground(Color.WHITE);

        if (this.image != null) {
            this.image.attach(this);
        }
        if (this.perspective != null) {
            this.perspective.attach(this);
        }

        if (this.controller != null) {
            MouseController mouseController = new MouseController(this.controller);
            addMouseListener(mouseController);
            addMouseMotionListener(mouseController);
            addMouseWheelListener(mouseController);
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

        BufferedImage img = image.getBufferedImage();
        if (img == null) {
            return;
        }

        BufferedImage cropped = getCroppedImage(img);
        Rectangle bounds = getScaledBounds(cropped.getWidth(), cropped.getHeight(), getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(cropped, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    private BufferedImage getCroppedImage(BufferedImage img) {
        double zoom = Math.max(1.0, perspective.getZoomFactor());

        int cropWidth = Math.max(1, (int) (img.getWidth() / zoom));
        int cropHeight = Math.max(1, (int) (img.getHeight() / zoom));

        int centerX = img.getWidth() / 2 + perspective.getOffsetX();
        int centerY = img.getHeight() / 2 + perspective.getOffsetY();

        int x = centerX - cropWidth / 2;
        int y = centerY - cropHeight / 2;

        x = Math.max(0, Math.min(x, img.getWidth() - cropWidth));
        y = Math.max(0, Math.min(y, img.getHeight() - cropHeight));

        return img.getSubimage(x, y, cropWidth, cropHeight);
    }

    private Rectangle getScaledBounds(int imgW, int imgH, int boxW, int boxH) {
        double scale = Math.min((double) boxW / imgW, (double) boxH / imgH);
        int w = (int) (imgW * scale);
        int h = (int) (imgH * scale);
        int x = (boxW - w) / 2;
        int y = (boxH - h) / 2;
        return new Rectangle(x, y, w, h);
    }
}