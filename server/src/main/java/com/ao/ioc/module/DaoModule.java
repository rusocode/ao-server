package com.ao.ioc.module;

import com.ao.data.dao.*;
import com.ao.data.dao.ini.CityDAOIni;
import com.ao.data.dao.ini.NpcDAOIni;
import com.ao.data.dao.ini.UserDAOIni;
import com.ao.data.dao.ini.ObjectDAOIni;
import com.ao.data.dao.map.MapDAOImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import java.util.Properties;

public class DaoModule extends AbstractModule {

    private final Properties properties;

    public DaoModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        bind(MapDAO.class).to(MapDAOImpl.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("mapsPath")).toInstance(properties.getProperty("config.path.maps"));
        bind(Integer.class).annotatedWith(Names.named("mapsAmount")).toInstance(Integer.parseInt(properties.getProperty("config.maps.amount")));
        bind(String.class).annotatedWith(Names.named("mapsConfigFile")).toInstance("maps.properties");

        bind(AccountDAO.class).to(UserDAOIni.class).in(Singleton.class);
        bind(UserCharacterDAO.class).to(UserDAOIni.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("CharfilesPath")).toInstance(properties.getProperty("config.path.charfiles"));

        bind(ObjectDAO.class).to(ObjectDAOIni.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("objectsFilePath")).toInstance(properties.getProperty("config.path.objsdat"));
        bind(Integer.class).annotatedWith(Names.named("itemsPerRow")).toInstance(Integer.parseInt(properties.getProperty("config.inventory.itemperrow")));

        bind(CityDAO.class).to(CityDAOIni.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("citiesFilePath")).toInstance(properties.getProperty("config.path.citiesdat"));

        bind(NpcCharacterDAO.class).to(NpcDAOIni.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("npcsFilePath")).toInstance(properties.getProperty("config.path.npcsdat"));
    }

}
