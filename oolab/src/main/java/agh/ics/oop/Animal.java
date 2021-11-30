package agh.ics.oop;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;

    public Animal(){    // ten konstruktor nie ma prawa bytu
        this.orientation = MapDirection.NORTH;
    }

    public Animal(IWorldMap map){
        this.map = map;
        this.orientation = MapDirection.NORTH;  // a jaka jest pozycja zwierzęcia?
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.orientation = MapDirection.NORTH;
    }

    public MapDirection getOrientation(){
        return orientation;
    }

    public Vector2d getPosition(){
        return position;
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

        Vector2d updatedPosition = switch (direction){
            case FORWARD -> position.add(orientation.toUnitVector());
            case BACKWARD -> position.subtract(orientation.toUnitVector());
            default -> position;
        };

        if(map.canMoveTo(updatedPosition)){
            position = updatedPosition;
        }
    }
}
