package com.ao.config.ini;

import com.ao.config.ArchetypeConfiguration;
import com.ao.model.character.archetype.Archetype;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArchetypeConfigurationIni implements ArchetypeConfiguration {

    private static final String BLOCK_POWER_HEADER = "MODESCUDO";
    private static final String EVASION_HEADER = "MODEVASION";
    private static final String MELEE_ACCURACY_HEADER = "MODATAQUEARMAS";
    private static final String MELEE_DAMAGE_HEADER = "MODDAÑOARMAS";
    private static final String RANGED_ACCURACY_HEADER = "MODATAQUEPROYECTILES";
    private static final String RANGED_DAMAGE_HEADER = "MODDAÑOPROYECTILES";
    private static final String WRESTLING_DAMAGE_HEADER = "MODDAÑOWRESTLING";
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchetypeConfigurationIni.class);

    private final INIConfiguration ini;

    /**
     * Constructor loads the configuration file.
     * <p>
     * Without DI (Dependency Injection), it is manual: new ArchetypeConfigurationIni("dat/balances.dat")
     * <p>
     * With DI, is automatic: @Inject ArchetypeConfigurationIni confi
     */
    @Inject
    public ArchetypeConfigurationIni(@Named("ArchetypeConfigIni") String archetypeConfigIni) {
        // Load from a classpath using try-with-resources for automatic resource management
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archetypeConfigIni);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + archetypeConfigIni + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ini = new INIConfiguration();
            ini.read(reader);
        } catch (Exception e) {
            LOGGER.error("Archetype configuration loading failed for file: {}", archetypeConfigIni, e);
            throw new RuntimeException("Failed to load archetype configuration", e);
        }
    }

    @Override
    public float getBlockPowerModifier(Class<? extends Archetype> archetype) {
        // TODO Deberia manejarlo con valor por defecto?
        return ini.getFloat(BLOCK_POWER_HEADER + "." + getArchetypeName(archetype));
    }

    @Override
    public float getEvasionModifier(Class<? extends Archetype> archetype) {
        return ini.getFloat(EVASION_HEADER + "." + getArchetypeName(archetype));
    }

    @Override
    public float getMeleeAccuracyModifier(Class<? extends Archetype> archetype) {
        return ini.getFloat(MELEE_ACCURACY_HEADER + "." + getArchetypeName(archetype));
    }

    @Override
    public float getMeleeDamageModifier(Class<? extends Archetype> archetype) {
        return ini.getFloat(MELEE_DAMAGE_HEADER + "." + getArchetypeName(archetype));
    }

    @Override
    public float getRangedAccuracyModifier(Class<? extends Archetype> archetype) {
        return ini.getFloat(RANGED_ACCURACY_HEADER + "." + getArchetypeName(archetype));
    }

    @Override
    public float getRangedDamageModifier(Class<? extends Archetype> archetype) {
        return ini.getFloat(RANGED_DAMAGE_HEADER + "." + getArchetypeName(archetype));
    }

    @Override
    public float getWrestlingDamageModifier(Class<? extends Archetype> archetype) {
        return ini.getFloat(WRESTLING_DAMAGE_HEADER + "." + getArchetypeName(archetype));
    }

    /**
     * Retrieves the archetype's name in the configuration file.
     *
     * @param archetype archetype
     * @return the archetype's name
     */
    private String getArchetypeName(Class<? extends Archetype> archetype) {

        // Intended to be compatible with current INI files... not the nicest thing ever...
        String archetypeName = archetype.getSimpleName().replace("Archetype", "");

        return switch (archetypeName) {
            case "Bard" -> "Bardo";
            case "Cleric" -> "Clerigo";
            case "Druid" -> "Druida";
            case "Fisher" -> "Pescador";
            case "Lumberjack" -> "Leñador";
            case "Mage" -> "Mago";
            case "Warrior" -> "Guerrero";
            case "Assasin" -> "Asesino";
            case "Thief" -> "Ladron";
            case "Bandit" -> "Bandido";
            case "Paladin" -> "Paladin";
            case "Hunter" -> "Cazador";
            case "Blacksmith" -> "Herrero";
            case "Carpenter" -> "Carpintero";
            case "Miner" -> "Minero";
            case "Pirate" -> "Pirata";
            case "Worker" -> "Trabajador";
            default -> null;
        };

    }

}
