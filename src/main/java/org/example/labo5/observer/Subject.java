package org.example.labo5.observer;
import java.util.LinkedList;
import java.util.List;

public abstract class Subject {

    private List<Observer> listObservers = new LinkedList<Observer>();

    public void attach(Observer observer) {
        listObservers.add(observer);
    }

    public void detach(Observer observer) {
        listObservers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer obv : listObservers) {
            obv.update(this);
        }
    }
}
