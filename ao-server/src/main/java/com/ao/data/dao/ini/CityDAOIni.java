package com.ao.data.dao.ini;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import javax.inject.Inject;
import javax.inject.Named;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ao.data.dao.CityDAO;
import com.ao.model.map.City;

/**
 * Implementation of the City DAO backed by ini files.
 * @author zaxtor
 */
public class CityDAOIni implements CityDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityDAOIni.class);

	private static final String INIT_HEADER = "INIT";
	private static final String NUM_CITIES_KEY = "NumCities";

	private static final String CITY_SECTION_PREFIX = "CIUDAD";
	private static final String X_KEY = "X";
	private static final String Y_KEY = "Y";
	private static final String MAP_KEY = "MAPA";

	private String citiesFilePath;

	/**
	 * Creates a new WorldObjectDAOIni instance.
	 * @param citiesFilePath The path to the file with all cities positions.
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

		try {
			// Make sure the reader is closed, since Ini4J gives no guarantees.
			Reader reader = new BufferedReader(new FileReader(citiesFilePath));
			iniFile = new Ini(reader);
			reader.close();
		} catch (Exception e) {
			LOGGER.error("Cities loading failed!", e);
			throw new RuntimeException(e);
		}

		int totalCities = Integer.parseInt(iniFile.get(INIT_HEADER, NUM_CITIES_KEY));

		City[] cities = new City[totalCities];

		for (int i = 1; i <= totalCities; i++) {
			cities[i - 1] = loadCity(i, iniFile);
		}

		return cities;
	}

	/**
	 * Creates a city from the given section.
	 * @param iniFile The ini file that contains all cities positions to be loaded.
	 * @return The city created.
	 */
	private City loadCity(int id, Ini iniFile) {

		//The section of the ini file containing the city to be loaded.
		Section section = iniFile.get(CITY_SECTION_PREFIX + id);

		// Make sure it's valid
		if (section == null) {
			return null;
		}

		int map = Integer.parseInt(section.get(MAP_KEY));
		byte x = Byte.parseByte(section.get(X_KEY));
		byte y = Byte.parseByte(section.get(Y_KEY));

		return new City(map, x, y);
	}
}
