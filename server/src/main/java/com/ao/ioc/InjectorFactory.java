package com.ao.ioc;

import com.ao.ioc.module.*;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Properties;

public class InjectorFactory {

    /**
     * Creates and returns an instance of Guice Injector configured with the specified properties.
     * <p>
     * This method combines several modules, including BootstrapModule, ConfigurationModule, DaoModule, ServiceModule, and
     * SecurityModule, each of which is initialized with the provided properties.
     *
     * @param properties properties used to configure the modules in the injector
     * @return an Injector instance configured with the specified modules
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
