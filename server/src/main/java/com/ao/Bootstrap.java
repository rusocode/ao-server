package com.ao;

import com.ao.config.ServerConfig;
import com.ao.context.ApplicationContext;
import com.ao.data.dao.exception.DAOException;
import com.ao.service.MapService;
import com.ao.service.NPCService;
import com.ao.service.WorldObjectService;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.logging.Logger;

/**
 * Bootstraps the application.
 */

public class Bootstrap {

    private static final Logger LOGGER = Logger.getLogger(Bootstrap.class.getName());

    public static void main(String[] args) {

        final AOServer server;

        try {
            server = Bootstrap.bootstrap();
        } catch (final Exception e) {
            LOGGER.severe("Server bootstraping failed!" + e.getMessage());
            System.exit(-1);
            return;
        }

        LOGGER.info("AO Server ready. Enjoy it!");
        server.run();
    }

    /**
     * Bootstraps the application.
     *
     * @return the application
     */
    private static AOServer bootstrap() throws IOException, DAOException {

        AOServer server = new AOServer();

        long timeMillis = System.currentTimeMillis();

        LOGGER.info("Initializing AO Server...");
        loadApplicationContext(server);
        startTimers(server);
        configureNetworking(server);

        LOGGER.info("AO Server initialized. Took " + (System.currentTimeMillis() - timeMillis) + "ms.");

        return server;
    }

    /**
     * Configures networking on the given server.
     *
     * @param server server on which to configure networking
     */
    private static void configureNetworking(AOServer server) throws IOException {
        byte[] addr = {0, 0, 0, 0};

        LOGGER.info("Initializing server socket configuration...");
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

        LOGGER.info("Starting up game timers...");

        Timer timer = new Timer(true);

        // TODO get task for each timer class (use the app context) and the interval for execution
    }

    /**
     * Loads the application context on the given server.
     *
     * @param server The server on which to load the application context.
     */
    private static void loadApplicationContext(AOServer server) throws DAOException {

        LOGGER.info("Loading application context...");

        LOGGER.info("Loading maps...");
        MapService mapService = ApplicationContext.getInstance(MapService.class);
        mapService.loadMaps();

        LOGGER.info("Loading cities...");
        mapService.loadCities();

        LOGGER.info("Loading world objects...");
        WorldObjectService objectService = ApplicationContext.getInstance(WorldObjectService.class);
        objectService.loadObjects();

        LOGGER.info("Loading NPCs...");
        NPCService npcService = ApplicationContext.getInstance(NPCService.class);
        npcService.loadNPCs();

        // TODO Load other services and classes from application context
    }

}
