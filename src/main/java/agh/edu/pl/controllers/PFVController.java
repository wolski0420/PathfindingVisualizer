package agh.edu.pl.controllers;

import agh.edu.pl.data.Field;
import agh.edu.pl.data.Point;
import agh.edu.pl.data.Status;
import agh.edu.pl.executable.PathFinder;
import agh.edu.pl.observable.ExecutorSubscriber;
import agh.edu.pl.observable.FieldSubscriber;
import agh.edu.pl.translators.StatusToColor;
import agh.edu.pl.translators.StatusToDescription;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class PFVController implements FieldSubscriber, ExecutorSubscriber {
    private final StatusToColor statusToColor = new StatusToColor();
    private final StatusToDescription statusToDescription = new StatusToDescription();
    private PathFinder pathFinder;
    private ToggleGroup sizeToggleGroup;
    private ToggleGroup pointChangeToggleGroup;
    private ToggleGroup algorithmToggleGroup;
    @FXML
    public Menu algorithmMenu;
    @FXML
    public MenuItem generateMenuItem;
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
    @FXML
    public RadioMenuItem changeTargetModeMenuItem;
    @FXML
    public RadioMenuItem changeSourceModeMenuItem;
    @FXML
    public Button startPauseButton;
    @FXML
    public Button resetButton;
    @FXML
    public Button clearButton;
    @FXML
    public Spinner<Integer> delaySpinner;
    @FXML
    public VBox legendVBox;

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        this.pathFinder.getAlgorithmExecutor().addSubscriber(this);
        setGridPane();
        setStartPauseButton();
        setResetButton();
        setClearButton();
        setSizeToggleGroup();
        setSizeMenuItems();
        setAlgorithmMenu();
        setGenerateMenuItem();
        setPointToggleGroup();
        setDelaySpinner();
        setLegendVBox();
    }

    private void setGridPane(){
        Node node = null;
        if(!gridPane.getChildren().isEmpty())
            node = gridPane.getChildren().remove(gridPane.getChildren().size()-1);
        gridPane.getChildren().clear();

        for (int y = 0; y < pathFinder.getSizeY(); y++) {
            for (int x = 0; x < pathFinder.getSizeX(); x++) {
                Field field = pathFinder.getField(new Point(x, y));
                field.addSubscriber(this);
                Rectangle rectangle = new Rectangle((double)750/pathFinder.getSizeX(), (double)750/pathFinder.getSizeY());
                rectangle.setX(x);
                rectangle.setY(y);
                rectangle.setFill(statusToColor.translate(field.getStatus()));
                gridPane.add(rectangle, x, y);

                rectangle.setOnMouseClicked(event -> {
                    if(addModeButton.isSelected()){
                        pathFinder.toggleObstacle(new Point((int)rectangle.getX(), (int)rectangle.getY()));
                    }
                    else if(changeSourceModeMenuItem.isSelected()){
                        pathFinder.changeSource(new Point((int)rectangle.getX(), (int)rectangle.getY()));
                    }
                    else if(changeTargetModeMenuItem.isSelected()){
                        pathFinder.changeTarget(new Point((int)rectangle.getX(), (int)rectangle.getY()));
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
                clearButton.setDisable(true);
                generateMenuItem.setDisable(true);
                addModeButton.setSelected(false);
                addModeButton.setDisable(true);
                changeSourceModeMenuItem.setDisable(true);
                changeTargetModeMenuItem.setDisable(true);
            }
            else{
                pathFinder.pause();
                startPauseButton.setText("Start");
                startPauseButton.setStyle("-fx-background-color: #00c700");
            }
        });
    }

    private void setSizeToggleGroup(){
        sizeToggleGroup = new ToggleGroup();
        fifteenSizeMenuItem.setToggleGroup(sizeToggleGroup);
        twentySizeMenuItem.setToggleGroup(sizeToggleGroup);
        twentyFiveSizeMenuItem.setToggleGroup(sizeToggleGroup);
    }

    private void setSizeMenuItems(){
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
                resetButton.fire();
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

    private void setPointToggleGroup(){
        pointChangeToggleGroup = new ToggleGroup();
        changeTargetModeMenuItem.setToggleGroup(pointChangeToggleGroup);
        changeSourceModeMenuItem.setToggleGroup(pointChangeToggleGroup);
    }

    private void setResetButton() {
        resetButton.setOnAction(event -> {
            pathFinder.reset();
            generateMenuItem.setDisable(false);
            addModeButton.setDisable(false);
            changeSourceModeMenuItem.setDisable(false);
            changeTargetModeMenuItem.setDisable(false);
            startPauseButton.setDisable(false);
            clearButton.setDisable(false);

            if(startPauseButton.getText().equals("Pause"))
                startPauseButton.fire();
        });
    }

    private void setClearButton(){
        clearButton.setOnAction(event -> {
            pathFinder.resetAll();
            startPauseButton.setDisable(false);
        });
    }

    private void setDelaySpinner(){
        delaySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pathFinder.changeDelay(delaySpinner.getValue());
        });
    }

    private void setLegendVBox(){
        Arrays.stream(Status.values()).forEach(status -> {
            HBox legendItemBox = new HBox();

            Rectangle rectangle = new Rectangle(30,20);
            rectangle.setFill(statusToColor.translate(status));
            HBox.setMargin(rectangle, new Insets(10,10,10,10));

            Label label = new Label();
            label.setText(" - " + statusToDescription.translate(status));
            HBox.setMargin(label, new Insets(10,10,10,10));

            legendItemBox.getChildren().addAll(rectangle, label);
            legendVBox.getChildren().add(legendItemBox);
        });
    }

    @Override
    public void update(Field field) {
        Rectangle rectangle = (Rectangle) gridPane.getChildren().get(
                field.getLocation().y * pathFinder.getSizeX() + field.getLocation().x
        );

        rectangle.setFill(statusToColor.translate(field.getStatus()));
    }

    @Override
    public void informOnFinished() {
        startPauseButton.fire();
        startPauseButton.setDisable(true);
        clearButton.setDisable(false);
    }
}
