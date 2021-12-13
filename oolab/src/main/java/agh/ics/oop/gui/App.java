package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.List;

public class App extends Application implements IPositionChangeObserver {
    GrassField map;
    SimulationEngine engine;
    GridPane grid = new GridPane();
    int CELL_HIGHT = 40;
    int CELL_WIDTH = 40;
    int moveDelay = 300;
    Stage primaryStage;
    Scene scene;

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            refreshScreen();
        });
    }

    private void refreshScreen(){
        grid.setGridLinesVisible(false);
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
        grid.setGridLinesVisible(true);

        Vector2d lowerLeft = map.getLowerLeftCorner();
        Vector2d upperRight = map.getUpperRightCorner();
        createGrid(lowerLeft, upperRight);
        try {
            putMapElements(lowerLeft, upperRight);
        } catch (FileNotFoundException e) {
            System.out.println(e+" file not found");
            return;
        }
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("Finished visualization");
    }

    public void init(){
        try {
//            String[] args = getParameters().getRaw().toArray(new String[0]);
            String[] args = new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f", "b", "b", "l", "r", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f"};
            MoveDirection[] directions = new OptionsParser().parse(args);
            this.map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
            this.engine = new SimulationEngine(map, positions);
            for(Vector2d pos : positions){
                Animal a = (Animal) map.objectAt(pos);
                a.addObserver(this);
            }

        } catch (IllegalArgumentException ex){
            System.out.println(ex);
            return;
        }
    }

    private void createGrid(Vector2d lowerLeft, Vector2d upperRight){
        Label label1 = new Label("y/x");



        grid.add(label1, 0, 0, 1, 1);

        for (int i=lowerLeft.x; i <= upperRight.x; i++){
            grid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, i-lowerLeft.x + 1, 0, 1, 1);
        }

        for (int i=upperRight.y; i >= lowerLeft.y; i--){
            grid.getRowConstraints().add(new RowConstraints(CELL_HIGHT));
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, upperRight.y - i + 1, 1, 1);
        }
    }


    private void putMapElements(Vector2d lowerLeft, Vector2d upperRight) throws FileNotFoundException {
        for (int i = upperRight.y; i >= lowerLeft.y; i--){
            for (int j = lowerLeft.x; j <= upperRight.x; j++){
                IWorldMapElement mapElement = (IWorldMapElement) map.objectAt(new Vector2d(j, i));
                if (mapElement != null){
//                    Label label = new Label(mapElement.toString());
//                    GridPane.setHalignment(label, HPos.CENTER);
                    GuiElementBox elementBox = new GuiElementBox(mapElement);
                    grid.add(elementBox.getVBox(), j - lowerLeft.x + 1, upperRight.y - i + 1, 1, 1);
                }
            }
        }

    }

    public void start(Stage primaryStage){
        scene = new Scene(grid, 700, 700);
        this.primaryStage = primaryStage;
        TextField textField = new TextField();
        HBox hBox = new HBox(textField);
        Button button = new Button("START");
        Button exitButton = new Button("Exit");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, button, exitButton);
        vBox.setAlignment(Pos.CENTER);
//        grid.getChildren().add(vBox);
        GridPane grid1 = new GridPane();
        grid1.getChildren().add(vBox);
        Scene scene1 =  new Scene(grid1, 150, 80);
        Stage stage1 = new Stage();;
        stage1.setScene(scene1);
        stage1.show();
        this.init();
        this.refreshScreen();
        primaryStage.setScene(scene);
        primaryStage.show();
        textField.setOnAction(x ->{
            String[] moves = textField.getText().split(" ");
            engine.setMoves(new OptionsParser().parse(moves));
            Thread thread1 = new Thread((Runnable) this.engine);
            thread1.start();
            textField.clear();
        });
        button.setOnAction(x -> {
            String[] moves = textField.getText().split(" ");
            engine.setMoves(new OptionsParser().parse(moves));
            Thread thread1 = new Thread((Runnable) this.engine);
            thread1.start();
        });
        exitButton.setOnAction(x -> {
            Platform.exit();
        });

        grid.setGridLinesVisible(true);
    }
}
