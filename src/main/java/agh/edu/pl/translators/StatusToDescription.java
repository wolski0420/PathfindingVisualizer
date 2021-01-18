package agh.edu.pl.translators;

import agh.edu.pl.data.Status;

public class StatusToDescription implements Translator<String>{
    private static final String[] descriptions = {
            "free and not busy",
            "being processed",
            "already visited",
            "result path",
            "obstacle",
            "source",
            "target"
    };

    @Override
    public String translate(Status status) {
        return descriptions[status.ordinal()];
    }
}
