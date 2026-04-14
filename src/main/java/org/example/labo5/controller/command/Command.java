package org.example.labo5.controller.command;

public interface Command {

    void execute();

    void undo();

    void redo();
}