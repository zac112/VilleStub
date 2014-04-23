package fi.utu.ville.exercises.testexer.elements;

public enum Bonds implements SelectableGridElement {
    VERTICAL1(new int[][] { { 0, 1 }, { 0, -1 } }), VERTICAL2(new int[][] {
            { 0, 1 }, { 0, -1 } }), VERTICAL3(
            new int[][] { { 0, 1 }, { 0, -1 } }), HORIZONTAL1(new int[][] {
            { 1, 0 }, { -1, 0 } }), HORIZONTAL2(new int[][] { { 1, 0 },
            { -1, 0 } }), HORIZONTAL3(new int[][] { { 1, 0 }, { -1, 0 } }), BACKSLASH1(
            new int[][] { { -1, -1 }, { 1, 1 } }), BACKSLASH2(new int[][] {
            { -1, -1 }, { 1, 1 } }), BACKSLASH3(new int[][] { { -1, -1 },
            { 1, 1 } }), SLASH1(new int[][] { { -1, 1 }, { 1, -1 } }), SLASH2(
            new int[][] { { -1, 1 }, { 1, -1 } }), SLASH3(new int[][] {
            { -1, 1 }, { 1, -1 } }), EMPTY(new int[][] { { 0, 0 }, { 0, 0 } });

    private static final long serialVersionUID = -6260031633710031466L;

    int[][] connection;

    private Bonds(int[][] connection) {
        this.connection = connection;
    }

    public int getNumberOfBonds() {
        // System.out.println(this + " has bonds: " + (this.ordinal() % 3 + 1));
        return this.ordinal() % 3 + 1;
    }

    /**
     * Returns the directions to get the elements this bond connects. The points
     * are relative to current position; i.e. {1,1} means one step on the x and
     * y.
     * 
     * @return The directions this bond connects in.
     */
    public int[][] getConnectedPoints() {
        return connection;
    }

    @Override
    public String getPlainSymbol() {
        return "";
    }

    @Override
    public String getHTMLSymbol() {
        return "";
    }

    @Override
    public String getStyleName() {
        return "testexer-" + toString().toLowerCase();
    }

    @Override
    public SelectableGridElement getEmpty() {
        return Bonds.EMPTY;
    }
}
