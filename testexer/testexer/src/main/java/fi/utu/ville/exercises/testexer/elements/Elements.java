package fi.utu.ville.exercises.testexer.elements;

/**
 * An enumeration of all elements known to man.
 * 
 * @author Jamora
 * 
 */
public enum Elements implements SelectableGridElement {
    HYDROGEN(0, "Yellow", "H"), HELIUM(17, "PaleTurquoise", "He"), LITHIUM(18,
            "Red", "Li"), BERYLLIUM(19, "Orange", "Be"), BORON(30, "Brown", "B"), COAL(
            31, "SpringGreen", "C"), NITROGEN(32, "Yellow", "N"), OXYGEN(33,
            "Yellow", "O"), FLUORINE(34, "Yellow", "F"), NEON(35,
            "PaleTurquoise", "Ne"), SODIUM(36, "Red", "Na"), MAGNESIUM(37,
            "Orange", "Mg"), ALUMINIUM(48, "DarkGray", "Al"), SILICON(49,
            "Brown", "Si"), PHOSPHORUS(50, "SpringGreen", "P"), SULFUR(51,
            "SpringGreen", "S"), CHLORINE(52, "Yellow", "Cl"), ARGON(53,
            "PaleTurquoise", "Ar"), POTASSIUM(54, "Red", "K"), CALSIUM(55,
            "Orange", "Ca"), SCANDIUM(56, "Pink", "Sc"), TITANUM(57, "Pink",
            "Ti"), VANDIUM(58, "Pink", "V"), CROME(59, "Pink", "Cr"), MANAGESE(
            60, "Pink", "Mn"), IRON(61, "Pink", "Fe"), COBOLT(62, "Pink", "Co"), NICKEL(
            63, "Pink", "Ni"), COPPER(64, "Pink", "Cu"), ZINC(65, "Pink", "Zn"), GALLIUM(
            66, "DarkGray", "Ga"), GERMANIUM(67, "Brown", "Ge"), ARSENIC(68,
            "Brown", "As"), SELENIUM(69, "SpringGreen", "Se"), BROMINE(70,
            "Yellow", "Br"), KRYPTON(71, "PaleTurquoise", "Kr"), RUBIDIUM(72,
            "Red", "Rb"), STRONTIUM(73, "Orange", "Sr"), YTTRIUM(74, "Pink",
            "Y"), ZIRCONIUM(75, "Pink", "Zr"), NIOBIOUM(76, "Pink", "Nb"), MOLYBDENUM(
            77, "Pink", "Mo"), TECHNETIUM(78, "Pink", "Tc"), RUTHENIUM(79,
            "Pink", "Ru"), RHODIUM(80, "Pink", "Rh"), PALLADIUM(81, "Pink",
            "Pd"), SILVER(82, "Pink", "Ag"), CADMIUM(83, "Pink", "Cd"), INDIUM(
            84, "DarkGray", "In"), TIN(85, "DarkGray", "Sn"), ANTIMONY(86,
            "Brown", "Sb"), TELLURIUM(87, "Brown", "Te"), IODINE(88, "Yellow",
            "I"), XENON(89, "PaleTurquoise", "Xe"), CAESIUM(90, "Red", "Cs"), BARIUM(
            91, "Orange", "Ba"), HAFNIUM(93, "Pink", "Hf"), TANTALUM(94,
            "Pink", "Ta"), TUNGSTEN(95, "Pink", "W"), RHENIUM(96, "Pink", "Re"), OSMIUM(
            97, "Pink", "Os"), IRIDIUM(98, "Pink", "Ir"), PLATINUM(99, "Pink",
            "Pt"), GOLD(100, "Pink", "Au"), MERCURY(101, "Pink", "Hg"), THALLIUM(
            102, "DarkGray", "Tl"), LEAD(103, "DarkGray", "Pb"), BISMUTH(104,
            "DarkGray", "Bi"), POLONIUM(105, "DarkGray", "Po"), ASTATINE(106,
            "Brown", "At"), RADON(107, "PaleTurquoise", "Rn"), FRANCIUM(108,
            "Red", "Fr"), RADIUM(109, "Orange", "Ra"), RUTHERFORDIUM(111,
            "Pink", "Rf"), DUBNIUM(112, "Pink", "Db"), SEABORGIUM(113, "Pink",
            "Sg"), BOHRIUM(114, "Pink", "Bh"), HASSIUM(115, "Pink", "Hs"), MEITNERIUM(
            116, "Linen", "Mt"), DARMSTADTIUM(117, "Linen", "Ds"), ROENTGENIUM(
            118, "Linen", "Rg"), COPERNICUM(119, "Pink", "Cn"), UNUNTRIUM(120,
            "Linen", "Uut"), FLEROVIUM(121, "Linen", "Fl"), UNUNPENTIUM(122,
            "Linen", "Uup"), LIVERMORIUM(123, "Linen", "Lv"), UNUNSEPTIUM(124,
            "Linen", "Uus"), UNUNOCTIUM(125, "Linen", "Uuo"), LANTHANIUM(146,
            "Violet", "La"), CERIUM(147, "Violet", "Ce"), PRASEODYMIUM(148,
            "Violet", "Pr"), NEODYMIUM(149, "Violet", "Nd"), PROMETHIUM(150,
            "Violet", "Pm"), SAMARIUM(151, "Violet", "Sm"), EUROPIUM(152,
            "Violet", "Eu"), GADOLINIUM(153, "Violet", "Gd"), TERBIUM(154,
            "Violet", "Tb"), DYSPROSIUM(155, "Violet", "Dy"), HOLMIUM(156,
            "Violet", "Ho"), ERBIUM(157, "Violet", "Er"), THULIUM(158,
            "Violet", "Tm"), YTTERBIUM(159, "Violet", "Yb"), LUTETIUM(160,
            "Violet", "Lu"), ACTINIUM(164, "Purple", "Ac"), THORIUM(165,
            "Purple", "Th"), PROTACTINIUM(166, "Purple", "Pa"), URANIUM(167,
            "Purple", "U"), NEPTUNIUM(168, "Purple", "Np"), PLUTONIUM(169,
            "Purple", "Pu"), AMERICIUM(170, "Purple", "Am"), CURIUM(171,
            "Purple", "Cm"), BERKELIUM(172, "Purple", "Bk"), CALIFORNIUM(173,
            "Purple", "Cf"), EINSTEINIUM(174, "Purple", "Es"), FERMIUM(175,
            "Purple", "Fm"), MENDELEVIUM(176, "Purple", "Md"), NOBELIUM(177,
            "Purple", "No"), LAWRENCIUM(178, "Purple", "Lr"), EMPTY(-1,
            "White", "");

