package agh.edu.pl.algorithms.greedybf;

import agh.edu.pl.algorithms.Algorithm;
import agh.edu.pl.algorithms.AlgorithmData;
import agh.edu.pl.algorithms.dijkstra.QueueNode;
import agh.edu.pl.data.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class GreedyBestFirstAlgorithm extends AlgorithmData implements Algorithm {
    private Point[][] predecessors;
    private boolean started;
    private boolean finished;
    private final PriorityQueue<QueueNode> queue;
    private final LinkedList<Field> foundPath;

    public GreedyBestFirstAlgorithm(Parameters parameters, Board board) {
        super(parameters, board);
        queue = new PriorityQueue<>();
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
                    predecessors[y][x] = null;
                }
            }

            Point src = parameters.getSource();
            queue.add(new QueueNode(src, distanceBetween(src, parameters.getTarget())));

            started = true;
        }

        if(!queue.isEmpty()){
            Point current = queue.poll().getPoint();
            if(parameters.getTarget().equals(current)){
                queue.clear();
                return;
            }

            if(board.getField(current).getStatus() != Status.SOURCE &&
                    board.getField(current).getStatus() != Status.TARGET)
                board.getField(current).setStatus(Status.VISITED);

            current.getNeighbours().forEach(nb -> {
                if(board.inBounds(nb) && board.getField(nb).getStatus() != Status.BLOCKED &&
                        board.getField(nb).getStatus() != Status.PROCESSING &&
                        board.getField(nb).getStatus() != Status.VISITED &&
                        board.getField(nb).getStatus() != Status.SOURCE){

                    double distanceToTarget = distanceBetween(nb, parameters.getTarget());
                    queue.add(new QueueNode(nb, distanceToTarget));
                    predecessors[nb.y][nb.x] = current;

                    if(board.getField(nb).getStatus() != Status.TARGET)
                        board.getField(nb).setStatus(Status.PROCESSING);
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
        predecessors = new Point[board.getHeight()][board.getWidth()];
        foundPath.clear();
        queue.clear();
    }

    @Override
    public String getName() {
        return "GreedyBF";
    }

    private int distanceBetween(Point point1, Point point2){
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }
}
