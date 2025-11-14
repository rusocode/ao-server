package com.ao.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Servicio que gestiona los privilegios del servidor.
 * <p>
 * Lee la seccion [Dioses] del archivo {@code server.ini} para determinar que usuarios tienen este privilegios.
 * <p>
 * Equivalente a las funciones EsDios(), EsSemiDios(), EsConsejero(), EsRolesMaster() del servidor VB6.
 */

@Singleton
public class PrivilegesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegesService.class);

    /** Nombres de las secciones en el archivo INI. */
    private static final String DIOSES_SECTION = "Dioses";

    /** Conjuntos para almacenar los nombres (case-insensitive). */
    private final Set<String> dioses = new HashSet<>();

    private final INIConfiguration ini;

    @Inject
    public PrivilegesService(@Named("ServerConfigIni") String serverConfigIni) {
        this.ini = loadConfiguration(serverConfigIni);
        loadPrivileges();
        logLoadedPrivileges();
    }

    /**
     * Verifica si un usuario es Dios. Equivalente a {@code EsDios()} en VB6.
     *
     * @param nick nick del usuario
     * @return true si el usuario esta en la lista de dioses
     */
    public boolean isDios(String nick) {
        if (nick == null || nick.isBlank()) return false;
        return dioses.contains(nick.trim().toUpperCase());
    }

    /**
     * Obtiene el tipo de privilegio del usuario. Retorna el valor de PlayerType correspondiente (bit flags).
     *
     * @param nick nombre del usuario
     * @return valor entero que representa los privilegios (PlayerType flags)
     */
    public int getPrivilegeFlags(String nick) {
        int user = 0x01, dios = 0x08;
        return isDios(nick) ? dios : user;
    }

    /**
     * Obtiene una descripcion textual del privilegio.
     *
     * @param nick nombre del usuario
     * @return una descripcion textual del privilegio
     */
    public String getPrivilegeDescription(String nick) {
        return isDios(nick) ? "Dios" : "User";
    }

    /**
     * Carga la configuracion del servidor desde el archivo INI.
     * <p>
     * TODO Se esta cargando dos veces el archivo de configuracion del servidor
     */
    private INIConfiguration loadConfiguration(String serverConfigIni) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(serverConfigIni);
        if (inputStream == null)
            throw new IllegalArgumentException("The file '" + serverConfigIni + "' was not found in the classpath!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            INIConfiguration config = new INIConfiguration();
            config.read(reader);
            LOGGER.info("Server configuration loaded successfully for privileges!");
            return config;
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error loading server configuration for privileges!", e);
            throw new RuntimeException("Failed to load privileges configuration", e);
        }
    }

    /**
     * Carga todos los privilegios desde el archivo de configuracion.
     */
    private void loadPrivileges() {
        // Obtiene la cantidad de dioses
        int diosesCount = ini.getInt("INIT.Dioses", 0);
        // Carga los dioses
        loadNicksFromDiosesSection(diosesCount, dioses); // Pasa la lista como referencia
    }

    /**
     * Lee los nombres de la seccion [Dioses].
     *
     * @param count  cantidad de elementos a leer
     * @param dioses conjunto donde almacenar los nombres
     */
    private void loadNicksFromDiosesSection(int count, Set<String> dioses) {
        for (int i = 1; i <= count; i++) {
            String key = DIOSES_SECTION + "." + "Dios" + i;
            String nick = ini.getString(key);
            // Verifica si el nick no es nulo y no esta en blanco
            if (nick != null && !nick.isBlank()) {
                // Normaliza el nombre a mayusculas para comparacion case-insensitive
                dioses.add(nick.trim().toUpperCase());
                LOGGER.debug("Loaded {} from section {}: {}", "Dios", PrivilegesService.DIOSES_SECTION, nick);
            } else LOGGER.warn("Empty or null value for key: {}", key);
        }
    }

    /**
     * Resgistra la cantidad de privilegios para los Dioses.
     */
    private void logLoadedPrivileges() {
        LOGGER.info("Privileges loaded successfully:");
        LOGGER.info("Dioses: {}", dioses.size());
    }

}
