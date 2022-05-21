package fr.fusion569.propertiesdata.events.numbers;

/**
 * This interface represents a {@link Double} event.
 */
public interface DoubleNumberEvent extends NumberEvent {

    /**
     * The method executed when a {@link fr.fusion569.propertiesdata.files.PropertiesFile#getDoubleWithListener(String)} method is called.
     *
     * @param key
     * The {@link String} key to check it.
     *
     * @param value
     * The {@link Double} returned value.
     */
    void on(String key, double value);
}
