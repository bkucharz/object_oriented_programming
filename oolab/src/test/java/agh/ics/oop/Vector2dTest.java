package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Vector2dTest {
    @Test
    public void equalsTest(){
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        Vector2d vector3 = new Vector2d(1, 2);
        Vector2d vector4 = new Vector2d(2, 1);
        Object ob = new Object();

        Assertions.assertFalse(vector1.equals(null));
        Assertions.assertFalse(vector1.equals(ob));
        Assertions.assertTrue(vector1.equals(vector2));
        Assertions.assertTrue(vector2.equals(vector1));
        Assertions.assertTrue(vector2.equals(vector3));
        Assertions.assertTrue(vector1.equals(vector3));
        Assertions.assertFalse(vector1.equals(vector4));
    }

    @Test
    public void toStringTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Assertions.assertEquals(v1.toString(), "(1,2)");
        Assertions.assertNotEquals(v1.toString(), "(2,1)");
    }

    @Test
    public void precedesTest(){
        Vector2d v1 = new Vector2d(0, 0);
        Assertions.assertTrue(v1.precedes(new Vector2d(0, 0)));
        Assertions.assertTrue(v1.precedes(new Vector2d(1, 0)));
        Assertions.assertTrue(v1.precedes(new Vector2d(0, 1)));
        Assertions.assertTrue(v1.precedes(new Vector2d(1, 1)));

        Assertions.assertFalse(v1.precedes(new Vector2d(-1, 0)));
        Assertions.assertFalse(v1.precedes(new Vector2d(0, -1)));
        Assertions.assertFalse(v1.precedes(new Vector2d(-1, -1)));
    }

    @Test
    public void followsTest(){
        Vector2d v1 = new Vector2d(0, 0);
        Assertions.assertTrue(v1.follows(new Vector2d(0, 0)));
        Assertions.assertTrue(v1.follows(new Vector2d(-1, 0)));
        Assertions.assertTrue(v1.follows(new Vector2d(0, -1)));
        Assertions.assertTrue(v1.follows(new Vector2d(-1, -1)));


        Assertions.assertFalse(v1.follows(new Vector2d(1, 0)));
        Assertions.assertFalse(v1.follows(new Vector2d(0, 1)));
        Assertions.assertFalse(v1.follows(new Vector2d(1, 1)));
    }

    @Test
    public void upperRightTest(){
        Vector2d v1 = new Vector2d(0, 1);
        Vector2d v2 = new Vector2d(1, 0);
        Assertions.assertEquals(v1.upperRight(v2), new Vector2d(1, 1));

        v1 = new Vector2d(1, 1);
        v2 = new Vector2d(0, 0);
        Assertions.assertEquals(v1.upperRight(v2), new Vector2d(1, 1));

        v1 = new Vector2d(0, 1);
        v2 = new Vector2d(0, 0);
        Assertions.assertEquals(v1.upperRight(v2), new Vector2d(0, 1));
    }

    @Test
    public void lowerLeftTest(){
        Vector2d v1 = new Vector2d(0, 1);
        Vector2d v2 = new Vector2d(1, 0);
        Assertions.assertEquals(v1.lowerLeft(v2), new Vector2d(0, 0));

        v1 = new Vector2d(1, 1);
        v2 = new Vector2d(0, 0);
        Assertions.assertEquals(v1.lowerLeft(v2), new Vector2d(0, 0));

        v1 = new Vector2d(0, 1);
        v2 = new Vector2d(0, 0);
        Assertions.assertEquals(v1.lowerLeft(v2), new Vector2d(0, 0));
    }

    @Test
    public void addTest(){
        Vector2d v1 = new Vector2d(-1, 2);
        Assertions.assertEquals(v1.add(new Vector2d(1, -2)), new Vector2d(0, 0));
        Assertions.assertEquals(v1.add(new Vector2d(-1, 2)), new Vector2d(-2, 4));

    }

    @Test
    public void subtractTest(){
        Vector2d v1 = new Vector2d(-1, 2);
        Assertions.assertEquals(v1.subtract(new Vector2d(1, -2)), new Vector2d(-2, 4));
        Assertions.assertEquals(v1.subtract(new Vector2d(-1, 2)), new Vector2d(0, 0));
    }

    @Test
    public void oppositeTest(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, 1);
        Vector2d v3 = new Vector2d(2, 3);

        Assertions.assertEquals(v1.opposite(), new Vector2d(0, 0));
        Assertions.assertEquals(v2.opposite(), new Vector2d(1, -1));
        Assertions.assertEquals(v3.opposite(), new Vector2d(-2, -3));
    }
}
