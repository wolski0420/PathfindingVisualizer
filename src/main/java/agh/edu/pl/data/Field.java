package agh.edu.pl.data;

import agh.edu.pl.observable.Observable;

public class Field extends Observable {
    private final Point location;
    private Status status = Status.RESETED;

    public Field(Point location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        notifySubscribers();
    }

    public Point getLocation() {
        return location;
    }

    @Override
    protected void notifySubscribers() {
        subscribers.forEach(subscriber -> subscriber.update(this));
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
