package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App extends Application implements ISimulationObserver{
    float STAGE_HEIGHT = 600.0F;
    float STAGE_WIDTH = 1400.0F;

    // followed animals stages
    List<AnimalInfoWindow> animalsInfo = new LinkedList<>();

    private Stage primaryStage;
    private Stage simulationStage;
    private SettingsMenu menu;
    private HBox rootPane;
    private Stage errorWindow;
    private Button okButton;

    private Thread portalMapThread;
    private AbstractWorldMap portalMap;
    private GridPane portalMapGrid;
    private Button stopPortalMapButton;
    private boolean stopPortalMapClicked = false;
    private Label portalMapDayLabel;
    private Button savePortalMapButton;

    private Thread boundedMapThread;
    private AbstractWorldMap boundedMap;
    private GridPane boundedMapGrid;
    private Button stopBoundedMapButton;
    private boolean stopBoundedMapClicked = false;
    private Label boundedMapDayLabel;
    private Button saveBoundedMapButton;

    private final StatisticsPanel portalMapStatisticsPanel = new StatisticsPanel();
    private final StatisticsPanel boundedMapStatisticsPanel = new StatisticsPanel();


    public void init(){
        portalMapGrid = new GridPane();
        portalMapGrid.setAlignment(Pos.CENTER);
        boundedMapGrid = new GridPane();
        boundedMapGrid.setAlignment(Pos.CENTER);

        // setting portal map side
        Label portalMapLabel = new Label("PORTAL MAP");
        portalMapLabel.setFont(Font.font(20));
        VBox portalMapLabelBox = new VBox(portalMapLabel);
        portalMapLabelBox.setAlignment(Pos.CENTER);
        stopPortalMapButton = new Button("STOP");
        savePortalMapButton = new Button("SAVE");
        portalMapDayLabel = new Label("Day: 0");
        HBox portalMapButtonBox = new HBox(40, portalMapDayLabel, stopPortalMapButton, savePortalMapButton);
        portalMapButtonBox.setAlignment(Pos.CENTER);
        VBox portalMapBox = new  VBox(1,portalMapLabelBox, portalMapGrid, portalMapButtonBox, portalMapStatisticsPanel.mainBox);
        portalMapBox.setAlignment(Pos.CENTER);

        // setting bounded map side
        Label boundedMapLabel = new Label("BOUNDED MAP");
        boundedMapLabel.setFont(Font.font(20));
        VBox boundedMapLabelBox = new VBox(boundedMapLabel);
        boundedMapLabelBox.setAlignment(Pos.CENTER);
        stopBoundedMapButton = new Button("STOP");
        saveBoundedMapButton = new Button("SAVE");
        boundedMapDayLabel = new Label("Day: 0");
        HBox boundedMapButtonBox = new HBox(40, boundedMapDayLabel, stopBoundedMapButton, saveBoundedMapButton);
        boundedMapButtonBox.setAlignment(Pos.CENTER);
        VBox boundedMapBox = new  VBox(1,boundedMapLabelBox, boundedMapGrid, boundedMapButtonBox, boundedMapStatisticsPanel.mainBox);
        boundedMapBox.setAlignment(Pos.CENTER);

        // adding both sides to rootPane
        rootPane = new HBox(40, portalMapBox, boundedMapBox);
        rootPane.setPadding(new Insets(10));
        rootPane.setAlignment(Pos.CENTER);

        // setting popup window when save without stopping
        Platform.runLater(() -> {
            errorWindow = new Stage();
            Label info = new Label("First you have to stop simulation");
            okButton = new Button("OK");
            HBox hBox = new HBox(okButton);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(10, info, hBox);
            Scene errorScene = new Scene(vBox);
            errorWindow.setScene(errorScene);
            errorWindow.setAlwaysOnTop(true);
        });
    }


    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.menu = new SettingsMenu(this);
        primaryStage.setScene(menu.menuScene);
        primaryStage.show();
        menu.action();
    }

    public void startSimulation(SimulationEngine portalMapEngine, AbstractWorldMap portalMap, SimulationEngine boundedMapEngine, AbstractWorldMap boundedMap){
        this.portalMap =  portalMap;
        this.boundedMap = boundedMap;
        portalMapEngine.addObserver(this);
        boundedMapEngine.addObserver(this);
        portalMapThread = new Thread(portalMapEngine);
        boundedMapThread = new Thread(boundedMapEngine);

        portalMapStatisticsPanel.setEngine(portalMapEngine);
        boundedMapStatisticsPanel.setEngine(boundedMapEngine);

        simulationStage = primaryStage;
        Scene simulationScene = new Scene(rootPane);
        simulationStage.setScene(simulationScene);
        simulationStage.setMaximized(true);

        boundedMapThread.start();
        portalMapThread.start();

        stopBoundedMapButton.setOnAction(x ->{
            boundedMapThread.suspend();
            stopBoundedMapButton.setText("START");

            if (stopBoundedMapClicked){
                boundedMapThread.resume();
                stopBoundedMapButton.setText("STOP");
            }
            stopBoundedMapClicked = !stopBoundedMapClicked;
        });

        stopPortalMapButton.setOnAction(x ->{
            portalMapThread.suspend();
            stopPortalMapButton.setText("START");

            if (stopPortalMapClicked){
                portalMapThread.resume();
                stopPortalMapButton.setText("STOP");
            }
            stopPortalMapClicked = !stopPortalMapClicked;
        });

        savePortalMapButton.setOnAction(x -> {
            if (!stopPortalMapClicked){
                errorWindow.show();
                okButton.setOnAction(y -> errorWindow.close());
            }
            else {
                try {
                    portalMapStatisticsPanel.saveToCSV("saved_data/portalMapSimulationData.csv");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        saveBoundedMapButton.setOnAction(x -> {
            if (!stopBoundedMapClicked){
                errorWindow.show();
                okButton.setOnAction(y -> errorWindow.close());
            }
            else {
                try {
                    boundedMapStatisticsPanel.saveToCSV("saved_data/boundedMapSimulationData.csv");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void removeAnimalInfo(AnimalInfoWindow animalInfo){
        // removing animal from followed
        animalsInfo.remove(animalInfo);
    }

    public void cyclePassed(AbstractWorldMap map, int day, int animalNumber, int grassNumber, float avgEnergy, float avgLifeTime, float avgChildNum, List<Integer> genesMode){
        Platform.runLater(() -> {
            for (AnimalInfoWindow animalInfo : animalsInfo){
                // updating watched animals information
                animalInfo.updateInfo();
            }

            if (map instanceof BoundedMap){
                boundedMapDayLabel.setText("Day: "+day);
                refreshScreen(boundedMapGrid, map);
            }
            if (map instanceof PortalMap){
                portalMapDayLabel.setText("Day: "+day);
                refreshScreen(portalMapGrid, map);
            }
        });
    }

    private void refreshScreen(GridPane accGrid, AbstractWorldMap map){
        Platform.runLater(() -> {
            accGrid.setGridLinesVisible(false);
            accGrid.getColumnConstraints().clear();
            accGrid.getRowConstraints().clear();
            accGrid.getChildren().clear();

            Vector2d lowerLeft = map.getLowerLeftCorner();
            Vector2d upperRight = map.getUpperRightCorner();

            createGrid(lowerLeft, upperRight, accGrid);
            try {
                putMapElements(lowerLeft, upperRight, accGrid, map);
            } catch (FileNotFoundException e) {
                System.out.println(e+" file not found");
                return;
            }

            simulationStage.setOnCloseRequest(x -> {
                portalMapThread.stop();
                boundedMapThread.stop();
                Platform.exit();
            });
            System.out.println("Finished visualization");
        });

    }

    private void createGrid(Vector2d lowerLeft, Vector2d upperRight, GridPane accGrid){
        for (int i=lowerLeft.x; i <= upperRight.x; i++){
            for (int j=upperRight.y; j >= lowerLeft.y; j--){
                Pane pane = new Pane();
                GridPane.setHalignment(pane, HPos.CENTER);
                Vector2d position = new Vector2d(i, j);

                if (position.precedes(portalMap.getJungleUpperRightCorner()) && position.follows(portalMap.getJungleLowerLeftCorner())){
                    // setting jungle color
                    pane.setStyle("-fx-background-color: #174f01;");
                }
                else {
                    // setting step color
                    pane.setStyle("-fx-background-color: #79ba05;");
                }
                accGrid.add(pane, i-lowerLeft.x, upperRight.y - j, 1, 1);
            }
        }

        // setting sizes of cells in grid (map)
        for (int i=lowerLeft.x; i <= upperRight.x; i++){
            accGrid.getColumnConstraints().add(new ColumnConstraints((STAGE_WIDTH/3)/(upperRight.x +2)));
        }
        for (int j=upperRight.y; j >= lowerLeft.y; j--){
            accGrid.getRowConstraints().add(new RowConstraints((STAGE_HEIGHT/3)/(upperRight.y + 2)));
        }
    }

    private void putMapElements(Vector2d lowerLeft, Vector2d upperRight, GridPane grid, AbstractWorldMap map) throws FileNotFoundException {
        GuiElementBox elementBox;
        for (int i = upperRight.y; i >= lowerLeft.y; i--) {
            for (int j = lowerLeft.x; j <= upperRight.x; j++) {
                IWorldMapElement mapElement = (IWorldMapElement) map.objectAt(new Vector2d(j, i));
                if (mapElement != null) {
                    elementBox = new GuiElementBox(mapElement, (STAGE_WIDTH/2)/(upperRight.x +2), (STAGE_HEIGHT/2)/(upperRight.y + 2));

                    if (mapElement instanceof Animal){
                        // adding box for animals needed for selecting animals to watch
                        Animal watchedAnimal = (Animal) mapElement;
                        VBox box = elementBox.getVBox();
                        box.setOnMouseClicked(x -> animalsInfo.add(new AnimalInfoWindow(watchedAnimal, box, this)));
                    }

                    grid.add(elementBox.getVBox(), j - lowerLeft.x, upperRight.y - i, 1, 1);
                }
            }
        }
    }

    public void gameOver(int days, AbstractWorldMap map){
        Platform.runLater(() -> {
            String message;
            if (map instanceof PortalMap){message = "portal map"; stopPortalMapButton.fire();}
            else {message = "bounded map"; stopBoundedMapButton.fire();}
            Stage endWindow = new Stage();
            GridPane endGrid = new GridPane();
            Scene endScene = new Scene(endGrid);
            VBox vBox = new VBox();
            Label endLabel = new Label("All dead!!!\nPopulation on "+ message +" managed to live for: "+days+" days");
            Button endButton = new Button("Close");
            HBox buttonBox = new HBox(endButton);
            buttonBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(endLabel, buttonBox);
            endGrid.getChildren().add(vBox);
            endWindow.setScene(endScene);
            endWindow.show();
            endButton.setOnAction(x -> endWindow.close());
        });
    }


    public void revival(int numberOfRevival, AbstractWorldMap map) {
        Platform.runLater(() ->{
            Stage window = new Stage();
            String mapMessage;
            if (map instanceof BoundedMap){mapMessage = "bounded map";}
            else {mapMessage = "portal map";}
            Label message = new Label("The "+ numberOfRevival+" magic revival on "+mapMessage+" occurred!");
            Button okButton = new Button("OK");
            HBox buttonBox = new HBox(okButton);
            buttonBox.setAlignment(Pos.CENTER);
            VBox box = new VBox(5, message, buttonBox);
            Scene scene = new Scene(box, 250, 50);
            window.setScene(scene);
            window.show();
            okButton.setOnAction(x -> window.close());
        });
    }
}
