package org.example.labo5.model.observer;
/**
 * Interface du patron Observateur.
 * Toute classe souhaitant réagir aux changements d'un Sujet doit l'implémenter.
 */
public interface Observer {
    /**
     * Appelé par le sujet lorsque son état change.
     *
     * @param subject le sujet qui a déclenché la notification
     */
    void update(Subject subject);
}

