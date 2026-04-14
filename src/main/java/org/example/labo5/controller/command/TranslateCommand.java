package org.example.labo5.controller.command;

import org.example.labo5.model.Perspective;

public class TranslateCommand extends PerspectiveCommand {

    private final int dx;
    private final int dy;

    public TranslateCommand(Perspective perspective, int dx, int dy) {
        super(perspective);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        if (before == null) {
            before = perspective.saveToMemento();
        }

        perspective.translate(dx, dy);
        after = perspective.saveToMemento();
    }

    @Override
    public void undo() {
        perspective.restoreFromMemento(before);
    }

    @Override
    public void redo() {
        perspective.restoreFromMemento(after);
    }
}