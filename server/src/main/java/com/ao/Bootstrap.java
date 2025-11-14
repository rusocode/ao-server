package com.ao;

import com.ao.config.ServerConfig;
import com.ao.context.ApplicationContext;
import com.ao.data.dao.exception.DAOException;
import com.ao.service.MapService;
import com.ao.service.NpcService;
import com.ao.service.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.Timer;

/**
 * Bootstraps the application.
 */

public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {

        AOServer server;

        try {
            server = Bootstrap.bootstrap();
        } catch (Exception e) {
            LOGGER.error("Server bootstrapping failed: {}", e.getMessage(), e);
            System.exit(-1);
            return;
        }

        LOGGER.info("\u001B[1;32mServer ready!\u001B[0m");
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

        LOGGER.info("Initializing AO Server...");
        loadApplicationContext(server);
        startTimers(server);
        configureNetworking(server);

        LOGGER.info("\u001B[1;32mServer initialized in {} ms\u001B[0m", System.currentTimeMillis() - start);

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
     * @param server the server on which to load the application context
     */
    private static void loadApplicationContext(AOServer server) throws DAOException {

        LOGGER.info("Loading application context...");

        LOGGER.info("Loading maps...");
        MapService mapService = ApplicationContext.getInstance(MapService.class); // Sin DI tendria que hardcodear la creacion del objeto -> new MapServiceImpl();
        mapService.loadMaps();

        LOGGER.info("Loading cities...");
        mapService.loadCities();

        LOGGER.info("Loading objects...");
        ObjectService objectService = ApplicationContext.getInstance(ObjectService.class);
        objectService.loadObjects();

        LOGGER.info("Loading npcs...");
        NpcService npcService = ApplicationContext.getInstance(NpcService.class);
        npcService.loadNpcs();

        // TODO Load other services and classes from application context
    }

}
