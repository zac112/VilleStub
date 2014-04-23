package fi.utu.ville.exercises.testexer;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import fi.utu.ville.exercises.model.Editor;
import fi.utu.ville.exercises.model.EditorHelper;
import fi.utu.ville.exercises.model.EditorHelper.EditedExerciseGiver;
import fi.utu.ville.exercises.testexer.elements.Molecule;
import fi.utu.ville.standardutils.Localizer;

public class TestExerEditor extends HorizontalLayout implements
        Editor<TestExerExerciseData> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4600841604409240872L;

    private EditorHelper<TestExerExerciseData> editorHelper;

    private TabSheet sheet = new TabSheet();

    private int tabPosition = 0;

    private TextField captionField = new TextField("Name of the tab");

    public TestExerEditor() {
    }

    @Override
    public Layout getView() {
        return this;
    }

    @Override
    public void initialize(Localizer localizer, TestExerExerciseData oldData,
            EditorHelper<TestExerExerciseData> editorHelper) {

        this.editorHelper = editorHelper;

        editorHelper.getTempManager().initialize();

        if (oldData == null) {
            oldData = new TestExerExerciseData(
                    new Molecule[] { new Molecule() },
                    new String[] { "Tab 1" }, new int[][] { { 10, 10 } });
        }
        doLayout(oldData);
    }

    private void doLayout(TestExerExerciseData oldData) {

        this.setMargin(true);
        this.setSpacing(true);
        this.setWidth("100%");
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Accordion layout = new Accordion();
        layout.addTab(createEditorControls(), "Edit Question Description");

        layout.addTab(createQuestionEditor(oldData), "Edit Molecules");

        this.addComponent(layout);

    }

    private Component createQuestionEditor(TestExerExerciseData oldData) {

        VerticalLayout questionEditor = new VerticalLayout();

        HorizontalLayout editLayout = new HorizontalLayout();

        editLayout.addComponent(TestExerGUIUtils.createSelectionArea());

        // The molecule area
        for (int i = 0; i < oldData.getTabs(); i++) {
            sheet.addTab(TestExerGUIUtils.createMoleculeGrid(
                    oldData.getMolecules()[i], oldData.getDimensions(i)));
            sheet.getTab(i).setCaption(oldData.getNameForTab(i));
        }

        sheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                captionField.setValue(sheet.getTab(sheet.getSelectedTab())
                        .getCaption());
            }
        });

        editLayout.addComponent(sheet);

        // the teacher's controls
        HorizontalLayout teachersControls = new HorizontalLayout();

        teachersControls.addComponent(addMoleculeButton());
        teachersControls.addComponent(removeMoleculeButton());
        teachersControls.addComponent(setCaptionField());
        teachersControls.addComponent(addColumnButton());
        teachersControls.addComponent(removeColumnButton());
        teachersControls.addComponent(addRowButton());
        teachersControls.addComponent(removeRowButton());

        questionEditor.addComponent(teachersControls);
        questionEditor.addComponent(editLayout);

        return questionEditor;
    }

    private Button addMoleculeButton() {
        Button addMolecule = new Button("Add new Molecule");
        addMolecule.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                int tabs = 0;
                while (sheet.getTab(tabs) != null) {
                    tabs++;
                }
                Tab tab = sheet.addTab(TestExerGUIUtils.createMoleculeGrid(
                        new Molecule(), new int[] { 10, 10 }));
                tab.setCaption("Tab " + (tabs + 1));
            }
        });
        return addMolecule;
    }

    private Button removeMoleculeButton() {
        Button removeMolecule = new Button("Remove molecule");
        removeMolecule.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                sheet.getTab(sheet.getSelectedTab());
                sheet.removeTab(sheet.getTab(sheet.getSelectedTab()));
            }
        });
        return removeMolecule;
    }

    private TextField setCaptionField() {
        String tabName = sheet.getTab(tabPosition).getCaption();
        captionField.setValue(tabName);
        captionField.addTextChangeListener(new TextChangeListener() {

            @Override
            public void textChange(TextChangeEvent event) {
                sheet.getTab(sheet.getSelectedTab())
                        .setCaption(event.getText());
            }
        });
        return captionField;
    }

    private Button removeColumnButton() {
        Button removeColumn = new Button("Remove Column");
        removeColumn.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                GridLayout layout = (GridLayout) sheet.getSelectedTab();
                for (int i = 0; i < layout.getRows(); i++) {
                    layout.removeComponent(layout.getColumns() - 1, i);
                }
                layout.setColumns(Math.max(layout.getColumns() - 1, 0));
            }
        });
        return removeColumn;
    }

    private Button addColumnButton() {
        Button addColumn = new Button("Add Column");
        addColumn.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                GridLayout layout = (GridLayout) sheet.getSelectedTab();
                layout.setColumns(layout.getColumns() + 1);
                for (int i = 0; i < layout.getRows(); i++) {
                    Label l = TestExerGUIUtils.createAtomLabel("", "");
                    layout.addComponent(l, layout.getColumns() - 1, i);
                }
            }
        });
        return addColumn;
    }

    private Button removeRowButton() {
        Button removeRow = new Button("Remove Row");
        removeRow.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                GridLayout layout = (GridLayout) sheet.getSelectedTab();
                int rows = layout.getRows();
                if (rows > 0) {
                    for (int i = 0; i < layout.getColumns(); i++) {
                        layout.removeComponent(i, layout.getRows() - 1);
                    }
                    layout.removeRow(rows - 1);

                }
            }
        });
        return removeRow;
    }

    private Button addRowButton() {
        Button addRow = new Button("Add Row");
        addRow.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                GridLayout layout = (GridLayout) sheet.getSelectedTab();
                layout.insertRow(layout.getRows());
                for (int i = 0; i < layout.getRows(); i++) {
                    Label l = TestExerGUIUtils.createAtomLabel("", "");
                    layout.addComponent(l, i, layout.getRows() - 1);
                }
            }
        });
        return addRow;
    }

    private Component createEditorControls() {

        VerticalLayout controlsLayout = new VerticalLayout();
        controlsLayout.setWidth("400px");
        controlsLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        controlsLayout.addComponent(editorHelper.getInfoEditorView());

        controlsLayout.addComponent(editorHelper
                .getControlbar(new EditedExerciseGiver<TestExerExerciseData>() {

                    @Override
                    public TestExerExerciseData getCurrExerData(
                            boolean forSaving) {
                        return getCurrentExercise();
                    }
                }));

        return controlsLayout;
    }

    private TestExerExerciseData getCurrentExercise() {
        int tabs = 0;
        while (sheet.getTab(tabs) != null) {
            tabs++;
        }

        GridLayout layout;
        Molecule[] molecules = new Molecule[tabs];
        String[] names = new String[tabs];
        int[][] dims = new int[tabs][2];

        for (int i = 0; i < tabs; i++) {
            Tab tab = sheet.getTab(i);
            layout = (GridLayout) tab.getComponent();
            molecules[i] = TestExerGUIUtils.getTrimmedMolecule(layout);
            names[i] = tab.getCaption();
            dims[i][0] = layout.getColumns();
            dims[i][1] = layout.getRows();
        }

        return new TestExerExerciseData(molecules, names, dims);
    }

}
