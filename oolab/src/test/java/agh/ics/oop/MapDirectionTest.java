package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MapDirectionTest {
    @Test
    public void testNext(){
        MapDirection dir = MapDirection.NORTH;
        dir = dir.next();
        Assertions.assertEquals(dir, MapDirection.EAST);
        dir = dir.next();
        Assertions.assertEquals(dir, MapDirection.SOUTH);
        dir = dir.next();
        Assertions.assertEquals(dir, MapDirection.WEST);
        dir = dir.next();
        Assertions.assertEquals(dir, MapDirection.NORTH);
    }

    @Test
    public void testPrevious(){
        MapDirection dir = MapDirection.NORTH;
        dir = dir.previous();
        Assertions.assertEquals(dir, MapDirection.WEST);
        dir = dir.previous();
        Assertions.assertEquals(dir, MapDirection.SOUTH);
        dir = dir.previous();
        Assertions.assertEquals(dir, MapDirection.EAST);
        dir = dir.previous();
        Assertions.assertEquals(dir, MapDirection.NORTH);
    }
}
