package fr.paulscoding.propertiesdata.utils;

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
    EQUAL_SPACE("= "),
    /**
     * The key value separator in the {@link java.io.File} will be " = ".
     */
    SPACE_EQUAL_SPACE(" = "),
    /**
     * The key value separator in the {@link java.io.File} will be ":".
     */
    DOUBLE_POINTS(":"),
    /**
     * The key value separator in the {@link java.io.File} will be ": ".
     */
    DOUBLE_POINTS_SPACE(": "),
    /**
     * The key value separator in the {@link java.io.File} will be " : ".
     */
    SPACE_DOUBLE_POINTS_SPACE(" : ");

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
