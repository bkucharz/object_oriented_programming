package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimulationEngineTest {
    @Test
    public void movingTest(){
        IWorldMap map0 = new RectangularMap(4, 3);
        String[] args = new String[]{"l" , "b", "f", "f", "r", "r", "b", "b", "r", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        Vector2d[] pos = {new Vector2d(0, 0),new Vector2d(0, 0),
                                new Vector2d(1, 1), new Vector2d(3, 2)};
        assertThrows(IllegalArgumentException.class, () -> {
            IEngine engine = new SimulationEngine(directions, map0, pos);

        });

        IWorldMap map = new RectangularMap(4, 3);
        Vector2d[] positions = {new Vector2d(0, 0), new Vector2d(1, 1), new Vector2d(3, 2)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        Animal animal = (Animal) map.objectAt(new Vector2d(0, 0));
        Assertions.assertEquals(animal.getPosition(), new Vector2d(0 ,0));
        Assertions.assertEquals(animal.getOrientation(), MapDirection.WEST);

        animal = (Animal) map.objectAt(new Vector2d(1, 0));
        Assertions.assertEquals(animal.getPosition(), new Vector2d(1 ,0));
        Assertions.assertEquals(animal.getOrientation(), MapDirection.EAST);

        animal = (Animal) map.objectAt(new Vector2d(3, 2));
        Assertions.assertEquals(animal.getPosition(), new Vector2d(3 ,2));
        Assertions.assertEquals(animal.getOrientation(), MapDirection.SOUTH);
    }
}
