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
        loadProperties("project.properties");
    }

    /**
     * Loads all properties from the given file. It's automatically searched as a resource.
     *
     * @param name the name of the file from which to load properties
     */
    public static void loadProperties(String name) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try {
            props.load(loader.getResourceAsStream(name));
            properties.putAll(props);
        } catch (Exception e) {
            LOGGER.error("Error loading {} properties file!", name, e);
        }
    }

    public static Properties getProperties() {
        return properties;
    }

}
