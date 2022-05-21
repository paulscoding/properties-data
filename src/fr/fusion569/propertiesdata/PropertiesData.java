package fr.fusion569.propertiesdata;

import fr.fusion569.propertiesdata.events.booleans.BooleanEvent;
import fr.fusion569.propertiesdata.events.Event;
import fr.fusion569.propertiesdata.events.numbers.DoubleNumberEvent;
import fr.fusion569.propertiesdata.events.numbers.FloatNumberEvent;
import fr.fusion569.propertiesdata.events.numbers.IntegerNumberEvent;
import fr.fusion569.propertiesdata.events.strings.StringEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the main class.
 */
public class PropertiesData {

    /**
     * The prefix in logs message.
     */
    private static String LOGS_PREFIX = "";

    /**
     * The list of all {@link Event}s registered.
     */
    private static final List<Event> EVENTS_LIST = new ArrayList<>();

    /**
     * Can't make an instance.
     */
    private PropertiesData() {
    }

    public static String getLogsPrefix() {
        return LOGS_PREFIX;
    }

    public static void setLogsPrefix(String logsPrefix) {
        LOGS_PREFIX = logsPrefix;
    }

    public static List<Event> getEventsList() {
        return EVENTS_LIST;
    }

    /**
     * Register an {@link IntegerNumberEvent} in the {@link PropertiesData#EVENTS_LIST}.
     *
     * @param event
     * The {@link IntegerNumberEvent} registered in the {@link PropertiesData#EVENTS_LIST}.
     */
    public static void registerIntegerNumberListener(IntegerNumberEvent event) {
        EVENTS_LIST.add(event);
    }

    /**
     * Register a {@link DoubleNumberEvent} in the {@link PropertiesData#EVENTS_LIST}s {@link List}.
     *
     * @param event
     * The {@link DoubleNumberEvent} registered in the {@link PropertiesData#EVENTS_LIST}.
     */
    public static void registerDoubleNumberListener(DoubleNumberEvent event) {
        EVENTS_LIST.add(event);
    }

    /**
     * Register a {@link FloatNumberEvent} in the {@link PropertiesData#EVENTS_LIST}.
     *
     * @param event
     * The {@link FloatNumberEvent} registered in the {@link PropertiesData#EVENTS_LIST}.
     */
    public static void registerFloatNumberListener(FloatNumberEvent event) {
        EVENTS_LIST.add(event);
    }

    /**
     * Register a {@link BooleanEvent} in the {@link PropertiesData#EVENTS_LIST}.
     *
     * @param event
     * The {@link BooleanEvent} registered in the {@link PropertiesData#EVENTS_LIST}.
     */
    public static void registerBooleanListener(BooleanEvent event) {
        EVENTS_LIST.add(event);
    }

    /**
     * Register a {@link StringEvent} in the {@link PropertiesData#EVENTS_LIST}.
     *
     * @param event
     * The {@link StringEvent} registered in the {@link PropertiesData#EVENTS_LIST}.
     */
    public static void registerStringListener(StringEvent event) {
        EVENTS_LIST.add(event);
    }
}
