package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString(){
        switch (this){
            case EAST: return "Wschód";
            case WEST: return "Zachód";
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            default: return null;
        }
    }

    public MapDirection next(){
        return MapDirection.values()[(this.ordinal() + 1)%4];
    }

    public MapDirection previous(){
        return MapDirection.values()[(this.ordinal() + 3)%4];
    }

    public Vector2d toUnitVector(){
        Vector2d UnitVector = switch (this){
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            default -> null;
        };
        return UnitVector;
    }
}
