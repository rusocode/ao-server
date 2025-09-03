package com.ao.utils;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilidades para leer valores tipados desde INI con logging uniforme, centralizando el logging y el fallback en un lugar.
 */

public final class IniUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IniUtils.class);

    private IniUtils() {
    }

    public static String getString(INIConfiguration ini, String key, String defaultValue) {
        if (!ini.containsKey(key)) return defaultValue; // TODO Deberia devolver el valor por defecto si no existe la key?
        String value = StringUtils.trimToNull(ini.getString(key));
        return value == null ? defaultValue : value;
    }

    public static int getRequiredInt(INIConfiguration ini, String key) {
        if (!ini.containsKey(key)) {
            LOGGER.error("Missing required key '{}'!", key);
            System.exit(-1);
        }
        Integer value = 0;
        try {
            value = ini.getInteger(key, null);
        } catch (ConversionException e) {
            LOGGER.error("{}", e.getMessage());
            System.exit(-1);
        }
        return value;
    }

    public static int getInt(INIConfiguration ini, String key, int defaultValue) {
        return readInt(ini, key, defaultValue);
    }

    /**
     * Aunque casi todas las flags usan "1" para indicar true, la flag {@code temeable} seria la exepcion ya que puede tener
     * valores distintos a 1 (que todavia no se que significan) y a 0. Por lo tanto la comprobacion {@code != 0} sigue siendo
     * valida.
     */
    public static boolean getBoolean(INIConfiguration ini, String key, boolean defaultValue) {
        return readInt(ini, key, defaultValue ? 1 : 0) != 0;
    }

    private static int readInt(INIConfiguration ini, String key, int defaultValue) {
        if (!ini.containsKey(key)) return defaultValue; // TODO Deberia devolver el valor por defecto si no existe la key?
        Integer value = ini.getInteger(key, null);
        return value == null ? defaultValue : value;
    }

}
