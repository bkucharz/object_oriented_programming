package agh.ics.oop;

import static agh.ics.oop.MoveDirection.*;

public class World {
    public static void run(MoveDirection[] arguments){
        System.out.println("Start");

        for (MoveDirection dir : arguments){
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

    public static MoveDirection[] convertToEnum(String[] strings){
        int n = strings.length;
        MoveDirection[] converted = new MoveDirection[n];
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
        Animal animal1 = new Animal();
        System.out.println(animal1);
        OptionsParser parser = new OptionsParser();
        for(MoveDirection option :  parser.parse(args)){
            animal1.move(option);
            System.out.println(animal1);
        }

    }
}
