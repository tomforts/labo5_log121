package org.example.labo5.command;

public interface Command {

    void execute();

    void undo();

    void redo();
}