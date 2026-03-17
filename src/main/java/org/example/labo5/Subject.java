package org.example.labo5;
import java.util.LinkedList;
import java.util.List;

public abstract class Subject {

    private List<Observer> listObservers = new LinkedList<Observer>();

    public void attach(Observer o) {
        listObservers.add(o);
    }

    public void detach(Observer o) {
        listObservers.remove(o);
    }

    public void notifyObservers() {
        for (Observer obv : listObservers) {
            obv.update(this);
        }
    }
}
