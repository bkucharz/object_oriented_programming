package agh.ics.oop;

public enum MapDirection {
    NORTH("Północ", 0, 1),
    NORTH_EAST("Północy-Wschód", 1, 1),
    EAST("Wschód", 1, 0),
    SOUTH_EAST("Południowy-Wschód", 1, -1),
    SOUTH("Południe", 0, -1),
    SOUTH_WEST("Półudniowy-Zachód", -1, -1),
    WEST("Zachód", -1, 0),
    NORTH_WEST("Północny-Zachód", -1, 1);


    private final String stringRepr;
    private final Vector2d unitVector;

    MapDirection(String repr, int x, int y){
        stringRepr = repr;
        unitVector = new Vector2d(x, y);
    }

    public String toString(){
        return stringRepr;
    }

    public MapDirection next(){
        return MapDirection.values()[(this.ordinal() + 1)%8];
    }

    public MapDirection previous(){
        return MapDirection.values()[(this.ordinal() + 7)%8];
    }

    public Vector2d toUnitVector(){
        return unitVector;
    }
}
