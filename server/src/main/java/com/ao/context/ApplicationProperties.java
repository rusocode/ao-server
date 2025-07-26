package com.ao.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * General properties for the application.
 */

public class ApplicationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);

    private static final Properties properties = new Properties();

    static {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        // Load global.properties
        try {
            final Properties globalProperties = new Properties();
            globalProperties.load(loader.getResourceAsStream("global.properties"));
            System.getProperties().putAll(globalProperties);
        } catch (final Exception e) {
            LOGGER.error("Error global loading application properties", e);
        }

        loadProperties("project.properties");
    }

    /**
     * @return the properties
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * Loads all properties from the given file. It's automatically searched as a resource.
     *
     * @param fileName the name of the file from which to load properties
     */
    public static void loadProperties(final String fileName) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Properties props = new Properties();

        // Load project.properties
        try {
            props.load(loader.getResourceAsStream(fileName));
        } catch (final Exception e) {
            LOGGER.error("Error loading {} properties file.", fileName, e);
        }

        properties.putAll(props);
    }

}
