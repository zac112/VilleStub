package fi.utu.ville.exercises.testexer.elements;

public class Element extends MoleculePart {

    private static final long serialVersionUID = -6260031633710031460L;

    private Elements element;

    public Element(Elements element, int x, int y) {
        super(x, y);
        this.element = element;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Elements) {
            return ((Elements) obj) == element;
        }
        if (obj instanceof Element) {
            return ((Element) obj).element == element;
        }
        return false;
    }

    @Override
    public SelectableGridElement getGridElement() {
        return element;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Element [element=" + element + " " + getX() + " " + getY()
                + "]\n";
    }

}
