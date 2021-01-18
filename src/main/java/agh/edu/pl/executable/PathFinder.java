package agh.edu.pl.executable;

import agh.edu.pl.algorithms.Algorithm;
import agh.edu.pl.algorithms.dijkstra.DijkstraAlgorithm;
import agh.edu.pl.data.Board;
import agh.edu.pl.data.Parameters;
import agh.edu.pl.data.Point;
import agh.edu.pl.data.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PathFinder {
    private final Board board;
    private final Parameters parameters;
    private final AlgorithmExecutor algorithmExecutor;
    private final List<Algorithm> algorithms;
    private final Random random;

    public PathFinder() {
        board = new Board();
        parameters = new Parameters(board);
        algorithmExecutor = new AlgorithmExecutor();
        algorithms = new ArrayList<>();
        random = new Random();
        init();
    }

    private void init(){
        resize(15,15);

        algorithms.add(new DijkstraAlgorithm(parameters, board));
        // @TODO more algorithms to add
        algorithmExecutor.setAlgorithm(algorithms.get(0));

        changeSource(new Point(0,7));
        changeTarget(new Point(14,7));
    }

    public void resize(int width, int height){
        if(width != board.getWidth() || height != board.getHeight())
            board.resize(width, height);
    }

    public void chooseAlgorithm(Algorithm algorithm){
        algorithmExecutor.setAlgorithm(algorithm);
    }

    public void generateObstacles(){
        for (int i = 0; i < board.getHeight() * board.getWidth() / 4; i++) {
            int x = random.nextInt(board.getWidth());
            int y = random.nextInt(board.getHeight());

            Point check = new Point(x, y);
            if(!check.equals(parameters.getSource()) && !check.equals(parameters.getTarget()))
                board.getField(check).setStatus(Status.BLOCKED);
        }
    }

    public void addObstacle(Point point){
        board.getField(point).setStatus(Status.BLOCKED);
    }

    public void changeSource(Point point){
        parameters.setSource(point);
    }

    public void changeTarget(Point point){
        parameters.setTarget(point);
    }

    public void start(){
        algorithmExecutor.start();
    }

    public void pause(){
        algorithmExecutor.pause();
    }

    public void reset(){
        algorithmExecutor.reset();
        board.clear();
    }

    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public Board getBoard() {
        return board;
    }
}
