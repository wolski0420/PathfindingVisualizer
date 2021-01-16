package agh.edu.pl.data;

public enum Status {
    RESETED, PROCESSING, VISITED, IN_PATH, BLOCKED;

    @Override
    public String toString() {
        if(this==PROCESSING) return "P";
        if(this==VISITED) return "V";
        if(this==IN_PATH) return "I";
        if(this==BLOCKED) return "B";
        else return "R";
    }
}
