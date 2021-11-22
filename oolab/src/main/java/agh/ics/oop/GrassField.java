package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap{
    private List<Grass> grasses = new LinkedList<>();
    private int width;


    public GrassField(int numberOfGrass){
        this(numberOfGrass, 0, 0);
    }
    public GrassField(int numberOfGrass, int x0, int y0){
        this.lowerLeftCorner = new Vector2d(x0, y0);
        this.width = (int) Math.sqrt(numberOfGrass * 10);
        this.upperRightCorner = new Vector2d(width, width);

        Random random = new Random();
        for(int i=0; i < numberOfGrass; i++){
            while (true){
                int x = random.nextInt(width + 1);
                int y = random.nextInt(width + 1);

                Vector2d position = new Vector2d(x, y);
                if (!(objectAt(position) instanceof Grass)){
                    this.grasses.add((new Grass(position)));
                    break;
                }
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position) || (objectAt(position) instanceof Grass);
    }


    public Object objectAt(Vector2d position){
        Object temp = super.objectAt(position);

        if(temp == null){
            for(Grass grass : grasses){
                if(grass.getPosition().equals(position)){
                    return grass;
                }
            }
        }
        return temp;
    }

    public String toString(){
        for(Animal animal : animals){
            this.upperRightCorner = upperRightCorner.upperRight(animal.getPosition());
            this.lowerLeftCorner = lowerLeftCorner.lowerLeft(animal.getPosition());
        }
        return visualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
