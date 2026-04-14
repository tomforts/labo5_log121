package org.example.labo5.controller.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveClipboard;

public class CopyNothingStrategy implements CopyPasteStrategy {

    @Override
    public void copy(Perspective source, PerspectiveClipboard clipboard) {
        clipboard.setContent(
                null,
                null,
               null,
                null,
                this
        );
    }

    @Override
    public void paste(PerspectiveClipboard clipboard, Perspective target) {}

    @Override
    public String getName() {
        return "Rien";
    }
}