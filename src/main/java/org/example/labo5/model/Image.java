package org.example.labo5.model;

import org.example.labo5.model.observer.Subject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends Subject implements java.io.Serializable{
    private String imagePath;
    private transient BufferedImage bufferedImage;

    public void loadImage(String path) throws IOException {
        this.imagePath = path;
        this.bufferedImage = ImageIO.read(new File(path));

        if (bufferedImage == null) {
            throw new IOException("Format d'image non supporté.");
        }
        notifyObservers();
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
}
