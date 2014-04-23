package fi.utu.ville.exercises.testexer.elements;

import java.io.Serializable;

public interface SelectableGridElement extends Serializable {

    String getPlainSymbol();

    String getHTMLSymbol();

    String getStyleName();

    SelectableGridElement getEmpty();
}
