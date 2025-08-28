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

    private final Properties properties;

    public BootstrapModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        // General server configuration
        bind(ServerConfig.class).to(ServerConfigIni.class).in(Singleton.class); // Le dice a Guice: "Cuando alguien pida ServerConfig, dale ServerConfigIni"
        bind(String.class).annotatedWith(Names.named("ServerConfigIni")).toInstance(properties.getProperty("config.path.server"));
    }

}
