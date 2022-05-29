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

    /**
     * Get the {@link String} logs prefix.
     *
     * @return
     * the {@link String} logs prefix.
     */
    public static String getLogsPrefix() {
        return LOGS_PREFIX;
    }

    /**
     * Set the new {@link String} logs prefix.
     *
     * @param logsPrefix
     * the new {@link String} logs prefix.
     */
    public static void setLogsPrefix(String logsPrefix) {
        LOGS_PREFIX = logsPrefix;
    }
}
