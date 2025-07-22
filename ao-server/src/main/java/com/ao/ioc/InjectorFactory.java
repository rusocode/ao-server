package com.ao.ioc;

import com.ao.ioc.module.*;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Properties;

public class InjectorFactory {

    /**
     * Retrieves a new injector with the given properties.
     *
     * @param properties injector properties
     * @return the injector
     */
    public static Injector get(Properties properties) {
        return Guice.createInjector(
                new BootstrapModule(properties),
                new ConfigurationModule(properties),
                new DaoModule(properties),
                new ServiceModule(properties),
                new SecurityModule(properties)
        );
    }

}
