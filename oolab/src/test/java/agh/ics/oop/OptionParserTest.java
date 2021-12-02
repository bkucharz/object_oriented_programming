package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {
    @Test
    public void parse(){
        String[] args = new String[]{"f","b","l","r","backward", "forward","left","right"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        directions[0] = MoveDirection.FORWARD;
        directions[1] = MoveDirection.BACKWARD;
        directions[2] = MoveDirection.LEFT;
        directions[3] = MoveDirection.RIGHT;
        directions[4] = MoveDirection.BACKWARD;
        directions[5] = MoveDirection.FORWARD;
        directions[6] = MoveDirection.LEFT;
        directions[7] = MoveDirection.RIGHT;

        assertThrows(IllegalArgumentException.class, () ->{
            String[] arguments = new String[]{"f","b","l","r","bakwerd", "forward","left","right"};
            new OptionsParser().parse(arguments);
        });

    }
}
