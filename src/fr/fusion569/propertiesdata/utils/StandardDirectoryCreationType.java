package fr.fusion569.propertiesdata.utils;

import fr.fusion569.propertiesdata.files.FileProperties;

/**
 * This enumeration represents standard directory creation type when the {@link FileProperties} {@link java.io.File} is created.
 */
public enum StandardDirectoryCreationType {

    /**
     * Only the file will be created.
     */
    IGNORE,
    /**
     * The file and its directory will be created if not.
     */
    CREATE;
}
