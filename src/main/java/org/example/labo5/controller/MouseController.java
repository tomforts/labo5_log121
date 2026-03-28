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

    @Override
    public void handleZoom(PerspectiveView view, double delta) {
        if (view == null || view.getPerspective() == null) return;
        Perspective perspective = view.getPerspective();
        Command cmd = new ZoomCommand(perspective, delta);
        CommandManager.getInstance().executeCommand(cmd);
    }

    @Override
    public void handleTranslation(PerspectiveView view, int dx, int dy, int extra) {
        if (view == null || view.getPerspective() == null) return;
        Perspective perspective = view.getPerspective();
        Command cmd = new TranslateCommand(perspective, dx, dy);
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
            handleTranslation(view, dx, dy, 0);
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
