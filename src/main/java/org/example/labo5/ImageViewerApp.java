package org.example.labo5;

import org.example.labo5.model.Image;
import org.example.labo5.model.ImageDocument;
import org.example.labo5.util.SaveFileManager;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageViewerApp extends JFrame {

    private JButton loadImageButton;
    private JButton loadDocumentButton;
    private JButton saveDocumentButton;

    private JPanel imagePanel;
    private JPanel perspective1Panel;
    private JPanel perspective2Panel;

    private JLabel imageLabel;
    private JLabel perspective1Label;
    private JLabel perspective2Label;

    private ImageDocument imageDocument;
    private final SaveFileManager saveFileManager;

    public ImageViewerApp() {
        saveFileManager = new SaveFileManager(this);

        setTitle("LOG121 - Test UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        loadImageButton = new JButton("Charger image");
        loadDocumentButton = new JButton("Charger document");
        saveDocumentButton = new JButton("Sauvegarder document");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(loadImageButton);
        topPanel.add(loadDocumentButton);
        topPanel.add(saveDocumentButton);

        imageLabel = createCenteredLabel("Aucune image");
        perspective1Label = createCenteredLabel("Perspective 1 vide");
        perspective2Label = createCenteredLabel("Perspective 2 vide");

        imagePanel = createImageContainer("Image", imageLabel);
        perspective1Panel = createImageContainer("Perspective 1", perspective1Label);
        perspective2Panel = createImageContainer("Perspective 2", perspective2Label);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.add(imagePanel);
        centerPanel.add(perspective1Panel);
        centerPanel.add(perspective2Panel);

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapper.add(topPanel, BorderLayout.NORTH);
        wrapper.add(centerPanel, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);

        loadImageButton.addActionListener(e -> loadImageDocumentFromImage());
        loadDocumentButton.addActionListener(e -> loadImageDocument());
        saveDocumentButton.addActionListener(e -> saveImageDocument());

        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JPanel createImageContainer(String title, JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void loadImageDocumentFromImage() {
        try {
            ImageDocument loadedDocument = saveFileManager.loadImage();

            if (loadedDocument != null) {
                imageDocument = loadedDocument;
                displayModel();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement de l'image : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadImageDocument() {
        try {
            ImageDocument loadedDocument = saveFileManager.loadImageDocument();

            if (loadedDocument != null) {
                imageDocument = loadedDocument;
                displayModel();
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement du document : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveImageDocument() {
        if (imageDocument == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Aucun document à sauvegarder.",
                    "Sauvegarde",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            saveFileManager.saveImageDocument(imageDocument);
            JOptionPane.showMessageDialog(
                    this,
                    "Document sauvegardé avec succès.",
                    "Sauvegarde",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la sauvegarde : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void displayModel() {
        if (imageDocument == null || imageDocument.getImage() == null) {
            return;
        }

        BufferedImage bufferedImage = imageDocument.getImage().getBufferedImage();
        if (bufferedImage == null) {
            return;
        }

        int imageWidth = getSafeDisplayWidth(imageLabel);
        int imageHeight = getSafeDisplayHeight(imageLabel);

        int p1Width = getSafeDisplayWidth(perspective1Label);
        int p1Height = getSafeDisplayHeight(perspective1Label);

        int p2Width = getSafeDisplayWidth(perspective2Label);
        int p2Height = getSafeDisplayHeight(perspective2Label);

        imageLabel.setText("");
        imageLabel.setIcon(new ImageIcon(scaleImage(bufferedImage, imageWidth, imageHeight)));

        if (imageDocument.getPerspectives().size() >= 2) {
            Perspective p1 = imageDocument.getPerspectives().get(0);
            Perspective p2 = imageDocument.getPerspectives().get(1);

            BufferedImage p1View = createPerspectiveView(bufferedImage, p1, p1Width, p1Height);
            BufferedImage p2View = createPerspectiveView(bufferedImage, p2, p2Width, p2Height);

            perspective1Label.setText("");
            perspective1Label.setIcon(new ImageIcon(p1View));

            perspective2Label.setText("");
            perspective2Label.setIcon(new ImageIcon(p2View));
        }
    }

    private int getSafeDisplayWidth(JLabel label) {
        return Math.max(label.getWidth(), 300);
    }

    private int getSafeDisplayHeight(JLabel label) {
        return Math.max(label.getHeight(), 300);
    }

    private java.awt.Image scaleImage(BufferedImage image, int targetWidth, int targetHeight) {
        double widthRatio = (double) targetWidth / image.getWidth();
        double heightRatio = (double) targetHeight / image.getHeight();
        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = Math.max(1, (int) (image.getWidth() * scale));
        int newHeight = Math.max(1, (int) (image.getHeight() * scale));

        return image.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
    }

    private BufferedImage createPerspectiveView(
            BufferedImage source,
            Perspective perspective,
            int targetWidth,
            int targetHeight
    ) {
        double zoom = perspective.getZoomFactor();
        if (zoom <= 0) {
            zoom = 1.0;
        }

        int cropWidth = Math.max(1, (int) (source.getWidth() / zoom));
        int cropHeight = Math.max(1, (int) (source.getHeight() / zoom));

        int centerX = source.getWidth() / 2;
        int centerY = source.getHeight() / 2;

        centerX += perspective.getOffsetX();
        centerY += perspective.getOffsetY();

        int x = centerX - (cropWidth / 2);
        int y = centerY - (cropHeight / 2);

        x = Math.max(0, Math.min(x, source.getWidth() - cropWidth));
        y = Math.max(0, Math.min(y, source.getHeight() - cropHeight));

        BufferedImage cropped = source.getSubimage(x, y, cropWidth, cropHeight);

        BufferedImage result = new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        java.awt.Graphics2D g2d = result.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, targetWidth, targetHeight);

        double widthRatio = (double) targetWidth / cropped.getWidth();
        double heightRatio = (double) targetHeight / cropped.getHeight();
        double scale = Math.min(widthRatio, heightRatio);

        int drawWidth = Math.max(1, (int) (cropped.getWidth() * scale));
        int drawHeight = Math.max(1, (int) (cropped.getHeight() * scale));

        int drawX = (targetWidth - drawWidth) / 2;
        int drawY = (targetHeight - drawHeight) / 2;

        g2d.drawImage(cropped, drawX, drawY, drawWidth, drawHeight, null);
        g2d.dispose();

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageViewerApp::new);
    }
}