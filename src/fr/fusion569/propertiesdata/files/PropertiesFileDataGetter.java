package fr.fusion569.propertiesdata.files;

import fr.fusion569.propertiesdata.PropertiesData;
import fr.fusion569.propertiesdata.utils.KeyValueSeparator;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class PropertiesFileDataGetter {

    private final File file;
    private final KeyValueSeparator keyValueSeparator;
    private BufferedReader bufferedReader;

    public PropertiesFileDataGetter(File file, KeyValueSeparator keyValueSeparator) {
        this.file = file;
        this.keyValueSeparator = keyValueSeparator;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
        } catch(IOException e) {
            e.printStackTrace();
        }
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
     * Get the {@link KeyValueSeparator}.
     *
     * @return
     * The {@link KeyValueSeparator}.
     */
    public KeyValueSeparator getKeyValueSeparator() {
        return keyValueSeparator;
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
     * Throw the exceptions {@link IllegalArgumentException} if the key is "" or starts with " ".
     *
     * @param key
     * The key to declare exceptions if needed.
     */
    private void throwKeyExceptions(String key) {
        if(key.equals(""))
            throw new IllegalArgumentException("You must set a valid String key");
        if(key.startsWith(" "))
            throw new IllegalArgumentException("Your String key can't starts with ' '.");
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
                if(line.startsWith(key)){
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] kv = line.split(this.keyValueSeparator.getSeparator());

                        if(kv.length >= 3) {
                            for(int i = 2; i < kv.length; i++) {
                                kv[1] += this.keyValueSeparator.getSeparator() + kv[i];
                            }
                        }
                        this.resetBufferedReader();
                        return kv[1];
                    }
                }
                line = this.bufferedReader.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
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
                if(line.startsWith(key)){
                    if(line.contains(this.keyValueSeparator.getSeparator())) {
                        final String[] kv = line.split(this.keyValueSeparator.getSeparator());

                        if(kv.length >= 3) {
                            for(int i = 2; i < kv.length; i++) {
                                kv[1] += this.keyValueSeparator.getSeparator() + kv[i];
                            }
                        }
                        this.resetBufferedReader();
                        if(kv[1].startsWith("\"") && kv[1].endsWith("\"")) {
                            return kv[1].substring(1, kv[1].length() - 1);
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
}
