package com.ao.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.lang3.StringUtils;
import org.tinylog.Logger;

/**
 * Utilidades para leer valores tipados desde INI con logging uniforme, centralizando el logging y el fallback en un lugar.
 */

public final class IniUtils {

    private IniUtils() {
    }

    public static String getString(Configuration config, String key, String defaultValue) {
        String value = StringUtils.trimToNull(config.getString(key));
        return value == null ? defaultValue : value;
    }

    public static int getRequiredInt(Configuration config, String key) {
        if (!config.containsKey(key)) {
            Logger.error("Missing required key '{}'!", key);
            throw new IllegalStateException("Missing required INI key: " + key);
        }
        try {
            Integer value = config.getInteger(key, null);
            return value == null ? 0 : value;
        } catch (ConversionException e) {
            Logger.error("{}", e.getMessage());
            throw new IllegalStateException("Invalid value for required INI key: " + key, e);
        }
    }

    public static int getInt(Configuration config, String key, int defaultValue) {
        return readInt(config, key, defaultValue);
    }

    /**
     * Aunque casi todas las flags usan "1" para indicar true, la flag {@code temeable} seria la exepcion ya que puede tener
     * valores distintos a 1 (que todavia no se que significan) y a 0. Por lo tanto la comprobacion {@code != 0} sigue siendo
     * valida.
     */
    public static boolean getBoolean(Configuration config, String key, boolean defaultValue) {
        return readInt(config, key, defaultValue ? 1 : 0) != 0;
    }

    private static int readInt(Configuration config, String key, int defaultValue) {
        Integer value = config.getInteger(key, null);
        return value == null ? defaultValue : value;
    }

}
