package fr.fusion569.propertiesdata.utils;

import fr.fusion569.propertiesdata.files.FileProperties;

/**
 * This enumeration represents standard file creation type when the {@link FileProperties} {@link java.io.File} is created.
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
