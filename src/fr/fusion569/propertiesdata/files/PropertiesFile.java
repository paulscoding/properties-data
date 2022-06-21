package fr.fusion569.propertiesdata.files;

import fr.fusion569.propertiesdata.PropertiesData;
import fr.fusion569.propertiesdata.utils.KeyValueSeparator;
import fr.fusion569.propertiesdata.utils.StandardDirectoryCreationType;
import fr.fusion569.propertiesdata.utils.StandardFileCreationType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PropertiesFile {

    private final String path, name, absolutePath;
    private final StandardFileCreationType standardFileCreationType;
    private final StandardDirectoryCreationType standardDirectoryCreationType;
    private final KeyValueSeparator keyValueSeparator;
    private final File file;
    private final List<String> lines;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

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
        this.lines = new ArrayList<>();
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
     * Get the {@link BufferedReader}.
     *
     * @return
     * The {@link BufferedReader}.
     */
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    /**
     * Get the {@link BufferedWriter}.
     *
     * @return
     * The {@link BufferedWriter}.
     */
    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    /**
     * Close and make a new instance of the {@link BufferedReader}.
     */
    private void resetBufferedReader() {
        try {
            this.bufferedReader.close();
            this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
        } catch(IOException e) {
            e.printStackTrace();
        }
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
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
            String line;
            line = this.bufferedReader.readLine();

            while(line != null) {
                this.lines.add(line);
                line = this.bufferedReader.readLine();
            }
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8));

            for(String l : this.lines) {
                this.bufferedWriter.write(l);
                this.bufferedWriter.newLine();
            }
            this.resetBufferedReader();
            this.bufferedWriter.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Throw the exceptions {@link IllegalArgumentException} if the key is "" or starts with " ".
     *
     * @param key
     * The key to declare exceptions if needed.
     */
    private void throwKeyExceptions(String key) {
        if(key.equals("")) {
            throw new IllegalArgumentException("You must set a valid String key");
        }
        if(key.startsWith(" ")) {
            throw new IllegalArgumentException("Your String key can't starts with ' '.");
        }
    }

    /**
     * Get a {@link String} value from a {@link String} key without quotation marks.
     *
     * @param key
     * The {@link String} key to get a {@link String} value without quotation marks.
     *
     * @return
     * A {@link String} value from a {@link String} key without quotation marks.
     */
    private String getStringWithoutQuotationMarks(String key) {
        this.throwKeyExceptions(key);
        String line;

        try {
            line = this.bufferedReader.readLine();

            while(line != null) {
                if(line.startsWith(key)) {
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                        if(keyValueArray.length >= 3) {
                            for(int i = 2; i < keyValueArray.length; i++) {
                                keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                            }
                        }
                        this.resetBufferedReader();
                        return keyValueArray[1];
                    }
                }
                line = this.bufferedReader.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        this.resetBufferedReader();
        throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Invalid key: '" + key + "' or key value separator '" + this.keyValueSeparator.getSeparator() + "'.");
    }

    /**
     * Get a {@link String} value from a {@link String} key with quotation marks.
     *
     * @param key
     * The {@link String} key to get a {@link String} value with quotation marks.
     *
     * @return
     * A {@link String} value from a {@link String} key with quotation marks.
     */
    public String getString(String key) {
        this.throwKeyExceptions(key);
        String line;

        try {
            line = this.bufferedReader.readLine();

            while(line != null) {
                if(line.startsWith(key)) {
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                        if(keyValueArray.length >= 3) {
                            for(int i = 2; i < keyValueArray.length; i++) {
                                keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                            }
                        }
                        this.resetBufferedReader();
                        if(keyValueArray[1].startsWith("\"") && keyValueArray[1].endsWith("\"")) {
                            return keyValueArray[1].substring(1, keyValueArray[1].length() - 1);
                        } else {
                            throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Your value must include \" at end and the beginning.");
                        }
                    }
                }
                line = this.bufferedReader.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        this.resetBufferedReader();
        throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Invalid key: '" + key + "' or key value separator '" + this.keyValueSeparator.getSeparator() + "'.");
    }

    /**
     * Get a {@link Integer} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to get a {@link Integer} value.
     *
     * @return
     * A {@link Integer} value from a {@link String} key.
     */
    public int getInteger(String key) {
        return Integer.parseInt(this.getStringWithoutQuotationMarks(key));
    }

    /**
     * Get a {@link Double} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to get a {@link Double} value.
     *
     * @return
     * A {@link Double} value from a {@link String} key.
     */
    public double getDouble(String key) {
        return Double.parseDouble(this.getStringWithoutQuotationMarks(key));
    }

    /**
     * Get a {@link Float} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to get a {@link Float} value.
     *
     * @return
     * A {@link Float} value from a {@link String} key.
     */
    public float getFloat(String key) {
        return Float.parseFloat(this.getStringWithoutQuotationMarks(key));
    }

    /**
     * Get a {@link Boolean} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to get a {@link Boolean} value.
     *
     * @return
     * A {@link Boolean} value from a {@link String} key.
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(this.getStringWithoutQuotationMarks(key));
    }

    public void setString(String key, String value) {
        this.throwKeyExceptions(key);
        try {
            for(String line : this.lines) {
                if(line.startsWith(key)) {
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                        if(keyValueArray.length >= 3) {
                            for(int i = 2; i < keyValueArray.length; i++) {
                                keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                            }
                        }
                        keyValueArray[1] = value;
                        this.lines.set(this.lines.indexOf(line), keyValueArray[0] + this.keyValueSeparator.getSeparator() + "\"" + keyValueArray[1] + "\"");
                    }
                } else {
                    this.lines.add(key + this.keyValueSeparator.getSeparator() + "\"" + value + "\"");
                }
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8));
                for(String l : this.lines) {
                    this.bufferedWriter.write(l);
                    this.bufferedWriter.newLine();
                }
                this.bufferedWriter.flush();
                return;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        // TODO - Check why when file is empty, this exception is thrown.
        throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Invalid key: '" + key + "' or key value separator '" + this.keyValueSeparator.getSeparator() + "'.");
    }
}
