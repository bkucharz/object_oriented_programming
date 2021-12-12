package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.AbstractMap;
import java.util.List;

public class App extends Application {
    GrassField map;
    IEngine engine;
    GridPane grid;

    public void init(){
        try {
//            String[] args = getParameters().getRaw().toArray(new String[0]);
            String[] args = new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f", "b", "b"};
            MoveDirection[] directions = new OptionsParser().parse(args);
            this.map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
            this.engine = new SimulationEngine(directions, map, positions);
        } catch (IllegalArgumentException ex){
            System.out.println(ex);
            return;
        }


    }

    private void createGrid(Vector2d lowerLeft, Vector2d upperRight){
        grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getRowConstraints().add(new RowConstraints(20));
        grid.setGridLinesVisible(true);

        Label label1 = new Label("y/x");



        grid.add(label1, 0, 0, 1, 1);

        for (int i=lowerLeft.x; i <= upperRight.x; i++){
            grid.getColumnConstraints().add(new ColumnConstraints(20));
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, i-lowerLeft.x + 1, 0, 1, 1);
        }

        for (int i=upperRight.y; i >= lowerLeft.y; i--){
            grid.getRowConstraints().add(new RowConstraints(20));
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, upperRight.y - i + 1, 1, 1);
        }
    }


    private void putMapElements(Vector2d lowerLeft, Vector2d upperRight){
        for (int i = upperRight.y; i >= lowerLeft.y; i--){
            for (int j = lowerLeft.x; j <= upperRight.x; j++){
                IWorldMapElement mapElement = (IWorldMapElement) map.objectAt(new Vector2d(j, i));
                if (mapElement != null){
                    Label label = new Label(mapElement.toString());
                    GridPane.setHalignment(label, HPos.CENTER);
                    grid.add(label, j - lowerLeft.x + 1, upperRight.y - i + 1, 1, 1);
                }
            }
        }
    }

    public void start(Stage primaryStage){
        this.init();
        Vector2d lowerLeft = map.getLowerLeftCorner();
        Vector2d upperRight = map.getUpperRightCorner();

        createGrid(lowerLeft, upperRight);
        putMapElements(lowerLeft, upperRight);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
