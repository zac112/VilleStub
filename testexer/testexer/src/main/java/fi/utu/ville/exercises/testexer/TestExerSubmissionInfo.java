package fi.utu.ville.exercises.testexer;

import fi.utu.ville.exercises.model.SubmissionInfo;
import fi.utu.ville.exercises.testexer.elements.Molecule;

public class TestExerSubmissionInfo implements SubmissionInfo {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8702870727095225372L;

    private final Molecule[] molecules;
    private final String[] questions;

    public TestExerSubmissionInfo(Molecule[] molecules, String[] tabs) {
        this.molecules = molecules;
        questions = tabs;
    }

    public Molecule[] getMolecules() {
        return molecules;
    }

    public String[] getQuestions() {
        return questions;
    }

    public int getTabs() {
        return molecules.length;
    }

    public int[] getMoleculeSize(int index) {
        return molecules[index].getMoleculeSize();
    }

    public int[] getOffset(int i) {
        return molecules[i].getOffset();
    }

    public String getAnswerAsString() {
        return "";
    }

}
