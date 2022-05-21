package fr.fusion569.propertiesdata.events.strings;

import fr.fusion569.propertiesdata.events.Event;

/**
 * This interface represents a {@link String} event.
 */
public interface StringEvent extends Event {

    /**
     * The method executed when a {@link fr.fusion569.propertiesdata.files.PropertiesFile#getDoubleWithListener(String)} method is called.
     *
     * @param key
     * The {@link String} key to check it.
     *
     * @param value
     * The {@link String} returned value.
     */
    void on(String key, String value);
}
