package agh.edu.pl.algorithms;

import agh.edu.pl.data.Field;

import java.util.LinkedList;

public interface Algorithm {
    boolean finished();
    void nextStep();
    LinkedList<Field> getPath();
    void reset();
}
