package org.example.labo5.controller;

import org.example.labo5.Perspective;
import org.example.labo5.model.ImageDocument;
import org.example.labo5.model.Image;
import org.example.labo5.command.Command;
import org.example.labo5.command.CommandManager;
import org.example.labo5.command.ZoomCommand;
import org.example.labo5.command.TranslateCommand;
import org.example.labo5.view.PerspectiveView;
import org.example.labo5.util.SaveFileManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuController implements ActionListener {

    private SaveFileManager saveFileManager;
    private ImageDocument imageDocument;

    public static final String ACTION_SAVE = "save";
    public static final String ACTION_UNDO = "undo";
    public static final String ACTION_LOAD_IMAGE = "loadImage";
    public static final String ACTION_LOAD_DOCUMENT = "loadDocument";

    public MenuController(SaveFileManager saveFileManager) {
        this.saveFileManager = saveFileManager;
    }

    public void setImageDocument(ImageDocument imageDocument) {
        this.imageDocument = imageDocument;
    }

    public ImageDocument getImageDocument() {
        return imageDocument;
    }

    public void onUndo() {
        CommandManager.getInstance().undoLastCommand();
    }

    public ImageDocument onLoadImage() {
        try {
            imageDocument = saveFileManager.loadImage();
            return imageDocument;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            return null;
        }
    }

    public ImageDocument onLoadDocument() {
        try {
            imageDocument = saveFileManager.loadImageDocument();
            return imageDocument;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du document : " + e.getMessage());
            return null;
        }
    }

    public void onSave() throws IOException {
        if (imageDocument == null) {
            throw new IOException("Aucun document à sauvegarder.");
        }
        saveFileManager.saveImageDocument(imageDocument);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case ACTION_UNDO:
                    onUndo();
                    break;
                case ACTION_SAVE:
                    onSave();
                    break;
                case ACTION_LOAD_IMAGE:
                    onLoadImage();
                    break;
                case ACTION_LOAD_DOCUMENT:
                    onLoadDocument();
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}