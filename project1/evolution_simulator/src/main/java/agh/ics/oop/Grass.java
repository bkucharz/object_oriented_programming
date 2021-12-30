package agh.ics.oop;

public class Grass implements IWorldMapElement{
    private final Vector2d position;
    private final int nutritiousness;

    public Grass(Vector2d position){
        this(position, 1);
    }

    public Grass(Vector2d position, int nutritiousness){
        this.position = position;
        this.nutritiousness = nutritiousness;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return nutritiousness;
    }

    public String toString(){
        return "*";
    }
}
