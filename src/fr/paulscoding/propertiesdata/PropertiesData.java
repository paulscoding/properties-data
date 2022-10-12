package fr.paulscoding.propertiesdata;

public final class PropertiesData {

    private static String LOGS_PREFIX = "";

    private PropertiesData() {
    }

    public static String getLogsPrefix() {
        return LOGS_PREFIX;
    }

    public static void setLogsPrefix(String logsPrefix) {
        LOGS_PREFIX = logsPrefix;
    }
}
