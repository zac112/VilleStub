package fi.utu.ville.exercises.testexer.elements;

import java.io.Serializable;
import java.util.ArrayList;

public class Molecule implements Serializable {

    private static final long serialVersionUID = -6260031633710031461L;

    public static final SelectableGridElement EmptyElement = Elements.EMPTY;
    public static final MoleculePart EmptyMoleculePart = new Element(
            Elements.EMPTY, 0, 0);

    // These lists need to be separate, because ViLLE's serializer apparently
    // doesn't serialize by the runtime type, but by the declared type. Using
    // one list of type MoleculePart will cause problems...
    ArrayList<Element> elements = new ArrayList<Element>();
    ArrayList<Bond> bonds = new ArrayList<Bond>();

    public Molecule() {

    }

    public void addGridElement(SelectableGridElement element, int x, int y) {

        if (!empty(x, y)) {
            removeAt(x, y);
        }

        if (element instanceof Bonds) {
            addBond(new Bond((Bonds) element, x, y));
        } else if (element instanceof Elements) {
            addElement(new Element((Elements) element, x, y));
        }

        rebuildBonds();
    }

    private void rebuildBonds() {
        for (Bond b : bonds) {
            Element[] connectedElements = new Element[2];
            for (int i = 0; i < b.getDirection().length; i++) {
                connectedElements[i] = findElement(calculatePosition(b,
                        b.getDirection()[i]));
            }
            b.bond(connectedElements);
            /*
             * System.out.println("created bond " + b + " connecting " +
             * connectedElements[0] + " and " + connectedElements[1]);
             */
        }
    }

    private int[] calculatePosition(Bond b, int[] direction) {
        return new int[] { b.getX() + direction[0], b.getY() + direction[1] };
    }

