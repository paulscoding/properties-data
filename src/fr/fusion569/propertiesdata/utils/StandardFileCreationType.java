package fr.fusion569.propertiesdata.utils;

import fr.fusion569.propertiesdata.files.PropertiesFile;

/**
 * This enumeration represents standard file creation type when the {@link PropertiesFile} {@link java.io.File} is created.
 */
public enum StandardFileCreationType {

    /**
     * Only the wanted {@link java.io.File} will be created.
     */
    ONLY_FILE_WANTED,
    /**
     * Only the wanted {@link java.io.File} and its copy will be created.
     */
    FILE_WANTED_WITH_COPY;
}
