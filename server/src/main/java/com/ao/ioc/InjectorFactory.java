package com.ao.ioc;

import com.ao.ioc.module.*;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Properties;

/**
 * Think of Guice as an intelligent butler in a mansion. Just as you tell your butler "I need breakfast", and he appears with
 * everything perfectly prepared without you worrying about finding ingredients, Guice works similarly with dependencies. When
 * your class declares it needs services through constructor injection, Guice acts as the butler who knows exactly what you need
 * and how to provide it. Configuration modules serve as the butler's instruction manual, defining your preferences like "use
 * organic eggs for breakfast." For complex scenarios, like organizing a dinner party, you simply declare your requirements and
 * Guice automatically provides everything needed, understanding that a chef needs ingredients and waiters need uniforms. This
 * allows you to focus on business logic rather than object creation, making your code more maintainable and testable.
 * <p>
 * <a href="https://www.youtube.com/watch?v=eQ90v7HQT-Q">DI</a>
 * <a href="https://www.youtube.com/watch?v=GATSXm7WAxU">DI with examples</a>
 * <a href="https://www.youtube.com/watch?v=jCz_kcrYqJE&list=PLp0ed20U4R4jknb4xYdhx3yJn5RhWECxn&index=7">Using Guice in
 * application</a> <a
 * href="https://java-design-patterns.com/patterns/dependency-injection/#when-to-use-the-dependency-injection-pattern-in-java">DI
 * Pattern in Java</a>
 * <a href="https://github.com/google/guice/wiki/Motivation">Motivation for using Guice</a>
 */

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
