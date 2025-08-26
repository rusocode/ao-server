package com.ao.data.dao.ini;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.map.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CityDAOIniTest {

    private CityDAOIni cityDAOIni;

    /**
     * La etiqueta @BeforeEach es una anotacion de JUnit 5 (Jupiter) que indica que el metodo anotado debe ejecutarse antes de
     * cada metodo de prueba (@Test) de la clase. Sirve para preparar el "estado inicial" o dependencias necesarias para cada
     * prueba (por ejemplo, crear una nueva instancia de CityDAOIni).
     */
    @BeforeEach
    public void setUp() throws DAOException {
        cityDAOIni = new CityDAOIni("dat/cities.dat");
    }

    @Test
    public void testLoad() {
        City[] cities = cityDAOIni.load();
        assertThat(cities.length).isEqualTo(7); // Se asegura que el valor actual sea igual al valor esperado
    }

}
