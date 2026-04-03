package org.example.labo5.command;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveMemento;

public class TranslateCommand extends PerspectiveCommand {

    private final int dx;
    private final int dy;
    private PerspectiveMemento before;
    private PerspectiveMemento after;

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