package agh.edu.pl.executable;

import agh.edu.pl.algorithms.Algorithm;
import agh.edu.pl.data.Status;
import javafx.animation.AnimationTimer;

// generally in java, we can extend only one class, that's why I have nested AnimationTimer here,
// I wanted to leave a possibility to extend another class here

public class AlgorithmExecutor {
    private final AnimationTimer executor;
    private long prevTime;
    private long delay;
    private Algorithm algorithm;
    private int pathIterIndex = 0;

    public AlgorithmExecutor() {
        this.prevTime = 0;
        this.delay = 100000000;
        this.algorithm = null;
        this.executor = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(now - prevTime < delay) return;
                prevTime = now;

                if(algorithm.finished()){
                    algorithm.getPath().get(pathIterIndex++).setStatus(Status.IN_PATH);
                    if(pathIterIndex == algorithm.getPath().size()){
                        stop();
                    }
                }
                else{
                    algorithm.nextStep();
                }
            }
        };
    }

    public void start(){
        executor.start();
    }

    public void pause(){
        executor.stop();
    }

    public void reset(){
        executor.stop();
        algorithm.reset();
    }

    public void setDelay(long millisDelay) {
        this.delay = millisDelay*1000000;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
