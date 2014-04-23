package fi.utu.ville.exercises.testexer.elements;

import java.io.Serializable;

public abstract class MoleculePart implements Serializable {

    private static final long serialVersionUID = -6260031633710031452L;

    protected int x;
    protected int y;

    public MoleculePart() {

    }

    public MoleculePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract SelectableGridElement getGridElement();

    public boolean overlaps(MoleculePart other) {
        return (other.x == this.x) && (other.y == this.y);
    }

    public boolean overlaps(int x, int y) {
        return this.x == x && this.y == y;
    }
}
