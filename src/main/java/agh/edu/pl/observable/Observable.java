package agh.edu.pl.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    protected final List<Subscriber> subscribers;

    public Observable() {
        this.subscribers = new ArrayList<>();
    }

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    protected abstract void notifySubscribers();
}
