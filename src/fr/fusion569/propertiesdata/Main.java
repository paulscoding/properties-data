package fr.fusion569.propertiesdata;

import fr.fusion569.propertiesdata.files.PropertiesFile;
import fr.fusion569.propertiesdata.utils.KeyValueSeparator;
import fr.fusion569.propertiesdata.utils.StandardDirectoryCreationType;
import fr.fusion569.propertiesdata.utils.StandardFileCreationType;

public class Main {

    public static void main(String[] args) {
        PropertiesFile file = new PropertiesFile(
                // Your file path.
                "path",
                // Your file name.
                "name",
                // Your standard file creation type.
                StandardFileCreationType.ONLY_WANTED_FILE,
                // Your standard directory creation type.
                StandardDirectoryCreationType.IGNORE,
                // Your key value separator.
                KeyValueSeparator.DOUBLE_POINTS_AND_SPACE
        );
    }
}
