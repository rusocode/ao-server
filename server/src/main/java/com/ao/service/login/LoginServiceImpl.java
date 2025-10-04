package com.ao.service.login;

import com.ao.action.ActionExecutor;
import com.ao.action.worldmap.MakeUserCharAction;
import com.ao.config.ServerConfig;
import com.ao.context.ApplicationContext;
import com.ao.data.dao.AccountDAO;
import com.ao.data.dao.UserCharacterDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.builder.UserCharacterBuilder;
import com.ao.model.character.*;
import com.ao.model.character.Character;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.map.City;
import com.ao.model.map.Position;
import com.ao.model.map.WorldMap;
import com.ao.model.user.Account;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.User;
import com.ao.network.Connection;
import com.ao.network.packet.outgoing.*;
import com.ao.security.SecurityManager;
import com.ao.service.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the login service. <b>An account is the same as a character.</b>
 */

public class LoginServiceImpl implements LoginService {

    public static final String DAO_ERROR = "Ocurrio un error, intenta de nuevo.";
    public static final String CHARACTER_NOT_FOUND_ERROR = "El personaje no existe.";
    public static final String CLIENT_OUT_OF_DATE_ERROR_FORMAT = "Esta version del juego es obsoleta, la version correcta es %s. La misma se encuentra disponible en http://www.argentumonline.com.ar/.";
    public static final String CORRUPTED_CLIENT_ERROR = "El cliente esta dañado, por favor descarguelo nuevamente desde http://www.argentumonline.com.ar/";
    public static final String BANNED_CHARACTER_ERROR = "Se te ha prohibido la entrada a Argentum debido a tu mal comportamiento. Puedes consultar el reglamento y el sistema de soporte desde www.argentumonline.com.ar";
    public static final String INCORRECT_PASSWORD_ERROR = "Contraseña incorrecta.";
    public static final String CHARACTER_CREATION_DISABLED_ERROR = "La creacion de personajes en este servidor se ha deshabilitado.";
    public static final String ONLY_ADMINS_ERROR = "Servidor restringido a administradores. Consulte la pagina oficial o el foro oficial para mas informacion.";
    public static final String MUST_THROW_DICES_BEFORE_ERROR = "Debe tirar los dados antes de poder crear un personaje.";
    public static final String INVALID_RACE_ERROR = "La raza seleccionada no es valida.";
    public static final String INVALID_GENDER_ERROR = "El género seleccionado no es valido.";
    public static final String INVALID_ARCHETYPE_ERROR = "La clase seleccionada no es valida.";
    public static final String INVALID_SKILLS_POINTS_ERROR = "Los skills asignados no son validos.";
    public static final String ACCOUNT_NAME_TAKEN_ERROR = "Ya existe el personaje.";
    public static final String CHARACTER_IS_LOGGED_IN = "El personaje esta conectado.";
    public static final String INVALID_HEAD_ERROR = "La cabeza seleccionada no es valida.";
    public static final String INVALID_BODY_ERROR = "No existe un cuerpo para la combinacion seleccionada.";
    public static final String INVALID_CITY_ERROR = "La ciudad seleccionada no es valida.";
    private final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final AccountDAO accDAO;
    private final UserCharacterDAO charDAO;
    private final ServerConfig config;
    private final int initialAvailableSkills;
    private final UserService userService;
    private final CharacterBodyService characterBodyService;
    private final MapService mapService;
    private final ActionExecutor<MapService> mapActionExecutor;
    private final CharacterIndexManager charIndexManager;
    private String[] clientHashes;
    private String currentClientVersion;

    @Inject
    public LoginServiceImpl(AccountDAO accDAO, UserCharacterDAO charDAO, ServerConfig config, UserService userService,
                            CharacterBodyService characterBodyService, MapService mapService, ActionExecutor<MapService> mapActionExecutor,
                            @Named("initialAvailableSkills") int initialAvailableSkills) {
        this.accDAO = accDAO;
        this.charDAO = charDAO;
        this.config = config;
        this.initialAvailableSkills = initialAvailableSkills;
        this.userService = userService;
        this.characterBodyService = characterBodyService;
        this.mapService = mapService;
        this.mapActionExecutor = mapActionExecutor;
        currentClientVersion = config.getVersion();
        charIndexManager = new CharacterIndexManager();

    }

