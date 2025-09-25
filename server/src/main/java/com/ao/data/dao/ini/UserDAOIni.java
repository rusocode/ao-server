package com.ao.data.dao.ini;

import com.ao.data.dao.AccountDAO;
import com.ao.data.dao.UserCharacterDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.character.*;
import com.ao.model.character.Character;
import com.ao.model.character.archetype.Archetype;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
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
import java.nio.file.Path;
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
    private static final String HOMELAND_KEY = "Hogar";
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
        // Crea un Path a partir del string configurado (puede ser relativo o absoluto)
        Path path = Paths.get(charfilesPath);
        // Si la ruta es relativa, la convierte respecto a la raiz del proyecto
        if (!path.isAbsolute()) {
            // Obtiene el directorio actual absoluto (en este caso, el del modulo "server")
            Path moduleDir = Paths.get("").toAbsolutePath().normalize(); // .../ao-server/server
            // Sube un nivel al padre (la raiz del proyecto "ao-server")
            Path projectRoot = moduleDir.getParent();                    // .../ao-server
            // Combina la raiz del proyecto con la ruta relativa configurada (p. ej. "charfiles" → "ao-server/charfiles")
            if (projectRoot != null) path = projectRoot.resolve(path);
        }
        // Convierte el Path resultante a una ruta absoluta normalizada y la guarda en el campo
        this.charfilesPath = path.toAbsolutePath().normalize().toString();
        LOGGER.info("Charfiles path: {}", this.charfilesPath);

        /* Si charfilesPath es relativo, se reinterpreta contra la raiz del proyecto (no contra el working dir del modulo), y
         * luego se guarda como ruta absoluta normalizada; si ya era absoluto, se respeta y solo se normaliza. */
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
    public Account create(String username, String password, String mail) throws DAOException, NameAlreadyTakenException {

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
    public UserCharacter create(ConnectedUser user, String name, Race race, Gender gender, UserArchetype archetype, int head,
                                City homeland, byte strength, byte dexterity, byte intelligence, byte charisma, byte constitution,
                                int initialAvailableSkills, int body)
            throws DAOException, NameAlreadyTakenException {

        INIConfiguration character = new INIConfiguration();

        character.setProperty(INIT_HEADER + "." + GENDER_KEY, gender.ordinal());
        character.setProperty(INIT_HEADER + "." + RACE_KEY, race.ordinal());
        character.setProperty(INIT_HEADER + "." + HOMELAND_KEY, homeland);
        character.setProperty(INIT_HEADER + "." + ARCHETYPE_KEY, archetype.ordinal());
        character.setProperty(INIT_HEADER + "." + HEADING_KEY, Heading.SOUTH.ordinal());
        character.setProperty(INIT_HEADER + "." + WEAPON_KEY, NO_WEAPON);
        character.setProperty(INIT_HEADER + "." + SHIELD_KEY, NO_SHIELD);
        character.setProperty(INIT_HEADER + "." + HELMET_KEY, NO_HELMET);
        character.setProperty(INIT_HEADER + "." + UPTIME_KEY, 0);
        character.setProperty(INIT_HEADER + "." + HEAD_KEY, head);
        character.setProperty(INIT_HEADER + "." + BODY_KEY, body);

        String positionKey = homeland.map() + "-" + homeland.x() + "-" + homeland.y();
        character.setProperty(INIT_HEADER + "." + POSITION_KEY, positionKey);

        // TODO Save last ip?

        character.setProperty(FLAGS_HEADER + "." + BANNED_KEY, 0);
        character.setProperty(FLAGS_HEADER + "." + DEAD_KEY, 0);
        character.setProperty(FLAGS_HEADER + "." + HIDDEN_KEY, 0);
        character.setProperty(FLAGS_HEADER + "." + THIRSTY_KEY, 0);
        character.setProperty(FLAGS_HEADER + "." + SAILING_KEY, 0);
        character.setProperty(FLAGS_HEADER + "." + POISONED_KEY, 0);
        character.setProperty(FLAGS_HEADER + "." + PARALYZED_KEY, 0);

        character.setProperty(COUNCIL_HEADER + "." + BELONGS_KEY, 0);
        character.setProperty(COUNCIL_HEADER + "." + BELONGS_TO_CHAOS_COUNCIL_KEY, 0);

        character.setProperty(COUNTERS_HEADER + "." + LEFT_TIME_IN_JAIL_KEY, 0);

        character.setProperty(FACTIONS_HEADER + "." + BELONGS_TO_ARMY_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + BELONGS_TO_CHAOS_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + BELONGS_TO_CHAOS_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + CRIMINALS_KILLED_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + CHAOS_ARMOR_RECEIVED_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + ARMY_ARMOR_RECEIVED_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + CHAOS_ARMOR_RECEIVED_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + ARMY_EXPERIENCE_RECEIVED_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + CHAOS_GRADE_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + ARMY_GRADE_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + REENLISTMENTS_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + ENLISTMENT_LEVEL_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + ENLISTMENT_DATE_KEY, NO_ENLISTMENT_KEY_MESSAGE);
        character.setProperty(FACTIONS_HEADER + "." + ENLISTMENT_KILLS_KEY, 0);
        character.setProperty(FACTIONS_HEADER + "." + NEXT_REWARD_KEY, 0);

        character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, Attribute.STRENGTH.ordinal() + 1), strength);
        character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, Attribute.DEXTERITY.ordinal() + 1), dexterity);
        character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, Attribute.CHARISMA.ordinal() + 1), charisma);
        character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, Attribute.CONSTITUTION.ordinal() + 1), constitution);
        character.setProperty(ATTRIBUTES_HEADER + "." + String.format(ATTRIBUTE_FORMAT_KEY, Attribute.INTELLIGENCE.ordinal() + 1), intelligence);

        for (byte i = 1; i < Skill.values().length; i++)
            character.setProperty(SKILLS_HEADER + "." + String.format(SKILL_KEY_FORMAT, i + 1), 0);

        character.setProperty(STATS_HEADER + "." + FREE_SKILL_POINTS_KEY, initialAvailableSkills);

        character.setProperty(STATS_HEADER + "." + GOLD_KEY, 0);
        character.setProperty(STATS_HEADER + "." + DEPOSITED_GOLD_KEY, 0);
        character.setProperty(STATS_HEADER + "." + LEVEL_KEY, 1);
        character.setProperty(STATS_HEADER + "." + EXPERIENCE_KEY, 0);

        // TODO Assign HP, mana, stamina, and free skill points
        // TODO Assign experience to level up

        character.setProperty(KILLS_HEADER + "." + KILLED_USERS_KEY, 0);
        character.setProperty(KILLS_HEADER + "." + KILLED_NPCS_KEY, 0);

        character.setProperty(BANK_INVENTORY_HEADER + "." + ITEMS_AMOUNT_KEY, 0);

        // TODO Put initial items
        character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_WEAPON_SLOT_KEY, 0);
        character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_ARMOUR_SLOT_KEY, 0);
        character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_HELMET_SLOT_KEY, 0);
        character.setProperty(INVENTORY_HEADER + "." + EQUIPPED_BOAT_SLOT_KEY, 0);
        character.setProperty(INVENTORY_HEADER + "." + MUNITION_SLOT_KEY, 0);
        character.setProperty(INVENTORY_HEADER + "." + RING_SLOT_KEY, 0);

        character.setProperty(REPUTATION_HEADER + "." + ASSASSIN_POINTS_KEY, 0);
        character.setProperty(REPUTATION_HEADER + "." + BANDIT_POINTS_KEY, 0);
        character.setProperty(REPUTATION_HEADER + "." + BOURGEOIS_POINTS_KEY, 0);
        character.setProperty(REPUTATION_HEADER + "." + THIEF_POINTS_KEY, 0);
        character.setProperty(REPUTATION_HEADER + "." + NOBLE_POINTS_KEY, INITIAL_NOBLE_POINTS);

        // TODO Assign initial spells

        for (byte i = 1; i < MAX_PETS_AMOUNT + 1; i++)
            character.setProperty(PETS_HEADER + "." + String.format(PET_KEY_FORMAT, i), 0);

        character.setProperty(RESEARCH_HEADER + "." + TRAINING_TIME_KEY, 0);

        character.setProperty(GUILD_HEADER + "." + GUILD_INDEX_KEY, 0);
        character.setProperty(GUILD_HEADER + "." + APPLICANT_TO_KEY, 0);

        character.setProperty(CRIMINAL_RECORD_HEADER + "." + RECORDS_AMOUNT_KEY, 0);

        Reputation rep = new ReputationImpl(0, 0, 0, 0, INITIAL_NOBLE_POINTS, false);

        try (Writer writer = new BufferedWriter(new FileWriter(getCharFilePath(name)))) {
            character.write(writer);
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Charfile (full charfile) creation failed!", e);
            throw new DAOException(e);
        }

        // TODO Update this when hp, mana and hit points get updated!
        return new LoggedUser(user, rep, race, gender, archetype.getArchetype(),
                false, false, false, false, false, false, false, 0, 0, 0, 0,
                Character.MAX_THIRSTINESS, 0, Character.MAX_HUNGER, 0, (byte) 1, name, "");
    }

    @Override
    public UserCharacter load(ConnectedUser user, String username) throws DAOException {

        if (username == null || username.isBlank()) throw new DAOException("Username cannot be null or empty");

        INIConfiguration ini = readCharFile(username);

        if (ini == null) {
            LOGGER.debug("Character '{}' not found", username);
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
        Race race = Race.get(Byte.parseByte(IniUtils.getString(ini, INIT_HEADER + "." + RACE_KEY, "0")));
        Gender gender = Gender.get(Byte.parseByte(IniUtils.getString(ini, INIT_HEADER + "." + GENDER_KEY, "0")));
        Archetype archetype = UserArchetype.get(Byte.parseByte(IniUtils.getString(ini, INIT_HEADER + "." + ARCHETYPE_KEY, "0"))).getArchetype();
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

        // TODO Complete description
        String description = "";

        // TODO Validate character
        return new LoggedUser(user, reputation, race, gender, archetype, poisoned, paralyzed, immobilized,
                invisible, mimetized, dumbed, hidden, maxMana, mana, maxHitPoints, hitpoints, maxThirstiness, thirstiness, maxHunger,
                hunger, lvl, username, description);
    }

    @Override
    public boolean exists(String username) {
        return (new File(getCharFilePath(username))).exists();
    }

    String getCharFilePath(String username) {
        return Paths.get(charfilesPath).resolve(username + FILE_EXTENSION).toString();
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
