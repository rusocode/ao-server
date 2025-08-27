package com.ao.context;

import com.ao.ioc.InjectorFactory;
import com.google.inject.Injector;

import java.util.Properties;

/**
 * General Application Context. Capable of loading common application classes.
 * <p>
 * <h4>Analogia simple de Guice</h4>
 * Es como un <b>restaurant con sommelier</b>:
 * <ul>
 *  <li>Sin DI: El chef tiene que salir a comprar vino, elegir la marca, etc.
 *  <li>Con DI: El sommelier (Guice) le trae al chef exactamente el vino que necesita para cada plato
 * </ul>
 * Utiliza anotaciones como {@code @Inject} para marcar los puntos de inyeccion y {@code @Singleton} para definir el alcance de
 * los objetos.
 * <p>
 * Ademas, permite configurar las dependencias mediante clases que extienden {@code AbstractModule}, donde defines que
 * implementaciones usar para cada interfaz.
 */

public class ApplicationContext {

    private static Injector injector;

    static {
        reload();
    }

    /**
     * Retrieves an instance of the requested class.
     *
     * @param <T>   type of the object being requested
     * @param clazz class of the object being requested
     * @return an instance of the requested class
     */
    public static <T> T getInstance(final Class<T> clazz) {
        return injector.getInstance(clazz); // Guice resuelve automaticamente
    }

    /**
     * Reloads all modules and associations. BEWARE, all previously created objects are no longer attached!
     */
    public static void reload() {
        final Properties properties = ApplicationProperties.getProperties();
        injector = InjectorFactory.get(properties);
    }

}
