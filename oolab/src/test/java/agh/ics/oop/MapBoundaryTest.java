package agh.ics.oop;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapBoundaryTest {
    @Test
    public void set(){
        IWorldMap map = new GrassField(10);
        MapBoundary boundary = new MapBoundary(map);
        boundary.put(new Vector2d(2, 0), Animal.class);
        boundary.put(new Vector2d(0, -1), Animal.class);
        boundary.put(new Vector2d(2, 1), Animal.class);
        boundary.put(new Vector2d(2,1), Grass.class);


        assertEquals(new Vector2d(0, -1), boundary.xAxis.first().getKey());
        assertEquals(new Vector2d(2, 1), boundary.xAxis.last().getKey());

        boundary = new MapBoundary(map);
        boundary.put(new Vector2d(0, 0), Animal.class);
        boundary.put(new Vector2d(0, 2), Animal.class);
        boundary.put(new Vector2d(-1, 0), Animal.class);
        boundary.put(new Vector2d(-1, 0), Animal.class);
        boundary.put(new Vector2d(1,2), Grass.class);

        assertEquals(4, boundary.xAxis.size());
        assertEquals(4, boundary.yAxis.size());

        boundary.put(new Vector2d(-1, 0), Grass.class);
        assertEquals(5, boundary.xAxis.size());
        assertEquals(5, boundary.yAxis.size());

        assertEquals(new Vector2d(-1, 0), boundary.yAxis.first().getKey());
        assertEquals(new Vector2d(1, 2), boundary.yAxis.last().getKey());

    }
}
