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

    public Field[][] getFields() {
        return fields;
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

    public void clear(){
        Arrays.stream(fields).flatMap(Arrays::stream).forEach(field -> field.setStatus(Status.RESETED));
    }
}
