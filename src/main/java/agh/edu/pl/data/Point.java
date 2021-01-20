package agh.edu.pl.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Point> getNeighbours(){
        return new ArrayList<>(
                Arrays.asList(
                        new Point(x, y+1),
                        new Point(x+1, y),
                        new Point(x, y-1),
                        new Point(x-1, y)
                )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
