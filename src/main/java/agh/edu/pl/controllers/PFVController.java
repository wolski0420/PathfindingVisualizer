package agh.edu.pl.controllers;

import agh.edu.pl.data.Field;
import agh.edu.pl.data.Point;
import agh.edu.pl.data.Status;
import agh.edu.pl.executable.PathFinder;
import agh.edu.pl.observable.Subscriber;
import agh.edu.pl.translators.StatusToColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class PFVController implements Subscriber {
    private PathFinder pathFinder;
    @FXML
    public Menu algorithmMenu;
    @FXML
    public Button startPauseButton;
    @FXML
    public GridPane gridPane;
    @FXML
    public CheckMenuItem tenSizeMenuItem;
    @FXML
    public CheckMenuItem fifteenSizeMenuItem;
    @FXML
    public CheckMenuItem twentySizeMenuItem;
    @FXML
    public CheckMenuItem addModeButton;

    @FXML
    public void initialize(){
        setStartPauseButton();
    }

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        initBoard();
    }

    private void initBoard(){
        for (int y = 0; y < pathFinder.getBoard().getHeight(); y++) {
            for (int x = 0; x < pathFinder.getBoard().getWidth(); x++) {
                Field field = pathFinder.getBoard().getField(new Point(x, y));
                field.addSubscriber(this);
                Rectangle rectangle = new Rectangle(50, 50);
                rectangle.setFill(StatusToColor.translate(field.getStatus()));
                gridPane.add(rectangle, x, y);
            }
        }
    }

    private void setStartPauseButton(){
        startPauseButton.setOnAction(event -> {
            if(startPauseButton.getText().equals("Start")){
                pathFinder.start();
                startPauseButton.setText("Pause");
                startPauseButton.setStyle("-fx-background-color: #fd2b2b");
            }
            else{
                pathFinder.pause();
                startPauseButton.setText("Start");
                startPauseButton.setStyle("-fx-background-color: #00c700");
            }
        });
    }

    @FXML
    public void handleGenerateMenuItem(ActionEvent actionEvent) {
        pathFinder.generateObstacles();
    }

    @FXML
    public void handleResetButton(ActionEvent actionEvent) {
        pathFinder.reset();

        if(startPauseButton.getText().equals("Pause"))
            startPauseButton.fire();
    }

    @Override
    public void update(Field field) {
        Rectangle rectangle = (Rectangle) gridPane.getChildren().get(
                field.getLocation().y * pathFinder.getBoard().getWidth() + field.getLocation().x
        );

        rectangle.setFill(StatusToColor.translate(field.getStatus()));
    }
}
