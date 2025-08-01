package com.ao.config.ini;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import com.ao.config.ArchetypeConfiguration;
import com.ao.model.character.archetype.Archetype;

public class ArchetypeConfigurationIni implements ArchetypeConfiguration {

	private static final String BLOCK_POWER_HEADER = "MODESCUDO";
	private static final String EVASION_HEADER = "MODEVASION";
	private static final String MELEE_ACCURACY_HEADER = "MODATAQUEARMAS";
	private static final String MELEE_DAMAGE_HEADER = "MODDAÑOARMAS";
	private static final String RANGED_ACCURACY_HEADER = "MODATAQUEPROYECTILES";
	private static final String RANGED_DAMAGE_HEADER = "MODDAÑOPROYECTILES";
	private static final String WRESTLING_DAMAGE_HEADER = "MODDAÑOWRESTLING";

	private Ini config;

	/**
	 * Constructor, loads the configuration file.
	 * @throws InvalidFileFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Inject
	public ArchetypeConfigurationIni(@Named("ArchetypeConfigIni") String archetypeConfigIni) throws IOException {
		config = new Ini(new FileInputStream(archetypeConfigIni));
	}

	/**
	 * Retrieves the archetype's name in the configuration file.
	 * @param archetype The archetype.
	 * @return The archetype's name.
	 */
	private String getArchetypeName(Class<? extends Archetype> archetype) {

		// Intended to be compatible with current INI files.... not the nicest thing ever...
		String archetypeName = archetype.getSimpleName().replace( "Archetype", "");

		if (archetypeName.equals("Bard")) {
			return "Bardo";
		} else if (archetypeName.equals("Cleric")) {
			return "Clerigo";
		} else if (archetypeName.equals("Druid")) {
			return "Druida";
		} else if (archetypeName.equals("Fisher")) {
			return "Pescador";
		} else if (archetypeName.equals("Lumberjack")) {
			return "Leñador";
		} else if (archetypeName.equals("Mage")) {
			return "Mago";
		} else if (archetypeName.equals("Warrior")) {
			return "Guerrero";
		} else if (archetypeName.equals("Assasin")) {
			return "Asesino";
		} else if (archetypeName.equals("Thief")) {
			return "Ladron";
		} else if (archetypeName.equals("Bandit")) {
			return "Bandido";
		} else if (archetypeName.equals("Paladin")) {
			return "Paladin";
		} else if (archetypeName.equals("Hunter")) {
			return "Cazador";
		} else if (archetypeName.equals("Blacksmith")) {
			return "Herrero";
		} else if (archetypeName.equals("Carpenter")) {
			return "Carpintero";
		} else if (archetypeName.equals("Miner")) {
			return "Minero";
		} else if (archetypeName.equals("Pirate")) {
			return "Pirata";
		} else if (archetypeName.equals("Worker")) {
			return "Trabajador";
		}

		return null;
	}

	@Override
	public float getBlockPowerModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(BLOCK_POWER_HEADER).get(getArchetypeName(archetype)));
	}

	@Override
	public float getEvasionModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(EVASION_HEADER).get(getArchetypeName(archetype)));
	}

	@Override
	public float getMeleeAccuracyModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(MELEE_ACCURACY_HEADER).get(getArchetypeName(archetype)));
	}

	@Override
	public float getMeleeDamageModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(MELEE_DAMAGE_HEADER).get(getArchetypeName(archetype)));
	}

	@Override
	public float getRangedAccuracyModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(RANGED_ACCURACY_HEADER).get(getArchetypeName(archetype)));
	}

	@Override
	public float getRangedDamageModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(RANGED_DAMAGE_HEADER).get(getArchetypeName(archetype)));
	}

	@Override
	public float getWrestlingDamageModifier(Class<? extends Archetype> archetype) {
		return Float.valueOf(config.get(WRESTLING_DAMAGE_HEADER).get(getArchetypeName(archetype)));
	}
}
