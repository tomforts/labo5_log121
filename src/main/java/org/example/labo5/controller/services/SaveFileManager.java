package org.example.labo5.controller.services;

import org.example.labo5.model.Image;
import org.example.labo5.model.ImageDocument;
import org.example.labo5.model.Perspective;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
/**
 * Gère le chargement et la sauvegarde des documents image.
 */
public class SaveFileManager {

    private final JFrame parent;

    public SaveFileManager(JFrame parent) {
        this.parent = parent;
    }

    /**
     * Charge une image à partir d'un fichier choisi par l'utilisateur
     * et crée un document image par défaut contenant deux perspectives.
     *
     * @return un document image initialisé à partir de l'image sélectionnée,
     *         ou rien si l'utilisateur annule l'opération
     * @throws IOException si une erreur survient lors de la lecture du fichier image
     */
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

    /**
     * Charge un document image à partir d'un fichier choisi par l'utilisateur.
     * Le document chargé est ensuite normalisé afin de s'assurer qu'il respecte
     * la structure attendue.
     *
     * @return le document image normalisé, ou rien si l'utilisateur annule l'opération
     * @throws IOException si une erreur survient lors de la lecture du fichier
     * @throws ClassNotFoundException si une classe nécessaire à la désérialisation est introuvable
     */
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

    /**
     * Sauvegarde un document image dans un fichier choisi par l'utilisateur.
     * Si le nom du fichier ne possède pas l'extension .perspective,
     * celle-ci est ajoutée automatiquement.
     *
     * @param imageDocument le document image à sauvegarder
     * @return true si la sauvegarde a été effectuée,
     *         false si l'utilisateur annule l'opération
     * @throws IOException si une erreur survient lors de l'écriture du fichier
     */
    public boolean saveImageDocument(ImageDocument imageDocument) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder le document image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Perspective Document (*.perspective)", "perspective"));

        int result = fileChooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File selectedFile = fileChooser.getSelectedFile();

        if (!selectedFile.getName().toLowerCase().endsWith(".perspective")) {
            selectedFile = new File(selectedFile.getAbsolutePath() + ".perspective");
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
            outputStream.writeObject(normalizeDocument(imageDocument));
        }
        return true;
    }

    /**
     * Crée un document image par défaut à partir d'une image donnée.
     * Le document créé contient automatiquement deux perspectives initiales.
     *
     * @param image l'image à inclure dans le document
     * @return un document image contenant l'image et ses deux perspectives
     */
    private ImageDocument createDefaultDocument(Image image) {
        ImageDocument imageDocument = new ImageDocument(image);
        imageDocument.addPerspective(new Perspective("Perspective 1"));
        imageDocument.addPerspective(new Perspective("Perspective 2"));
        return imageDocument;
    }

    /**
     * S'assure qu'un document image possède
     * au minimum deux perspectives.
     *
     * @param imageDocument le document image à normaliser
     * @return le document image normalisé, ou rien si le document fourni est nul
     */
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