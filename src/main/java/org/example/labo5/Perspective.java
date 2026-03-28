package org.example.labo5;

import org.example.labo5.observer.Subject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;

public class Perspective extends Subject implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private double zoomFactor;
    private int offsetX;
    private int offsetY;
    private int viewportWidth;
    private int viewportHeight;

    public Perspective(String id) {
        this.id = id;
        this.zoomFactor = 1.0;
        this.offsetX = 0;
        this.offsetY = 0;
        this.viewportWidth = 0;
        this.viewportHeight = 0;
    }

    public void zoom(double delta) {
        zoomFactor += delta;
        if (zoomFactor < 0.1) {
            zoomFactor = 0.1;
        }
    }

    public void translate(int dx, int dy) {
        if (zoomFactor <= 1.0) {
            return;
        }
        offsetX += dx;
        offsetY += dy;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void setOffsets(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    public String getId() {
        return id;
    }
}
