package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class RectangularMap implements IWorldMap{
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;
    private List<Animal> animals = new LinkedList<>();

    int x0 = 0;
    int y0 = 0;

    public RectangularMap(int width, int height){

        lowerLeftCorner = new Vector2d(x0, y0);
        upperRightCorner = new Vector2d(x0 + width - 1, y0 + height - 1);
    }

    public boolean canMoveTo(Vector2d position){
        return (position.precedes(upperRightCorner) && position.follows(lowerLeftCorner) && !isOccupied(position));
    }

    public boolean place(Animal animal){
        if(!isOccupied(animal.getPosition())){
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

    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
