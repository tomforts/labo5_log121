package org.example.labo5;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

public class ImageViewerApp extends JFrame {

    private final JButton loadImageButton;
    private final JButton loadDocumentButton;
    private final JButton saveDocumentButton;
    private final JButton undoButton;

    private final JPanel imagePanel;
    private final JPanel perspective1Panel;
    private final JPanel perspective2Panel;
    //private final JLabel instructionsLabel;

    private ImageDocument imageDocument;
    private final SaveFileManager saveFileManager;
    private final MenuController menuController;

    public ImageViewerApp() {
        saveFileManager = new SaveFileManager(this);
        menuController = new MenuController(saveFileManager);

        setTitle("LOG121 - Labo 5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        loadImageButton = new JButton("Charger image");
        loadDocumentButton = new JButton("Charger document");
        saveDocumentButton = new JButton("Sauvegarder document");
        undoButton = new JButton("Undo");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(loadImageButton);
        topPanel.add(loadDocumentButton);
        topPanel.add(saveDocumentButton);
        topPanel.add(undoButton);

        //instructionsLabel = new JLabel("Molette = zoom, glisser = déplacer la perspective quand le zoom est > 1.");

        imagePanel = createImageContainer("Image originale");
        perspective1Panel = createImageContainer("Perspective 1");
        perspective2Panel = createImageContainer("Perspective 2");

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.add(imagePanel);
        centerPanel.add(perspective1Panel);
        centerPanel.add(perspective2Panel);

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapper.add(topPanel, BorderLayout.NORTH);
        wrapper.add(centerPanel, BorderLayout.CENTER);
        //wrapper.add(instructionsLabel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);

        installEmptyState();
        registerListeners();

        setSize(1200, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerListeners() {
        loadImageButton.addActionListener(e -> loadImageDocumentFromImage());
        loadDocumentButton.addActionListener(e -> loadImageDocument());
        saveDocumentButton.addActionListener(e -> saveImageDocument());
        undoButton.addActionListener(e -> undoLastCommand());
    }

    private JPanel createImageContainer(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));
        return panel;
    }

    private JLabel createPlaceholderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    private void installEmptyState() {
        setPanelContent(imagePanel, createPlaceholderLabel("Aucune image"));
        setPanelContent(perspective1Panel, createPlaceholderLabel("Perspective 1 vide"));
        setPanelContent(perspective2Panel, createPlaceholderLabel("Perspective 2 vide"));
    }

    private void setPanelContent(JPanel panel, JComponent component) {
        panel.removeAll();
        panel.add(component, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private void installDocument(ImageDocument document) {
        imageDocument = document;
        menuController.setImageDocument(document);

        if (document == null || document.getImage() == null) {
            installEmptyState();
            return;
        }

        setPanelContent(imagePanel, new ImageView(document.getImage()));

        Perspective perspective1 = document.getPerspectives().get(0);
        Perspective perspective2 = document.getPerspectives().get(1);

        setPanelContent(
                perspective1Panel,
                new PerspectiveView("Perspective 1", document.getImage(), perspective1, menuController)
        );
        setPanelContent(
                perspective2Panel,
                new PerspectiveView("Perspective 2", document.getImage(), perspective2, menuController)
        );
    }

    private void loadImageDocumentFromImage() {
        try {
            ImageDocument loadedDocument = menuController.onLoadImage();
            if (loadedDocument != null) {
                installDocument(loadedDocument);
            }
        } catch (Exception ex) {
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
            ImageDocument loadedDocument = menuController.onLoadDocument();
            if (loadedDocument != null) {
                installDocument(loadedDocument);
            }
        } catch (Exception ex) {
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
            menuController.onSave();
            JOptionPane.showMessageDialog(
                    this,
                    "Document sauvegardé avec succès.",
                    "Sauvegarde",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la sauvegarde : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void undoLastCommand() {
        menuController.onUndo();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageViewerApp::new);
    }
}