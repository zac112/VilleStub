package fi.utu.ville.exercises.testexer;

import fi.utu.ville.exercises.model.ExerciseData;
import fi.utu.ville.exercises.testexer.elements.Molecule;

public class TestExerExerciseData implements ExerciseData {

    /**
	 * 
	 */
    private static final long serialVersionUID = -716445297446246493L;

    private final Molecule[] oldMolecules;

    private final String[] names;

    private final int[][] dimensions;

    public TestExerExerciseData(Molecule[] molecules, String[] names,
            int[][] dimensions) {
        this.names = names;
        this.oldMolecules = molecules;
        this.dimensions = dimensions;
    }

    public Molecule[] getMolecules() {
        return oldMolecules;
    }

    public String getNameForTab(int i) {
        return names[i];
    }

    public int getTabs() {
        return oldMolecules.length;
    }

    public int[] getDimensions(int i) {
        return dimensions[i];
    }

}
