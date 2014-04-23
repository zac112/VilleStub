package fi.utu.ville.exercises.testexer;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.Area;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import fi.utu.ville.exercises.testexer.elements.Bonds;
import fi.utu.ville.exercises.testexer.elements.Elements;
import fi.utu.ville.exercises.testexer.elements.Molecule;
import fi.utu.ville.exercises.testexer.elements.SelectableGridElement;

public class TestExerGUIUtils {

    private static SelectableGridElement currentlySelectedElement;
    private static final Label currentlySelectedElementLabel = new Label("");

    // no need to create instances
    private TestExerGUIUtils() {
    }

    /**
     * Creates the grid on which molecule-elements can be placed to create
     * molecules. The given molecule is drawn on the grid; to start with an
     * empty grid, pass new Molecule().
     * 
     * @param oldMolecule
     *            the molecule that is drawn on the grid.
     * @param dimensions
     *            the size of the grid
     * 
     * @return the drawn molecule on a gridlayout
     */
    public static GridLayout createMoleculeGrid(Molecule oldMolecule,
            int[] dimensions) {
        return createMoleculeGrid(oldMolecule, dimensions, new int[] { 0, 0 });
    }

    /**
     * Creates the grid on which molecule-elements can be placed to create
     * molecules. The given molecule is drawn on the grid; to start with an
     * empty grid, pass new Molecule(). The offset gives the offset from the top
     * left corner from which to start drawing the molecule in relation to the
     * original grid.
     * 
     * @param oldMolecule
     *            the molecule that is drawn on the grid.
     * @param dimensions
     *            the size of the grid
     * @param offset
     *            the offset from which drawing is begun. It's counted from the
     *            top left corner.
     * @return the drawn molecule on a gridlayout
     */
    public static GridLayout createMoleculeGrid(Molecule oldMolecule,
            int[] dimensions, int[] offset) {

        final GridLayout moleculeGrid = new GridLayout(dimensions[0],
                dimensions[1]);

        moleculeGrid.addStyleName("testexer-border");
        moleculeGrid.setData(new Molecule()); // make sure the data
                                              // is not null

        /*
         * System.out.println("the offset is " + offset[0] + " " + offset[1]);
         * System.out.println("the dimenson is " + dimensions[0] + " " +
         * dimensions[1]);
         */

        for (int y = 0; y < moleculeGrid.getRows(); y++) {
            for (int x = 0; x < moleculeGrid.getColumns(); x++) {
                SelectableGridElement currentElement = oldMolecule
                        .getGridElement(x + offset[0], y + offset[1]);
                final Label atom = createAtomLabel(
                        currentElement.getPlainSymbol(),
                        currentElement.getStyleName());
                getMolecule(moleculeGrid).addGridElement(currentElement, x, y);

                moleculeGrid.addComponent(atom, x, y);
            }
        }
        moleculeGrid.addLayoutClickListener(new LayoutClickListener() {

            @Override
            public void layoutClick(LayoutClickEvent event) {
                if (event.getChildComponent() instanceof Label) {
                    Label target = (Label) event.getChildComponent();
                    target.setStyleName(currentlySelectedElement.getStyleName());
                    target.addStyleName("testexer-atom");

                    target.setValue(currentlySelectedElement.getPlainSymbol());

                    GridLayout layout = (GridLayout) event.getComponent();
                    Area area = layout.getComponentArea(target);
                    getMolecule(layout).addGridElement(
                            currentlySelectedElement, area.getColumn1(),
                            area.getRow1());

                }
            }
        });
        return moleculeGrid;
    }

    /**
     * Creates a label for the molecule grid.
     * 
     * @param content
     *            label's content
     * @param style
     *            the style for the label
     * @return the label itself
     */
    public static Label createAtomLabel(String content, String style) {
        Label atom = new Label(content);
        atom.setWidth("40px");
        atom.setHeight("40px");
        atom.setContentMode(ContentMode.HTML);
        atom.setStyleName(style);
        atom.addStyleName("testexer-atom");
        return atom;
    }

    /**
     * Gets the molecule from the given Gridlayout tab. The returned molecule
     * will be trimmed as a side effect.
     * 
     * @param target
     * @return The trimmed molecule associated with the target. It's never null
     *         and will contain no empty atoms.
     */
    public static Molecule getTrimmedMolecule(GridLayout target) {
        Molecule result = getMolecule(target);
        result.trimMolecule();
        return result;
    }

    /**
     * Gets the molecule from the given Gridlayout tab.
     * 
     * @param target
     * @return The molecule associated with the target. It's never null.
     */
    public static Molecule getMolecule(GridLayout target) {
        Molecule result = (Molecule) target.getData();
        return (result == null) ? new Molecule() : result;
    }

