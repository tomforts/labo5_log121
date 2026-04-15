package org.example.labo5.model;

import org.example.labo5.model.observer.Subject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Modèle représentant une image chargée depuis le système de fichiers.
 * Étend Subject pour notifier les vues lors d'un changement.
 * Sérialisable ;
 */
public class Image extends Subject implements java.io.Serializable{
    private String imagePath;
    private transient BufferedImage bufferedImage;

    /**
     * Charge une image depuis le chemin spécifié et notifie les observateurs.
     *
     * @param path chemin vers le fichier image
     * @throws IOException si le fichier est introuvable ou pas le bon format
     */
    public void loadImage(String path) throws IOException {
        this.imagePath = path;
        this.bufferedImage = ImageIO.read(new File(path));

        if (bufferedImage == null) {
            throw new IOException("Format d'image non supporté.");
        }
        notifyObservers();
    }

    /**
     * Retourne l'image en mémoire, en la rechargeant depuis le disque si nécessaire.
     *
     * @return BufferedImage ou null en cas d'échec
     */
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
