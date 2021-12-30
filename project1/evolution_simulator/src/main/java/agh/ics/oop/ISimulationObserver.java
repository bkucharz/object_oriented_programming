package agh.ics.oop;

import java.util.List;

public interface ISimulationObserver {
    void cyclePassed(AbstractWorldMap map, int day, int animalNumber, int grassNumber, float avgEnergy, float avgLifeTime, float avgChildNum, List<Integer> genesMode);

    void gameOver(int days, AbstractWorldMap map);

    void revival(int numberOfRevival, AbstractWorldMap map);
}
