package agh.ics.oop;

import java.util.*;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Vector2d upperRightCorner;
    protected Vector2d lowerLeftCorner;
    protected Map<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected MapVisualizer visualizer = new MapVisualizer(this);


    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animals.put(newPosition, animals.remove(oldPosition));
    }

    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Cannot place animal at position "+  animal.getPosition());
    }

    public boolean isOccupied(Vector2d position){
        if(animals.containsKey(position)){
            return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position){
        return animals.get(position);
    }

    public Vector2d getUpperRightCorner(){
        return upperRightCorner;
    }

    public Vector2d getLowerLeftCorner(){
        return lowerLeftCorner;
    }
}
