package agh.edu.pl.algorithms.dijkstra;

import agh.edu.pl.algorithms.Algorithm;
import agh.edu.pl.algorithms.AlgorithmData;
import agh.edu.pl.data.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DijkstraAlgorithm extends AlgorithmData implements Algorithm {
    private double[][] distances;
    private Point[][] predecessors;
    private boolean started;
    private boolean finished;
    private final PriorityQueue<QueueNode> queue;
    private final LinkedList<Field> foundPath;

    public DijkstraAlgorithm(Parameters parameters, Board board) {
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
                    distances[y][x] = Double.POSITIVE_INFINITY;
                    predecessors[y][x] = null;
                }
            }

            Point src = parameters.getSource();
            distances[src.y][src.x] = 0;
            queue.add(new QueueNode(new Point(src.x, src.y), distances[src.y][src.x]));

            started = true;
        }

        if(!queue.isEmpty()){
            Point current = queue.poll().getPoint();

            if(board.getField(current).getStatus() != Status.VISITED){
                if(current.equals(parameters.getTarget())){
                    queue.clear();
                    return;
                }

                if(board.getField(current).getStatus() != Status.SOURCE &&
                        board.getField(current).getStatus() != Status.TARGET)
                    board.getField(current).setStatus(Status.VISITED);

                current.getNeighbours().forEach(nb -> {
                    if(board.inBounds(nb) && board.getField(nb).getStatus() != Status.VISITED &&
                            board.getField(nb).getStatus() != Status.BLOCKED){

                        // relaxation below
                        if(distances[nb.y][nb.x] > distances[current.y][current.x] + 1){
                            distances[nb.y][nb.x] = distances[current.y][current.x] + 1;
                            predecessors[nb.y][nb.x] = current;
                            queue.add(new QueueNode(nb, distances[nb.y][nb.x]));

                            if(board.getField(nb).getStatus() != Status.TARGET)
                                board.getField(nb).setStatus(Status.PROCESSING);
                        }

                    }
                });
            }
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
        distances = new double[board.getHeight()][board.getWidth()];
        predecessors = new Point[board.getHeight()][board.getWidth()];
        foundPath.clear();
        queue.clear();
    }

    @Override
    public String getName() {
        return "Dijkstra";
    }
}
