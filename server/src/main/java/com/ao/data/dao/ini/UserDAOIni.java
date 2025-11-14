package com.ao.data.dao.ini;

import com.ao.data.dao.AccountDAO;
import com.ao.data.dao.UserCharacterDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.character.*;
import com.ao.model.character.archetype.Archetype;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.map.City;
import com.ao.model.map.Position;
import com.ao.model.user.Account;
import com.ao.model.user.AccountImpl;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.LoggedUser;
import com.ao.utils.IniUtils;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO Username o charfile?
 */

public record UserDAOIni(String charfilesPath) implements AccountDAO, UserCharacterDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOIni.class);

    private static final String FILE_EXTENSION = ".chr";

    private static final String INIT_HEADER = "INIT";
    private static final String PASSWORD_KEY = "Password";
    private static final String GENDER_KEY = "Genero";
    private static final String RACE_KEY = "Raza";
    private static final String MAP_KEY = "Map";
    private static final String ARCHETYPE_KEY = "Clase";
    private static final String HEADING_KEY = "Heading";
    private static final String WEAPON_KEY = "Arma";
    private static final String SHIELD_KEY = "Escudo";
    private static final String HELMET_KEY = "Casco";
    private static final String UPTIME_KEY = "UpTime";
    private static final String HEAD_KEY = "Head";
    private static final String BODY_KEY = "Body";
    private static final String POSITION_KEY = "Position";
    private static final String LOGGED_KEY = "Logged";
    private static final String LAST_IP_FORMAT = "LastIP%d";
    private static final byte LOGGED_IPS_AMOUNT = 5;

    private static final String FLAGS_HEADER = "FLAGS";
    private static final String DEAD_KEY = "Muerto";
    private static final String HIDDEN_KEY = "Escondido";
    private static final String HUNGRY_KEY = "Hambre";
    private static final String THIRSTY_KEY = "Sed";
    // TODO Missing "desnudo"
    private static final String BANNED_KEY = "Ban";
    private static final String SAILING_KEY = "Navegando";
    private static final String POISONED_KEY = "Envenenado";
    private static final String PARALYZED_KEY = "Paralizado";

    private static final String COUNCIL_HEADER = "CONSEJO";
    private static final String BELONGS_KEY = "PERTENECE";
    private static final String BELONGS_TO_CHAOS_COUNCIL_KEY = "PERTENECECAOS";

    private static final String COUNTERS_HEADER = "COUNTERS";
    private static final String LEFT_TIME_IN_JAIL_KEY = "Pena";

    private static final String FACTIONS_HEADER = "FACCIONES";
    private static final String BELONGS_TO_ARMY_KEY = "EjercitoReal";
    private static final String BELONGS_TO_CHAOS_KEY = "EjercitoCaos";
    private static final String CRIMINALS_KILLED_KEY = "CrimMatados";
    private static final String CITIZENS_KILLED_KEY = "CiudMatados"; // TODO No esta en test.chr
    private static final String CHAOS_ARMOR_RECEIVED_KEY = "rArCaos";
    private static final String ARMY_ARMOR_RECEIVED_KEY = "rArReal";
    private static final String CHAOS_EXPERIENCE_RECEIVED_KEY = "rExCaos";
    private static final String ARMY_EXPERIENCE_RECEIVED_KEY = "rExReal";
    private static final String CHAOS_GRADE_KEY = "recCaos";
    private static final String ARMY_GRADE_KEY = "recReal";
    private static final String REENLISTMENTS_KEY = "Reenlistadas";
    private static final String ENLISTMENT_LEVEL_KEY = "NivelIngreso";
    private static final String ENLISTMENT_DATE_KEY = "FechaIngreso";
    private static final String ENLISTMENT_KILLS_KEY = "MatadosIngreso";
    private static final String NEXT_REWARD_KEY = "NextRecompensa";

    private static final String ATTRIBUTES_HEADER = "ATRIBUTOS";
    private static final String ATTRIBUTE_FORMAT_KEY = "AT%d";

    private static final String SKILLS_HEADER = "SKILLS";
    private static final String SKILL_KEY_FORMAT = "SK%d";

    private static final String CONTACT_HEADER = "CONTACTO";
    private static final String MAIL_KEY = "Email";

    private static final String STATS_HEADER = "STATS";
    private static final String GOLD_KEY = "GLD";
    private static final String DEPOSITED_GOLD_KEY = "BANCO";
    private static final String MAX_HP_KEY = "MaxHP";
    private static final String MIN_HP_KEY = "MinHP";
    private static final String MAX_STAMINA_KEY = "MaxSTA";
    private static final String MIN_STAMINA_KEY = "MinSTA";
    private static final String MAX_MANA_KEY = "MaxMAN";
    private static final String MIN_MANA_KEY = "MinMAN";
    private static final String MAX_HIT_KEY = "MaxHIT";
    private static final String MIN_HIT_KEY = "MinHIT";
    private static final String MAX_THIRSTINESS_KEY = "MaxAGU";
    private static final String MIN_THIRSTINESS_KEY = "MinAGU";
    private static final String MAX_HUNGER_KEY = "MaxHAM";
    private static final String MIN_HUNGER_KEY = "MinHAM";
    private static final String FREE_SKILL_POINTS_KEY = "SkillPtsLibres";
    private static final String EXPERIENCE_KEY = "EXP";
    private static final String LEVEL_KEY = "ELV";
    private static final String EXPERIENCE_TO_LEVEL_UP_KEY = "ELU";

    private static final String KILLS_HEADER = "MUERTES";
    private static final String KILLED_USERS_KEY = "UserMuertes";
    private static final String KILLED_NPCS_KEY = "NpcsMuertes";

    private static final String BANK_INVENTORY_HEADER = "BancoInventory";
    private static final String ITEMS_AMOUNT_KEY = "CantidadItems"; // Tambien forma parte de la seccion [Inventory]
    private static final String ITEM_KEY_FORMAT = "Obj%d";

    private static final String INVENTORY_HEADER = "Inventory";
    private static final String EQUIPPED_WEAPON_SLOT_KEY = "WeaponEqpSlot";
    private static final String EQUIPPED_ARMOUR_SLOT_KEY = "ArmourEqpSlot";
    private static final String EQUIPPED_HELMET_SLOT_KEY = "CascoEqpSlot";
    private static final String EQUIPPED_BOAT_SLOT_KEY = "BarconEqpSlot";
    private static final String MUNITION_SLOT_KEY = "MunicionSlot";
    private static final String RING_SLOT_KEY = "AnilloSlot";

    private static final String REPUTATION_HEADER = "REP";
    private static final String ASSASSIN_POINTS_KEY = "Asesino";
    private static final String BANDIT_POINTS_KEY = "Bandido";
    private static final String BOURGEOIS_POINTS_KEY = "Burguesia";
    private static final String THIEF_POINTS_KEY = "Ladrones";
    private static final String NOBLE_POINTS_KEY = "Nobles";

    private static final String SPELLS_HEADER = "HECHIZOS";
    private static final String SPELL_KEY_FORMAT = "H%d";
    // TODO This shouldn't be here
    private static final byte MAX_SPELLS_AMOUNT = 35;

    private static final String PETS_HEADER = "MASCOTAS";
    private static final String PET_KEY_FORMAT = "MAS%d";
    // TODO This shouldn't be here
    private static final byte MAX_PETS_AMOUNT = 3;

    private static final String RESEARCH_HEADER = "RESEARCH";
    private static final String TRAINING_TIME_KEY = "TrainingTime";

    private static final String GUILD_HEADER = "GUILD";
    private static final String GUILD_INDEX_KEY = "GUILDINDEX";
    private static final String APPLICANT_TO_KEY = "AspiranteA";

    private static final String CRIMINAL_RECORD_HEADER = "PENAS";
    private static final String RECORDS_AMOUNT_KEY = "Cant";
    private static final String RECORD_KEY_FORMAT = "P%d";

    private static final byte NO_SHIELD = 2;
    private static final byte NO_WEAPON = 2;
    private static final byte NO_HELMET = 2;
    // TODO This shouldn't be here
    private static final String NO_ENLISTMENT_KEY_MESSAGE = "No ingreso a ninguna faccion";
    private static final int INITIAL_NOBLE_POINTS = 1000;

    @Inject
    public UserDAOIni(@Named("CharfilesPath") String charfilesPath) {
        this.charfilesPath = charfilesPath;
    }

    @Override
    public Account get(String username) throws DAOException {

        if (username == null || username.isBlank()) throw new DAOException("Username cannot be null or blank.");

        INIConfiguration ini = readCharFile(username);

        if (ini == null) {
            LOGGER.debug("Character '{}' not found!", username);
            return null;
        }

        try {

            // Required keys
            String password = IniUtils.getString(ini, INIT_HEADER + "." + PASSWORD_KEY, "");
            String mail = IniUtils.getString(ini, CONTACT_HEADER + "." + MAIL_KEY, "");
            String banned = IniUtils.getString(ini, FLAGS_HEADER + "." + BANNED_KEY, "");

            if (password.isBlank() || mail.isBlank() || banned.isBlank()) {
                LOGGER.warn("Missing required fields for '{}': password={}, mail={}, banned={}", username, password, mail, banned);
                throw new DAOException("Character file is missing required data.");
            }

            // Add the single character's name
            Set<String> characters = new HashSet<>();
            characters.add(username);

            // Parse banned flag
            boolean isBanned = "1".equals(banned);

            return new AccountImpl(username, password, mail, characters, isBanned);

        } catch (Exception e) {
            LOGGER.error("Error geting charfile (account data) for username '{}'.", username, e);
            throw new DAOException("Failed to geting account: " + e.getMessage());
        }

    }

    @Override
    public Account create(String username, String password, String mail) throws DAOException {

        // Throws exception if the username already exists
        if (exists(username)) throw new NameAlreadyTakenException();

        INIConfiguration ini = new INIConfiguration();
        ini.setProperty(INIT_HEADER + "." + PASSWORD_KEY, password);
        ini.setProperty(CONTACT_HEADER + "." + MAIL_KEY, mail);
        ini.setProperty(FLAGS_HEADER + "." + BANNED_KEY, "0");

        String filePath = getCharFilePath(username);

        try (Writer writer = new BufferedWriter(new FileWriter(filePath))) {
            ini.write(writer);
            LOGGER.debug("Charfile '{}' created successfully!", username);
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error creating charfile! {}", e.getMessage());
            throw new DAOException(e);
        }

        return new AccountImpl(username, password, mail, new HashSet<>(), false);
    }

    @Override
    public void delete(String username) {
        File charfile = new File(getCharFilePath(username));
        if (!charfile.exists()) return;
        boolean success = charfile.delete();
        if (!success) LOGGER.error("{} deletion failed.", username);
    }

    @Override
    public UserCharacter load(ConnectedUser user, String nick) throws DAOException {

        if (nick == null || nick.isBlank()) throw new DAOException("Nick cannot be null or empty.");

        INIConfiguration ini = readCharFile(nick);

        if (ini == null) {
            LOGGER.debug("Character '{}' not found", nick);
            return null;
        }

        int assassinPoints = IniUtils.getInt(ini, REPUTATION_HEADER + "." + ASSASSIN_POINTS_KEY, 0);
        int banditPoints = IniUtils.getInt(ini, REPUTATION_HEADER + "." + BANDIT_POINTS_KEY, 0);
        int bourgeoisPoints = IniUtils.getInt(ini, REPUTATION_HEADER + "." + BOURGEOIS_POINTS_KEY, 0);
        int thiefPoints = IniUtils.getInt(ini, REPUTATION_HEADER + "." + THIEF_POINTS_KEY, 0);
        int noblePoints = IniUtils.getInt(ini, REPUTATION_HEADER + "." + NOBLE_POINTS_KEY, 0);
        boolean belongsToFaction = IniUtils.getInt(ini, FACTIONS_HEADER + "." + BELONGS_TO_CHAOS_KEY, 0) == 1 || IniUtils.getInt(ini, FACTIONS_HEADER + "." + BELONGS_TO_ARMY_KEY, 0) == 1;

        Reputation reputation = new ReputationImpl(assassinPoints, banditPoints, bourgeoisPoints, thiefPoints, noblePoints, belongsToFaction);

        // TODO Por que necesita obtener el valor de la clave a traves del getString()?
        Race race = Race.findById(Byte.parseByte(IniUtils.getString(ini, INIT_HEADER + "." + RACE_KEY, "0")));
        Gender gender = Gender.findById(Byte.parseByte(IniUtils.getString(ini, INIT_HEADER + "." + GENDER_KEY, "0")));
        Archetype archetype = UserArchetype.findById(Byte.parseByte(IniUtils.getString(ini, INIT_HEADER + "." + ARCHETYPE_KEY, "0"))).getArchetype();
        boolean poisoned = IniUtils.getInt(ini, FLAGS_HEADER + "." + POISONED_KEY, 0) == 1;
        boolean paralyzed = IniUtils.getInt(ini, FLAGS_HEADER + "." + PARALYZED_KEY, 0) == 1;

        // TODO Check what to do, immobilized/invisible/mimetized/dumbed state isn't saved in charfile
        boolean immobilized = false;
        boolean invisible = false;
        boolean mimetized = false;
        boolean dumbed = false;

        boolean hidden = IniUtils.getInt(ini, FLAGS_HEADER + "." + HIDDEN_KEY, 0) == 1;

        int mana = IniUtils.getInt(ini, STATS_HEADER + "." + MIN_MANA_KEY, 0);
        int maxMana = IniUtils.getInt(ini, STATS_HEADER + "." + MAX_MANA_KEY, 0);
        int hitpoints = IniUtils.getInt(ini, STATS_HEADER + "." + MIN_HIT_KEY, 0);
        int maxHitPoints = IniUtils.getInt(ini, STATS_HEADER + "." + MAX_HIT_KEY, 0);
        int thirstiness = IniUtils.getInt(ini, STATS_HEADER + "." + MIN_THIRSTINESS_KEY, 0);
        int maxThirstiness = IniUtils.getInt(ini, STATS_HEADER + "." + MAX_THIRSTINESS_KEY, 0);
        int hunger = IniUtils.getInt(ini, STATS_HEADER + "." + MIN_HUNGER_KEY, 0);
        int maxHunger = IniUtils.getInt(ini, STATS_HEADER + "." + MAX_HUNGER_KEY, 0);
        byte lvl = Byte.parseByte(IniUtils.getString(ini, STATS_HEADER + "." + LEVEL_KEY, "0"));

        int body = IniUtils.getInt(ini, INIT_HEADER + "." + BODY_KEY, 0);
        int head = IniUtils.getInt(ini, INIT_HEADER + "." + HEAD_KEY, 0);

        String positionString = IniUtils.getString(ini, INIT_HEADER + "." + POSITION_KEY, null);
        Position position = null;
        if (positionString != null) {
            // Espera formato: "map-x-y" (ej: "1-50-50")
            String[] parts = positionString.split("-");
            if (parts.length == 3) {
                try {
                    int map = Integer.parseInt(parts[0]);
                    byte x = Byte.parseByte(parts[1]);
                    byte y = Byte.parseByte(parts[2]);
                    position = new Position(x, y, map);
                } catch (NumberFormatException e) {
                    LOGGER.error("Invalid position data for '{}': {}", nick, positionString);
                }
            } else LOGGER.error("Malformed POSITION for '{}': {}", nick, positionString);
        } else LOGGER.error("Position not set for '{}'", nick);

        // TODO Complete description
        String description = "";

        return new LoggedUser(user, reputation, race, gender, archetype, poisoned, paralyzed, immobilized, invisible, mimetized,
                dumbed, hidden, maxMana, mana, maxHitPoints, hitpoints, maxThirstiness, thirstiness, maxHunger, hunger, lvl, nick,
                description, position, body, head);
    }

    @Override
    public UserCharacter create(ConnectedUser user, String name, String password, String mail, Race race, Gender gender, UserArchetype archetype, int head, City city, byte strength, byte dexterity, byte intelligence, byte charisma, byte constitution, int initialAvailableSkills, int body) throws DAOException, NameAlreadyTakenException {
        return null;
    }

    @Override
    public boolean exists(String username) {
        return (new File(getCharFilePath(username))).exists();
    }

    @Override
    public AccountAndCharacter createAccountAndCharacter(ConnectedUser user, String nick, String password, String mail, Race race, Gender gender, UserArchetype archetype, int head, City city, byte strength, byte dexterity, byte intelligence, byte charisma, byte constitution, int initialAvailableSkills, int body) throws DAOException {

        if (exists(nick)) throw new NameAlreadyTakenException();

        // Crear el archivo .chr
        String charFilePath = getCharFilePath(nick);
        File charFile = new File(charFilePath);

        INIConfiguration character = new INIConfiguration();

        try {
            // ============================================
            // SECCIÓN [INIT] - Datos básicos y de cuenta
            // ============================================
            character.setProperty(INIT_HEADER + "." + PASSWORD_KEY, password);
            character.setProperty(INIT_HEADER + "." + GENDER_KEY, gender.getId());
            character.setProperty(INIT_HEADER + "." + RACE_KEY, race.getId());
            character.setProperty(INIT_HEADER + "." + ARCHETYPE_KEY, archetype.getId());
            character.setProperty(INIT_HEADER + "." + HEAD_KEY, head);
            character.setProperty(INIT_HEADER + "." + BODY_KEY, body);
            character.setProperty(INIT_HEADER + "." + MAP_KEY, city.map());
            // ini.setProperty(INIT_HEADER + "." + POSITION_KEY, city.x() + "-" + city.y());

            String position = city.map() + "-" + city.x() + "-" + city.y();
            character.setProperty(INIT_HEADER + "." + POSITION_KEY, position);

            character.setProperty(INIT_HEADER + "." + HEADING_KEY, 3); // Default heading
            character.setProperty(INIT_HEADER + "." + LOGGED_KEY, 0);
            character.setProperty(INIT_HEADER + "." + WEAPON_KEY, NO_WEAPON);
            character.setProperty(INIT_HEADER + "." + SHIELD_KEY, NO_SHIELD);
            character.setProperty(INIT_HEADER + "." + HELMET_KEY, NO_HELMET);
            character.setProperty(INIT_HEADER + "." + UPTIME_KEY, 0);

            // ============================================
            // SECCIÓN [CONTACTO] - Email
            // ============================================
            character.setProperty(CONTACT_HEADER + "." + MAIL_KEY, mail);

            // ============================================
            // SECCIÓN [FLAGS] - Estados del personaje
            // ============================================
            character.setProperty(FLAGS_HEADER + "." + BANNED_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + DEAD_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + HIDDEN_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + HUNGRY_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + THIRSTY_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + SAILING_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + POISONED_KEY, 0);
            character.setProperty(FLAGS_HEADER + "." + PARALYZED_KEY, 0);

            // ============================================
            // SECCIÓN [ATRIBUTOS] - Stats básicos
            // ============================================
            character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, 1), strength);
            character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, 2), dexterity);
            character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, 3), intelligence);
            character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, 4), charisma);
            character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, 5), constitution);

            // ============================================
            // SECCIÓN [SKILLS] - Inicializar skills en 0
            // ============================================
            for (int i = 1; i <= 20; i++) { // Ajusta según la cantidad de skills
                character.setProperty(SKILLS_HEADER + "." + String.format(SKILL_KEY_FORMAT, i), 0);
            }

            // ============================================
            // SECCIÓN [STATS] - Stats del personaje
            // ============================================
            int maxHP = calculateMaxHP(constitution, archetype);
            int maxStamina = calculateMaxStamina(constitution);
            int maxMana = calculateMaxMana(intelligence, archetype);

            character.setProperty(STATS_HEADER + "." + MAX_HP_KEY, maxHP);
            character.setProperty(STATS_HEADER + "." + MIN_HP_KEY, maxHP);
            character.setProperty(STATS_HEADER + "." + MAX_STAMINA_KEY, maxStamina);
            character.setProperty(STATS_HEADER + "." + MIN_STAMINA_KEY, maxStamina);
            character.setProperty(STATS_HEADER + "." + MAX_MANA_KEY, maxMana);
            character.setProperty(STATS_HEADER + "." + MIN_MANA_KEY, maxMana);
            character.setProperty(STATS_HEADER + "." + MAX_HIT_KEY, 2);
            character.setProperty(STATS_HEADER + "." + MIN_HIT_KEY, 1);
            character.setProperty(STATS_HEADER + "." + MAX_HUNGER_KEY, 100);
            character.setProperty(STATS_HEADER + "." + MIN_HUNGER_KEY, 100);
            character.setProperty(STATS_HEADER + "." + MAX_THIRSTINESS_KEY, 100);
            character.setProperty(STATS_HEADER + "." + MIN_THIRSTINESS_KEY, 100);
            character.setProperty(STATS_HEADER + "." + FREE_SKILL_POINTS_KEY, initialAvailableSkills);
            character.setProperty(STATS_HEADER + "." + LEVEL_KEY, 1);
            character.setProperty(STATS_HEADER + "." + EXPERIENCE_KEY, 0);
            character.setProperty(STATS_HEADER + "." + EXPERIENCE_TO_LEVEL_UP_KEY, 300);
            character.setProperty(STATS_HEADER + "." + GOLD_KEY, 0);
            character.setProperty(STATS_HEADER + "." + DEPOSITED_GOLD_KEY, 0);

            // ============================================
            // SECCIÓN [REP] - Reputación inicial
            // ============================================
            character.setProperty(REPUTATION_HEADER + "." + ASSASSIN_POINTS_KEY, 0);
            character.setProperty(REPUTATION_HEADER + "." + BANDIT_POINTS_KEY, 0);
            character.setProperty(REPUTATION_HEADER + "." + BOURGEOIS_POINTS_KEY, 0);
            character.setProperty(REPUTATION_HEADER + "." + THIEF_POINTS_KEY, 0);
            character.setProperty(REPUTATION_HEADER + "." + NOBLE_POINTS_KEY, INITIAL_NOBLE_POINTS);

            // ============================================
            // SECCIÓN [INVENTORY] - Inventario vacío
            // ============================================
            character.setProperty(INVENTORY_HEADER + "." + ITEMS_AMOUNT_KEY, 0);
            character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_WEAPON_SLOT_KEY, 0);
            character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_ARMOUR_SLOT_KEY, 0);
            character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_HELMET_SLOT_KEY, 0);
            character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_BOAT_SLOT_KEY, 0);
            character.setProperty(INVENTORY_HEADER + "." + MUNITION_SLOT_KEY, 0);
            character.setProperty(INVENTORY_HEADER + "." + RING_SLOT_KEY, 0);

            // ============================================
            // Guardar el archivo
            // ============================================
            try (FileWriter writer = new FileWriter(charFile)) {
                character.write(writer);
                LOGGER.info("Created new character file: {}", charFilePath);
            }

        } catch (Exception e) {
            LOGGER.error("Error creating character file for '{}'", nick, e);
            // Limpiar archivo si algo salió mal
            if (charFile.exists()) charFile.delete();
            throw new DAOException();
        }

        // Crear las instancias de Account y UserCharacter
        Account account = new AccountImpl(nick, password, mail, new HashSet<>(), false);

        return new UserCharacterDAO.AccountAndCharacter(account, load(user, nick));
    }

    String getCharFilePath(String username) {
        return Paths.get(charfilesPath).resolve(username + FILE_EXTENSION).toString();
    }

    // Métodos auxiliares para calcular stats iniciales
    private int calculateMaxHP(byte constitution, UserArchetype archetype) {
        // Implementa la lógica según tus reglas
        return 15 + constitution / 3;
    }

    private int calculateMaxStamina(byte constitution) {
        // Implementa la lógica según tus reglas
        return 15 + constitution / 3;
    }

    private int calculateMaxMana(byte intelligence, UserArchetype archetype) {
        // Implementa la lógica según tus reglas
        // Los magos deberían tener más maná
        // return archetype.canCastSpells() ? intelligence * 3 : 0;
        return 0;
    }

    private INIConfiguration readCharFile(String username) throws DAOException {
        INIConfiguration ini;
        File file = new File(getCharFilePath(username));

        // If the file does exist in the dynamic directory, try searching for it in the classpath (for test files)
        if (!file.exists()) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("charfiles/" + username/* .toUpperCase() */ + FILE_EXTENSION);

            // The file does exist in the file system and classpath
            if (inputStream == null) return null;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                ini = new INIConfiguration();
                ini.read(reader);
                LOGGER.info("Charfile loaded successfully from classpath!");
            } catch (IOException | ConfigurationException e) {
                LOGGER.error("Charfile loading from classpath failed!", e);
                throw new DAOException(e); // TODO O sys.ext?
            }
        } else {
            // Read from the file system
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                ini = new INIConfiguration();
                ini.read(reader);
                LOGGER.info("Charfile loaded successfully from filesystem!");
            } catch (IOException | ConfigurationException e) {
                LOGGER.error("Charfile loading from filesystem failed!", e);
                throw new DAOException(e);
            }
        }

        return ini;
    }

}
