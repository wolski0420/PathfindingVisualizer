package agh.edu.pl.data;

public class Parameters {
    private Point source;
    private Point target;
    private final Board board;

    public Parameters(Board board) {
        this.board = board;
        target = null;
        source = null;
    }

    public Point getSource() {
        return source;
    }

    public void setSource(Point source) {
        if(this.source != null && board.inBounds(this.source))
            board.getField(this.source).setStatus(Status.RESETED);

        this.source = source;
        board.getField(source).setStatus(Status.SOURCE);
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        if(this.target != null && board.inBounds(this.target))
            board.getField(this.target).setStatus(Status.RESETED);

        this.target = target;
        board.getField(target).setStatus(Status.TARGET);
    }
}
