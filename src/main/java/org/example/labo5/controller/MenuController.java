package org.example.labo5.controller;

import org.example.labo5.controller.command.Command;
import org.example.labo5.controller.command.RotateCommand;
import org.example.labo5.model.Perspective;
import org.example.labo5.controller.services.SaveFileManager;
import org.example.labo5.controller.command.CommandManager;
import org.example.labo5.model.ImageDocument;

import javax.swing.*;
import java.io.IOException;
/**
 * Contrôleur des actions du menu : chargement, sauvegarde, undo/redo et rotation.
 * Délègue la persistance à SaveFileManager et l'historique à CommandManager.
 */
public class MenuController  {

    private SaveFileManager saveFileManager;
    private ImageDocument imageDocument;

    public MenuController(SaveFileManager saveFileManager) {
        this.saveFileManager = saveFileManager;
    }

    public ImageDocument getImageDocument() {
        return imageDocument;
    }

    /** Annule la dernière commande exécutée. */
    public void onUndo() {
        CommandManager.getInstance().undoLastCommand();
    }

    /** Rejoue la dernière commande annulée. */
    public void onRedo() {CommandManager.getInstance().redoLastCommand();}

    /**
     * Crée et exécute une RotateCommand de 90° sur la perspective indiquée.
     * Affiche une erreur si aucun document n'est chargé.
     *
     * @param perspectiveIndex index de la perspective à faire pivoter (0 ou 1)
     */
    public void onRotate(int perspectiveIndex) {
        ImageDocument document = getImageDocument();
        if (document == null) {
            JOptionPane.showMessageDialog(null,
                    "Aucun document chargé.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Perspective perspective = document.getPerspectives().get(perspectiveIndex);
        Command cmd = new RotateCommand(perspective,90);
        CommandManager.getInstance().executeCommand(cmd);
    }

    /**
     * Ouvre un sélecteur de fichier image et crée un ImageDocument à partir de celle-ci.
     *
     * @return le document créé null en cas d'échec
     */
    public ImageDocument onLoadImage() {
        try {
            imageDocument = saveFileManager.loadImage();
            return imageDocument;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'image", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Ouvre un sélecteur de fichier et désérialise un ImageDocument.
     *
     * @return le document chargé ou null en cas d'échec
     */
    public ImageDocument onLoadDocument() {
        try {
            imageDocument = saveFileManager.loadImageDocument();
            return imageDocument;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement du document", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Sauvegarde le document courant via SaveFileManager.
     *
     * @return true si la sauvegarde a réussi
     * @throws IOException si aucun document n'est chargé ou en cas d'erreur d'écriture
     */
    public boolean onSave() throws IOException {
        if (imageDocument == null) {
            throw new IOException("Aucun document à sauvegarder.");
        }
        return saveFileManager.saveImageDocument(imageDocument);
    }
}