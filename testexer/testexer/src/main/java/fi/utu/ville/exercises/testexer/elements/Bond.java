package fi.utu.ville.exercises.testexer.elements;

public class Bond extends MoleculePart {

    private static final long serialVersionUID = -6260031633710031463L;

    Bonds bondType;
    Element[] connectedElements;

    public Bond(Bonds bond, int x, int y) {
        super(x, y);
        connectedElements = new Element[2];
        bondType = bond;
    }

    public boolean isValidBond() {
        for (int i = 0; i < connectedElements.length; i++) {
            if (connectedElements[i] == null
                    || connectedElements[i].equals(Elements.EMPTY)) {
                return false;
            }
        }
        return true;
    }

    public int[][] getDirection() {
        return bondType.getConnectedPoints();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Bond [bondType=" + bondType + " " + getX() + " " + getY()
                + "]\n";
    }

    public void bond(Element[] elements) {
        connectedElements = elements;
    }

    @Override
    public SelectableGridElement getGridElement() {
        return bondType;
    }

    /**
     * Two bonds are equal if they connect the same elements and have the same
     * number of bonds. The direction of bonds is irrelevant.
     * 
     * @return true if bonds are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = obj instanceof Bond;
        if (result) {
            Bond other = (Bond) obj;
            if (!other.isValidBond()) {
                return false;
            }

            result &= connectsSameElements(other);
            result &= hasEqualNumberOfBonds(other);
        }
        return result;
    }

    private boolean connectsSameElements(Bond other) {
        boolean result = true;
        boolean[] used = new boolean[other.connectedElements.length];
        for (int i = 0; i < connectedElements.length; i++) {
            for (int j = 0; j < other.connectedElements.length; j++) {
                if (!used[j]
                        && other.connectedElements[j]
                                .equals(connectedElements[i])) {
                    used[j] = true;
                    break;
                }
            }
        }
        for (boolean b : used) {
            result &= b;
        }
        return result;
    }

    private boolean hasEqualNumberOfBonds(Bond other) {
        return other.bondType.getNumberOfBonds() == this.bondType
                .getNumberOfBonds();
    }

}

/*
 * TODO:submission viewing
 */
