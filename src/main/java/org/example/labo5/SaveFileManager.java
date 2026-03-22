package org.example.labo5;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class SaveFileManager {

    private final JFrame parent;

    public SaveFileManager(JFrame parent) {
        this.parent = parent;
    }

    public ImageDocument loadImage() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir une image");
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Images (*.png, *.jpg, *.jpeg)",
                "png", "jpg", "jpeg"
        ));

        int result = fileChooser.showOpenDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File selectedFile = fileChooser.getSelectedFile();
        Image image = new Image();
        image.loadImage(selectedFile.getAbsolutePath());
        return createDefaultDocument(image);
    }

    public ImageDocument loadImageDocument() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir un document image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Perspective Document (*.perspective)", "perspective"));

        int result = fileChooser.showOpenDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File selectedFile = fileChooser.getSelectedFile();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
            ImageDocument imageDocument = (ImageDocument) inputStream.readObject();
            return normalizeDocument(imageDocument);
        }
    }

    public void saveImageDocument(ImageDocument imageDocument) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder le document image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Perspective Document (*.perspective)", "perspective"));

        int result = fileChooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

        if (!selectedFile.getName().toLowerCase().endsWith(".perspective")) {
            selectedFile = new File(selectedFile.getAbsolutePath() + ".perspective");
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
            outputStream.writeObject(normalizeDocument(imageDocument));
        }
    }

    private ImageDocument createDefaultDocument(Image image) {
        ImageDocument imageDocument = new ImageDocument(image);
        imageDocument.addPerspective(new Perspective("Perspective 1"));
        imageDocument.addPerspective(new Perspective("Perspective 2"));
        return imageDocument;
    }

    private ImageDocument normalizeDocument(ImageDocument imageDocument) {
        if (imageDocument == null) {
            return null;
        }

        if (imageDocument.getPerspectives() == null) {
            return createDefaultDocument(imageDocument.getImage());
        }

        while (imageDocument.getPerspectives().size() < 2) {
            imageDocument.addPerspective(new Perspective("Perspective " + (imageDocument.getPerspectives().size() + 1)));
        }

        return imageDocument;
    }
}
