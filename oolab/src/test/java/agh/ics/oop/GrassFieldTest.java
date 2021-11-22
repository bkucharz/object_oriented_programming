package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    void place(){
        IWorldMap map = new GrassField(10);
        assertTrue(map.place(new Animal(map, new Vector2d(2, 2))));
        assertTrue(map.place(new Animal(map, new Vector2d(3, 4))));

        assertTrue(map.place(new Animal(map, new Vector2d(10, 10))));
        assertTrue(map.place(new Animal(map, new Vector2d(11, 5))));
    }

    @Test
    void canMoveTo() {
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(2, 2)));
        map.place(new Animal(map, new Vector2d(3, 4)));

        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(3, 4)));

        assertTrue(map.canMoveTo(new Vector2d(4, 2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 5)));

        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));
        assertTrue(map.canMoveTo(new Vector2d(2, 0)));
        assertTrue(map.canMoveTo(new Vector2d(0, 3)));
    }

    @Test
    void isOccupied() {
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(1, 6)));
        map.place(new Animal(map, new Vector2d(9, 5)));
        map.place(new Animal(map, new Vector2d(2, 1)));


        assertTrue(map.isOccupied(new Vector2d(1, 6)));
        assertTrue(map.isOccupied(new Vector2d(9, 5)));
        assertTrue(map.isOccupied(new Vector2d(2, 1)));
    }

    @Test
    void objectAt() {
        IWorldMap map = new GrassField(10);
        Animal a1 = new Animal(map, new Vector2d(1, 6));
        map.place(a1);
        Animal a2 = new Animal(map, new Vector2d(9, 5));
        map.place(a2);
        Animal a3 = new Animal(map, new Vector2d(2, 1));
        map.place(a3);
        Animal a4 = new Animal(map, new Vector2d(2, 1));
        map.place(a4);

        assertEquals(map.objectAt(new Vector2d(1, 6)), a1);
        assertEquals(map.objectAt(new Vector2d(9, 5)), a2);
        assertEquals(map.objectAt(new Vector2d(2, 1)), a3);

        assertNotEquals(map.objectAt(new Vector2d(2, 1)), a4);
    }
}
