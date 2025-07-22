package com.ao.ioc.module;

import com.ao.config.ArchetypeConfiguration;
import com.ao.model.character.archetype.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ArchetypeModule extends AbstractModule {

    private final ArchetypeConfiguration config;

    public ArchetypeModule(ArchetypeConfiguration config) {
        this.config = config;
    }

    @Override
    protected void configure() {
    }

    @Provides
    AssasinArchetype provideAssasinArchetype() throws Exception {
        return (AssasinArchetype) getArchetype(AssasinArchetype.class);
    }

    @Provides
    BanditArchetype provideBanditArchetype() throws Exception {
        return (BanditArchetype) getArchetype(BanditArchetype.class);
    }

    @Provides
    BardArchetype provideBardArchetype() throws Exception {
        return (BardArchetype) getArchetype(BardArchetype.class);
    }

    @Provides
    BlacksmithArchetype provideBlacksmithArchetype() throws Exception {
        return (BlacksmithArchetype) getArchetype(BlacksmithArchetype.class);
    }

    @Provides
    CarpenterArchetype provideCarpenterArchetype() throws Exception {
        return (CarpenterArchetype) getArchetype(CarpenterArchetype.class);
    }

    @Provides
    ClericArchetype provideClericArchetype() throws Exception {
        return (ClericArchetype) getArchetype(ClericArchetype.class);
    }

    @Provides
    DruidArchetype provideDruidArchetype() throws Exception {
        return (DruidArchetype) getArchetype(DruidArchetype.class);
    }

    @Provides
    FisherArchetype provideFisherArchetype() throws Exception {
        return (FisherArchetype) getArchetype(FisherArchetype.class);
    }

    @Provides
    HunterArchetype provideHunterArchetype() throws Exception {
        return (HunterArchetype) getArchetype(HunterArchetype.class);
    }

    @Provides
    LumberjackArchetype provideLumberjackArchetype() throws Exception {
        return (LumberjackArchetype) getArchetype(LumberjackArchetype.class);
    }

    @Provides
    MageArchetype provideMageArchetype() throws Exception {
        return (MageArchetype) getArchetype(MageArchetype.class);
    }

    @Provides
    MinerArchetype provideMinerArchetype() throws Exception {
        return (MinerArchetype) getArchetype(MinerArchetype.class);
    }

    @Provides
    PaladinArchetype providePaladinArchetype() throws Exception {
        return (PaladinArchetype) getArchetype(PaladinArchetype.class);
    }

    @Provides
    PirateArchetype providePirateArchetype() throws Exception {
        return (PirateArchetype) getArchetype(PirateArchetype.class);
    }

    @Provides
    ThiefArchetype provideThiefArchetype() throws Exception {
        return (ThiefArchetype) getArchetype(ThiefArchetype.class);
    }

    @Provides
    WarriorArchetype provideWarriorArchetype() throws Exception {
        return (WarriorArchetype) getArchetype(WarriorArchetype.class);
    }

    @Provides
    WorkerArchetype provideWorkerArchetype() throws Exception {
        return (WorkerArchetype) getArchetype(WorkerArchetype.class);
    }

    /**
     * Retrieves a new instance of the given archetype class.
     *
     * @param archetype archetype class
     * @return a new instance of the archetype
     */
    private Archetype getArchetype(Class<? extends Archetype> archetype) throws Exception {
        @SuppressWarnings("rawtypes")
        Class[] args = new Class[]{
                float.class, float.class, float.class,
                float.class, float.class, float.class,
                float.class
        };

        return archetype.getConstructor(args)
                .newInstance(
                        config.getEvasionModifier(archetype),
                        config.getMeleeAccuracyModifier(archetype),
                        config.getRangedAccuracyModifier(archetype),
                        config.getMeleeDamageModifier(archetype),
                        config.getRangedDamageModifier(archetype),
                        config.getWrestlingDamageModifier(archetype),
                        config.getBlockPowerModifier(archetype)
                );
    }

}
