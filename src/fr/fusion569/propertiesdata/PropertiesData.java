package fr.fusion569.propertiesdata;

import fr.fusion569.propertiesdata.files.PropertiesFile;
import fr.fusion569.propertiesdata.utils.KeyValueSeparator;
import fr.fusion569.propertiesdata.utils.StandardDirectoryCreationType;
import fr.fusion569.propertiesdata.utils.StandardFileCreationType;

/**
 * This class represents the main class.
 */
public final class PropertiesData {

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

    public static void main(String[] args) {
        final PropertiesFile file = new PropertiesFile(
                "C://Users/Papa/Desktop/",
                "data",
                StandardFileCreationType.ONLY_WANTED_FILE,
                StandardDirectoryCreationType.IGNORE,
                KeyValueSeparator.DOUBLE_POINTS_AND_SPACE
        );

        file.create();
        file.setString("language", "Java");
        file.setString("type", "library");
        file.setString("country", "France");
    }
}
