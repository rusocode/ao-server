package com.ao.context;

import org.tinylog.Logger;

import java.util.Properties;

/**
 * General properties for the application.
 */

public class ApplicationProperties {

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
        Properties props = new Properties();
        try (java.io.InputStream stream = com.ao.utils.ResourceUtils.getStream(name)) {
            if (stream != null) {
                props.load(stream);
                properties.putAll(props);
            } else {
                Logger.error("Properties file {} not found!", name);
            }
        } catch (Exception e) {
            Logger.error("Error loading {} properties file!", name, e);
        }
    }

    public static Properties getProperties() {
        return properties;
    }

}
