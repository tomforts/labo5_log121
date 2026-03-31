package org.example.labo5;

import org.example.labo5.controller.MenuController;
import org.example.labo5.controller.MouseController;
import org.example.labo5.model.ImageDocument;
import org.example.labo5.util.SaveFileManager;
import org.example.labo5.view.ImageView;
import org.example.labo5.view.PerspectiveView;
import org.example.labo5.model.Image;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ImageViewerApp extends JFrame {

    private final JButton loadImageButton;
    private final JButton loadDocumentButton;
    private final JButton saveDocumentButton;
    private final JButton undoButton;

    private final JPanel imagePanel;
    private final JPanel perspective1Panel;
    private final JPanel perspective2Panel;

    private final MenuController menuController;
    private final MouseController mouseController;

    public ImageViewerApp() {
        menuController  = new MenuController(new SaveFileManager(this));
        mouseController = new MouseController();

        setTitle("LOG121 - Labo 5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        loadImageButton    = new JButton("Charger image");
        loadDocumentButton = new JButton("Charger document");
        saveDocumentButton = new JButton("Sauvegarder document");
        undoButton         = new JButton("Undo");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(loadImageButton);
        topPanel.add(loadDocumentButton);
        topPanel.add(saveDocumentButton);
        topPanel.add(undoButton);

        imagePanel        = createImageContainer("Image originale");
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
        undoButton.addActionListener(e -> menuController.onUndo());
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

    private PerspectiveView createPerspectiveView(String name, Image image, Perspective perspective) {
        PerspectiveView view = new PerspectiveView(name, image, perspective, mouseController);
        view.addMouseListener(mouseController);
        view.addMouseMotionListener(mouseController);
        view.addMouseWheelListener(mouseController);
        return view;
    }

    private void installDocument() {
        ImageDocument document = menuController.getImageDocument();
        if (document == null || document.getImage() == null) {
            installEmptyState();
            return;
        }

        setPanelContent(imagePanel, new ImageView(document.getImage()));

        Perspective p1 = document.getPerspectives().get(0);
        Perspective p2 = document.getPerspectives().get(1);

        setPanelContent(perspective1Panel, createPerspectiveView("Perspective 1", document.getImage(), p1));
        setPanelContent(perspective2Panel, createPerspectiveView("Perspective 2", document.getImage(), p2));
    }

    private void loadImageDocumentFromImage() {
        try {
            if (menuController.onLoadImage() != null) installDocument();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement de l'image : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadImageDocument() {
        try {
            if (menuController.onLoadDocument() != null) installDocument();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement du document : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveImageDocument() {
        if (menuController.getImageDocument() == null) {
            JOptionPane.showMessageDialog(this,
                    "Aucun document à sauvegarder.",
                    "Sauvegarde", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            menuController.onSave();
            JOptionPane.showMessageDialog(this,
                    "Document sauvegardé avec succès.",
                    "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la sauvegarde : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageViewerApp::new);
    }
}