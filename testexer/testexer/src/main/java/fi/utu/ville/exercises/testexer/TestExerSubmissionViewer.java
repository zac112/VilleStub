package fi.utu.ville.exercises.testexer;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import fi.utu.ville.exercises.model.ExerciseException;
import fi.utu.ville.exercises.model.SubmissionVisualizer;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.TempFilesManager;

public class TestExerSubmissionViewer extends VerticalLayout implements
        SubmissionVisualizer<TestExerExerciseData, TestExerSubmissionInfo> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6260031633710031462L;
    private TestExerSubmissionInfo submInfo;

    private Localizer localizer;

    public TestExerSubmissionViewer() {
    }

    @Override
    public void initialize(TestExerExerciseData exercise,
            TestExerSubmissionInfo dataObject, Localizer localizer,
            TempFilesManager tempManager) throws ExerciseException {
        this.localizer = localizer;
        this.submInfo = dataObject;
        doLayout();
    }

    private void doLayout() {
        TabSheet sheet = new TabSheet();
        for (int i = 0; i < submInfo.getTabs(); i++) {
            sheet.addTab(TestExerGUIUtils.createMoleculeGrid(
                    submInfo.getMolecules()[i], submInfo.getMoleculeSize(i),
                    submInfo.getOffset(i)), submInfo.getQuestions()[i]);
        }
        this.addComponent(sheet);
    }

    @Override
    public Component getView() {
        return this;
    }

    @Override
    public String exportSubmissionDataAsText() {
        return localizer.getUIText(TestExerUiConstants.QUESTION, "\n");

    }

}
