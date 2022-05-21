package fr.fusion569.propertiesdata.events.booleans;

import fr.fusion569.propertiesdata.events.Event;

/**
 * This interface represents a {@link Boolean} event.
 */
public interface BooleanEvent extends Event {

    /**
     * The method executed when a {@link fr.fusion569.propertiesdata.files.PropertiesFile#getBooleanWithListener(String)} method is called.
     *
     * @param key
     * The {@link String} key to check it.
     *
     * @param value
     * The {@link Boolean} returned value.
     */
    void on(String key, boolean value);
}
