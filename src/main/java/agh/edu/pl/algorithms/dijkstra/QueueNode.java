package agh.edu.pl.algorithms.dijkstra;

import agh.edu.pl.data.Point;

public class QueueNode implements Comparable<QueueNode>{
    private final Point point;
    private final double distance;

    public QueueNode(Point point, double distanceFromSource) {
        this.point = point;
        this.distance = distanceFromSource;
    }

    public double getDistance() {
        return distance;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public int compareTo(QueueNode queueNode) {
        return Double.compare(distance, queueNode.getDistance());
    }
}
