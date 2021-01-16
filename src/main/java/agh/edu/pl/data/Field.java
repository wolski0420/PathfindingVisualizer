package agh.edu.pl.data;

public class Field {
    private Status currentStatus = Status.RESETED;

    public void reset(){
        currentStatus = Status.RESETED;
    }

    public boolean isReseted(){
        return currentStatus == Status.RESETED;
    }

    public void process(){
        currentStatus = Status.PROCESSING;
    }

    public boolean isBeingProcessed(){
        return currentStatus == Status.PROCESSING;
    }

    public void visit(){
        currentStatus = Status.VISITED;
    }

    public boolean isVisited(){
        return currentStatus == Status.VISITED;
    }

    public void toPath(){
        currentStatus = Status.IN_PATH;
    }

    public boolean isInPath(){
        return currentStatus == Status.IN_PATH;
    }

    public void block(){
        currentStatus = Status.BLOCKED;
    }

    public boolean isBlocked(){
        return currentStatus == Status.BLOCKED;
    }

    @Override
    public String toString() {
        return currentStatus.toString();
    }
}
