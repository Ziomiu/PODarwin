package agh.ics.oop.model.world.layers;

public class AbstractLayer implements MapLayer {
    private MapLayer next = null;

    @Override
    public MapLayer getNext() {
        return next;
    }

    @Override
    public void setNext(MapLayer phase) {
        next = phase;
    }
}
