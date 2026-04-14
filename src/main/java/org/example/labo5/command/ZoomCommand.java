package org.example.labo5.command;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveMemento;

public class ZoomCommand extends PerspectiveCommand {

    private final double deltaZoom;

    public ZoomCommand(Perspective perspective, double deltaZoom) {
        super(perspective);
        this.deltaZoom = deltaZoom;
    }

    @Override
    public void execute() {
        if (before == null) {
            before = perspective.saveToMemento();
        }

        perspective.zoom(deltaZoom);
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