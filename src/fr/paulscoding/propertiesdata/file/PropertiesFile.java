package fr.paulscoding.propertiesdata.file;

import fr.paulscoding.propertiesdata.PropertiesData;
import fr.paulscoding.propertiesdata.util.KeyValueSeparator;
import fr.paulscoding.propertiesdata.util.StandardDirectoryCreationType;
import fr.paulscoding.propertiesdata.util.StandardFileCreationType;

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

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public StandardFileCreationType getStandardFileCreationType() {
        return standardFileCreationType;
    }

    public StandardDirectoryCreationType getStandardDirectoryCreationType() {
        return standardDirectoryCreationType;
    }

    public KeyValueSeparator getKeyValueSeparator() {
        return keyValueSeparator;
    }

    public File getFile() {
        return file;
    }

    public List<String> getLines() {
        return lines;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    private void resetBufferedReader() {
        try {
            this.bufferedReader.close();
            this.bufferedReader = new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

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

    private void throwKeyExceptions(String key) {
        if(key.equals("")) {
            throw new IllegalArgumentException("You must set a valid String key");
        }
        else if(key.startsWith(" ")) {
            throw new IllegalArgumentException("Your String key can't starts with ' '.");
        }
    }

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

    public String getString(String key) {
        return this.getStringWithQuotationMarksCondition(key, true);
    }

    public int getInteger(String key) {
        return Integer.parseInt(this.getStringWithQuotationMarksCondition(key, false));
    }

    public double getDouble(String key) {
        return Double.parseDouble(this.getStringWithQuotationMarksCondition(key, false));
    }

    public float getFloat(String key) {
        return Float.parseFloat(this.getStringWithQuotationMarksCondition(key, false));
    }

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

    public void setString(String key, String value) {
        this.setStringWithQuotationMarksCondition(key, value, true);
    }

    public void setInteger(String key, int value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

    public void setDouble(String key, double value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

    public void setFloat(String key, float value) {
        this.setStringWithQuotationMarksCondition(key, value, false);
    }

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

    public void setDefaultString(String key, String value) {
        if(!this.contains(key)) {
            this.setString(key, value);
        }
    }

    public void setDefaultInteger(String key, int value) {
        if(!this.contains(key)) {
            this.setInteger(key, value);
        }
    }

    public void setDefaultDouble(String key, double value) {
        if(!this.contains(key)) {
            this.setDouble(key, value);
        }
    }

    public void setDefaultFloat(String key, float value) {
        if(!this.contains(key)) {
            this.setFloat(key, value);
        }
    }

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