    public static Component createSelectionArea() {
        VerticalLayout quickSelectArea = new VerticalLayout();
        quickSelectArea.addComponent(createBondsArea());
        quickSelectArea.addComponent(createElementQuickSelect());
        quickSelectArea.setMargin(true);

        return quickSelectArea;
    }

    /**
     * Creates the area where the bonds can be chosen
     * 
     * @return The GridLayout containing buttons for all molecular bonds
     */
    private static GridLayout createBondsArea() {
        final GridLayout layout = new GridLayout(3, 4);
        layout.addStyleName("testexer-border");

        Bonds[] bonds = Bonds.values();
        for (int i = 0; i < bonds.length - 1; i++) {
            Label bond = new Label();
            bond.setData(bonds[i]);
            bond.addStyleName(bonds[i].getStyleName());
            bond.setWidth("40px");
            bond.setHeight("40px");
            layout.addComponent(bond);
        }
        layout.addLayoutClickListener(new LayoutClickListener() {

            @Override
            public void layoutClick(LayoutClickEvent event) {
                if (event.getChildComponent() instanceof Label) {
                    Label target = (Label) event.getChildComponent();
                    currentlySelectedElement = (SelectableGridElement) target
                            .getData();
                    currentlySelectedElementLabel
                            .setStyleName(currentlySelectedElement
                                    .getStyleName());
                    // l.addStyleName("testexer-atom");
                    currentlySelectedElementLabel
                            .setValue(currentlySelectedElement.getPlainSymbol());
                }
            }
        });
        return layout;
    }

    /**
     * Creates component that contains buttons for the five common elements +
     * eraser.
     * 
     * @return
     */
    private static Component createElementQuickSelect() {
        GridLayout layout = new GridLayout(3, 5);

        Elements[] elements = Elements.getQuickSelectElements();
        for (Elements element : elements) {
            Label label = new Label(
                    element.getHTMLFormattedSymbolForPeriodicTable(),
                    ContentMode.HTML);
            layout.addComponent(label);
            label.setData(element);
            if (element == Elements.EMPTY) {
                label.setValue("<center>Eraser</center>");
            }

        }
        layout.addComponent(createPeriodicTableButton(), 0, 2, 2, 2);
        layout.addLayoutClickListener(new LayoutClickListener() {

            @Override
            public void layoutClick(LayoutClickEvent event) {
                if (event.getChildComponent() instanceof Label) {
                    Label target = (Label) event.getChildComponent();
                    currentlySelectedElement = (SelectableGridElement) target
                            .getData();
                    currentlySelectedElementLabel
                            .setStyleName(currentlySelectedElement
                                    .getStyleName());
                    currentlySelectedElementLabel
                            .setValue(currentlySelectedElement.getPlainSymbol() == "" ? "Eraser"
                                    : currentlySelectedElement.getPlainSymbol());
                }
            }
        });

        layout.addComponent(new Label("Currently selected:"), 0, 3, 2, 3);
        currentlySelectedElementLabel.setWidth("40px");
        currentlySelectedElementLabel.setHeight("40px");
        layout.addComponent(currentlySelectedElementLabel, 0, 4, 2, 4);
        return layout;
    }

    /**
     * Creates the button, which, when pressed, will create the periodic table
     * window.
     */
    private static Component createPeriodicTableButton() {

        Button periodicTableButton = new Button("Show the periodic table");// TODO:
                                                                           // translation
        periodicTableButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                final Window window = new Window("Periodic Table");
                GridLayout elements = new GridLayout(18, 10);
                for (int x = 0; x < elements.getColumns() * elements.getRows(); x++) {
                    Elements currentElement = Elements
                            .getElementByLocationInPeriodicTable(x);
                    final Label button = new Label(currentElement
                            .getHTMLFormattedSymbolForPeriodicTable());
                    button.setWidth("25px");
                    button.setHeight("21px");
                    button.setContentMode(ContentMode.HTML);
                    button.setData(currentElement);
                    elements.addComponent(button);
                }

                elements.addLayoutClickListener(new LayoutClickListener() {

                    @Override
                    public void layoutClick(LayoutClickEvent event) {
                        if (event.getChildComponent() instanceof Label) {
                            Label target = (Label) event.getChildComponent();
                            currentlySelectedElement = (SelectableGridElement) target
                                    .getData();

                            // close window if clicked on a non-empty element
                            if (currentlySelectedElement != currentlySelectedElement
                                    .getEmpty()) {
                                window.close();
                            }
                        }
                    }
                });
                window.setContent(elements);
                window.setResizable(false);
                UI.getCurrent().addWindow(window);
            }
        });
        return periodicTableButton;
    }
}
