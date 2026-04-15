package org.example.labo5.controller.command;

import java.util.ArrayDeque;
import java.util.Deque;
/**
 * Singleton gérant l'historique des commandes (undo/redo).
 * Maintient deux piles : une pour les commandes annulables, une pour les rejouables.
 */
public class CommandManager {

    private final static CommandManager instance = new CommandManager();
    private Deque<Command> undoStack = new ArrayDeque<>();
    private Deque<Command> redoStack = new ArrayDeque<>();

    private CommandManager() {}

    /**
     * Permet d'accéder à l'instance unique du gestionnaire (Singleton).
     *
     * @return CommandManager gestionnaire de commandes
     */
    public static CommandManager getInstance() {
        return instance;
    }

    /**
     * Exécute une commande, l'empile dans l'historique et vide la pile redo.
     *
     * @param command commande à exécuter
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Annule la dernière commande exécutée et la déplace vers la pile redo.
     * Sans effet si la pile undo est vide.
     */
    public void undoLastCommand() {
        if (canUndo()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    /**
     * Refait la dernière commande annulée et la remet dans la pile undo.
     * Sans effet si la pile redo est vide.
     */
    public void redoLastCommand() {
        if (canRedo()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() { return !redoStack.isEmpty(); }
}
