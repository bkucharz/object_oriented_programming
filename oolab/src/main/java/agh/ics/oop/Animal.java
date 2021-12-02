package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Animal implements IWorldMapElement{
    private Vector2d position;
    private MapDirection orientation;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.position = initialPosition;
        this.map = map;
        this.orientation = MapDirection.NORTH;
    }

    public Object clone(){
        return new Animal(this.map, this.position);
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation(){
        return orientation;
    }

    public String toString(){
        String symbol = switch (orientation){
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
        return symbol;
    }

    public void move(MoveDirection direction){
        this.orientation = switch (direction){
            case LEFT -> this.orientation.previous();
            case RIGHT -> this.orientation.next();
            default -> this.orientation;
        };

        Vector2d oldPosition = position;
        Vector2d updatedPosition = switch (direction){
            case FORWARD -> position.add(orientation.toUnitVector());
            case BACKWARD -> position.subtract(orientation.toUnitVector());
            default -> position;
        };

        if(map.canMoveTo(updatedPosition)){
            position = updatedPosition;
            positionChanged(oldPosition, updatedPosition);
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