    private static final long serialVersionUID = -6260031633710031465L;

    public static Elements getElementByLocationInPeriodicTable(int location) {

        // move this to caller if called from only one location
        Elements[] elements = Elements.values();
        for (Elements element : elements) {
            if (element.location == location) {
                return element;
            }
        }
        return Elements.EMPTY;
    }

    public static Elements getElementBySymbol(String symbol) {

        Elements[] elements = Elements.values();
        for (Elements element : elements) {
            if (element.symbol.equals(symbol)) {
                return element;
            }
        }
        return Elements.EMPTY;
    }

    public static Elements getElementFromAtomicNumber(int atomicNumber)
            throws IllegalArgumentException {
        if (atomicNumber < 1 || atomicNumber > 118) {
            throw new IllegalArgumentException(
                    "Attempting to access element which doesn't exist");
        }

        Elements[] elements = Elements.values();
        Elements result = null;
        for (Elements element : elements) {
            if (element.ordinal() == (atomicNumber - 1)) {
                result = element;
                break;
            }
        }
        return result;
    }

    // instance variables
    private final int location;
    private final String symbol;
    private final String color;

    private Elements(int locationInTable, String color, String symbol) {
        location = locationInTable;
        this.symbol = symbol;
        this.color = color;
    }

    @Override
    public String getPlainSymbol() {
        return symbol;
    }

    public String getHTMLFormattedSymbolForPeriodicTable() {
        return symbol.equals("") ? "" : "<center style = \"background-color: "
                + color + "; border: 1px solid black\">" + symbol + "</center>";
    }

    @Override
    public String getHTMLSymbol() {
        return symbol.equals("") ? ""
                : "<center style = \"background-color: grey; font-size : 20px\">"
                        + symbol + "</center>";
    }

    @Override
    public String getStyleName() {
        return "";
    }

    @Override
    public SelectableGridElement getEmpty() {
        return Elements.EMPTY;
    }

    /**
     * Used to get the elements in the quick select panel.
     * 
     * @return all elements in the quick select panel
     */
    public static Elements[] getQuickSelectElements() {
        return new Elements[] { Elements.HYDROGEN, Elements.COAL,
                Elements.NITROGEN, Elements.OXYGEN, Elements.SULFUR,
                Elements.EMPTY };
    }

}
