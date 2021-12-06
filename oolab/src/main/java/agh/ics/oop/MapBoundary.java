package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import  javafx.util.Pair;

public class MapBoundary implements IPositionChangeObserver{
    IWorldMap map;

    public MapBoundary(IWorldMap map){
        this.map = map;
    }

    SortedSet<Pair<Vector2d, Class>> xAxis = new TreeSet<>((pair1, pair2) -> {
        Vector2d v1 = pair1.getKey();
        Vector2d v2 = pair2.getKey();
        if (v1.x != v2.x)   return v1.x - v2.x;
        else if (v1.y != v2.y)  return v1.y - v2.y;
        else if (pair1.getValue().equals(pair2.getValue())) return 0;
        else return 1;
    });

    SortedSet<Pair<Vector2d, Class>> yAxis = new TreeSet<>((pair1, pair2) -> {
        Vector2d v1 = pair1.getKey();
        Vector2d v2 = pair2.getKey();
        if (v1.y != v2.y)   return v1.y - v2.y;
        else if (v1.x != v2.x)  return v1.x - v2.x;
        else if (pair1.getValue().equals(pair2.getValue())) return 0;
        else return 1;
    });

    public void put(Vector2d v, Class c){
        xAxis.add(new Pair<>(v, c));
        yAxis.add(new Pair<>(v, c));
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        xAxis.remove(new Pair<>(oldPosition, Animal.class));
        xAxis.add(new Pair<>(newPosition, Animal.class));
        yAxis.remove(new Pair<>(oldPosition, Animal.class));
        yAxis.add(new Pair<>(newPosition, Animal.class));
    }

    public Vector2d getUpperRightCorner(){
        return new Vector2d(xAxis.last().getKey().x, yAxis.last().getKey().y);
    }

    public Vector2d getLowerLeftCorner(){
        return new Vector2d(xAxis.first().getKey().x, yAxis.first().getKey().y);
    }
}
