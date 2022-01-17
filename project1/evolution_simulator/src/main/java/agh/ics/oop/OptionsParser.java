package agh.ics.oop;

public class OptionsParser {
    public MoveDirection parse(int directionArg){

        return switch (directionArg) {
            case 0 -> MoveDirection.FORWARD;    // values + ordinal
            case 1 -> MoveDirection.SLIGHT_RIGHT;
            case 2 -> MoveDirection.RIGHT;
            case 3 -> MoveDirection.SHARP_RIGHT;
            case 4 -> MoveDirection.BACKWARD;
            case 5 -> MoveDirection.SHARP_LEFT;
            case 6 -> MoveDirection.LEFT;
            case 7 -> MoveDirection.SLIGHT_LEFT;
            default -> throw new IllegalArgumentException("\"" + directionArg + "\"" + " is not legal move specification");
        };
    }
}
