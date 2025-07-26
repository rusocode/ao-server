package com.ao.ioc;

import com.ao.config.ArchetypeConfiguration;
import com.ao.context.ApplicationContext;
import com.ao.ioc.module.ArchetypeModule;
import com.ao.model.character.archetype.Archetype;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ArchetypeLocator {

    private static final Injector injector = Guice.createInjector(new ArchetypeModule(ApplicationContext.getInstance(ArchetypeConfiguration.class)));

    /**
     * Locates archetype instances.
     *
     * @param archetype Class of the archetype to be located
     * @return the archetype instance
     */
    public static Archetype getArchetype(Class<? extends Archetype> archetype) {
        return injector.getInstance(archetype);
    }

}
