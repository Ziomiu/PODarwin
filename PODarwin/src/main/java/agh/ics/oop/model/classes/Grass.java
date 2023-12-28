package agh.ics.oop.model.classes;

public class Grass implements WorldElement, Drawable {
    private final Vector2D position;

    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void draw() {
        // todo
    }
}
