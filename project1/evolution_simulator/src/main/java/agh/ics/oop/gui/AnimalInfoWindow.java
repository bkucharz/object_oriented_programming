package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.SimulationEngine;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class AnimalInfoWindow{
    private final Animal animal;
    private final int initialChildNumber;
    private final Stage infoWindow;
    private VBox clickBox;
    private Label dnaLabel;
    private Label ageLabel;
    private Label childNumLabel;
    private Label descendantNumLabel;
    private Label deathDayLabel;
    private final App app;

    public AnimalInfoWindow(Animal animal, VBox clickBox, App app){
        this.animal = animal;
        this.app = app;
        initialChildNumber = animal.getChildNumber();
        this.clickBox = clickBox;

        infoWindow = new Stage();
        dnaLabel = new Label("Animal's DNA: "+ Arrays.toString(animal.getDna()));
        ageLabel = new Label("Age: "+animal.getAge());
        childNumLabel = new Label("Number of children: 0");
        descendantNumLabel = new Label("Number of descendant: 0");
        deathDayLabel = new Label("The animal is still alive");

        Button closeButton = new Button("Close");
        HBox buttonBox = new HBox(closeButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox InfoBox = new VBox(5, dnaLabel, ageLabel, childNumLabel, descendantNumLabel, deathDayLabel, buttonBox);
        Scene InfoScene = new Scene(InfoBox);
        infoWindow.setScene(InfoScene);
        infoWindow.setAlwaysOnTop(true);
        infoWindow.show();

        closeButton.setOnAction(x ->{
            infoWindow.close();
            app.removeAnimalInfo(this);
        });
    }

    public void updateInfo(){
        Platform.runLater(() -> {
            dnaLabel.setText("Animal's DNA: "+ Arrays.toString(animal.getDna()));
            ageLabel.setText("Age: "+animal.getAge());
            childNumLabel.setText("Number of children: "+(animal.getChildNumber() - initialChildNumber));
            deathDayLabel.setText((animal.getDeathDay() != -1) ? ("Death day: "+animal.getDeathDay()) : "The animal is still alive");
        });
    }
}
