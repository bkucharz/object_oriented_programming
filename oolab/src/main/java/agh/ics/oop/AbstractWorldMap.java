package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap{
    protected Vector2d upperRightCorner;
    protected Vector2d lowerLeftCorner;
    protected List<Animal> animals = new LinkedList<>();
    protected MapVisualizer visualizer = new MapVisualizer(this);


    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            animals.add(animal);
            return true;
        }
        return false;
    }

    public boolean isOccupied(Vector2d position){
        for(Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }

    public Object objectAt(Vector2d position){
        for(Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return animal;
            }
        }
        return null;
    }

    // a gdzie toString?
    // canMoveTo się nie dało tu zaimplementować?
}
