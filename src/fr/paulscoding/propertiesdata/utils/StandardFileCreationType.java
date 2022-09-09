package fr.paulscoding.propertiesdata.utils;

import fr.paulscoding.propertiesdata.files.PropertiesFile;

/**
 * This enumeration represents standard file creation type when the {@link PropertiesFile} {@link java.io.File} is created.
 */
public enum StandardFileCreationType {

    /**
     * Only the wanted {@link java.io.File} will be created.
     */
    ONLY_WANTED_FILE,
    /**
     * Only the wanted {@link java.io.File} and its copy will be created.
     */
    WANTED_FILE_WITH_COPY;
}
