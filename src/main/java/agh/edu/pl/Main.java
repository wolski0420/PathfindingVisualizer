package agh.edu.pl;

import agh.edu.pl.controllers.PFVController;
import agh.edu.pl.executable.PathFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PFVController.class.getResource("../../../../view/visualizerView.fxml"));
            BorderPane layout = loader.load();

            PathFinder pathFinder = new PathFinder();
            PFVController controller = loader.getController();
            controller.setPathFinder(pathFinder);

            Scene scene = new Scene(layout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Pathfinding Visualizer");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
