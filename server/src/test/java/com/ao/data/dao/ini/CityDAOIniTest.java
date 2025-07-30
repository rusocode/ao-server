package com.ao.data.dao.ini;

import com.ao.model.map.City;
import org.ini4j.Ini;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityDAOIniTest {

    private static final String CITIES_DAT_PATH = "Ciudades.dat";
    private static final String INIT_HEADER = "INIT";
    private static final String NUM_CITIES_KEY = "NumCities";
    private CityDAOIni dao;

    @Before
    public void setUp() throws Exception {
        dao = new CityDAOIni(CITIES_DAT_PATH);
    }

    /**
     * Test method for {@link com.ao.data.dao.ini.CityDAOIni#retrieveAll()}.
     */
    @Test
    public final void testRetrieveAll() {

        Ini iniFile = null;

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CITIES_DAT_PATH);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + CITIES_DAT_PATH + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            iniFile = new Ini(reader);
        } catch (Exception e) {
            fail("Cities loading failed! " + e.getMessage());
        }

        final int totalCities = Integer.parseInt(iniFile.get(INIT_HEADER, NUM_CITIES_KEY));

        final City[] cities = dao.retrieveAll();

        assertEquals(totalCities, cities.length);

    }

}
