package org.example.labo5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ViewController, ActionListener {

    private SaveFileManager saveFileManager;

    public static final String ACTION_SAVE = "save";
    public static final String ACTION_UNDO = "undo";

    public MenuController(SaveFileManager saveFileManager) {
        this.saveFileManager = saveFileManager;
    }

    @Override
    public void handleZoom(PerspectiveView view, double delta) {
        Perspective perspective = view.getPerspective();
        Command cmd = new ZoomCommand(perspective, delta);
        CommandManager.getInstance().executeCommand(cmd);
    }

    @Override
    public void handleTranslation(PerspectiveView view, int dx, int dy, int extra) {
        Perspective perspective = view.getPerspective();
        Command cmd = new TranslateCommand(perspective, dx, dy);
        CommandManager.getInstance().executeCommand(cmd);
    }

    public void onUndo() {
        CommandManager.getInstance().undoLastCommand();
    }

    public void saveProjectState(ImageDocument imageDocument, String filePath) {
        saveFileManager.save(imageDocument, filePath);
    }

    public ImageDocument loadProjectState(String filePath) {
        return saveFileManager.load(filePath);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACTION_UNDO:
                onUndo();
                break;
            case ACTION_SAVE:
                break;
        }
    }
}
