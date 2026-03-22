package org.example.labo5;

public abstract class PerspectiveCommand implements Command {

    protected Perspective perspective;

    public PerspectiveCommand(Perspective perspective) {
        this.perspective = perspective;
    }

    public abstract void execute();

    public abstract void undo();
}