package org.example.labo5.command;

import org.example.labo5.model.Perspective;

public abstract class PerspectiveCommand implements Command {

    protected Perspective perspective;

    public PerspectiveCommand(Perspective perspective) {
        this.perspective = perspective;
    }

    public abstract void execute();

    public abstract void undo();
}