    @Override
    public void connectNewCharacter(ConnectedUser user, String nick, String password, int raceId, int genderId, byte archetypeId,
                                    int head, String mail, byte cityId, String clientHash, String version) throws LoginErrorException {

        checkClient(clientHash, version);

        // Validaciones del servidor
        if (!config.isCharacterCreationEnabled()) throw new LoginErrorException(CHARACTER_CREATION_DISABLED_ERROR);
        if (config.isRestrictedToAdmins()) throw new LoginErrorException(ONLY_ADMINS_ERROR);

        // TODO Check to avoid mass characters creation for this IP

        if (user.getAttribute(Attribute.DEXTERITY) == null) throw new LoginErrorException(MUST_THROW_DICES_BEFORE_ERROR);

        UserCharacterBuilder userCharacterBuilder = new UserCharacterBuilder();

        City city = mapService.getCity(cityId);
        if (city == null) throw new LoginErrorException(INVALID_CITY_ERROR);

        UserArchetype archetype = UserArchetype.findById(archetypeId);
        if (archetype == null) {
            LOGGER.warn("Invalid archetype ID: {}", archetypeId);
            throw new LoginErrorException("Invalid archetype ID: " + archetypeId);
        }

        Race race = Race.findById(raceId);
        if (race == null) {
            LOGGER.warn("Invalid race ID: {}", raceId);
            throw new LoginErrorException("Invalid race ID: " + raceId);
        }

        Gender gender = Gender.findById(genderId);
        if (gender == null) {
            LOGGER.warn("Invalid gender ID: {}", genderId);
            throw new LoginErrorException("Invalid gender ID: " + genderId);
        }

        if (!characterBodyService.isValidHead(head, race, gender)) throw new LoginErrorException(INVALID_HEAD_ERROR);

        // Get default body
        int body = characterBodyService.getBody(race, gender);
        if (body == 0) throw new LoginErrorException(INVALID_BODY_ERROR);

        // Build character
        try {
            userCharacterBuilder.withName(nick)
                    .withEmail(mail)
                    .withGender(gender)
                    .withCity(city)
                    .withRace(race)
                    .withArchetype(archetype)
                    .withHead(head)
                    .withBody(body);
        } catch (Exception e) {
            throw new LoginErrorException(e.getMessage());
        }

        // Create an account
        Account account;
        try {
            account = accDAO.create(nick, password, mail);
        } catch (NameAlreadyTakenException e) {
            throw new LoginErrorException(ACCOUNT_NAME_TAKEN_ERROR);
        } catch (DAOException e) {
            accDAO.delete(nick);
            throw new LoginErrorException(DAO_ERROR);
        }

        // Once we have the account, let's create the character itself
        UserCharacter character;
        try {
            character = charDAO.create(user, nick, race, gender, archetype, head, city,
                    user.getAttribute(Attribute.STRENGTH),
                    user.getAttribute(Attribute.DEXTERITY),
                    user.getAttribute(Attribute.INTELLIGENCE),
                    user.getAttribute(Attribute.CHARISMA),
                    user.getAttribute(Attribute.CONSTITUTION),
                    initialAvailableSkills, body);

            int charIndex = charIndexManager.assignCharIndex();
            character.setCharIndex(charIndex);
            LOGGER.info("Assigned CharIndex {} to character '{}'", charIndex, character.getName());

        } catch (DAOException e) {
            accDAO.delete(nick);
            throw new LoginErrorException(e.getMessage());
        }

        // Associate character with account and user
        account.addCharacter(nick);
        user.setAccount(account);

        // 1. Crea la posicion inicial del personaje basada en la ciudad
        Position initialPosition = new Position(city.x(), city.y(), city.map());
        character.setPosition(initialPosition);

        // 2. Envia estado inicial al cliente (inventario, spells, etc.)
        sendInitialState(user, character);

        // 3. Actualiza la conexion para que use el character como User (esto convierte el ConnectedUser en LoggedUser)
        user.getConnection().changeUser((User) character);

        // 4. Marca al usuario como logueado en el servicio (equivalente a UserLogged = True en VB6)
        userService.logIn(user);

        // Debugging
        LOGGER.info("New character '{}' successfully placed in the world at {}", character.getName(), character.getPosition().toString());

    }

