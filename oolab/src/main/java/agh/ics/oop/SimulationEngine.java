package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
    private IWorldMap map;
    private MoveDirection[] moves;
    private List<Animal> animals = new ArrayList<>();

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] initialPositions){
        this.moves = moves;
        this.map = map;
        for(Vector2d initialPosition : initialPositions){
            Animal animal = new Animal(map, initialPosition);
            if(map.place(animal)){
                animals.add(animal);
            }
        }
    }

    public void run(){
        int i = 0;
        while(i < moves.length){
            for(Animal animal : animals){
                if(i >= moves.length){return;}
                animal.move(moves[i]);
                i++;
                System.out.println(map);
            }
        }
    }

}
