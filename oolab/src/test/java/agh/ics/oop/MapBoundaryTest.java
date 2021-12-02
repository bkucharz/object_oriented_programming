package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapBoundaryTest {
    @Test
    public void set(){
        IWorldMap map = new GrassField(10);
        MapBoundary boundary = new MapBoundary(map);
        Animal a = new Animal(map, new Vector2d(0, 0));
        boundary.put(a);
        boundary.put(new Animal(map, new Vector2d(2, 0)));
        boundary.put(new Animal(map, new Vector2d(0, -1)));
        boundary.put(new Animal(map, new Vector2d(2, 1)));
        boundary.put(new Grass(new Vector2d(2, 1)));


        boundary.xAxis.remove(new Animal(map, new Vector2d(0, 0)));
//        boundary.xAxis.remove(a);

        for(IWorldMapElement element : boundary.xAxis){
            System.out.print(element.getPosition() + ", ");
        }

        assertEquals(new Vector2d(0, -1), boundary.xAxis.first().getPosition());
        assertEquals(new Vector2d(2, 1), boundary.xAxis.last().getPosition());

        boundary = new MapBoundary(map);
        boundary.put(new Animal(map, new Vector2d(0, 0)));
        boundary.put(new Animal(map, new Vector2d(0, 2)));
        boundary.put(new Animal(map, new Vector2d(-1, 0)));
        boundary.put(new Animal(map, new Vector2d(1, 2)));

        assertEquals(new Vector2d(-1, 0), boundary.yAxis.first().getPosition());
        assertEquals(new Vector2d(1, 2), boundary.yAxis.last().getPosition());

    }
}
