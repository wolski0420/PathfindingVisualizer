package agh.edu.pl.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {
    protected final List<T> subscribers;

    public Observable() {
        this.subscribers = new ArrayList<>();
    }

    public void addSubscriber(T subscriber){
        subscribers.add(subscriber);
    }

    protected abstract void notifySubscribers();
}
