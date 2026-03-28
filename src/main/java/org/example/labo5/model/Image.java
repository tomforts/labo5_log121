package org.example.labo5.model;

import org.example.labo5.observer.Subject;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;

public class Image extends Subject implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private String imagePath;
    private transient BufferedImage bufferedImage;
    private int width;
    private int height;

    public void loadImage(String path) throws IOException {
        this.imagePath = path;
        this.bufferedImage = ImageIO.read(new File(path));

        if (bufferedImage == null) {
            throw new IOException("Format d'image non supporté.");
        }

        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
    }

    public void changeImage(String path) throws IOException {
        loadImage(path);
    }

    public String getImagePath() {
        return imagePath;
    }

    public BufferedImage getBufferedImage() {
        if (bufferedImage == null && imagePath != null) {
            try {
                bufferedImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                return null;
            }
        }
        return bufferedImage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
