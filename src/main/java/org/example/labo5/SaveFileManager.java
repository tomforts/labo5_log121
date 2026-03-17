package org.example.labo5;

import java.io.*;

public class SaveFileManager {

    public void save(ImageDocument imageDocument, String filePath) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(imageDocument);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public ImageDocument load(String filePath) {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(filePath))) {
            return (ImageDocument) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
            return null;
        }
    }
}
