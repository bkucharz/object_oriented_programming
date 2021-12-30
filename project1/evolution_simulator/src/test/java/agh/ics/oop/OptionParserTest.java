package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {
    @Test
    public void parse(){
        OptionsParser parser = new OptionsParser();
        MoveDirection direction = parser.parse(0);
        assertEquals(MoveDirection.FORWARD, direction);
        direction = parser.parse(1);
        assertEquals(MoveDirection.SLIGHT_RIGHT, direction);
        direction = parser.parse(2);
        assertEquals(MoveDirection.RIGHT, direction);
        direction = parser.parse(3);
        assertEquals(MoveDirection.SHARP_RIGHT, direction);
        direction = parser.parse(4);
        assertEquals(MoveDirection.BACKWARD, direction);
        direction = parser.parse(5);
        assertEquals(MoveDirection.SHARP_LEFT, direction);
        direction = parser.parse(6);
        assertEquals(MoveDirection.LEFT, direction);
        direction = parser.parse(7);
        assertEquals(MoveDirection.SLIGHT_LEFT, direction);


        assertThrows(IllegalArgumentException.class, () -> new OptionsParser().parse(9));
        assertThrows(IllegalArgumentException.class, () -> new OptionsParser().parse(-1));

    }
}
