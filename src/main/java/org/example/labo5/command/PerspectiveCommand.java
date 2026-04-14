package org.example.labo5.command;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveMemento;

public abstract class PerspectiveCommand implements Command {

    protected Perspective perspective;

    protected PerspectiveMemento before;
    protected PerspectiveMemento after;

    public PerspectiveCommand(Perspective perspective) {
        this.perspective = perspective;
    }

    public abstract void execute();

    public abstract void undo();
}