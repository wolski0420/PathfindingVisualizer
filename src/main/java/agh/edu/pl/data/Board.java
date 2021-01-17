package agh.edu.pl.data;

import java.util.Arrays;

public class Board {
    private Field[][] fields;
    private int height;
    private int width;

    public Board(int width, int height) {
        resize(width, height);
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
