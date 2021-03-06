package agh.edu.pl.data;

import java.util.Arrays;

public class Board {
    private Field[][] fields;
    private int height;
    private int width;

    public Board() {
        height = -1;
        width = -1;
    }

    public void resize(int width, int height){
        this.height = height;
        this.width = width;
        fields = new Field[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                fields[y][x] = new Field(new Point(x,y));
            }
        }
    }

    public Field getField(Point point){
        return fields[point.y][point.x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean inBounds(Point point){
        return point.x >= 0 && point.x < width && point.y >= 0 && point.y < height;
    }

    public void clearExecuted(){
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field.getStatus() == Status.PROCESSING ||
                        field.getStatus() == Status.VISITED ||
                        field.getStatus() == Status.IN_PATH)
                .forEach(field -> field.setStatus(Status.RESETED));
    }

    public void clearAll(){
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field.getStatus() != Status.TARGET && field.getStatus() != Status.SOURCE)
                .forEach(field -> field.setStatus(Status.RESETED));
    }
}
