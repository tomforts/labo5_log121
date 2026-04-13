package org.example.labo5.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveClipboard;

public class CopyZoomStrategy implements CopyPasteStrategy {

    @Override
    public void copy(Perspective source, PerspectiveClipboard clipboard) {
        clipboard.setContent(
                source.getZoomFactor(),
                null,
                null,
                null,
                this
        );
    }

    @Override
    public void paste(PerspectiveClipboard clipboard, Perspective target) {
        if (clipboard.getZoomFactor() != null) {
            target.setZoomFactor(clipboard.getZoomFactor());
        }
    }

    @Override
    public String getName() {
        return "Zoom";
    }
}