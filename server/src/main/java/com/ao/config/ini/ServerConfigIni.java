package com.ao.config.ini;

import com.ao.config.ServerConfig;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * General server configuration, backed by a INI file.
 */

public class ServerConfigIni implements ServerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfigIni.class);

    private static final String INIT_HEADER = "INIT";

    /** INI file keys. */
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

    private INIConfiguration ini;

    /**
     * The value of {@code ServerConfigIni} is injected using Guice's {@code @Inject} annotation from a binding defined in
     * configuration module {@code BootstrapModule}.
     * <p>
     * We can use the optional {@code @Named} annotation as a qualifier to implement targeted injection based on the name.
     */
    @Inject
    public ServerConfigIni(@Named("ServerConfigIni") String serverConfigIni) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(serverConfigIni);
        if (inputStream == null)
            throw new IllegalArgumentException("The file '" + serverConfigIni + "' was not found in the classpath!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ini = new INIConfiguration();
            ini.read(reader);
            LOGGER.info("Server configuration loaded successfully!");
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error loading server configuration!", e);
            System.exit(-1);
        }
    }

    @Override
    public int getListeningBacklog() {
        String backlog = getString(BACKLOG_KEY);
        return backlog == null ? DEFAULT_BACKLOG : Integer.parseInt(backlog);
    }

    @Override
    public int getMaximumConcurrentUsers() {
        return Integer.parseInt(getString(MAXUSERS_KEY));
    }

    @Override
    public int getServerListeningPort() {
        String listeningPort = getString(LISTENING_PORT_KEY);
        return listeningPort == null ? DEFAULT_LISTENING_PORT : Integer.parseInt(listeningPort);
    }

    @Override
    public String getVersion() {
        return getString(VERSION_KEY);
    }

    @Override
    public boolean isCharacterCreationEnabled() {
        return "1".equals(getString(CHARACTER_CREATION_KEY));
    }

    @Override
    public void setCharacterCreationEnabled(boolean enabled) {
        setProperty(CHARACTER_CREATION_KEY, enabled ? "1" : "0");
    }

    @Override
    public boolean isRestrictedToAdmins() {
        return "1".equals(getString(RESTRICTED_TO_ADMINS_KEY));
    }

    @Override
    public void setRestrictedToAdmins(boolean restricted) {
        setProperty(RESTRICTED_TO_ADMINS_KEY, restricted ? "1" : "0");
    }

    /**
     * Gets a string value from a specific section and key.
     *
     * @param key key name
     * @return the string value, or null if not found
     */
    private String getString(String key) {
        return ini.getString(INIT_HEADER + "." + key);
    }

    /**
     * Sets a property value in a specific section.
     *
     * @param key   key name
     * @param value the value to set
     */
    private void setProperty(String key, String value) {
        ini.setProperty(INIT_HEADER + "." + key, value);
    }

}
