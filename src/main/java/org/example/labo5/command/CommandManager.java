package org.example.labo5.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager {

    private final static CommandManager instance = new CommandManager();
    private Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    private CommandManager() {}

    public static CommandManager getInstance() {
        return instance;
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undoLastCommand() {
        if (canUndo()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }
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
