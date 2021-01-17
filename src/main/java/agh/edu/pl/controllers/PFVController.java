package agh.edu.pl.controllers;

import agh.edu.pl.data.Field;
import agh.edu.pl.data.Point;
import agh.edu.pl.executable.PathFinder;
import agh.edu.pl.observable.Subscriber;
import agh.edu.pl.translators.StatusToColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class PFVController implements Subscriber {
    private PathFinder pathFinder;
    private ToggleGroup sizeToggleGroup;
    private ToggleGroup algorithmToggleGroup;
    @FXML
    public Menu algorithmMenu;
    @FXML
    public Button startPauseButton;
    @FXML
    public GridPane gridPane;
    @FXML
    public RadioMenuItem tenSizeMenuItem;
    @FXML
    public RadioMenuItem fifteenSizeMenuItem;
    @FXML
    public RadioMenuItem twentySizeMenuItem;
    @FXML
    public CheckMenuItem addModeButton;

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        initBoard();
        setStartPauseButton();
        setToggleGroup();
        setRadioMenuItems();
        setAlgorithmMenu();
    }

    private void initBoard(){
        gridPane.getChildren().clear();
        for (int y = 0; y < pathFinder.getBoard().getHeight(); y++) {
            for (int x = 0; x < pathFinder.getBoard().getWidth(); x++) {
                Field field = pathFinder.getBoard().getField(new Point(x, y));
                field.addSubscriber(this);
                Rectangle rectangle = new Rectangle(30, 30);
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

    private void setToggleGroup(){
        sizeToggleGroup = new ToggleGroup();
        tenSizeMenuItem.setToggleGroup(sizeToggleGroup);
        fifteenSizeMenuItem.setToggleGroup(sizeToggleGroup);
        twentySizeMenuItem.setToggleGroup(sizeToggleGroup);
    }

    private void setRadioMenuItems(){
        tenSizeMenuItem.setOnAction(event -> {
            if(tenSizeMenuItem.isSelected()){
                pathFinder.resize(10,10);
                pathFinder.changeSource(new Point(0, 5));
                pathFinder.changeTarget(new Point(9,5));
                pathFinder.reset();
                initBoard();
                if(startPauseButton.getText().equals("Pause"))
                    startPauseButton.fire();
            }
        });

        fifteenSizeMenuItem.setOnAction(event -> {
            if(fifteenSizeMenuItem.isSelected()){
                pathFinder.resize(15,15);
                pathFinder.changeSource(new Point(0, 7));
                pathFinder.changeTarget(new Point(14,7));
                pathFinder.reset();
                initBoard();
                if(startPauseButton.getText().equals("Pause"))
                    startPauseButton.fire();
            }
        });

        twentySizeMenuItem.setOnAction(event -> {
            if(twentySizeMenuItem.isSelected()){
                pathFinder.resize(20,20);
                pathFinder.changeSource(new Point(0, 10));
                pathFinder.changeTarget(new Point(19,10));
                pathFinder.reset();
                initBoard();
                if(startPauseButton.getText().equals("Pause"))
                    startPauseButton.fire();
            }
        });

        tenSizeMenuItem.setSelected(true);
    }

    public void setAlgorithmMenu(){
        algorithmToggleGroup = new ToggleGroup();

        pathFinder.getAlgorithms().forEach(algorithm -> {
            RadioMenuItem radioMenuItem = new RadioMenuItem();
            radioMenuItem.setText(algorithm.getName());
            radioMenuItem.setOnAction(event -> {
                pathFinder.chooseAlgorithm(algorithm);
            });
            radioMenuItem.setToggleGroup(algorithmToggleGroup);
            algorithmMenu.getItems().add(radioMenuItem);
        });

        RadioMenuItem radioMenuItem = (RadioMenuItem) algorithmMenu.getItems().get(0);
        radioMenuItem.setSelected(true);
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
