package org.example.labo5.controller.command;

import org.example.labo5.model.Perspective;
/**
 * Commande de rotation d'une perspective d'un angle donné.
 */
public class RotateCommand extends PerspectiveCommand {
    private int deltaAngle;

    /**
     * @param perspective perspective à faire pivoter
     * @param deltaAngle  angle à ajouter en degrés (ex. 90)
     */
    public RotateCommand(Perspective perspective, int deltaAngle) {
        super(perspective);
        this.deltaAngle = deltaAngle;
    }

    @Override
    public void execute() {
        if (before == null) {
            before = perspective.saveToMemento();
        }

        perspective.rotate(deltaAngle);
        after = perspective.saveToMemento();

    }

    @Override
    public void undo() {perspective.restoreFromMemento(before);

    }

    @Override
    public void redo() {perspective.restoreFromMemento(after);

    }
}
