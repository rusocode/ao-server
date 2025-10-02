package com.ao.ioc.module;

import com.ao.action.ActionExecutor;
import com.ao.data.dao.CityDAO;
import com.ao.data.dao.WorldMapDAO;
import com.ao.service.*;
import com.ao.service.character.CharacterBodyServiceImpl;
import com.ao.service.login.LoginServiceImpl;
import com.ao.service.map.AreaServiceImpl;
import com.ao.service.map.MapServiceImpl;
import com.ao.service.npc.NpcServiceImpl;
import com.ao.service.timedevents.TimedEventsServiceImpl;
import com.ao.service.user.UserServiceImpl;
import com.ao.service.worldobject.ObjectServiceImpl;
import com.ao.utils.RangeParser;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.List;
import java.util.Properties;

public class ServiceModule extends AbstractModule {

    private final Properties properties;

    public ServiceModule(Properties properties) {
        this.properties = properties;
    }

    @Provides
    @Singleton
    public MapServiceImpl provideMapServiceImpl(WorldMapDAO mapsDAO, CityDAO citiesDAO, AreaService areaService) {
        return new MapServiceImpl(mapsDAO, citiesDAO, areaService);
    }

    @Provides
    @Singleton
    public MapService provideMapService(MapServiceImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    public ActionExecutor<MapService> provideMapActionExecutor(MapServiceImpl impl) {
        return impl;
    }

    @Override
    protected void configure() {
        bind(LoginService.class).to(LoginServiceImpl.class).in(Singleton.class);
        bind(AreaService.class).to(AreaServiceImpl.class).in(Singleton.class);

//        bind(MapService.class).to(MapServiceImpl.class).in(Singleton.class);
//        bind(new TypeLiteral<ActionExecutor<MapService>>() {
//        }).to(MapServiceImpl.class).in(Singleton.class);

        bind(TimedEventsService.class).to(TimedEventsServiceImpl.class).in(Singleton.class);
        bind(ObjectService.class).to(ObjectServiceImpl.class).in(Singleton.class);
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(CharacterBodyService.class).to(CharacterBodyServiceImpl.class).in(Singleton.class);
        bind(NpcService.class).to(NpcServiceImpl.class).in(Singleton.class);

        bind(Integer.class).annotatedWith(Names.named("initialAvailableSkills")).toInstance(Integer.parseInt(properties.getProperty("config.loginservice.initialavailableskills")));

        // Heads ranges
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsDarkelfMale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.darkelf.male")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsDarkelfFemale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.darkelf.female")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsDwarfMale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.dwarf.male")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsDwarfFemale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.dwarf.female")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsElfMale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.elf.male")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsElfFemale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.elf.female")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsGnomeMale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.gnome.male")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsGnomeFemale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.gnome.female")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsHumanMale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.human.male")));
        bind(new TypeLiteral<List<Integer>>() {
        }).annotatedWith(Names.named("headsHumanFemale")).toInstance(RangeParser.parseIntegers(properties.getProperty("config.heads.human.female")));

        // Default Bodies
        bind(Integer.class).annotatedWith(Names.named("darkElfMaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.darkelf.male")));
        bind(Integer.class).annotatedWith(Names.named("darkElfFemaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.darkelf.female")));
        bind(Integer.class).annotatedWith(Names.named("dwarfMaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.dwarf.male")));
        bind(Integer.class).annotatedWith(Names.named("dwarfFemaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.dwarf.female")));
        bind(Integer.class).annotatedWith(Names.named("elfMaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.elf.male")));
        bind(Integer.class).annotatedWith(Names.named("elfFemaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.elf.female")));
        bind(Integer.class).annotatedWith(Names.named("gnomeMaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.gnome.male")));
        bind(Integer.class).annotatedWith(Names.named("gnomeFemaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.gnome.female")));
        bind(Integer.class).annotatedWith(Names.named("humanMaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.human.male")));
        bind(Integer.class).annotatedWith(Names.named("humanFemaleBody")).toInstance(Integer.parseInt(properties.getProperty("config.bodies.human.female")));
    }

}
