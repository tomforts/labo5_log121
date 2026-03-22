package org.example.labo5;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseController extends MouseAdapter implements ViewController {

    private int lastX;
    private int lastY;

    @Override
    public void handleZoom(PerspectiveView view, double delta) {
        if (view == null || view.getPerspective() == null) return;
        Command cmd = new ZoomCommand(view.getPerspective(), delta);
        CommandManager.getInstance().executeCommand(cmd);
    }

    @Override
    public void handleTranslation(PerspectiveView view, int dx, int dy) {
        if (view == null || view.getPerspective() == null) return;
        Command cmd = new TranslateCommand(view.getPerspective(), dx, dy);
        CommandManager.getInstance().executeCommand(cmd);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() instanceof PerspectiveView) {
            PerspectiveView view = (PerspectiveView) e.getSource();
            int dx = e.getX() - lastX;
            int dy = e.getY() - lastY;
            double zoom = view.getPerspective() != null ? view.getPerspective().getZoomFactor() : 1.0;
            if (zoom > 1) {
                dx = (int) Math.round(dx / zoom);
                dy = (int) Math.round(dy / zoom);
            }
            handleTranslation(view, -dx, -dy);
            lastX = e.getX();
            lastY = e.getY();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getSource() instanceof PerspectiveView) {
            PerspectiveView view = (PerspectiveView) e.getSource();
            double delta = (e.getWheelRotation() < 0) ? 0.1 : -0.1;
            handleZoom(view, delta);
        }
    }
}