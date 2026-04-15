package org.example.labo5.model.observer;
import java.util.LinkedList;
import java.util.List;
/**
 * Classe abstraite du patron Observateur.
 * Gère la liste des observateurs et leur notification.
 */
public abstract class Subject {

    /** Enregistre un observateur */
    private List<Observer> listObservers = new LinkedList<Observer>();

    public void attach(Observer observer) {
        listObservers.add(observer);
    }

    /** Retire un observateur. */
    public void detach(Observer observer) {
        listObservers.remove(observer);
    }

    /** Notifie tous les observateurs abonnés */
    public void notifyObservers() {
        for (Observer obv : listObservers) {
            obv.update(this);
        }
    }
}
