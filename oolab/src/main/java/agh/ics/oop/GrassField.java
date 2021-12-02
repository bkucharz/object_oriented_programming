package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    private Map<Vector2d, Grass> grasses = new LinkedHashMap<>();
    private int width;


    public GrassField(int numberOfGrass){
        this.lowerLeftCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upperRightCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        this.width = (int) Math.sqrt(numberOfGrass * 10);

        Random random = new Random();
        for(int i=0; i < numberOfGrass; i++){
            while (true){
                int x = random.nextInt(width + 1);
                int y = random.nextInt(width + 1);

                Vector2d position = new Vector2d(x, y);
                if (!(objectAt(position) instanceof Grass)){
                    this.grasses.put(position, new Grass(position));
                    boundaries.put(new Grass(position));
                    break;
                }
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position) || (objectAt(position) instanceof Grass);
    }

    public boolean isOccupied(Vector2d position) {
        boolean temp = super.isOccupied(position);
        if(!temp){
            if(grasses.containsKey(position)){
                return true;
            }
        }
        return temp;
    }

    public Object objectAt(Vector2d position){
        Object temp = super.objectAt(position);

        if(temp == null){
            return grasses.get(position);
        }
        return temp;
    }

    public String toString(){
        return visualizer.draw(boundaries.getLowerLeftCorner(), boundaries.getUpperRightCorner());
    }
}
