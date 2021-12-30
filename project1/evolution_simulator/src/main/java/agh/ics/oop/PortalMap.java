package agh.ics.oop;

public class PortalMap extends AbstractWorldMap{
    public PortalMap(int width, int height){
        super(width, height);
    }

    public PortalMap(int width, int height, float jungleRatio) {
        super(width, height, jungleRatio);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public Vector2d onMapPosition(Vector2d wishedPosition) {
        return new Vector2d((upperRightCorner.x + 1 + wishedPosition.x)% (upperRightCorner.x+1), (upperRightCorner.y + 1 + wishedPosition.y)% (upperRightCorner.y+1));
    }
}
