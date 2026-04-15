package org.example.labo5.controller.command;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveMemento;
/**
 * Commande abstraite portant sur une Perspective.
 * Sauvegarde les états avant (before) et après (after)
 * l'exécution via PerspectiveMemento pour les opérations undo/redo.
 */
public abstract class PerspectiveCommand implements Command {

    protected Perspective perspective;

    protected PerspectiveMemento before;
    protected PerspectiveMemento after;

    public PerspectiveCommand(Perspective perspective) {
        this.perspective = perspective;
    }

    public abstract void execute();

    public abstract void undo();

    public abstract void redo();
}