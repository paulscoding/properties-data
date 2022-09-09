package fr.paulscoding.propertiesdata.files;

import fr.paulscoding.propertiesdata.PropertiesData;
import fr.paulscoding.propertiesdata.utils.KeyValueSeparator;
import fr.paulscoding.propertiesdata.utils.StandardDirectoryCreationType;
import fr.paulscoding.propertiesdata.utils.StandardFileCreationType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Get the {@link String} {@link List} lines.
     *
     * @return
     * The {@link String} {@link List} lines.
     */
    public List<String> getLines() {
        return lines;
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
            this.bufferedReader = new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the {@link File} as constructor and its copy if the {@link StandardFileCreationType} as constructor is {@link StandardFileCreationType#WANTED_FILE_WITH_COPY}.
     */
    public void create() {
        final File dir = new File(this.path);

        try {
            if(!dir.exists()) {
                if(this.standardDirectoryCreationType.equals(StandardDirectoryCreationType.CREATE)) {
                    dir.mkdir();
                    System.out.println(PropertiesData.getLogsPrefix() + "The directory " + this.absolutePath + " has been created.");
                }
            } else {
                System.out.println(PropertiesData.getLogsPrefix() + "No directory created.");
            }
            if(!this.file.exists()) {
                this.file.createNewFile();
                System.out.println(PropertiesData.getLogsPrefix() + "The file " + this.absolutePath + " has been created.");
            } else {
                System.out.println(PropertiesData.getLogsPrefix() + "No file created.");
            }
            if(this.standardFileCreationType.equals(StandardFileCreationType.WANTED_FILE_WITH_COPY)) {
                this.createCopy();
            } else {
                System.out.println(PropertiesData.getLogsPrefix() + "No copy created.");
            }
            this.bufferedReader = new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8));

            String line;
            line = this.bufferedReader.readLine();

            while(line != null) {
                this.lines.add(line);
                line = this.bufferedReader.readLine();
            }
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(this.file.toPath()), StandardCharsets.UTF_8));
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

        try {
            if(!copy.exists()) {
                copy.createNewFile();
                System.out.println(PropertiesData.getLogsPrefix() + "The copy file " + this.path + "copy-" + this.name + " of " + this.absolutePath + " has been created.");
            } else {
                System.out.println(PropertiesData.getLogsPrefix() + "No copy created.");
            }
        } catch(IOException e) {
            e.printStackTrace();
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
        else if(key.startsWith(" ")) {
            throw new IllegalArgumentException("Your String key can't starts with ' '.");
        }
    }

    /**
     * Check if an elements exists from a {@link String} key.
     *
     * @param key
     * The key to check an elements exists.
     *
     * @return
     * True if the element from the {@link String} exists. False if not.
     */
    public boolean contains(String key) {
        boolean contains = false;

        for(int i = 0; i <= 10; i++) {
            try {
                switch(i) {
                    case 0:
                        this.getString(key);
                        contains = true;
                        break;
                    case 1:
                        this.getInteger(key);
                        contains = true;
                        break;
                    case 2:
                        this.getDouble(key);
                        contains = true;
                        break;
                    case 3:
                        this.getFloat(key);
                        contains = true;
                        break;
                    case 4:
                        this.getBoolean(key);
                        contains = true;
                        break;
                    case 5:
                        this.getStringList(key);
                        contains = true;
                        break;
                    case 6:
                        this.getIntegerList(key);
                        contains = true;
                        break;
                    case 7:
                        this.getDoubleList(key);
                        contains = true;
                        break;
                    case 8:
                        this.getFloatList(key);
                        contains = true;
                        break;
                    default:
                        this.getBooleanList(key);
                        contains = true;
                        break;
                }
            } catch(IllegalArgumentException ignored) {
            }
        }
        return contains;
    }

    /**
     * Get a {@link String} value from a {@link String} key with/without quotation marks.
     *
     * @param key
     * The {@link String} key to get a {@link String} value with/without quotation marks.
     *
     * @param withQuotationMarks
     * True to add quotation marks condition, else, false.
     *
     * @return
     * A {@link String} value from a {@link String} key with/without quotation marks.
     */
    private String getStringWithQuotationMarksCondition(String key, boolean withQuotationMarks) {
        this.throwKeyExceptions(key);
        String line;

        try {
            line = this.bufferedReader.readLine();

            while(line != null) {
                if(line.contains(this.keyValueSeparator.getSeparator())) {
                    final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                    if(keyValueArray[0].equals(key)) {
                        if(keyValueArray.length >= 3) {
                            for(int i = 2; i < keyValueArray.length; i++) {
                                keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                            }
                        }
                        this.resetBufferedReader();
                        if(withQuotationMarks) {
                            if(keyValueArray[1].startsWith("\"") && keyValueArray[1].endsWith("\"")) {
                                return keyValueArray[1].substring(1, keyValueArray[1].length() - 1);
                            } else {
                                throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Your value must include \" at end and the beginning.");
                            }
                        } else {
                            return keyValueArray[1];
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
     * Get a {@link String} value from a {@link String} key with quotation marks.
     *
     * @param key
     * The {@link String} key to get a {@link String} value with quotation marks.
     *
     * @return
     * A {@link String} value from a {@link String} key with quotation marks.
     */
    public String getString(String key) {
        return this.getStringWithQuotationMarksCondition(key, true);
    }

     /**
     * Get an {@link Integer} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to get an {@link Integer} value.
     *
     * @return
     * An {@link Integer} value from a {@link String} key.
     */
    public int getInteger(String key) {
        return Integer.parseInt(this.getStringWithQuotationMarksCondition(key, false));
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
        return Double.parseDouble(this.getStringWithQuotationMarksCondition(key, false));
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
        return Float.parseFloat(this.getStringWithQuotationMarksCondition(key, false));
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
        return Boolean.parseBoolean(this.getStringWithQuotationMarksCondition(key, false));
    }

    private List<String> getStringListWithQuotationMarksCondition(String key, boolean withQuotationMarks) {
        this.throwKeyExceptions(key);
        String line;

        try {
            line = this.bufferedReader.readLine();

            while(line != null) {
                if(line.contains(this.keyValueSeparator.getSeparator())) {
                    final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                    if(keyValueArray[0].equals(key)) {
                        if(keyValueArray.length >= 3) {
                            for(int i = 2; i < keyValueArray.length; i++) {
                                keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                            }
                        }
                        if(keyValueArray[1].startsWith("[") && keyValueArray[1].endsWith("]")) {
                            final String[] bruteElements = keyValueArray[1].substring(1, keyValueArray[1].length() - 1).split(", ");
                            final List<String> elements = new ArrayList<>(Arrays.asList(bruteElements));

                            this.resetBufferedReader();
                            if(withQuotationMarks) {
                                boolean mustDeclareException = false;

                                for(String s : elements) {
                                    if(!(s.startsWith("\"") && s.endsWith("\""))) {
                                        mustDeclareException = true;
                                        break;
                                    }
                                }
                                if(!mustDeclareException) {
                                    elements.replaceAll(s -> s.substring(1, s.length() - 1));
                                } else {
                                    throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Your element of your array must include \" at end and the beginning.");
                                }
                            }
                            return elements;
                        }
                        throw new IllegalArgumentException("Your value must include a [ and a ] at the end and the beginning");
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

    public List<String> getStringList(String key) {
        return this.getStringListWithQuotationMarksCondition(key, true);
    }

    public List<Integer> getIntegerList(String key) {
        final List<Integer> list = new ArrayList<>();

        for(String s : this.getStringListWithQuotationMarksCondition(key, false)) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    public List<Double> getDoubleList(String key) {
        final List<Double> list = new ArrayList<>();

        for(String s : this.getStringListWithQuotationMarksCondition(key, false)) {
            list.add(Double.parseDouble(s));
        }
        return list;
    }

    public List<Float> getFloatList(String key) {
        final List<Float> list = new ArrayList<>();

        for(String s : this.getStringListWithQuotationMarksCondition(key, false)) {
            list.add(Float.parseFloat(s));
        }
        return list;
    }

    public List<Boolean> getBooleanList(String key) {
        final List<Boolean> list = new ArrayList<>();

        for(String s : this.getStringListWithQuotationMarksCondition(key, false)) {
            list.add(Boolean.parseBoolean(s));
        }
        return list;
    }

    /**
     * Set an {@link Object} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to set a {@link Object} value.
     *
     * @param value
     * The {@link Object} value set from a {@link String} key.
     *
     * @param withQuotationMarks
     * The condition to set or no the quotation marks at the end and the beginning of the {@link Object} value.
     *
     * @param <V>
     * The value ({@link String}, {@link Integer}, {@link Double}, {@link Float}, {@link Boolean}) set in the {@link File} from a {@link String} key.
     */
    private <V> void setStringWithQuotationMarksCondition(String key, V value, boolean withQuotationMarks) {
        this.throwKeyExceptions(key);
        try {
            if(!this.lines.isEmpty()) {
                boolean containsLine = false;

                for(String line : this.lines) {
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                        if(keyValueArray[0].equals(key)) {
                            if(keyValueArray.length >= 3) {
                                for(int i = 2; i < keyValueArray.length; i++) {
                                    keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                                }
                            }
                            containsLine = true;
                            this.lines.set(this.lines.indexOf(line), withQuotationMarks ? keyValueArray[0] + this.keyValueSeparator.getSeparator() + "\"" + value + "\"" : keyValueArray[0] + this.keyValueSeparator.getSeparator() + value);
                        }
                    }
                }
                final String line = withQuotationMarks ? key + this.keyValueSeparator.getSeparator() + "\"" + value + "\"" :  key + this.keyValueSeparator.getSeparator() + value;

                if(!containsLine) {
                    this.lines.add(line);
                }
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(this.file.toPath()), StandardCharsets.UTF_8));
                for(String l : this.lines) {
                    this.bufferedWriter.write(l);
                    this.bufferedWriter.newLine();
                }
            } else {
                final String line = withQuotationMarks ? key + this.keyValueSeparator.getSeparator() + "\"" + value + "\"" :  key + this.keyValueSeparator.getSeparator() + value;

                this.lines.add(line);
                this.bufferedWriter.write(line);
            }
            this.bufferedWriter.flush();
            return;
        } catch(IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Invalid key: '" + key + "' or key value separator '" + this.keyValueSeparator.getSeparator() + "'.");
    }

    /**
     * Set a {@link String} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to set a {@link String} value.
     *
     * @param value
     * The {@link String} value set from a {@link String} key.
     */
    public void setString(String key, String value) {
        this.setStringWithQuotationMarksCondition(key, value, true);
    }

    /**
     * Set an {@link Integer} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to set an {@link Integer} value.
     *
     * @param value
     * The {@link Integer} value set from a {@link String} key.
     */
    public void setInteger(String key, int value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

    /**
     * Set a {@link Double} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to set a {@link Double} value.
     *
     * @param value
     * The {@link Double} value set from a {@link String} key.
     */
    public void setDouble(String key, double value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

    /**
     * Set a {@link Float} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to set a {@link Float} value.
     *
     * @param value
     * The {@link Float} value set from a {@link String} key.
     */
    public void setFloat(String key, float value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

    /**
     * Set a {@link Boolean} value from a {@link String} key.
     *
     * @param key
     * The {@link String} key to set a {@link Boolean} value.
     *
     * @param value
     * The {@link Boolean} value set from a {@link String} key.
     */
    public void setBoolean(String key, boolean value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

    private <V> void setStringListWithQuotationMarksCondition(String key, List<V> value, boolean withQuotationMarks) {
        this.throwKeyExceptions(key);
        final StringBuilder finalValue = new StringBuilder();

        finalValue.append("[");
        for(V element : value) {
            finalValue.append(withQuotationMarks ? "\"" : "").append(element).append(withQuotationMarks ? "\"" : "").append(", ");
        }
        finalValue.append("]");
        final String lineToAdd = key + this.keyValueSeparator.getSeparator() + finalValue.deleteCharAt(finalValue.toString().length() - 3).deleteCharAt(finalValue.toString().length() - 2);

        try {
            boolean containsLine = false;

            if(!this.lines.isEmpty()) {
                for(String line : this.lines) {
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] keyValueArray = line.split(this.keyValueSeparator.getSeparator());

                        if(keyValueArray[0].equals(key)) {
                             if(keyValueArray.length >= 3) {
                                for(int i = 2; i < keyValueArray.length; i++) {
                                    keyValueArray[1] += this.keyValueSeparator.getSeparator() + keyValueArray[i];
                                }
                            }
                            containsLine = true;
                            this.lines.set(this.lines.indexOf(line), lineToAdd);
                        }
                    }
                }
                if(!containsLine) {
                    this.lines.add(lineToAdd);
                }
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(this.file.toPath()), StandardCharsets.UTF_8));
                for(String line : this.lines) {
                    this.bufferedWriter.write(line);
                    this.bufferedWriter.newLine();
                }
            } else {
                this.lines.add(lineToAdd);
                this.bufferedWriter.write(lineToAdd);
            }
            this.bufferedWriter.flush();
            return;
        } catch(IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(PropertiesData.getLogsPrefix() + "Invalid key: '" + key + "' or key value separator '" + this.keyValueSeparator.getSeparator() + "'.");
    }

    public void setStringList(String key, List<String> list) {
        this.setStringListWithQuotationMarksCondition(key, list, true);
    }

    public void setIntegerList(String key, List<Integer> list) {
        this.setStringListWithQuotationMarksCondition(key, list, false);
    }

    public void setDoubleList(String key, List<Double> list) {
        this.setStringListWithQuotationMarksCondition(key, list, false);
    }

    public void setFloatList(String key, List<Float> list) {
        this.setStringListWithQuotationMarksCondition(key, list, false);
    }

    public void setBooleanList(String key, List<Boolean> list) {
        this.setStringListWithQuotationMarksCondition(key, list, false);
    }

    /**
     * Set a {@link String} value from a {@link String} key if the element doesn't exist.
     *
     * @param key
     * The {@link String} key to set a {@link String} value if the element doesn't exist.
     *
     * @param value
     * The {@link String} value set from a {@link String} key if the element doesn't exist.
     */
    public void setDefaultString(String key, String value) {
        if(!this.contains(key)) {
            this.setString(key, value);
        }
    }

    /**
     * Set an {@link Integer} value from a {@link String} key if the element doesn't exist.
     *
     * @param key
     * The {@link String} key to set an {@link Integer} value if the element doesn't exist.
     *
     * @param value
     * The {@link Integer} value set from a {@link String} key if the element doesn't exist.
     */
    public void setDefaultInteger(String key, int value) {
        if(!this.contains(key)) {
            this.setInteger(key, value);
        }
    }

    /**
     * Set a {@link Double} value from a {@link String} key if the element doesn't exist.
     *
     * @param key
     * The {@link String} key to set a {@link Double} value if the element doesn't exist.
     *
     * @param value
     * The {@link Double} value set from a {@link String} key if the element doesn't exist.
     */
    public void setDefaultDouble(String key, double value) {
        if(!this.contains(key)) {
            this.setDouble(key, value);
        }
    }

    /**
     * Set a {@link Float} value from a {@link String} key if the element doesn't exist.
     *
     * @param key
     * The {@link String} key to set a {@link Float} value if the element doesn't exist.
     *
     * @param value
     * The {@link Float} value set from a {@link String} key if the element doesn't exist.
     */
    public void setDefaultFloat(String key, float value) {
        if(!this.contains(key)) {
            this.setFloat(key, value);
        }
    }

    /**
     * Set a {@link Boolean} value from a {@link String} key if the element doesn't exist.
     *
     * @param key
     * The {@link String} key to set a {@link Boolean} value if the element doesn't exist.
     *
     * @param value
     * The {@link Boolean} value set from a {@link String} key if the element doesn't exist.
     */
    public void setDefaultBoolean(String key, boolean value) {
        if(!this.contains(key)) {
            this.setBoolean(key, value);
        }
    }

    public void setDefaultStringList(String key, List<String> list) {
        if(!this.contains(key)) {
            this.setStringList(key, list);
        }
    }

    public void setDefaultIntegerList(String key, List<Integer> list) {
        if(!this.contains(key)) {
            this.setIntegerList(key, list);
        }
    }

    public void setDefaultDoubleList(String key, List<Double> list) {
        if(!this.contains(key)) {
            this.setDoubleList(key, list);
        }
    }

    public void setDefaultFloatList(String key, List<Float> list) {
        if(!this.contains(key)) {
            this.setFloatList(key, list);
        }
    }

    public void setDefaultBooleanList(String key, List<Boolean> list) {
        if(!this.contains(key)) {
            this.setBooleanList(key, list);
        }
    }
}
