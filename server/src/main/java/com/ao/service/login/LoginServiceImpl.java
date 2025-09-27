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
import com.ao.model.map.Position;
import com.ao.model.map.WorldMap;
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
    public static final String INVALID_HOMELAND_ERROR = "El hogar seleccionado no es valido.";
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
    public LoginServiceImpl(AccountDAO accDAO, UserCharacterDAO charDAO, ServerConfig config, UserService userService,
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
    public void connectNewCharacter(ConnectedUser user, String nick, String password, Race race, Gender gender, byte bArchetype,
                                    int head, String mail, byte bHomeland, String clientHash, String version) throws LoginErrorException {

        checkClient(clientHash, version);

        // Validaciones del servidor
        if (!config.isCharacterCreationEnabled()) throw new LoginErrorException(CHARACTER_CREATION_DISABLED_ERROR);
        if (config.isRestrictedToAdmins()) throw new LoginErrorException(ONLY_ADMINS_ERROR);

        // TODO Check to avoid mass characters creation for this IP

        if (user.getAttribute(Attribute.DEXTERITY) == null) throw new LoginErrorException(MUST_THROW_DICES_BEFORE_ERROR);

        UserCharacterBuilder userCharacterBuilder = new UserCharacterBuilder();

        // Validar homeland con protección extra
        if (bHomeland < 1 || bHomeland > 7) {
            System.err.println("ERROR: Homeland ID inválido: " + bHomeland + ". Usando Ullathorpe por defecto.");
            bHomeland = 2; // CITY2 = Ullathorpe
        }

        // Validar homeland
        City homeland = mapService.getCity(bHomeland);
        if (homeland == null) throw new LoginErrorException(INVALID_HOMELAND_ERROR);

        // DEBUG: Mostrar información de la ciudad seleccionada
        System.out.println("DEBUG - Ciudad seleccionada:");
        System.out.println("- bHomeland (ID recibido del cliente): " + bHomeland);
        System.out.println("- homeland obtenido: " + homeland);

        // DEBUG: Mostrar todas las ciudades válidas
        System.out.println("DEBUG - Ciudades válidas:");
        for (byte i = 1; i <= 7; i++) {
            City city = mapService.getCity(i);
            System.out.println("- CITY" + i + ": " + city);
        }

        // Validar archetype
        UserArchetype archetype;
        try {
            archetype = UserArchetype.get(bArchetype); // Solo archetype sigue siendo byte
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LoginErrorException(INVALID_ARCHETYPE_ERROR);
        }

        if (!characterBodyService.isValidHead(head, race, gender)) throw new LoginErrorException(INVALID_HEAD_ERROR);

        // Get default body
        int body = characterBodyService.getBody(race, gender); // Usar directamente
        if (body == 0) throw new LoginErrorException(INVALID_BODY_ERROR);

        // Build character
        try {
            userCharacterBuilder.withName(nick)
                    .withEmail(mail)
                    .withGender(gender) // Usar directamente
                    .withCity(homeland)
                    .withRace(race) // Usar directamente
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

        // Create character file and load complete character
        UserCharacter chara;
        try {
            // Crear el archivo del personaje
            charDAO.create(user, nick, race, gender,
                    archetype, head, homeland,
                    user.getAttribute(Attribute.STRENGTH),
                    user.getAttribute(Attribute.DEXTERITY),
                    user.getAttribute(Attribute.INTELLIGENCE),
                    user.getAttribute(Attribute.CHARISMA),
                    user.getAttribute(Attribute.CONSTITUTION),
                    initialAvailableSkills, body);

            // Cargar el personaje completo (con inventario, spells, etc.)
            chara = charDAO.load(user, nick);
            if (chara == null) {
                accDAO.delete(nick);
                throw new LoginErrorException("Error al cargar el personaje recién creado.");
            }
        } catch (DAOException e) {
            accDAO.delete(nick);
            throw new LoginErrorException(e.getMessage());
        }

        // Associate character with account and user
        account.addCharacter(nick);
        user.setAccount(account);

        // Put it in the world!
        try {
            // Send initial state to client (adaptado para nuevos personajes)
            sendInitialStateForNewCharacter(user, chara);

            // Upgrade the user to a logged user
            user.getConnection().changeUser((User) chara);

            // Obtener la posición desde el personaje (el DAO ya la estableció)
            Position characterPosition = chara.getPosition();

            // DEBUG: Mostrar información para diagnosticar el problema
            System.out.println("DEBUG - Información de posición:");
            System.out.println("- chara.getPosition(): " + characterPosition);
            System.out.println("- homeland.map(): " + homeland.map());
            System.out.println("- homeland.x(): " + homeland.x());
            System.out.println("- homeland.y(): " + homeland.y());

            if (characterPosition == null) {
                // Fallback: crear posición basada en homeland si por alguna razón no está establecida
                characterPosition = new Position(homeland.x(), homeland.y(), homeland.map());
                System.out.println("- Usando fallback position: " + characterPosition);
            }

            // VALIDACIÓN: Verificar que el mapa sea válido (1-300)
            if (characterPosition.getMap() < 1 || characterPosition.getMap() > 300) {
                System.err.println("ERROR: Mapa inválido " + characterPosition.getMap() +
                        ". Usando Ullathorpe como ciudad por defecto.");
                // Usar ciudad por defecto: CITY2 = Ullathorpe (map=1, x=58, y=45)
                characterPosition = new Position((byte) 58, (byte) 45, 1);
            }

            // Para personajes nuevos, usar directamente el mapa sin AreaService ya que el AreaInfo no está inicializado para personajes recién creados
            WorldMap spawnMap = mapService.getMap(characterPosition.getMap());
            if (spawnMap == null) {
                accDAO.delete(nick);
                throw new LoginErrorException("Mapa de origen no válido.");
            }

            Connection connection = user.getConnection();

            // 1. Decirle al cliente que cargue el mapa correcto
            System.out.println("DEBUG - Enviando ChangeMapPacket:");
            System.out.println("- spawnMap.getId(): " + spawnMap.getId());
            System.out.println("- spawnMap.getVersion(): " + spawnMap.getVersion());
            System.out.println("- spawnMap info: " + spawnMap);

            // TEMPORAL: Comentar ChangeMapPacket hasta resolver el problema del ID incorrecto
            connection.send(new ChangeMapPacket(spawnMap));
            System.out.println("DEBUG - ChangeMapPacket comentado temporalmente para evitar crash");

            // ALTERNATIVA: Usar mapa 1 (Ullathorpe) que sabemos que es seguro
            WorldMap safeMap = mapService.getMap(1);
            if (safeMap != null) {
                System.out.println("DEBUG - Alternativa con mapa seguro:");
                System.out.println("- safeMap.getId(): " + safeMap.getId());
                System.out.println("- safeMap.getVersion(): " + safeMap.getVersion());
                // connection.send(new ChangeMapPacket(safeMap)); // También comentado por ahora
            }

            // 2. Crear el personaje visual en el cliente
            connection.send(new CharacterCreatePacket(chara));

            // Verificar si la posición está disponible, si no, buscar una libre cerca
            if (!spawnMap.isTileAvailable(characterPosition.getX(), characterPosition.getY(), true, false, true, true)) {
                Position freePosition = findFreePosition(spawnMap, characterPosition, 3);
                if (freePosition != null) {
                    characterPosition = freePosition;
                } else {
                    accDAO.delete(nick);
                    throw new LoginErrorException("No hay posiciones disponibles en la ciudad de origen.");
                }
            }

            // Colocar el personaje directamente en el mapa sin usar AreaService para evitar el problema con AreaInfo null en personajes nuevos
            spawnMap.putCharacterAtPos(chara, characterPosition.getX(), characterPosition.getY());

            // Marcar el usuario como conectado
            userService.logIn(user);

        } catch (LoginErrorException e) {
            // Re-lanzar errores de login
            throw e;
        } catch (Exception e) {
            // Si algo falla, limpiar la cuenta creada
            accDAO.delete(nick);
            throw new LoginErrorException("Error al colocar el personaje en el mundo: " + e.getMessage());
        }
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

    private void sendInitialStateForNewCharacter(ConnectedUser user, UserCharacter character) {
        Connection connection = user.getConnection();

        // inventory - manejar el caso donde el inventario es null para personajes nuevos
        if (character.getInventory() != null) {
            int invCapacity = character.getInventory().getCapacity();
            for (int i = 0; i < invCapacity; i++)
                connection.send(new ChangeInventorySlotPacket(character, (byte) i));
        }
        // Si el inventario es null, simplemente no enviamos nada (inventario vacío)

        // spellbook - manejar el caso donde los spells son null para personajes nuevos
        Spell[] spellbook = character.getSpells();
        if (spellbook != null) {
            int spells = spellbook.length;
            for (int i = 0; i < spells; i++)
                connection.send(new ChangeSpellSlotPacket(spellbook[i], (byte) i));
        }
        // Si los spells son null, simplemente no enviamos nada (spellbook vacío)

        if (character.isParalyzed()) connection.send(new ParalizedPacket());

        // TODO Check if map pos is valid, or disconnect user if invalid map
        // TODO If position taken, find a suitable position
        // TODO Set sailing and use boat if in water (and has a boat)
        // TODO Send user index in server?
    }

    private Position findFreePosition(WorldMap map, Position centerPos, int radius) {
        // Busca en un patron en espiral desde el centro
        for (int distance = 1; distance <= radius; distance++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dy = -distance; dy <= distance; dy++) {
                    // Solo verifica el borde del cuadrado actual
                    if (Math.abs(dx) != distance && Math.abs(dy) != distance) continue;

                    byte newX = (byte) (centerPos.getX() + dx);
                    byte newY = (byte) (centerPos.getY() + dy);

                    // Verifica limites del mapa
                    if (newX < WorldMap.MIN_X || newX > WorldMap.MAX_X || newY < WorldMap.MIN_Y || newY > WorldMap.MAX_Y)
                        continue;

                    // Verifica si la posicion esta disponible
                    if (map.isTileAvailable(newX, newY, true, false, true, true))
                        return new Position(newX, newY, centerPos.getMap());

                }
            }
        }
        return null; // No se encontro posicion libre
    }

    private void sendInitialState(ConnectedUser user, UserCharacter character) {
        Connection connection = user.getConnection();


        // inventory
        int invCapacity = character.getInventory().getCapacity();
        for (int i = 0; i < invCapacity; i++)
            connection.send(new ChangeInventorySlotPacket(character, (byte) i));

        // spellbook
        Spell[] spellbook = character.getSpells();
        int spells = spellbook.length;
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
    private void checkClient(String hash, String version) throws LoginErrorException {

        if (!currentClientVersion.equals(version))
            throw new LoginErrorException(String.format(CLIENT_OUT_OF_DATE_ERROR_FORMAT, currentClientVersion));

        if (clientHashes == null)
            // FIXME Why is it loaded here?
            clientHashes = ApplicationContext.getInstance(SecurityManager.class).getValidClientHashes();

        if (clientHashes.length < 1) return;

        for (String validHash : clientHashes)
            if (hash.equals(validHash)) return;

        throw new LoginErrorException(CORRUPTED_CLIENT_ERROR);
    }

}
