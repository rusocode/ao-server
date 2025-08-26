package com.ao.service.login;

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
import com.ao.model.spell.Spell;
import com.ao.model.user.Account;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.User;
import com.ao.network.Connection;
import com.ao.network.packet.outgoing.*;
import com.ao.security.SecurityManager;
import com.ao.service.CharacterBodyService;
import com.ao.service.LoginService;
import com.ao.service.MapService;
import com.ao.service.UserService;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Default implementation of the login service. An account is the exact same as a character.
 */

public class LoginServiceImpl implements LoginService {

    public static final String DAO_ERROR = "Ocurrió un error, intentá de nuevo.";
    public static final String CHARACTER_NOT_FOUND_ERROR = "El personaje no existe.";
    public static final String CLIENT_OUT_OF_DATE_ERROR_FORMAT = "Esta versión del juego es obsoleta, la versión correcta es %s. La misma se encuentra disponible en http://www.argentumonline.com.ar/.";
    public static final String CORRUPTED_CLIENT_ERROR = "El cliente está dañado, por favor descárguelo nuevamente desde http://www.argentumonline.com.ar/";
    public static final String BANNED_CHARACTER_ERROR = "Se te ha prohibido la entrada a Argentum debido a tu mal comportamiento. Puedes consultar el reglamento y el sistema de soporte desde www.argentumonline.com.ar";
    public static final String INCORRECT_PASSWORD_ERROR = "Contraseña incorrecta.";
    public static final String CHARACTER_CREATION_DISABLED_ERROR = "La creación de personajes en este servidor se ha deshabilitado.";
    public static final String ONLY_ADMINS_ERROR = "Servidor restringido a administradores. Consulte la página oficial o el foro oficial para más información.";
    public static final String MUST_THROW_DICES_BEFORE_ERROR = "Debe tirar los dados antes de poder crear un personaje.";
    public static final String INVALID_RACE_ERROR = "La raza seleccionada no es válida.";
    public static final String INVALID_GENDER_ERROR = "El género seleccionado no es válido.";
    public static final String INVALID_ARCHETYPE_ERROR = "La clase seleccionada no es válida.";
    public static final String INVALID_SKILLS_POINTS_ERROR = "Los skills asignados no son válidos.";
    public static final String ACCOUNT_NAME_TAKEN_ERROR = "Ya existe el personaje.";
    public static final String CHARACTER_IS_LOGGED_IN = "El personaje está conectado.";
    public static final String INVALID_HEAD_ERROR = "La cabeza seleccionada no es válida.";
    public static final String INVALID_BODY_ERROR = "No existe un cuerpo para la combinación seleccionada.";
    public static final String INVALID_HOMELAND_ERROR = "El hogar seleccionado no es válido.";
    private final AccountDAO accDAO;
    private final UserCharacterDAO charDAO;
    private final ServerConfig config;
    private final int initialAvailableSkills;
    private final UserService userService;
    private final CharacterBodyService characterBodyService;
    private final MapService mapService;
    private String[] clientHashes;
    private String currentClientVersion;


    @Inject
    public LoginServiceImpl(AccountDAO accDAO, UserCharacterDAO charDAO,
                            ServerConfig config, UserService userService,
                            CharacterBodyService characterBodyService, MapService mapService,
                            @Named("initialAvailableSkills") int initialAvailableSkills) {
        this.accDAO = accDAO;
        this.charDAO = charDAO;
        this.config = config;
        this.initialAvailableSkills = initialAvailableSkills;
        this.userService = userService;
        this.characterBodyService = characterBodyService;
        this.mapService = mapService;
        currentClientVersion = config.getVersion();
    }

    @Override
    public void connectNewCharacter(ConnectedUser user, String username, String password, byte bRace, byte bGender, byte bArchetype,
                                    int head, String mail, byte bHomeland, String clientHash, String version) throws LoginErrorException {

        checkClient(clientHash, version);

        if (!config.isCharacterCreationEnabled()) throw new LoginErrorException(CHARACTER_CREATION_DISABLED_ERROR);
        if (config.isRestrictedToAdmins()) throw new LoginErrorException(ONLY_ADMINS_ERROR);

        // TODO Check to avoid mass characters creation for this IP

        if (user.getAttribute(Attribute.DEXTERITY) == null) throw new LoginErrorException(MUST_THROW_DICES_BEFORE_ERROR);

        UserCharacterBuilder userCharacterBuilder = new UserCharacterBuilder();

        Race race;
        try {
            race = Race.get(bRace);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LoginErrorException(INVALID_RACE_ERROR);
        }

        Gender gender;
        try {
            gender = Gender.get(bGender);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LoginErrorException(INVALID_GENDER_ERROR);
        }

        City homeland = mapService.getCity(bHomeland);
        if (homeland == null) throw new LoginErrorException(INVALID_HOMELAND_ERROR);

        UserArchetype archetype;
        try {
            archetype = UserArchetype.get(bArchetype);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LoginErrorException(INVALID_ARCHETYPE_ERROR);
        }

        if (!characterBodyService.isValidHead(head, race, gender)) throw new LoginErrorException(INVALID_HEAD_ERROR);

        // Get default body
        int body = characterBodyService.getBody(race, gender);

        if (body == 0) throw new LoginErrorException(INVALID_BODY_ERROR);

        try {
            userCharacterBuilder.withName(username).withEmail(mail).withGender(gender).withCity(homeland).withRace(race).withArchetype(archetype).withHead(head).withBody(body);
        } catch (Exception e) {
            throw new LoginErrorException(e.getMessage());
        }

        // First, we have to create the new account.
        Account account;

        try {
            account = accDAO.create(username, password, mail);
        } catch (NameAlreadyTakenException e) {
            throw new LoginErrorException(ACCOUNT_NAME_TAKEN_ERROR);
        } catch (DAOException e) {
            accDAO.delete(username);
            throw new LoginErrorException(DAO_ERROR);
        }

        // Once we have the account, let's create the character itself!
        try {
            UserCharacter chara = charDAO.create(user, username, race, gender,
                    archetype, head, homeland,
                    user.getAttribute(Attribute.STRENGTH),
                    user.getAttribute(Attribute.DEXTERITY),
                    user.getAttribute(Attribute.INTELLIGENCE),
                    user.getAttribute(Attribute.CHARISMA),
                    user.getAttribute(Attribute.CONSTITUTION),
                    initialAvailableSkills, body);
        } catch (DAOException e) {
            accDAO.delete(username);
            throw new LoginErrorException(e.getMessage());
        }

        // Everything is okay, associate the character with the account and the account with the user
        account.addCharacter(username);
        user.setAccount(account);

        // TODO Put it in the world!

    }

