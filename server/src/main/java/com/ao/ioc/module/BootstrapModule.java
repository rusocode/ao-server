package com.ao.ioc.module;

import com.ao.config.ServerConfig;
import com.ao.config.ini.ServerConfigIni;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import java.util.Properties;

/**
 * Module for application bootstrapping.
 */

public class BootstrapModule extends AbstractModule {

    protected Properties properties;

    /**
     * Creates a new BootstrapModule.
     *
     * @param properties general project properties
     */
    public BootstrapModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        // General server configuration
        bind(ServerConfig.class).to(ServerConfigIni.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("ServerConfigIni")).toInstance(properties.getProperty("config.path.server"));
    }

}
