package com.interview.libraryapi.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public final class PropertiesReader {

    private static final Properties PROPERTIES;

    private static final String PROPERTIES_FILE = "api.properties";

    public PropertiesReader() {
    }

    static {
        PROPERTIES = new Properties();
        final URL props = ClassLoader.getSystemResource(PROPERTIES_FILE);
        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String getProperty(final String name) {
        return PROPERTIES.getProperty(name);
    }

}
