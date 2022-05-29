package fr.fusion569.propertiesdata;

/**
 * This class represents the main class.
 */
public class PropertiesData {

    /**
     * The prefix in logs message.
     */
    private static String LOGS_PREFIX = "";

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
}
