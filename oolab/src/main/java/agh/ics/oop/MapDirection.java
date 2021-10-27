package agh.ics.oop;

public enum MapDirection {
    NORTH("Północ", 0, 1),
    EAST("Wschód", 1, 0),
    SOUTH("Południe", 0, -1),
    WEST("Zachód", -1, 0);

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
        return MapDirection.values()[(this.ordinal() + 1)%4];
    }

    public MapDirection previous(){
        return MapDirection.values()[(this.ordinal() + 3)%4];
    }

    public Vector2d toUnitVector(){
        return unitVector;
    }
}
