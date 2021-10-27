package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
    public MoveDirection[] parse(String[] directionArgs){
        int i = 0;
        MoveDirection[] directions = new MoveDirection[directionArgs.length];

        for(String dir : directionArgs){
            switch (dir){
                case "f":
                case "forward": directions[i] = MoveDirection.FORWARD; i++; break;

                case "b":
                case "backward": directions[i] = MoveDirection.BACKWARD; i++;break;

                case "l":
                case "left": directions[i] = MoveDirection.LEFT; i++;break;

                case "r":
                case "right": directions[i] = MoveDirection.RIGHT; i++;break;
            }
        }
        return Arrays.copyOfRange(directions, 0, i);
    }
}
