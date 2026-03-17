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

        int result = fileChooser.showOpenDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File selectedFile = fileChooser.getSelectedFile();

        Image image = new Image();
        image.loadImage(selectedFile.getAbsolutePath());

        Perspective perspective1 = new Perspective("Perspective 1");
        Perspective perspective2 = new Perspective("Perspective 2");

        perspective1.setZoomFactor(2);
        perspective1.setOffsets(-150, -80);

        perspective2.setZoomFactor(2);
        perspective2.setOffsets(180, 120);

        ImageDocument imageDocument = new ImageDocument(image);
        imageDocument.addPerspective(perspective1);
        imageDocument.addPerspective(perspective2);

        return imageDocument;
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
            return (ImageDocument) inputStream.readObject();
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
            outputStream.writeObject(imageDocument);
        }
    }
}
