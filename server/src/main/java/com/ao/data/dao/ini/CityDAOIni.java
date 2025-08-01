package com.ao.data.dao.ini;

import com.ao.data.dao.CityDAO;
import com.ao.model.map.City;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Implementation of the City DAO backed by ini files.
 */

public class CityDAOIni implements CityDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityDAOIni.class);

    private static final String INIT_HEADER = "INIT";
    private static final String NUM_CITIES_KEY = "NumCities";

    private static final String CITY_SECTION_PREFIX = "CIUDAD";
    private static final String X_KEY = "X";
    private static final String Y_KEY = "Y";
    private static final String MAP_KEY = "MAPA";

    private final String citiesFilePath;

    /**
     * Creates a new WorldObjectDAOIni instance.
     *
     * @param citiesFilePath path to the file with all city positions
     */
    @Inject
    public CityDAOIni(@Named("citiesFilePath") String citiesFilePath) {
        this.citiesFilePath = citiesFilePath;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.data.dao.CityDAO#retrieveAll()
     */
    @Override
    public City[] retrieveAll() {
        Ini iniFile;

        // Load from a classpath using try-with-resources for automatic resource management
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(citiesFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + citiesFilePath + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            iniFile = new Ini(reader);
        } catch (Exception e) {
            LOGGER.error("Cities loading failed!", e);
            throw new RuntimeException(e);
        }

        int totalCities = Integer.parseInt(iniFile.get(INIT_HEADER, NUM_CITIES_KEY));

        City[] cities = new City[totalCities];

        for (int i = 1; i <= totalCities; i++)
            cities[i - 1] = loadCity(i, iniFile);

        return cities;
    }

    /**
     * Creates a city from the given section.
     *
     * @param iniFile ini file that contains all city positions to be loaded
     * @return the city created
     */
    private City loadCity(int id, Ini iniFile) {

        // The section of the ini file containing the city to be loaded
        Section section = iniFile.get(CITY_SECTION_PREFIX + id);

        // Make sure it's valid
        if (section == null) return null;

        int map = Integer.parseInt(section.get(MAP_KEY));
        byte x = Byte.parseByte(section.get(X_KEY));
        byte y = Byte.parseByte(section.get(Y_KEY));

        return new City(map, x, y);
    }

}