    @Override
    public void connectExistingCharacter(final ConnectedUser user, final String username,
                                         final String password, final String version, final String clientHash)
            throws LoginErrorException {

        checkClient(clientHash, version);

        // TODO Check for max users limit?

        final Account acc;

        try {
            acc = accDAO.retrieve(username);
        } catch (final DAOException e) {
            throw new LoginErrorException(DAO_ERROR);
        }

        // TODO Is the ip in use?

        if (acc == null) throw new LoginErrorException(CHARACTER_NOT_FOUND_ERROR);
        if (!acc.authenticate(password)) throw new LoginErrorException(INCORRECT_PASSWORD_ERROR);
        if (acc.isBanned()) throw new LoginErrorException(BANNED_CHARACTER_ERROR);
        if (userService.isLoggedIn(user)) throw new LoginErrorException(CHARACTER_IS_LOGGED_IN);

        // TODO Add ip to connected ips

        // TODO Do something with the account!!!

        if (!acc.hasCharacter(username)) throw new LoginErrorException(CHARACTER_NOT_FOUND_ERROR);

        final UserCharacter character;
        try {
            character = charDAO.load(user, username);
        } catch (final DAOException e) {
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
        user.setAccount(acc);

        userService.logIn(user);
    }

    /**
     * Sets the current client's version.
     *
     * @param version new client version
     */
    public void setCurrentClientVersion(final String version) {
        // TODO Update the config!
        currentClientVersion = version;
    }

    private void sendInitialState(final ConnectedUser user, final UserCharacter character) {
        final Connection connection = user.getConnection();

        // inventory
        final int invCapacity = character.getInventory().getCapacity();
        for (int i = 0; i < invCapacity; i++)
            connection.send(new ChangeInventorySlotPacket(character, (byte) i));

        // spellbook
        final Spell[] spellbook = character.getSpells();
        final int spells = spellbook.length;
        for (int i = 0; i < spells; i++)
            connection.send(new ChangeSpellSlotPacket(spellbook[i], (byte) i));

        if (character.isParalyzed()) connection.send(new ParalizedPacket());

        // TODO Check if map pos is valid, or disconnect user if invalid map

        // TODO If position taken, find a suitable position

        // TODO Set sailing and use boat if in water (and has a boat)

        // TODO Send user index in server? The client doesn't use it at all, and we have no user indexes in this server...

        // TODO Tell client to load map

        // TODO Tell client to play midi

        // TODO Initialize chat color

        // TODO We want to take this as fat down as possible in this method...
        // Add the user to the map, notify everyone on area, and let the player know it's surroundings
        new MakeUserCharAction(character, character.getPosition()).dispatch();

        // TODO Other (continue from TCP.bas line 1280-1288)

        // TODO these lines can be moved further up, but I want to be sure other TODOs don't mess with that...
        connection.send(new UpdateUserStatsPacket(character));
        connection.send(new UpdateHungerAndThirstPacket(character.getHunger(), Character.MAX_HUNGER, character.getThirstiness(), Character.MAX_THIRSTINESS));
        connection.send(new UpdateStrengthAndDexterityPacket(character.getStrength(), character.getDexterity()));

        // TODO Other (continue from TCP.bas line 1296)
    }

    /**
     * Checks if the given hash matches any of the valid hashes and if the given version is up to date.
     *
     * @param hash    hash to check
     * @param version client's version
     */
    private void checkClient(final String hash, final String version)
            throws LoginErrorException {

        if (!currentClientVersion.equals(version))
            throw new LoginErrorException(String.format(CLIENT_OUT_OF_DATE_ERROR_FORMAT, currentClientVersion));

        if (clientHashes == null)
            // FIXME Why is it loaded here?
            clientHashes = ApplicationContext.getInstance(SecurityManager.class).getValidClientHashes();

        if (clientHashes.length < 1) return;

        for (final String validHash : clientHashes)
            if (hash.equals(validHash)) return;

        throw new LoginErrorException(CORRUPTED_CLIENT_ERROR);
    }

}
