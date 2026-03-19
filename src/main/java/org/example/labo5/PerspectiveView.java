package org.example.labo5;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class PerspectiveView extends JPanel implements Observer {

    private Image image;
    private Perspective perspective;

    public PerspectiveView(Image image, Perspective perspective) {
        this.image = image;
        this.perspective = perspective;

        perspective.attach(this); // 🔥 clé du labo
    }

    @Override
    public void update(Subject s) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage source = image.getBufferedImage();
        if (source == null) return;

        double zoom = perspective.getZoomFactor();

        int cropWidth = (int)(source.getWidth() / zoom);
        int cropHeight = (int)(source.getHeight() / zoom);

        int centerX = source.getWidth()/2 + perspective.getOffsetX();
        int centerY = source.getHeight()/2 + perspective.getOffsetY();

        int x = centerX - cropWidth/2;
        int y = centerY - cropHeight/2;

        x = Math.max(0, Math.min(x, source.getWidth() - cropWidth));
        y = Math.max(0, Math.min(y, source.getHeight() - cropHeight));

        BufferedImage cropped = source.getSubimage(x, y, cropWidth, cropHeight);

        g.drawImage(cropped, 0, 0, getWidth(), getHeight(), null);
    }
}