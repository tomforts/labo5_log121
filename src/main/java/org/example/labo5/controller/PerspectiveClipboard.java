package org.example.labo5.controller;

import org.example.labo5.controller.strategy.CopyPasteStrategy;
/**
 * Représente un presse-papiers pour une perspective.
 *
 * Cette classe permet de stocker temporairement l’état d’une perspective
 * (zoom, translation et rotation) afin de le copier et de le coller vers
 * une autre perspective.
 */
public class PerspectiveClipboard {

    private Double zoomFactor;
    private Integer offsetX;
    private Integer offsetY;
    private Integer angle;
    private CopyPasteStrategy strategy;
    private boolean hasContent;

    public boolean hasContent() {
        return hasContent;
    }

    /**
     * Enregistre un état de perspective dans le presse-papiers.
     *
     * @param zoomFactor facteur de zoom à stocker
     * @param offsetX    décalage horizontal à stocker
     * @param offsetY    décalage vertical à stocker
     * @param angle      angle de rotation à stocker
     * @param strategy   stratégie à appliquer lors du collage
     */
    public void setContent(Double zoomFactor, Integer offsetX, Integer offsetY, Integer angle,CopyPasteStrategy strategy) {
        this.zoomFactor = zoomFactor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.strategy = strategy;
        this.angle = angle;
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

    public Integer getAngle() {return angle; }

    public CopyPasteStrategy getStrategy() {
        return strategy;
    }
}