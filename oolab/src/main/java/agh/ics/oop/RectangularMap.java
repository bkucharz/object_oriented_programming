package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap{
    public RectangularMap(int width, int height, int x0, int y0){
        lowerLeftCorner = new Vector2d(x0, y0);
        upperRightCorner = new Vector2d(x0 + width - 1, y0 + height - 1);
    }


    public RectangularMap(int width, int height){
        this(width, height, 0, 0);
    }

    public boolean canMoveTo(Vector2d position){
        return (position.precedes(upperRightCorner) && position.follows(lowerLeftCorner) && !isOccupied(position));
    }


    public String toString(){
        return visualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
