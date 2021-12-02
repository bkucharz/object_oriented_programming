package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class AnimalTest {
    @Test
    public void orientation(){
        Animal animal1 = new Animal(new RectangularMap(5, 5));
        OptionsParser parser = new OptionsParser();

        MoveDirection[] moves1 = parser.parse(new String[]{"left", "l", "right", "r", "right", "r"});

        animal1.move(moves1[0]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.WEST);
        animal1.move(moves1[1]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.SOUTH);
        animal1.move(moves1[2]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.WEST);
        animal1.move(moves1[3]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.NORTH);
        animal1.move(moves1[4]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.EAST);
        animal1.move(moves1[5]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.SOUTH);
    }

    @Test
    public void movement(){
        OptionsParser parser = new OptionsParser();
        Animal animal1 = new Animal(new RectangularMap(5, 5));
        MoveDirection[] moves1 = parser.parse(new String[]{"r", "forward", "f", "f", "l", "f", "f", "f"});

        animal1.move(moves1[0]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(2, 2));
        animal1.move(moves1[1]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(3, 2));
        animal1.move(moves1[2]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 2));
        animal1.move(moves1[3]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 2));
        animal1.move(moves1[4]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.NORTH);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 2));
        animal1.move(moves1[5]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 3));
        animal1.move(moves1[6]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 4));
        animal1.move(moves1[7]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(4, 4));

        animal1 = new Animal(new RectangularMap(5, 5));
        moves1 = parser.parse(new String[]{"l", "forward", "f", "f", "r", "b", "b", "b"});

        animal1.move(moves1[0]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.WEST);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(2, 2));
        animal1.move(moves1[1]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(1, 2));
        animal1.move(moves1[2]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(0, 2));
        animal1.move(moves1[3]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(0, 2));
        animal1.move(moves1[4]);
        Assertions.assertEquals(animal1.getOrientation(), MapDirection.NORTH);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(0, 2));
        animal1.move(moves1[5]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(0, 1));
        animal1.move(moves1[6]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(0, 0));
        animal1.move(moves1[7]);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(0, 0));



    }
}
