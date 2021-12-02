package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    IWorldMap map;

    public MapBoundary(IWorldMap map){
        this.map = map;
    }

    SortedSet<IWorldMapElement> xAxis = new TreeSet<>((e1, e2) -> {
        if (e1.getPosition().x < e2.getPosition().x) return -1;
        else if (e1.getPosition().x > e2.getPosition().x) return 1;
        else if (e1.getPosition().y < e2.getPosition().y) return -1;
        else if (e1.getPosition().y > e2.getPosition().y) return 1;
        else if (e1.getClass() == e2.getClass()) return 0;
        else return 1;
    });

    SortedSet<IWorldMapElement> yAxis = new TreeSet<>((e1, e2) -> {
        if (e1.getPosition().y < e2.getPosition().y) return -1;
        else if (e1.getPosition().y > e2.getPosition().y) return 1;
        else if (e1.getPosition().x < e2.getPosition().x) return -1;
        else if (e1.getPosition().x > e2.getPosition().x) return 1;
        else if (e1.getClass() == e2.getClass()) return 0;
        else return 1;
    });
    public void put(Object o){
        xAxis.add((IWorldMapElement) o);
        yAxis.add((IWorldMapElement) o);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        xAxis.remove(new Animal(map, oldPosition));
        xAxis.add(new Animal(map, newPosition));
        yAxis.remove(new Animal(map, oldPosition));
        yAxis.add(new Animal(map, newPosition));
    }

    public Vector2d getUpperRightCorner(){
        return new Vector2d(xAxis.last().getPosition().x, yAxis.last().getPosition().y);
    }

    public Vector2d getLowerLeftCorner(){
        return new Vector2d(xAxis.first().getPosition().x, yAxis.first().getPosition().y);
    }
}