    private void removeAt(int x, int y) {
        for (int i = 0; i < bonds.size(); i++) {
            if (bonds.get(i).overlaps(x, y)) {
                bonds.remove(i);
                break;
            }
        }
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).overlaps(x, y)) {
                elements.remove(i);
                break;
            }
        }
    }

    private boolean empty(int x, int y) {
        for (Bond b : bonds) {
            if (b.overlaps(x, y)) {
                return false;
            }
        }
        for (Element e : elements) {
            if (e.overlaps(x, y)) {
                return false;
            }
        }
        return true;
    }

    private void addBond(Bond bond) {
        bonds.add(bond);
    }

    private void addElement(Element element) {
        elements.add(element);
    }

    private Element[] getElements() {
        Element[] result = new Element[0];
        return elements.toArray(result);

    }

    private Bond[] getBonds() {
        Bond[] result = new Bond[0];
        return bonds.toArray(result);

    }

    public int getNumberOfElements() {
        return elements.size();
    }

    public int getNumberOfBonds() {
        return bonds.size();
    }

    private int getNumberOfElements(Element element) {
        int result = 0;
        Element[] tempList = getElements();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].equals(element)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Molecules are equal if they have the same number of bonds and elements in
     * them.
     * 
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = obj instanceof Molecule;
        if (result) {
            Molecule other = (Molecule) obj;
            result &= other.getNumberOfBonds() == this.getNumberOfBonds();
            result &= other.getNumberOfElements() == this.getNumberOfElements();

            Element[] elements = getElements();
            for (Element element : elements) {
                result &= other.getNumberOfElements(element) == this
                        .getNumberOfElements(element);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // int OH = countOH();
        return elements.toString() + bonds.toString();
    }

    private Element findElement(int[] point) {
        return findElement(point[0], point[1]);
    }

    private Element findElement(int x, int y) {
        Element[] elements = getElements();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].overlaps(x, y)) {
                return elements[i];
            }
        }
        return null;
    }

    /**
     * Finds the bond at the given x,y-coordinate
     * 
     * @param x
     * @param y
     * @return
     */
    private Bond findBond(int x, int y) {
        Bond[] bonds = getBonds();
        for (int i = 0; i < bonds.length; i++) {
            if (bonds[i].overlaps(x, y)) {
                return bonds[i];
            }
        }
        return null;
    }

    public SelectableGridElement getGridElement(int x, int y) {
        MoleculePart result;

        result = findElement(x, y);
        if (result != null) {
            return result.getGridElement();
        }
        result = findBond(x, y);
        if (result != null) {
            return result.getGridElement();
        }

        return EmptyElement;
    }

    /**
     * Evaluates this molecule by comparing it with the correct one. Evaluation
     * happens by first comparing the elements and then by comparing bonds. If
     * all elements are not correct, a final score of 0 is given. For each equal
     * bond a score increase of 1/(bonds in larger molecule) is given. If all
     * bonds in this molecule are equal to those in the correctMolecule, a score
     * of 1 is given.
     * 
     * @return
     */
    public double evaluate(Molecule correctMolecule) {
        /*
         * System.out.println("correctMolecule: " + correctMolecule +
         * " this molecule: " + toString());
         */
        double score = 0.0;
        // Compare elements
        if (getNumberOfElements() != correctMolecule.getNumberOfElements()) {
            /*
             * System.out.println("mismatch in element number; this: " +
             * getNumberOfElements() + " correct: " +
             * correctMolecule.getNumberOfElements());
             */
            return 0.0;
        }

        boolean[] used = new boolean[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < correctMolecule.elements.size(); j++) {
                if (!used[j]
                        && correctMolecule.elements.get(j).equals(
                                elements.get(i))) {
                    used[j] = true;
                    break;
                }
            }
        }
        for (boolean b : used) {
            if (!b) {
                // System.out.println("not all elements were found");
                return 0.0;
            }
        }

        // Compare bonds
        Molecule largerMolecule = correctMolecule;
        Molecule smallerMolecule = this;
        if (this.getNumberOfBonds() > correctMolecule.getNumberOfBonds()) {
            largerMolecule = this;
            smallerMolecule = correctMolecule;
        } else {
            largerMolecule = correctMolecule;
            smallerMolecule = this;

        }
        used = new boolean[smallerMolecule.getNumberOfBonds()];
        for (int i = 0; i < largerMolecule.getNumberOfBonds(); i++) {
            for (int j = 0; j < smallerMolecule.getNumberOfBonds(); j++) {
                if (!used[j]
                        && largerMolecule.bonds.get(i).equals(
                                smallerMolecule.bonds.get(j))) {
                    used[j] = true;
                    break;
                }
            }
        }

        for (boolean b : used) {
            if (b) {
                // make sure no div by zero by getting max[1,bonds]
                score += (1.0 / Math
                        .max(largerMolecule.getNumberOfBonds(), 1.0));
            }
        }
        return score;
    }

    /**
     * Trims the molecule. After the trim this molecule will contain no empty
     * atoms.
     */
    public void trimMolecule() {
        // iterating backwards so we've less to move around in the array
        int i = elements.size() - 1;
        while (i >= 0) {
            if (elements.get(i).equals(Elements.EMPTY)) {
                elements.remove(i);
            }
            i--;

        }
    }

    /**
     * Calculates the width and height of this element.
     * 
     * @return an int-array where index 0 is width and 1 is height
     */
    public int[] getMoleculeSize() {
        int[] result = new int[2];
        int[] offset = getOffset();
        int rightmostElement = Integer.MIN_VALUE;
        int bottomElement = Integer.MIN_VALUE;

        for (Element e : elements) {
            if (e.getX() > rightmostElement) {
                rightmostElement = e.getX();
            }
            if (e.getY() > bottomElement) {
                bottomElement = e.getY();
            }
        }

        result[0] = (rightmostElement - offset[0]) + 1;
        result[1] = (bottomElement - offset[1]) + 1;

        return result;
    }

    /**
     * Calculates the position of the top-leftmost element in a coordinate
     * system where the origo (0,0) is the top-left corner.
     * 
     * @return an int array of the top-leftmost position in the form [x,y]
     */
    public int[] getOffset() {
        int leftmostElement = Integer.MAX_VALUE;
        int topmostElement = Integer.MAX_VALUE;

        for (Element e : elements) {
            if (e.getX() < leftmostElement) {
                leftmostElement = e.getX();
            }

            if (e.getY() < topmostElement) {
                topmostElement = e.getY();
            }
        }
        return new int[] { leftmostElement, topmostElement };
    }

}
