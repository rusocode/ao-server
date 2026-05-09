package com.ao;

import com.ao.config.IntervalsConfig;
import com.ao.config.ServerConfig;
import com.ao.context.ApplicationContext;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.UserCharacter;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.User;
import com.ao.network.packet.outgoing.UpdateHungerAndThirstPacket;
import com.ao.network.packet.outgoing.UpdateUserStatsPacket;
import com.ao.service.MapService;
import com.ao.service.NpcService;
import com.ao.service.ObjectService;
import com.ao.service.UserService;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Bootstraps the application.
 */

public class Bootstrap {

    public static void main(String[] args) {

        AOServer server;

        try {
            server = Bootstrap.bootstrap();
        } catch (Exception e) {
            Logger.error("Server bootstrapping failed: {}", e.getMessage(), e);
            System.exit(-1);
            return;
        }

        Logger.info("\u001B[1;32mServer ready!\u001B[0m");
        server.run();
    }

    /**
     * Bootstraps the application.
     *
     * @return the application
     */
    private static AOServer bootstrap() throws IOException, DAOException {

        AOServer server = new AOServer();

        long start = System.currentTimeMillis();

        Logger.info("Initializing AO Server...");
        loadApplicationContext(server);
        startTimers(server);
        configureNetworking(server);

        Logger.info("\u001B[1;32mServer initialized in {} ms\u001B[0m", System.currentTimeMillis() - start);

        return server;
    }

    /**
     * Configures networking on the given server.
     *
     * @param server server on which to configure networking
     */
    private static void configureNetworking(AOServer server) throws IOException {
        byte[] addr = {0, 0, 0, 0};
        Logger.info("Initializing server socket configuration...");
        ServerConfig config = ApplicationContext.getInstance(ServerConfig.class);
        InetSocketAddress endpoint = new InetSocketAddress(Inet4Address.getByAddress(addr), config.getServerListeningPort());
        server.setListeningAddr(endpoint);
        server.setBacklog(config.getListeningBacklog());
    }

    /**
     * Starts the game timers on the given server.
     *
     * @param server server on which to start the timers
     */
    private static void startTimers(AOServer server) {

        Logger.info("Starting up game timers...");

        IntervalsConfig intervals = ApplicationContext.getInstance(IntervalsConfig.class);
        UserService userService = ApplicationContext.getInstance(UserService.class);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        server.setScheduler(scheduler);

        // 1. REGENERACIÓN (Vida, Maná, Stamina)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                for (ConnectedUser connectedUser : userService.getConnectedUsers()) {
                    User user = connectedUser.getConnection().getUser();

                    if (user instanceof UserCharacter character && !character.isDead()) {
                        boolean changed = false;

                        /* Los bloques synchronized protegen las secuencias read-modify-write de cada stat. Esto protege entre los
                         * propios game loops, pero la sincronizacion completa requiere que los handlers de Netty tambien
                         * sincronicen sobre el mismo objeto — queda pendiente como trabajo futuro cuando se implementen esos
                         * handlers */
                        synchronized (character) {
                            if (character.getHitPoints() < character.getMaxHitPoints()) {
                                character.addToHitPoints(1);
                                changed = true;
                            }
                            if (character.getMana() < character.getMaxMana()) {
                                character.addToMana(1);
                                changed = true;
                            }
                            if (character.getStamina() < character.getMaxStamina()) {
                                character.setStamina(Math.min(character.getMaxStamina(), character.getStamina() + 5));
                                changed = true;
                            }
                        }

                        if (changed) {
                            connectedUser.getConnection().send(new UpdateUserStatsPacket(character));
                        }
                    }
                }
            } catch (Exception e) {
                Logger.error("Regen loop failed, skipping tick", e);
            }
        }, 0, intervals.getRegeneration().getHp(), TimeUnit.MILLISECONDS);

        // 2. Hambre y Sed
        scheduler.scheduleAtFixedRate(() -> {
            try {
                for (ConnectedUser connectedUser : userService.getConnectedUsers()) {
                    User user = connectedUser.getConnection().getUser();

                    if (user instanceof UserCharacter character && !character.isDead()) {
                        synchronized (character) {
                            if (character.getHunger() > 0)
                                character.addToHunger(-1);
                            if (character.getThirstiness() > 0)
                                character.addToThirstiness(-1);
                        }

                        connectedUser.getConnection().send(new UpdateHungerAndThirstPacket(
                                character.getHunger(), UserCharacter.MAX_HUNGER,
                                character.getThirstiness(), UserCharacter.MAX_THIRSTINESS));
                    }
                }
            } catch (Exception e) {
                Logger.error("Survival loop failed, skipping tick", e);
            }
        }, 0, intervals.getSurvival().getHunger(), TimeUnit.MILLISECONDS);

        // 3. IA DE NPCs (Movimiento y ataque de criaturas)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // TODO: Lógica NPCs
            } catch (Exception e) {
                Logger.error("NPC AI loop failed, skipping tick", e);
            }
        }, 0, intervals.getNpc().getAiTick(), TimeUnit.MILLISECONDS);

        // 4. WORLD SAVE (Guardado automático de personajes)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // TODO: Lógica de guardado masivo
            } catch (Exception e) {
                Logger.error("World save loop failed, skipping tick", e);
            }
        }, 15, 15, TimeUnit.MINUTES);

        // 5. EFECTOS TEMPORALES (Veneno, Parálisis, Invisibilidad)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // TODO: Lógica de limpieza de estados temporales
            } catch (Exception e) {
                Logger.error("Temp effects loop failed, skipping tick", e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

    }

    /**
     * Loads the application context on the given server.
     *
     * @param server the server on which to load the application context
     */
    private static void loadApplicationContext(AOServer server) throws DAOException {

        Logger.info("Loading application context...");

        Logger.info("Loading maps...");
        MapService mapService = ApplicationContext.getInstance(MapService.class); // Sin DI tendria que hardcodear la creacion del objeto -> new MapServiceImpl();
        mapService.loadMaps();

        Logger.info("Loading cities...");
        mapService.loadCities();

        Logger.info("Loading objects...");
        ObjectService objectService = ApplicationContext.getInstance(ObjectService.class);
        objectService.loadObjects();

        Logger.info("Loading npcs...");
        NpcService npcService = ApplicationContext.getInstance(NpcService.class);
        npcService.loadNpcs();
    }

}
