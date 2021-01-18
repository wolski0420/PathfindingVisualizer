package agh.edu.pl.translators;

import agh.edu.pl.data.Status;

public interface Translator<T> {
    T translate(Status status);
}
