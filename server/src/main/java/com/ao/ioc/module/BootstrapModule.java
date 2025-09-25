package com.ao.ioc.module;

import com.ao.config.ServerConfig;
import com.ao.config.ini.ServerConfigIni;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import java.util.Properties;

/**
 * Module for application bootstrapping.
 * <p>
 * <b>The <i>Module</i> is the basic unit of the definition of bindings</b> (or wiring, as it’s known in Spring).
 * <p>
 * A <b>binding</b> is an object that corresponds to an entry in the Guice map. With bindings, we <b>define how Guice is going to
 * inject dependencies</b> into a class.
 * <p>
 * A binding is defined in an implementation of {@code com.google.inject.AbstractModule}.
 * <p>
 * This module implementation specifies that an instance of {@code ServerConfigIni} is to be injected wherever a
 * {@code ServerConfig} variable is found.
 */

public class BootstrapModule extends AbstractModule {

    private final Properties properties;

    public BootstrapModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        // Binds ServerConfig implementation to ServerConfigIni as a singleton (unique instance), this telling Guice: "When someone asks for ServerConfig, give him ServerConfigIni"
        bind(ServerConfig.class).to(ServerConfigIni.class).in(Singleton.class);
        // Another incarnation of this mechanism is the named binding that binds the string with the name ServerConfigIni and asigned the value of the property config.path.server
        bind(String.class).annotatedWith(Names.named("ServerConfigIni")).toInstance(properties.getProperty("config.path.server"));
    }

}
