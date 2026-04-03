package org.example.labo5.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveClipboard;

public class CopyAllStrategy implements CopyPasteStrategy {

    @Override
    public void copy(Perspective source, PerspectiveClipboard clipboard) {
        clipboard.setContent(
                source.getZoomFactor(),
                source.getOffsetX(),
                source.getOffsetY(),
                this
        );
    }

    @Override
    public void paste(PerspectiveClipboard clipboard, Perspective target) {
        if (clipboard.getZoomFactor() != null) {
            target.setZoomFactor(clipboard.getZoomFactor());
        }

        if (clipboard.getOffsetX() != null && clipboard.getOffsetY() != null) {
            target.setOffsets(clipboard.getOffsetX(), clipboard.getOffsetY());
        }
    }

    @Override
    public String getName() {
        return "Tout";
    }
}