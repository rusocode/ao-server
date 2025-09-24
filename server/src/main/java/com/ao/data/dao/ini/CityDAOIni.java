package com.ao.data.dao.ini;

import com.ao.data.dao.CityDAO;
import com.ao.model.map.City;
import com.ao.utils.IniUtils;
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
 * Implementation of the City DAO backed by INI files.
 */

public record CityDAOIni(String citiesFilePath) implements CityDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityDAOIni.class);

    private static final String INIT_HEADER = "INIT";
    private static final String CITIE_COUNT_KEY = "citie_count";

    /** Prefixs keys. */
    private static final String CITY_PREFIX = "CITY";

    /** Ini file keys. */
    private static final String MAP_KEY = "map";
    private static final String X_KEY = "x";
    private static final String Y_KEY = "y";

    @Inject
    public CityDAOIni(@Named("citiesFilePath") String citiesFilePath) {
        this.citiesFilePath = citiesFilePath;
    }

    @Override
    public City[] load() {
        INIConfiguration ini = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(citiesFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file '" + citiesFilePath + "' was not found in the classpath!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ini = new INIConfiguration();
            ini.read(reader);
            LOGGER.info("Cities loaded successfully!");
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error loading citites!", e);
            System.exit(-1);
        }

        // Required key
        int citieCount = IniUtils.getRequiredInt(ini, INIT_HEADER + "." + CITIE_COUNT_KEY);

        City[] cities = new City[citieCount];

        for (int i = 1; i <= citieCount; i++)
            cities[i - 1] = loadCity(i, ini);

        return cities;
    }

    /**
     * Loads a city.
     *
     * @param id  city's id
     * @param ini ini configuration
     * @return new city
     */
    private City loadCity(int id, INIConfiguration ini) {
        String section = CITY_PREFIX + id;

        int map = IniUtils.getInt(ini, section + "." + MAP_KEY, -1);
        byte x = (byte) IniUtils.getInt(ini, section + "." + X_KEY, -1);
        byte y = (byte) IniUtils.getInt(ini, section + "." + Y_KEY, -1);

        return new City(map, x, y);
    }

}
