package com.ao.context;

import com.ao.ioc.InjectorFactory;
import com.google.inject.Injector;

import java.util.Properties;

/**
 * General Application Context. Capable of loading common application classes.
 */

public class ApplicationContext {

    private static Injector injector;

    static {
        reload();
    }

    /**
     * Retrieves an instance of the requested class.
     *
     * @param <T>   Type of the object being requested
     * @param clazz The class of the object being requested
     * @return An instance of the requested class
     */
    public static <T> T getInstance(final Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    /**
     * Reloads all modules and associations. BEWARE, all previously created objects are no longer attached!
     */
    public static void reload() {
        final Properties properties = ApplicationProperties.getProperties();
        injector = InjectorFactory.get(properties);
    }

}
