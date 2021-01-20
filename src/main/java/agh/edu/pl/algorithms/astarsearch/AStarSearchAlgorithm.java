package agh.edu.pl.algorithms.astarsearch;

import agh.edu.pl.algorithms.Algorithm;
import agh.edu.pl.algorithms.AlgorithmData;
import agh.edu.pl.data.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class AStarSearchAlgorithm extends AlgorithmData implements Algorithm {
    private double[][] gValues;
    private double[][] fValues;
    private Point[][] predecessors;
    private final Set<Point> open;
    private final Set<Point> closed;
    private final LinkedList<Field> foundPath;
    private boolean started;
    private boolean finished;

    public AStarSearchAlgorithm(Parameters parameters, Board board) {
        super(parameters, board);
        open = new HashSet<>();
        closed = new HashSet<>();
        foundPath = new LinkedList<>();
        reset();
    }

    @Override
    public boolean finished() {
        return finished;
    }

    @Override
    public void nextStep() {
        if(finished) return;

        if(!started){
            for (int y = 0; y < board.getHeight(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {
                    gValues[y][x] = Double.POSITIVE_INFINITY;
                    fValues[y][x] = Double.POSITIVE_INFINITY;
                    predecessors[y][x] = null;
                }
            }

            Point src = parameters.getSource();
            gValues[src.y][src.x] = 0;
            fValues[src.y][src.x] = distanceBetween(src, parameters.getTarget());
            open.add(src);

            started = true;
        }

        if(!open.isEmpty()){
            Point current = getLowestOpen();
            if(parameters.getTarget().equals(current)){
                open.clear();
                return;
            }

            if(board.getField(current).getStatus() != Status.SOURCE &&
                    board.getField(current).getStatus() != Status.TARGET)
                board.getField(current).setStatus(Status.VISITED);

            open.remove(current);
            closed.add(current);

            current.getNeighbours().forEach(nb -> {
                if(board.inBounds(nb) && !closed.contains(nb) && board.getField(nb).getStatus() != Status.BLOCKED) {
                    double distance = gValues[current.y][current.x] + 1;

                    if(open.contains(nb) && distance < gValues[nb.y][nb.x]){
                        open.remove(nb);
                    }

                    if(closed.contains(nb) && distance < gValues[nb.y][nb.x]){
                        closed.remove(nb);
                    }

                    if(!open.contains(nb) && !closed.contains(nb)){
                        open.add(nb);
                        gValues[nb.y][nb.x] = distance;
                        fValues[nb.y][nb.x] = distance + distanceBetween(nb, parameters.getTarget());

                        predecessors[nb.y][nb.x] = current;
                        if(board.getField(nb).getStatus() != Status.TARGET)
                            board.getField(nb).setStatus(Status.PROCESSING);
                    }
                }
            });
        }
        else{
            finished = true;
            Point current = parameters.getTarget();
            while(current != null){
                foundPath.add(board.getField(current));
                current = predecessors[current.y][current.x];
            }

            Collections.reverse(foundPath);
        }
    }

    @Override
    public LinkedList<Field> getPath() {
        return foundPath;
    }

    @Override
    public void reset() {
        started = false;
        finished = false;
        gValues = new double[board.getHeight()][board.getWidth()];
        fValues = new double[board.getHeight()][board.getWidth()];
        predecessors = new Point[board.getHeight()][board.getWidth()];
        foundPath.clear();
        open.clear();
        closed.clear();
    }

    @Override
    public String getName() {
        return "A*Search";
    }

    private int distanceBetween(Point point1, Point point2){
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }

    private Point getLowestOpen(){
        return open.stream().min((o1, o2) -> {
            if(fValues[o1.y][o1.x] == fValues[o2.y][o2.x])
                return Double.compare(distanceBetween(o1, parameters.getTarget()), distanceBetween(o2, parameters.getTarget()));
            return Double.compare(fValues[o1.y][o1.x], fValues[o2.y][o2.x]);
        }).orElse(null);
    }
}
