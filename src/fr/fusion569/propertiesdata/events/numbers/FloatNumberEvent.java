package fr.fusion569.propertiesdata.events.numbers;

/**
 * This interface represents a {@link Float} event.
 */
public interface FloatNumberEvent extends NumberEvent {

    /**
     * The method executed when a {@link fr.fusion569.propertiesdata.files.PropertiesFile#getFloatWithListener(String)} method is called.
     *
     * @param key
     * The {@link String} key to check it.
     *
     * @param value
     * The {@link Float} returned value.
     */
    void on(String key, float value);
}
