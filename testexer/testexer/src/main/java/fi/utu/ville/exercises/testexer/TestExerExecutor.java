package fi.utu.ville.exercises.testexer;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import fi.utu.ville.exercises.helpers.ExerciseExecutionHelper;
import fi.utu.ville.exercises.model.ExecutionSettings;
import fi.utu.ville.exercises.model.ExecutionState;
import fi.utu.ville.exercises.model.ExecutionStateChangeListener;
import fi.utu.ville.exercises.model.Executor;
import fi.utu.ville.exercises.model.ExerciseException;
import fi.utu.ville.exercises.model.SubmissionListener;
import fi.utu.ville.exercises.model.SubmissionType;
import fi.utu.ville.exercises.testexer.elements.Molecule;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.TempFilesManager;

public class TestExerExecutor extends VerticalLayout implements
        Executor<TestExerExerciseData, TestExerSubmissionInfo> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2682119786422750060L;

    private final ExerciseExecutionHelper<TestExerSubmissionInfo> execHelper =

    new ExerciseExecutionHelper<TestExerSubmissionInfo>();

    private TestExerExerciseData exerciseData;
    private TabSheet sheet = new TabSheet();
    private int tabAmount;

    public TestExerExecutor() {

    }

    @Override
    public void initialize(Localizer localizer,
            TestExerExerciseData exerciseData, TestExerSubmissionInfo oldSubm,
            TempFilesManager materials, ExecutionSettings fbSettings)
            throws ExerciseException {

        this.exerciseData = exerciseData;
        Molecule[] molecules = new Molecule[exerciseData.getTabs()];
        for (int i = 0; i < molecules.length; i++) {
            molecules[i] = new Molecule();
        }
        if (oldSubm != null) {
            molecules = oldSubm.getMolecules();
        }

        tabAmount = exerciseData.getTabs();
        doLayout(exerciseData, molecules);
    }

    private void doLayout(TestExerExerciseData exerciseData,
            Molecule[] oldMolecules) {

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        HorizontalLayout molecyleArea = new HorizontalLayout();

        molecyleArea.addComponent(TestExerGUIUtils.createSelectionArea());

        for (int i = 0; i < exerciseData.getTabs(); i++) {
            sheet.addTab(TestExerGUIUtils.createMoleculeGrid(oldMolecules[i],
                    exerciseData.getDimensions(i)), exerciseData
                    .getNameForTab(i));
        }

        molecyleArea.addComponent(sheet);
        this.addComponent(molecyleArea);
    }

    @Override
    public void registerSubmitListener(
            SubmissionListener<TestExerSubmissionInfo> submitListener) {
        execHelper.registerSubmitListener(submitListener);
    }

    @Override
    public Layout getView() {
        return this;
    }

    @Override
    public void shutdown() {
        // nothing to do here
    }

    @Override
    public void askReset() {
        GridLayout layout = (GridLayout) (sheet.getTab(sheet.getSelectedTab())
                .getComponent());
        layout.removeAllComponents();

        int tab = sheet.getTabPosition(sheet.getTab(sheet.getSelectedTab()));
        ((GridLayout) sheet.getSelectedTab()).addComponent(TestExerGUIUtils
                .createMoleculeGrid(new Molecule(),
                        exerciseData.getDimensions(tab)));
        ((GridLayout) sheet.getSelectedTab()).setData(new Molecule());
    }

    @Override
    public ExecutionState getCurrentExecutionState() {
        return execHelper.getState();
    }

    @Override
    public void askSubmit(SubmissionType submType) {
        double[] scores = new double[tabAmount];
        Molecule[] submissions = new Molecule[tabAmount];
        String[] tabNames = new String[tabAmount];
        for (int i = 0; i < tabAmount; i++) {
            submissions[i] = TestExerGUIUtils
                    .getTrimmedMolecule((GridLayout) sheet.getTab(i)
                            .getComponent());// trimming to save space on disk
            // TODO: use an Evaluation-object to store feedback on performance,
            // eg. "You have too many/few elements."
            scores[i] = submissions[i].evaluate(exerciseData.getMolecules()[i]);
            tabNames[i] = exerciseData.getNameForTab(i);
        }

        execHelper.informOnlySubmit(average(scores),
                new TestExerSubmissionInfo(submissions, tabNames), submType,
                null);

    }

    private double average(double[] scores) {
        double score = 0.0;
        for (double d : scores) {
            score += d;
        }

        return score / scores.length;
    }

    @Override
    public void registerExecutionStateChangeListener(
            ExecutionStateChangeListener execStateListener) {
        execHelper.registerExerciseExecutionStateListener(execStateListener);

    }

}
