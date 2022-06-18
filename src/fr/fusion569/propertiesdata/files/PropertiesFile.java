package fr.fusion569.propertiesdata.files;

import fr.fusion569.propertiesdata.PropertiesData;
import fr.fusion569.propertiesdata.utils.KeyValueSeparator;
import fr.fusion569.propertiesdata.utils.StandardDirectoryCreationType;
import fr.fusion569.propertiesdata.utils.StandardFileCreationType;

import java.io.*;

public final class PropertiesFile {

    private final String path, name, absolutePath;
    private final StandardFileCreationType standardFileCreationType;
    private final StandardDirectoryCreationType standardDirectoryCreationType;
    private final KeyValueSeparator keyValueSeparator;
    private final File file;
    private PropertiesFileDataGetter propertiesFileDataGetter;

    /**
     * The {@link File} extension for a custom file properties.
     */
    public static final String FILE_EXTENSION = ".proper";

    public PropertiesFile(String path, String name, StandardFileCreationType standardFileCreationType, StandardDirectoryCreationType standardDirectoryCreationType, KeyValueSeparator keyValueSeparator) {
        this.path = path;
        this.name = name + FILE_EXTENSION;
        this.absolutePath = this.path + this.name;
        this.standardFileCreationType = standardFileCreationType;
        this.standardDirectoryCreationType = standardDirectoryCreationType;
        this.keyValueSeparator = keyValueSeparator;
        this.file = new File(this.absolutePath);
    }

    /**
     * Get the {@link String} {@link File} path.
     *
     * @return
     * The {@link String} {@link File} path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the {@link String} {@link File} name.
     *
     * @return
     * The {@link String} {@link File} name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the {@link String} {@link File} absolute path.
     *
     * @return
     * The {@link String} {@link File} absolute path.
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * Get the {@link File}.
     *
     * @return
     * The {@link File}.
     */
    public StandardFileCreationType getStandardFileCreationType() {
        return standardFileCreationType;
    }

    /**
     * Get the {@link StandardDirectoryCreationType}.
     *
     * @return
     * The {@link StandardDirectoryCreationType}.
     */
    public StandardDirectoryCreationType getStandardDirectoryCreationType() {
        return standardDirectoryCreationType;
    }

    /**
     * Get the {@link KeyValueSeparator}.
     *
     * @return
     * The {@link KeyValueSeparator}.
     */
    public KeyValueSeparator getKeyValueSeparator() {
        return keyValueSeparator;
    }

    /**
     * Get the {@link File}.
     *
     * @return
     * The {@link File}.
     */
    public File getFile() {
        return file;
    }

    /**
     * Create the {@link File} as constructor and its copy if the {@link StandardFileCreationType} as constructor is {@link StandardFileCreationType#WANTED_FILE_WITH_COPY}.
     */
    public void create() {
        final File dir = new File(this.path);

        if(!dir.exists()) {
            if(this.standardDirectoryCreationType.equals(StandardDirectoryCreationType.CREATE)) {
                dir.mkdir();
                System.out.println(PropertiesData.getLogsPrefix() + "The directory " + this.absolutePath + " has been created.");
            }
        } else {
            System.out.println(PropertiesData.getLogsPrefix() + "No directory created.");
        }
        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                System.out.println(PropertiesData.getLogsPrefix() + "The file " + this.absolutePath + " has been created.");
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(PropertiesData.getLogsPrefix() + "No file created.");
        }
        if(this.standardFileCreationType.equals(StandardFileCreationType.WANTED_FILE_WITH_COPY)) {
            this.createCopy();
        } else {
            System.out.println(PropertiesData.getLogsPrefix() + "No copy created.");
        }
        this.propertiesFileDataGetter = new PropertiesFileDataGetter(this.file, keyValueSeparator);
    }

    /**
     * Create a {@link File} copy.
     */
    private void createCopy() {
        final File copy = new File(this.path, "copy-" + this.name);

        if(!copy.exists()) {
            try {
                copy.createNewFile();
                System.out.println(PropertiesData.getLogsPrefix() + "The copy file " + this.path + "copy-" + this.name + " of " + this.absolutePath + " has been created.");
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(PropertiesData.getLogsPrefix() + "No copy created.");
        }
    }
}
