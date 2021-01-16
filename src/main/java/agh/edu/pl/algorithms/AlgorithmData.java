package agh.edu.pl.algorithms;

import agh.edu.pl.data.Board;
import agh.edu.pl.data.Parameters;

public class AlgorithmData{
    protected final Parameters parameters;
    protected final Board board;

    protected AlgorithmData(Parameters parameters, Board board) {
        this.parameters = parameters;
        this.board = board;
    }
}
