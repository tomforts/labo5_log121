package org.example.labo5.view;

import org.example.labo5.controller.ViewController;
import org.example.labo5.model.Image;
import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveClipboard;
import org.example.labo5.observer.Observer;
import org.example.labo5.observer.Subject;
import org.example.labo5.strategy.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PerspectiveView extends JPanel implements Observer {
    private final Image image;
    private final Perspective perspective;
    private final PerspectiveClipboard clipboard;
    private final JPopupMenu popupMenu;

    public PerspectiveView(Image image, Perspective perspective, PerspectiveClipboard clipboard) {
        this.image = image;
        this.perspective = perspective;
        this.clipboard = clipboard;

        setBackground(Color.WHITE);

        if (this.image != null) {
            this.image.attach(this);
        }
        if (this.perspective != null) {
            this.perspective.attach(this);
        }

        this.popupMenu = createPopupMenu();
        registerPopupListener();
    }

    public Perspective getPerspective() {
        return perspective;
    }

    @Override
    public void update(Subject subject) {
        repaint();
    }


    /**
     * Crée le menu pour copier et coller
     * les paramètres d'une perspective.
     *
     * @return le menu contextuel configuré
     */
    private JPopupMenu createPopupMenu() {
        JPopupMenu popup = new JPopupMenu();

        JMenu copyMenu = new JMenu("Copier");
        JMenuItem copyZoomItem = new JMenuItem("Zoom");
        JMenuItem copyTranslationItem = new JMenuItem("Translation");
        JMenuItem copyAllItem = new JMenuItem("Tout");
        JMenuItem copyNothingItem = new JMenuItem("Rien");

        JMenuItem pasteItem = new JMenuItem("Coller");

        copyZoomItem.addActionListener(e -> {
            CopyPasteStrategy strategy = new CopyZoomStrategy();
            strategy.copy(perspective, clipboard);
        });

        copyTranslationItem.addActionListener(e -> {
            CopyPasteStrategy strategy = new CopyTranslationStrategy();
            strategy.copy(perspective, clipboard);
        });

        copyAllItem.addActionListener(e -> {
            CopyPasteStrategy strategy = new CopyAllStrategy();
            strategy.copy(perspective, clipboard);
        });

        copyNothingItem.addActionListener(e -> {
            CopyPasteStrategy strategy = new CopyNothingStrategy();
            strategy.copy(perspective, clipboard);
        });
        pasteItem.addActionListener(e -> {
            if (clipboard.hasContent() && clipboard.getStrategy() != null) {
                clipboard.getStrategy().paste(clipboard, perspective);
            }
        });



        copyMenu.add(copyZoomItem);
        copyMenu.add(copyTranslationItem);
        copyMenu.add(copyAllItem);
        copyMenu.add(copyNothingItem);

        popup.add(copyMenu);
        popup.addSeparator();
        popup.add(pasteItem);

        popup.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                pasteItem.setEnabled(clipboard.hasContent());
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        return popup;
    }

    private void registerPopupListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupIfNeeded(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupIfNeeded(e);
            }
        });
    }

    private void showPopupIfNeeded(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    /**
     * Dessine la vue de la perspective.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (image == null || perspective == null) {
            return;
        }

        BufferedImage img = image.getBufferedImage();
        if (img == null) {
            return;
        }

        BufferedImage cropped = getCroppedImage(img);
        Rectangle bounds = getScaledBounds(cropped.getWidth(), cropped.getHeight(), getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        int angle = perspective.getAngle();
        if (angle != 0) {
            AffineTransform originalTransform = g2d.getTransform();
            
            int centerX = bounds.x + bounds.width / 2;
            int centerY = bounds.y + bounds.height / 2;
            
            g2d.rotate(Math.toRadians(angle), centerX, centerY);
            g2d.drawImage(cropped, bounds.x, bounds.y, bounds.width, bounds.height, null);
            
            g2d.setTransform(originalTransform);
        } else {
            g2d.drawImage(cropped, bounds.x, bounds.y, bounds.width, bounds.height, null);
        }
    }

    /**
     * Retourne une portion de l'image en fonction du zoom et de la translation
     * de la perspective.
     *
     * @param img image source
     * @return image recadrée selon la perspective
     */
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