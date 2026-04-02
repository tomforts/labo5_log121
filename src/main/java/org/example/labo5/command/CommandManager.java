package org.example.labo5.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager {

    private static CommandManager instance = new CommandManager();
    private Deque<Command> undoStack = new ArrayDeque<>();

    private CommandManager() {}

    public static CommandManager getInstance() {
        return instance;
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public void accumulateTranslation(TranslateCommand command) {
        if (!undoStack.isEmpty() && undoStack.peek() instanceof TranslateCommand) {
            TranslateCommand lastCmd = (TranslateCommand) undoStack.peek();
            lastCmd.accumulate(command.getDx(), command.getDy());
        } else {
            undoStack.push(command);
        }
    }

    public TranslateCommand getLastCommand() {
        return undoStack.isEmpty() ? null : (TranslateCommand) undoStack.peek();
    }

    public void undoLastCommand() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().undo();
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
}
