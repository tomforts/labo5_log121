package org.example.labo5.controller.command;

/**
 * Interface du patron Commande.
 * Toute action réversible sur une perspective doit l'implémenter.
 */
public interface Command {

    /** Exécute la commande. */
    void execute();

    /** Annule la commande en restaurant l'état précédent. */
    void undo();

    /** Refaire la commande après une annulation. */
    void redo();
}