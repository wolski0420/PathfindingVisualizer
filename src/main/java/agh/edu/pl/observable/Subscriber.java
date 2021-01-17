package agh.edu.pl.observable;

import agh.edu.pl.data.Field;

public interface Subscriber {
    void update(Field field);
}
