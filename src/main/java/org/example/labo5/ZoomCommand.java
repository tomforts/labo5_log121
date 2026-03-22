package org.example.labo5;

public class ZoomCommand extends PerspectiveCommand {

    private double deltaZoom;
    private double oldZoom;

    public ZoomCommand(Perspective perspective, double deltaZoom) {
        super(perspective);
        this.deltaZoom = deltaZoom;
        this.oldZoom   = perspective.getZoomFactor();
    }

    @Override
    public void execute() {
        perspective.zoom(deltaZoom);
    }

    @Override
    public void undo() {
        perspective.setZoomFactor(oldZoom);
    }
}
