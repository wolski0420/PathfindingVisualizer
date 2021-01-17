package agh.edu.pl.translators;

import agh.edu.pl.data.Status;
import javafx.scene.paint.Color;

public class StatusToColor {
    private final static Color[] colors = {
            Color.WHITE, Color.LIGHTGREEN, Color.LIGHTBLUE, Color.ORANGE, Color.BLACK, Color.RED, Color.PURPLE
    };

    public static Color translate(Status status){
        return colors[status.ordinal()];
    }
}
