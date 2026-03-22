package org.example.labo5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;

public class Perspective extends Subject implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private static final double MIN_ZOOM = 0.1;

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
        setZoomFactor(zoomFactor + delta);
    }

    public void translate(int dx, int dy) {
        if (zoomFactor <= 1.0) {
            return;
        }
        setOffsets(offsetX + dx, offsetY + dy);
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = Math.max(MIN_ZOOM, zoomFactor);
        notifyObservers();
    }

    public void setOffsets(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
        notifyObservers();
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

    public void setViewportSize(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    public String getId() {
        return id;
    }
}
