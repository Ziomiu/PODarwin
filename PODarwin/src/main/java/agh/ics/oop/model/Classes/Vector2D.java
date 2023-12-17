package agh.ics.oop.model.Classes;

public record Vector2D(int x, int y) implements Comparable<Vector2D> {

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2D other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2D other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vector2D Vector2D))
            return false;
        return x == Vector2D.x && y == Vector2D.y;
    }

    @Override
    public int compareTo(Vector2D other) {
        if (this.x != other.x) {
            return Integer.compare(this.x, other.x);
        }
        return Integer.compare(this.y, other.y);
    }
}
