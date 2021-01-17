package agh.edu.pl.translators;

import agh.edu.pl.data.Status;
import javafx.scene.paint.Color;

public class StatusToColor {
    private final static Color[] colors = {
            Color.WHITE, Color.GREEN, Color.BLUE, Color.ORANGE, Color.BLACK
    };

    public static Color translate(Status status){
        System.out.println(status.ordinal());
        return colors[status.ordinal()];
    }
}
