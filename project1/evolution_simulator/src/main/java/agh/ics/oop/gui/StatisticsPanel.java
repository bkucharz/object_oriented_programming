package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.ISimulationObserver;
import agh.ics.oop.SimulationEngine;
import com.opencsv.CSVWriter;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsPanel implements ISimulationObserver {
    public VBox mainBox = new VBox();

    private SimulationEngine engine;

    private final NumberAxis xAxisPopulation = new NumberAxis();
    private final NumberAxis yAxisPopulation = new NumberAxis();
    private final LineChart<Number, Number> populationChart = new LineChart<Number, Number>(xAxisPopulation, yAxisPopulation);
    private final XYChart.Series animalSeries = new XYChart.Series();
    private final XYChart.Series grassSeries = new XYChart.Series();

    private final NumberAxis xAxisAvgEnergy = new NumberAxis();
    private final NumberAxis yAxisAvgEnergy = new NumberAxis();
    private final LineChart<Number, Number> avgEnergyChart = new LineChart<Number, Number>(xAxisAvgEnergy, yAxisAvgEnergy);
    private final XYChart.Series avgEnergySeries = new XYChart.Series();

    private final NumberAxis xAxisAvgLifeTime = new NumberAxis();
    private final NumberAxis yAxisAvgLifeTime = new NumberAxis();
    private final LineChart<Number, Number> avgLifeTimeChart = new LineChart<Number, Number>(xAxisAvgLifeTime, yAxisAvgLifeTime);
    private final XYChart.Series avgLifeTimeSeries= new XYChart.Series();

    private final NumberAxis xAxisAvgChildNum = new NumberAxis();
    private final NumberAxis yAxisAvgChildNum = new NumberAxis();
    private final LineChart<Number, Number> avgChildNumChart = new LineChart<Number, Number>(xAxisAvgChildNum, yAxisAvgChildNum);
    private final XYChart.Series avgChildNumSeries= new XYChart.Series();

    private final Label isMagicLabel;
    private Label revivalNumberLabel;
    private Label genesModeLabel;

    public StatisticsPanel(){
        xAxisPopulation.setLabel("Day");
        yAxisPopulation.setLabel("Population");
        populationChart.setCreateSymbols(false);
        populationChart.getData().add(animalSeries);
        populationChart.getData().add(grassSeries);
        animalSeries.setName("Animal population");
        grassSeries.setName("Grass population");
        populationChart.setStyle("CHART_COLOR_1: #ff0000; CHART_COLOR_2: green;");

        xAxisAvgEnergy.setLabel("Day");
        yAxisAvgEnergy.setLabel("AVG energy");
        avgEnergyChart.setCreateSymbols(false);
        avgEnergyChart.getData().add(avgEnergySeries);
        avgEnergySeries.setName("Animal average energy");

        xAxisAvgLifeTime.setLabel("Day");
        yAxisAvgLifeTime.setLabel("AVG life time");
        avgLifeTimeChart.setCreateSymbols(false);
        avgLifeTimeChart.getData().add(avgLifeTimeSeries);
        avgLifeTimeSeries.setName("Animal average life time");

        xAxisAvgChildNum.setLabel("Day");
        yAxisAvgChildNum.setLabel("AVG child number");
        avgChildNumChart.setCreateSymbols(false);
        avgChildNumChart.getData().add(avgChildNumSeries);
        avgChildNumSeries.setName("Animal average child number");

        HBox chartBox1 = new HBox(10, populationChart, avgEnergyChart);
        HBox chartBox2 = new HBox(10, avgLifeTimeChart, avgChildNumChart);

        isMagicLabel = new Label("Magic revival: Disabled");
        revivalNumberLabel = new Label("Number of magic revivals: 0");
        VBox labelBoxColumn1 = new VBox(5, isMagicLabel, revivalNumberLabel);
        labelBoxColumn1.setAlignment(Pos.CENTER);

        genesModeLabel = new Label("");
        VBox labelBoxColumn2 = new VBox(5, genesModeLabel);
        labelBoxColumn2.setAlignment(Pos.CENTER);

        HBox labelBox = new HBox(10, labelBoxColumn1, labelBoxColumn2);
        labelBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(chartBox1, chartBox2, labelBox);
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
        engine.addObserver(this);
        if (engine.isMagic()){
            isMagicLabel.setText("Magic revival: Enabled");
        }
    }


    public void saveToCSV(String filePath) throws IOException {
        List<String[]> resultString = new ArrayList<>();
        int sumAnimalPopulation = 0;
        int sumGrassPopulation = 0;
        float sumAvgEnergy = 0;
        float sumAvgLifeTime = 0;
        float sumAvgChildNum = 0;

        int n = animalSeries.getData().size();

        String[] header = {"day", "animal population", "grass population", "AVG energy", "AVG life time", "AVG children number"};
        resultString.add(header);

        for (int x = 0; x < n; x++){
            int animalPopulation = (int) ((XYChart.Data) animalSeries.getData().get(x)).getYValue();
            int grassPopulation = (int) ((XYChart.Data) grassSeries.getData().get(x)).getYValue();
            float avgEnergy = (float) ((XYChart.Data) avgEnergySeries.getData().get(x)).getYValue();
            float avgLifeTime = (float) ((XYChart.Data) avgLifeTimeSeries.getData().get(x)).getYValue();
            float avgChildNum = (float) ((XYChart.Data) avgChildNumSeries.getData().get(x)).getYValue();

            sumAnimalPopulation += animalPopulation;
            sumGrassPopulation += grassPopulation;
            sumAvgEnergy += avgEnergy;
            sumAvgLifeTime += avgLifeTime;
            sumAvgChildNum += avgChildNum;

            String[] record = {String.valueOf(x), String.valueOf(animalPopulation), String.valueOf(grassPopulation), String.valueOf(avgEnergy), String.valueOf(avgLifeTime), String.valueOf(avgChildNum)};
            resultString.add(record);
        }

        String[] record = {"AVG", String.valueOf(sumAnimalPopulation/engine.getDay()), String.valueOf(sumGrassPopulation/engine.getDay()), String.valueOf(sumAvgEnergy/engine.getDay()), String.valueOf(sumAvgLifeTime/engine.getDay()), String.valueOf(sumAvgChildNum/engine.getDay())};
        resultString.add(record);

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(resultString);
        }
    }

    @Override
    public void cyclePassed(AbstractWorldMap map, int day, int animalNumber, int grassNumber, float avgEnergy, float avgLifeTime, float avgChildNum, List<Integer> genesMode){
        Platform.runLater(() ->{
            animalSeries.getData().add(new XYChart.Data(day, animalNumber));
            grassSeries.getData().add(new XYChart.Data(day, grassNumber));
            avgEnergySeries.getData().add(new XYChart.Data(day, avgEnergy));
            avgLifeTimeSeries.getData().add(new XYChart.Data(day, avgLifeTime));
            avgChildNumSeries.getData().add(new XYChart.Data(day, avgChildNum));
            genesModeLabel.setText("Genes mode: "+genesMode);
        });
    }

    @Override
    public void gameOver(int days, AbstractWorldMap map) {

    }

    @Override
    public void revival(int numberOfRevival, AbstractWorldMap map) {
        Platform.runLater(() ->{
            revivalNumberLabel.setText("Used magic revivals: "+numberOfRevival);
        });
    }
}
