package com.ao.context;

import com.ao.ioc.InjectorFactory;
import com.google.inject.Injector;

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
     * Gets an instance of the specified class using dependency injection.
     *
     * @param <T>   type of the class to be retrieved
     * @param clazz class object of the type T for which an instance is required
     * @return an instance of the specified class
     */
    public static <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz); // Guice resolves automatically
    }

    /**
     * Reloads all modules and associations. BEWARE, all previously created objects are no longer attached!
     */
    public static void reload() {
        injector = InjectorFactory.get(ApplicationProperties.getProperties());
    }

}
