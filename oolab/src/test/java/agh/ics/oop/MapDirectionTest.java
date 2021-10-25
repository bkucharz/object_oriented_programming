package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MapDirectionTest {
    @Test
    public void testNext(){
        MapDirection dir = MapDirection.NORTH;
        for (int i = 0; i < 4; i++){
            Assertions.assertEquals(dir, MapDirection.values()[i%4]);
            dir = dir.next();
        }
    }

    @Test
    public void testPrevious(){
        MapDirection dir = MapDirection.NORTH;
        for (int i = 4; i > 0; i--){
            Assertions.assertEquals(dir, MapDirection.values()[i%4]);
            dir = dir.previous();
        }
    }
}
