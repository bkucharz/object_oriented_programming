package agh.ics.oop;

public class BoundedMap extends AbstractWorldMap{
    public BoundedMap(int width, int height){
        super(width, height);
    }

    public BoundedMap(int width, int height, float jungleRatio){
        super(width, height, jungleRatio);
    }
    public boolean canMoveTo(Vector2d position){
        return (position.precedes(upperRightCorner) && position.follows(lowerLeftCorner));
    }

    @Override
    public Vector2d onMapPosition(Vector2d wishedPosition) {
        int newX = Math.max(wishedPosition.x, lowerLeftCorner.x);
        newX = Math.min(newX, upperRightCorner.x);
        int newY = Math.max(wishedPosition.y, lowerLeftCorner.y);
        newY = Math.min(newY, upperRightCorner.y);
        return new Vector2d(newX, newY);
    }
}
