package fr.fusion569.propertiesdata.events.numbers;

/**
 * This interface represents an {@link Integer} event.
 */
public interface IntegerNumberEvent extends NumberEvent {

    /**
     * The method executed when a {@link fr.fusion569.propertiesdata.files.PropertiesFile#getIntegerWithListener(String)} method is called.
     *
     * @param key
     * The {@link String} key to check it.
     *
     * @param value
     * The {@link Integer} returned value.
     */
    void on(String key, int value);
}
