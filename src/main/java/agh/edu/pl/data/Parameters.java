package agh.edu.pl.data;

public class Parameters {
    private Point source;
    private Point target;
    private final Board board;

    public Parameters(Board board) {
        this.board = board;
    }

    public Point getSource() {
        return source;
    }

    public void setSource(Point source) {
        this.source = source;
        board.getField(source).setStatus(Status.SOURCE);
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
        board.getField(target).setStatus(Status.TARGET);
    }
}
