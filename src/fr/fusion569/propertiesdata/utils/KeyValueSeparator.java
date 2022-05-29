package fr.fusion569.propertiesdata.utils;

/**
 * This enumeration represents the key value separator in the {@link java.io.File}.
 */
public enum KeyValueSeparator {

    /**
     * The key value separator in the {@link java.io.File} will be " ".
     */
    SPACE(" "),
    /**
     * The key value separator in the {@link java.io.File} will be "=".
     */
    EQUAL("="),
    /**
     * The key value separator in the {@link java.io.File} will be "= ".
     */
    EQUAL_AND_SPACE("= "),
    /**
     * The key value separator in the {@link java.io.File} will be " = ".
     */
    SPACE_THEN_EQUAL_THEN_SPACE(" = "),
    /**
     * The key value separator in the {@link java.io.File} will be ":".
     */
    DOUBLE_POINTS(":"),
    /**
     * The key value separator in the {@link java.io.File} will be ": ".
     */
    DOUBLE_POINTS_AND_SPACE(": "),
    /**
     * The key value separator in the {@link java.io.File} will be " : ".
     */
    SPACE_THEN_DOUBLE_POINTS_THEN_SPACE(" : ");

    private final String separator;

    KeyValueSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Get the {@link String} separator.
     *
     * @return
     * The {@link String} separator.
     */
    public String getSeparator() {
        return separator;
    }
}
