package org.example.labo5.controller;

import org.example.labo5.Perspective;
import org.example.labo5.command.Command;
import org.example.labo5.command.CommandManager;
import org.example.labo5.command.ZoomCommand;
import org.example.labo5.command.TranslateCommand;
import org.example.labo5.view.PerspectiveView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseController extends MouseAdapter implements ViewController {

    private int lastX;
    private int lastY;
    private TranslateCommand currentTranslation;

    @Override
    public void handleZoom(PerspectiveView view, double delta) {
        if (view == null || view.getPerspective() == null) return;
        Command cmd = new ZoomCommand(view.getPerspective(), delta);
        CommandManager.getInstance().executeCommand(cmd);
    }

    @Override
    public void handleTranslation(PerspectiveView view, int dx, int dy) {
        if (view == null || view.getPerspective() == null) return;
        
        if (currentTranslation == null) {
            currentTranslation = new TranslateCommand(view.getPerspective(), dx, dy);
            currentTranslation.execute();
        } else {
            currentTranslation.accumulate(dx, dy);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentTranslation != null) {
            CommandManager.getInstance().executeCommand(currentTranslation);
            currentTranslation = null;
        }
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