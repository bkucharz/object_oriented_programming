package agh.ics.oop;

import static agh.ics.oop.Direction.*;

public class World {
    public static void run(Direction[] arguments){
        System.out.println("Start");

        for (Direction dir : arguments){
            if (dir == null){
                continue;
            }
            String message = switch (dir){
                case FORWARD -> "idzie do przodu";
                case BACKWARD -> "idzie do tyłu";
                case RIGHT -> "skręca w prawo";
                case LEFT -> "skręca w lewo";
            };
            System.out.println("Zwierzak " + message);
        }
        System.out.println("Stop");
    }

    public static Direction[] convertToEnum(String[] strings){
        int n = strings.length;
        Direction[] converted = new Direction[n];
        for(int i = 0; i < n; i++){
            converted[i] = switch(strings[i]){
                case "f" -> FORWARD;
                case "b" -> BACKWARD;
                case "r" -> RIGHT;
                case "l" -> LEFT;
                default -> null;
            };
        }
        return converted;
    }

    public static void main(String[] args){
        System.out.println("System wystartował");
        Direction[] directions = convertToEnum(args);
        run(directions);
        System.out.println("System zakonczył działanie");

    }
}
