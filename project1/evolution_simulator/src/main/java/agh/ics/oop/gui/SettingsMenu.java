package agh.ics.oop.gui;

import agh.ics.oop.BoundedMap;
import agh.ics.oop.PortalMap;
import agh.ics.oop.SimulationEngine;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class SettingsMenu {
    Scene menuScene;

    TextField mapWidth;
    Button startButton;
    TextField mapHeight;
    TextField jungleRatio;
    TextField initialPopulation;
    TextField initialGrassNumber;
    TextField startEnergy;
    TextField moveEnergy;
    TextField plantEnergy;
    Button isPortalMapMagicButton;
    Button isBoundedMapMagicButton;
    boolean isPortalMapMagic = false;
    boolean isBoundedMapMagic = false;
    App app;

    GridPane grid = new GridPane();


    public SettingsMenu(App app){
        this.app = app;
        Scene menuScene = new Scene(grid, 400, 550);

        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(15);

        Label MapTitle = new Label("MAP PROPERTIES");
        MapTitle.setFont(Font.font(20));
        grid.add(MapTitle, 0, 0);
        grid.setHalignment(MapTitle, HPos.CENTER);

        HBox mapWidthBox = new HBox();
        Label mapWidthLabel = new Label("Map width:");
        mapWidth = new TextField("30");
        mapWidthBox.setAlignment(Pos.BASELINE_RIGHT);
        mapWidthBox.getChildren().addAll(mapWidthLabel, mapWidth);
        mapWidthBox.setSpacing(50);
        grid.add(mapWidthBox, 0, 1);

        HBox mapHeightBox = new HBox();
        Label mapHeightLabel = new Label("Map height:");
        mapHeight = new TextField("40");
        mapHeightBox.setAlignment(Pos.BASELINE_RIGHT);
        mapHeightBox.getChildren().addAll(mapHeightLabel, mapHeight);
        mapHeightBox.setSpacing(50);
        grid.add(mapHeightBox, 0, 2);

        HBox jungleRatioBox = new HBox();
        Label jungleRatioLabel = new Label("Jungle ratio:");
        jungleRatio = new TextField(".3");
        jungleRatioBox.setAlignment(Pos.BASELINE_RIGHT);
        jungleRatioBox.getChildren().addAll(jungleRatioLabel, jungleRatio);
        jungleRatioBox.setSpacing(50);
        grid.add(jungleRatioBox, 0, 3);

        HBox initialPopulationBox = new HBox();
        Label initialPopulationLabel = new Label("Initial population:");
        initialPopulation = new TextField("30");
        initialPopulationBox.setAlignment(Pos.BASELINE_RIGHT);
        initialPopulationBox.getChildren().addAll(initialPopulationLabel, initialPopulation);
        initialPopulationBox.setSpacing(50);
        grid.add(initialPopulationBox, 0, 4);

        HBox initialGrassNumberBox = new HBox();
        Label initialGrassNumberLabel = new Label("Initial grass number:");
        initialGrassNumber = new TextField("30");
        initialGrassNumberBox.setAlignment(Pos.BASELINE_RIGHT);
        initialGrassNumberBox.getChildren().addAll(initialGrassNumberLabel, initialGrassNumber);
        initialGrassNumberBox.setSpacing(50);
        grid.add(initialGrassNumberBox, 0, 5);

        HBox startEnergyBox = new HBox();
        Label startEnergyLabel = new Label("Initial animal energy:");
        startEnergy = new TextField("30");
        startEnergyBox.setAlignment(Pos.BASELINE_RIGHT);
        startEnergyBox.getChildren().addAll(startEnergyLabel, startEnergy);
        startEnergyBox.setSpacing(50);
        grid.add(startEnergyBox, 0, 6);

        HBox moveEnergyBox = new HBox();
        Label moveEnergyLabel = new Label("Move energy:");
        moveEnergy = new TextField("1");
        moveEnergyBox.setAlignment(Pos.BASELINE_RIGHT);
        moveEnergyBox.getChildren().addAll(moveEnergyLabel, moveEnergy);
        moveEnergyBox.setSpacing(50);
        grid.add(moveEnergyBox, 0, 7);

        HBox plantEnergyBox = new HBox();
        Label plantEnergyLabel = new Label("Plant energy:");
        plantEnergy = new TextField("10");
        plantEnergyBox.setAlignment(Pos.BASELINE_RIGHT);
        plantEnergyBox.getChildren().addAll(plantEnergyLabel, plantEnergy);
        plantEnergyBox.setSpacing(50);
        grid.add(plantEnergyBox, 0, 8);

        HBox isPortalMapMagicBox = new HBox(10);
        Label isPortalMapMagicLabel = new Label("Magic revival for portal map:");
        isPortalMapMagicButton = new Button("Enable");
        isPortalMapMagicBox.getChildren().addAll(isPortalMapMagicLabel, isPortalMapMagicButton);
        isPortalMapMagicBox.setAlignment(Pos.BASELINE_CENTER);
        grid.add(isPortalMapMagicBox, 0, 9);

        HBox isBoundedMapMagicBox= new HBox(10);
        Label isBoundedMapMagicLabel = new Label("Magic revival for bounded map:");
        isBoundedMapMagicButton = new Button("Enable");
        isBoundedMapMagicBox.getChildren().addAll(isBoundedMapMagicLabel, isBoundedMapMagicButton);
        isBoundedMapMagicBox.setAlignment(Pos.BASELINE_CENTER);
        grid.add(isBoundedMapMagicBox, 0, 10);


        HBox startButtonBox = new HBox();
        startButton = new Button("START");
        startButtonBox.getChildren().addAll(startButton);
        startButtonBox.setAlignment(Pos.CENTER);
        grid.add(startButtonBox, 0, 11);
        grid.setAlignment(Pos.TOP_CENTER);


        this.menuScene = menuScene;
    }

    public void action(){
        isPortalMapMagicButton.setOnAction(x ->{
            isPortalMapMagicButton.setText("Enable");
            if (!isPortalMapMagic){
                isPortalMapMagicButton.setText("Disable");
            }
            isPortalMapMagic = !isPortalMapMagic;
        });

        isBoundedMapMagicButton.setOnAction(x ->{
            isBoundedMapMagicButton.setText("Enable");
            if (!isBoundedMapMagic){
                isBoundedMapMagicButton.setText("Disable");
            }
            isBoundedMapMagic = !isBoundedMapMagic;
        });

        startButton.setOnAction(x ->{
            BoundedMap boundedMap = new BoundedMap(
                    Integer.parseInt(mapWidth.getText()),
                    Integer.parseInt(mapHeight.getText()),
                    Float.parseFloat(jungleRatio.getText()));
            SimulationEngine boundedEngine = new SimulationEngine(boundedMap, Integer.parseInt(initialPopulation.getText()), Integer.parseInt(initialGrassNumber.getText()), Integer.parseInt(startEnergy.getText()), Integer.parseInt(moveEnergy.getText()), Integer.parseInt(plantEnergy.getText()), isBoundedMapMagic);

            PortalMap portalMap = new PortalMap(
                    Integer.parseInt(mapWidth.getText()),
                    Integer.parseInt(mapHeight.getText()),
                    Float.parseFloat(jungleRatio.getText()));
            SimulationEngine portalEngine = new SimulationEngine(portalMap, Integer.parseInt(initialPopulation.getText()), Integer.parseInt(initialGrassNumber.getText()), Integer.parseInt(startEnergy.getText()), Integer.parseInt(moveEnergy.getText()), Integer.parseInt(plantEnergy.getText()), isPortalMapMagic);


            app.startSimulation(portalEngine, portalMap, boundedEngine, boundedMap);
        });
    }
}
