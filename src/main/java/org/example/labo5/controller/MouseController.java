package org.example.labo5.controller;

import org.example.labo5.view.PerspectiveView;
import org.example.labo5.controller.command.TranslateCommand;
import org.example.labo5.controller.command.ZoomCommand;
import org.example.labo5.controller.command.Command;
import org.example.labo5.controller.command.CommandManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
/**
 * Contrôleur souris pour les interactions sur une PerspectiveView.
 * Implémente ViewController et gère latranslation
 * et la zoom via le patron Commande.
 */
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

    /**
     * Mémorise la position initiale du curseur au moment du clic.
     *
     * @param event événement souris
     */
    @Override
    public void mousePressed(MouseEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }

    /**
     * Calcule le déplacement depuis le dernier point connu, l'ajuste selon le zoom
     * courant
     *
     * @param event événement souris
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        if (event.getSource() instanceof PerspectiveView) {
            PerspectiveView view = (PerspectiveView) event.getSource();
            int dx = event.getX() - lastX;
            int dy = event.getY() - lastY;
            double zoom = view.getPerspective() != null ? view.getPerspective().getZoomFactor() : 1.0;
            if (zoom > 1) {
                dx = (int) Math.round(dx / zoom);
                dy = (int) Math.round(dy / zoom);
            }
            handleTranslation(view, -dx, -dy);
            lastX = event.getX();
            lastY = event.getY();
        }
    }

    /**
     * Convertit la rotation de la molette en delta de zoom
     * Molette vers le haut → zoom avant (+0.1), vers le bas → zoom arrière (−0.1).
     *
     * @param event événement molette
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (event.getSource() instanceof PerspectiveView) {
            PerspectiveView view = (PerspectiveView) event.getSource();
            double delta = (event.getWheelRotation() < 0) ? 0.1 : -0.1;
            handleZoom(view, delta);
        }
    }
}