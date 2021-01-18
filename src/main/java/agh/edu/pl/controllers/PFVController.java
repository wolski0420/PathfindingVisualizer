package agh.edu.pl.controllers;

import agh.edu.pl.data.Field;
import agh.edu.pl.data.Point;
import agh.edu.pl.executable.PathFinder;
import agh.edu.pl.observable.Subscriber;
import agh.edu.pl.translators.StatusToColor;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    public MenuItem generateMenuItem;
    @FXML
    public Button startPauseButton;
    @FXML
    public Button resetButton;
    @FXML
    public GridPane gridPane;
    @FXML
    public RadioMenuItem fifteenSizeMenuItem;
    @FXML
    public RadioMenuItem twentySizeMenuItem;
    @FXML
    public RadioMenuItem twentyFiveSizeMenuItem;
    @FXML
    public CheckMenuItem addModeButton;

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        setGridPane();
        setStartPauseButton();
        setResetButton();
        setToggleGroup();
        setRadioMenuItems();
        setAlgorithmMenu();
        setGenerateMenuItem();
    }

    private void setGridPane(){
        Node node = null;
        if(!gridPane.getChildren().isEmpty())
            node = gridPane.getChildren().remove(gridPane.getChildren().size()-1);
        gridPane.getChildren().clear();

        for (int y = 0; y < pathFinder.getBoard().getHeight(); y++) {
            for (int x = 0; x < pathFinder.getBoard().getWidth(); x++) {
                Field field = pathFinder.getBoard().getField(new Point(x, y));
                field.addSubscriber(this);
                Rectangle rectangle = new Rectangle((double)750/pathFinder.getBoard().getWidth(), (double)750/pathFinder.getBoard().getHeight());
                rectangle.setX(x);
                rectangle.setY(y);
                rectangle.setFill(StatusToColor.translate(field.getStatus()));
                gridPane.add(rectangle, x, y);

                rectangle.setOnMouseClicked(event -> {
                    if(addModeButton.isSelected()){
                        pathFinder.addObstacle(new Point((int)rectangle.getX(), (int)rectangle.getY()));
                    }
                });
            }
        }

        if(node != null)
            gridPane.getChildren().add(node);
    }

    private void setStartPauseButton(){
        startPauseButton.setOnAction(event -> {
            if(startPauseButton.getText().equals("Start")){
                pathFinder.start();
                startPauseButton.setText("Pause");
                startPauseButton.setStyle("-fx-background-color: #fd2b2b");
                generateMenuItem.setDisable(true);
                addModeButton.setSelected(false);
                addModeButton.setDisable(true);
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
        fifteenSizeMenuItem.setToggleGroup(sizeToggleGroup);
        twentySizeMenuItem.setToggleGroup(sizeToggleGroup);
        twentyFiveSizeMenuItem.setToggleGroup(sizeToggleGroup);
    }

    private void setRadioMenuItems(){
        fifteenSizeMenuItem.setOnAction(event -> {
            if(fifteenSizeMenuItem.isSelected()){
                pathFinder.resize(15,15);
                pathFinder.changeSource(new Point(0, 7));
                pathFinder.changeTarget(new Point(14,7));
                resetButton.fire();
                setGridPane();
            }
        });

        twentySizeMenuItem.setOnAction(event -> {
            if(twentySizeMenuItem.isSelected()){
                pathFinder.resize(20,20);
                pathFinder.changeSource(new Point(0, 10));
                pathFinder.changeTarget(new Point(19,10));
                resetButton.fire();
                setGridPane();
            }
        });

        twentyFiveSizeMenuItem.setOnAction(event -> {
            if(twentyFiveSizeMenuItem.isSelected()){
                pathFinder.resize(25,25);
                pathFinder.changeSource(new Point(0, 12));
                pathFinder.changeTarget(new Point(24,12));
                resetButton.fire();
                setGridPane();
            }
        });

        fifteenSizeMenuItem.setSelected(true);
    }

    private void setAlgorithmMenu(){
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

    private void setGenerateMenuItem(){
        generateMenuItem.setOnAction(event -> {
            pathFinder.generateObstacles();
        });
    }

    public void setResetButton() {
        resetButton.setOnAction(event -> {
            pathFinder.reset();
            generateMenuItem.setDisable(false);
            addModeButton.setDisable(false);

            if(startPauseButton.getText().equals("Pause"))
                startPauseButton.fire();
        });
    }

    @Override
    public void update(Field field) {
        Rectangle rectangle = (Rectangle) gridPane.getChildren().get(
                field.getLocation().y * pathFinder.getBoard().getWidth() + field.getLocation().x
        );

        rectangle.setFill(StatusToColor.translate(field.getStatus()));
    }
}
