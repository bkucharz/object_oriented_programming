package agh.ics.oop;

public class World {
    public static void run(String[] arguments){
        System.out.println("Zwierzak idzie do przodu");
        String message = "";
        for (String argument : arguments){
            message = message + argument + ", ";
        }
        message.replaceAll(".$", "");
        message.replaceAll(".$", "");
        System.out.println(message);
    }


    public static void main(String[] args){
        System.out.println("System wystartowal");
        String[] strArray = new String[]{"Foo","Bar","Baz"};
        run(strArray);
        System.out.println("System zakonczyl dzialanie");

    }
}
