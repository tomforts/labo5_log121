package org.example.labo5.controller;

import org.example.labo5.services.SaveFileManager;
import org.example.labo5.command.CommandManager;
import org.example.labo5.model.ImageDocument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuController  {

    private SaveFileManager saveFileManager;
    private ImageDocument imageDocument;

    public MenuController(SaveFileManager saveFileManager) {
        this.saveFileManager = saveFileManager;
    }

    public ImageDocument getImageDocument() {
        return imageDocument;
    }

    public void onUndo() {
        CommandManager.getInstance().undoLastCommand();
    }

    public void onRedo() {CommandManager.getInstance().redoLastCommand();}

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

    public boolean onSave() throws IOException {
        if (imageDocument == null) {
            throw new IOException("Aucun document à sauvegarder.");
        }
        return saveFileManager.saveImageDocument(imageDocument);
    }
}