package com.ao.ioc.module;

import com.ao.config.ArchetypeConfiguration;
import com.ao.config.ini.ArchetypeConfigurationIni;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Properties;

/**
 * Module for game specific configuration.
 */

public class ConfigurationModule extends AbstractModule {

    private final Properties properties;

    public ConfigurationModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        // Bind game specific configuration
        bind(ArchetypeConfiguration.class).to(ArchetypeConfigurationIni.class);
        bind(String.class).annotatedWith(Names.named("ArchetypeConfigIni")).toInstance(properties.getProperty("config.path.archetype"));
    }

}
