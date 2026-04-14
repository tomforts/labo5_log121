package org.example.labo5.controller.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveClipboard;

public class CopyTranslationStrategy implements CopyPasteStrategy {

    @Override
    public void copy(Perspective source, PerspectiveClipboard clipboard) {
        clipboard.setContent(
                null,
                source.getOffsetX(),
                source.getOffsetY(),
                null,
                this
        );
    }

    @Override
    public void paste(PerspectiveClipboard clipboard, Perspective target) {
        if (clipboard.getOffsetX() != null && clipboard.getOffsetY() != null) {
            target.setOffsets(clipboard.getOffsetX(), clipboard.getOffsetY());
        }
    }

    @Override
    public String getName() {
        return "Translation";
    }
}