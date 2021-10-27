package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    public MapDirection getOrientation(){
        return orientation;
    }

    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        return "Position: "+position+"\t\tOrientation: "+orientation;
    }

    public void move(MoveDirection direction){
        Vector2d upperRightCorner = new Vector2d(4, 4);
        Vector2d lowerLeftCorner = new Vector2d(0, 0);

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

        if (updatedPosition.precedes(upperRightCorner) && updatedPosition.follows(lowerLeftCorner)){
            this.position = updatedPosition;
        }
    }
}
