package org.example.labo5.model;

import org.example.labo5.strategy.CopyPasteStrategy;

public class PerspectiveClipboard {

    private Double zoomFactor;
    private Integer offsetX;
    private Integer offsetY;
    private CopyPasteStrategy strategy;
    private boolean hasContent;

    public void clear() {
        zoomFactor = null;
        offsetX = null;
        offsetY = null;
        strategy = null;
        hasContent = false;
    }

    public boolean hasContent() {
        return hasContent;
    }

    public void setContent(Double zoomFactor, Integer offsetX, Integer offsetY, CopyPasteStrategy strategy) {
        this.zoomFactor = zoomFactor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.strategy = strategy;
        this.hasContent = true;
    }

    public Double getZoomFactor() {
        return zoomFactor;
    }

    public Integer getOffsetX() {
        return offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public CopyPasteStrategy getStrategy() {
        return strategy;
    }
}