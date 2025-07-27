package com.ao.config.ini;

import com.ao.config.ServerConfig;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.ini4j.Ini;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * General server configuration, backed by a INI file.
 */

public class ServerConfigIni implements ServerConfig {

    private static final String INIT_HEADER = "INIT";

    private static final String BACKLOG_KEY = "Backlog";
    private static final String MAXUSERS_KEY = "MaxUsers";
    private static final String LISTENING_PORT_KEY = "StartPort";
    private static final String VERSION_KEY = "Version";
    private static final String CHARACTER_CREATION_KEY = "PuedeCrearPersonajes";
    private static final String RESTRICTED_TO_ADMINS_KEY = "ServerSoloGMs";

    private static final String HASHES_HEADER = "MD5Hush";
    private static final String HASHES_ACTIVATED_KEY = "Activado";
    private static final String HASHES_AMOUNT_KEY = "MD5Aceptados";
    private static final String HASHES_ACCEPTED_KEY_FORMAT = "Md5Aceptado%d";

    private final Ini config;

    /**
     * Creates a new ServerConfigIni instance.
     */
    @Inject
    public ServerConfigIni(@Named("ServerConfigIni") String serverConfigIni) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(serverConfigIni);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + serverConfigIni + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            config = new Ini(reader);
        } catch (Exception e) {
            System.err.println("Server config loading failed!" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getListeningBacklog() {
        String backlog = config.get(INIT_HEADER, BACKLOG_KEY);
        return backlog == null ? DEFAULT_BACKLOG : Integer.valueOf(backlog);
    }

    @Override
    public int getMaximumConcurrentUsers() {
        return Integer.valueOf(config.get(INIT_HEADER, MAXUSERS_KEY));
    }

    @Override
    public int getServerListeningPort() {
        String listeningPort = config.get(INIT_HEADER, LISTENING_PORT_KEY);
        return listeningPort == null ? DEFAULT_LISTENING_PORT : Integer.valueOf(listeningPort);
    }

    @Override
    public String getVersion() {
        return config.get(INIT_HEADER, VERSION_KEY);
    }

    @Override
    public boolean isCharacterCreationEnabled() {
        return config.get(INIT_HEADER, CHARACTER_CREATION_KEY).equals("1");
    }

    @Override
    public void setCharacterCreationEnabled(boolean enabled) {
        config.put(INIT_HEADER, CHARACTER_CREATION_KEY, enabled ? "1" : "0");
    }

    @Override
    public boolean isRestrictedToAdmins() {
        return config.get(INIT_HEADER, RESTRICTED_TO_ADMINS_KEY).equals("1");
    }

    @Override
    public void setRestrictedToAdmins(boolean restricted) {
        config.put(INIT_HEADER, RESTRICTED_TO_ADMINS_KEY, restricted ? "1" : "0");
    }

}
