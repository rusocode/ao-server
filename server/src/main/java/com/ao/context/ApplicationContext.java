package com.ao.context;

import com.ao.ioc.InjectorFactory;
import com.google.inject.Injector;

/**
 * General Application Context. Capable of loading common application classes.
 */

public class ApplicationContext {


    /** The basic entry point into Guice is the <i>Injector</i>. Think of Guice's {@code @Inject} as the new {@code new}. */
    private static Injector injector;

    static {
        reload();
    }

    /**
     * Gets an instance of the specified class using dependency injection.
     *
     * @param <T>   type of the class to be retrieved
     * @param clazz class object of the type T for which an instance is required
     * @return an instance of the specified class
     */
    public static <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz); // Guice resolves automatically
    }

    /**
     * Reloads all modules and associations. BEWARE, all previously created objects are no longer attached!
     */
    public static void reload() {
        injector = InjectorFactory.get(ApplicationProperties.getProperties());
    }

}
