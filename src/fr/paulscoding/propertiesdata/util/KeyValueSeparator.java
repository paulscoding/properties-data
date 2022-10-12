package fr.paulscoding.propertiesdata.util;

public enum KeyValueSeparator {

    SPACE(" "),
    EQUAL("="),
    EQUAL_SPACE("= "),
    SPACE_EQUAL_SPACE(" = "),
    DOUBLE_POINTS(":"),
    DOUBLE_POINTS_SPACE(": "),
    SPACE_DOUBLE_POINTS_SPACE(" : ");

    private final String separator;

    KeyValueSeparator(String separator) {
        this.separator = separator;
    }

    public String getSeparator() {
        return separator;
    }
}
