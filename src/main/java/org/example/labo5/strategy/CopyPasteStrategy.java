package org.example.labo5.strategy;

import org.example.labo5.model.Perspective;
import org.example.labo5.model.PerspectiveClipboard;

public interface CopyPasteStrategy {

    void copy(Perspective source, PerspectiveClipboard clipboard);

    void paste(PerspectiveClipboard clipboard, Perspective target);

    String getName();
}