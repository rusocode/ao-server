package com.ao.data.dao.ini;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.map.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CityDAOIniTest {

    private static final String DAT_PATH = "ciudades.dat";

    private CityDAOIni dao;

    @BeforeEach
    public void setUp() throws DAOException {
        dao = new CityDAOIni(DAT_PATH);
    }

    @Test
    public void testLoadAll() {
        City[] cities = dao.loadAll();
        assertThat(cities.length).isEqualTo(7); // Se asegura que el valor actual sea igual al valor esperado
    }

}
