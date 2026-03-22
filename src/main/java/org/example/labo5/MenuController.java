package org.example.labo5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuController implements ViewController, ActionListener {

    private SaveFileManager saveFileManager;
    private ImageDocument imageDocument;

    public static final String ACTION_SAVE         = "save";
    public static final String ACTION_UNDO         = "undo";
    public static final String ACTION_LOAD_IMAGE   = "loadImage";
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

    @Override
    public void handleZoom(PerspectiveView view, double delta) {
        if (view == null || view.getPerspective() == null) return;
        Perspective perspective = view.getPerspective();
        Command cmd = new ZoomCommand(perspective, delta);
        CommandManager.getInstance().executeCommand(cmd);
    }

    @Override
    public void handleTranslation(PerspectiveView view, int dx, int dy, int extra) {
        if (view == null || view.getPerspective() == null) return;
        Perspective perspective = view.getPerspective();
        Command cmd = new TranslateCommand(perspective, dx, dy);
        CommandManager.getInstance().executeCommand(cmd);
    }

    public void onUndo() {
        CommandManager.getInstance().undoLastCommand();
    }

    public ImageDocument onLoadImage() {
        try {
            imageDocument = saveFileManager.loadImage();
            return imageDocument;
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            return null;
        }
    }

    public ImageDocument onLoadDocument() {
        try {
            imageDocument = saveFileManager.loadImageDocument();
            return imageDocument;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement du document : " + e.getMessage());
            return null;
        }
    }

    public void onSave() {
        if (saveFileManager == null || imageDocument == null) {
            System.err.println("Aucun document à sauvegarder.");
            return;
        }
        try {
            saveFileManager.saveImageDocument(imageDocument);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        }
    }
}