    @Override
    public void connectExistingCharacter(ConnectedUser user, String username, String password, String version, String clientHash) throws LoginErrorException {

        checkClient(clientHash, version);

        // TODO Check for max users limit?

        Account account;

        try {
            account = accDAO.get(username);
        } catch (DAOException e) {
            throw new LoginErrorException(DAO_ERROR);
        }

        // TODO Is the ip in use?

        if (account == null) throw new LoginErrorException(CHARACTER_NOT_FOUND_ERROR);
        if (!account.authenticate(password)) throw new LoginErrorException(INCORRECT_PASSWORD_ERROR);
        if (account.isBanned()) throw new LoginErrorException(BANNED_CHARACTER_ERROR);
        if (userService.isLoggedIn(user)) throw new LoginErrorException(CHARACTER_IS_LOGGED_IN);

        // TODO Add ip to connected ips

        // TODO Do something with the account!!!

        if (!account.hasCharacter(username)) throw new LoginErrorException(CHARACTER_NOT_FOUND_ERROR);

        UserCharacter character;
        try {
            character = charDAO.load(user, username);
        } catch (DAOException e) {
            throw new LoginErrorException(DAO_ERROR);
        }

        // TODO If user is a GM, log it to admin's log with it's ip.

        if (config.isRestrictedToAdmins() && !character.getPrivileges().isGameMaster())
            throw new LoginErrorException(ONLY_ADMINS_ERROR);

        // TODO tell the client it's current resuscitation lock state

        // Send initial state to client!
        sendInitialState(user, character);

        // Upgrade the user to a logged user
        user.getConnection().changeUser((User) character);
        user.setAccount(account);

        userService.logIn(user);
    }

    /**
     * Sets the current client's version.
     *
     * @param version new client version
     */
    public void setCurrentClientVersion(String version) {
        // TODO Update the config!
        currentClientVersion = version;
    }

    private void sendInitialState(ConnectedUser user, UserCharacter character) {
        Connection connection = user.getConnection();

        // inventory
        /* int invCapacity = character.getInventory().getCapacity();
        for (int i = 0; i < invCapacity; i++)
            connection.send(new ChangeInventorySlotPacket(character, (byte) i)); */

        // spellbook
        /* Spell[] spells = character.getSpells();
        for (int i = 0; i < spells.length; i++)
            connection.send(new ChangeSpellSlotPacket(spells[i], (byte) i)); */

        if (character.isParalyzed()) connection.send(new ParalyzedPacket());

        // TODO Check if map position is valid, or disconnect user if invalid map

        // TODO If position taken, find a suitable position

        // TODO Set sailing and use boat if in water (and has a boat)

        // TODO Send user index in server? The client doesn't use it at all, and we have no user indexes in this server...

        WorldMap map = mapService.getMap(character.getPosition().getMap());
        connection.send(new ChangeMapPacket(map));

        // TODO Tell client to play midi

        // TODO Initialize chat color

        // TODO We want to take this as fat down as possible in this method...
        // Add the user to the map, notify everyone on area, and let the player know it's surroundings
        MakeUserCharAction action = new MakeUserCharAction(character, character.getPosition());
        action.setExecutor(mapActionExecutor);
        action.dispatch();

        // TODO Other (continue from TCP.bas line 1280-1288)

        // TODO these lines can be moved further up, but I want to be sure other TODOs don't mess with that...
        connection.send(new UpdateUserStatsPacket(character));
        connection.send(new UpdateHungerAndThirstPacket(character.getHunger(), Character.MAX_HUNGER, character.getThirstiness(), Character.MAX_THIRSTINESS));
        connection.send(new UpdateStrengthAndDexterityPacket(character.getStrength(), character.getDexterity()));

        // TODO Other (continue from TCP.bas line 1296)
    }

    /**
     * Checks if the given clientHash matches any of the valid hashes and if the given version is up to date.
     *
     * @param clientHash clientHash to check
     * @param version    client's version
     */
    private void checkClient(String clientHash, String version) throws LoginErrorException {

        if (!currentClientVersion.equals(version))
            throw new LoginErrorException(String.format(CLIENT_OUT_OF_DATE_ERROR_FORMAT, currentClientVersion));

        if (clientHashes == null)
            // FIXME Why is it loaded here?
            clientHashes = ApplicationContext.getInstance(SecurityManager.class).getValidClientHashes();

        if (clientHashes.length < 1) return;

        for (String validHash : clientHashes)
            if (clientHash.equals(validHash)) return;

        throw new LoginErrorException(CORRUPTED_CLIENT_ERROR);
    }

}
