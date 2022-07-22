package fr.fusion569.propertiesdata;

import fr.fusion569.propertiesdata.files.PropertiesFile;
import fr.fusion569.propertiesdata.utils.KeyValueSeparator;
import fr.fusion569.propertiesdata.utils.StandardDirectoryCreationType;
import fr.fusion569.propertiesdata.utils.StandardFileCreationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
     * The {@link String} logs prefix.
     */
    public static String getLogsPrefix() {
        return LOGS_PREFIX;
    }

    /**
     * Set the new {@link String} logs prefix.
     *
     * @param logsPrefix
     * The new {@link String} logs prefix.
     */
    public static void setLogsPrefix(String logsPrefix) {
        LOGS_PREFIX = logsPrefix;
    }
}
