package com.ao.ioc;

import java.util.Properties;

import com.ao.ioc.module.BootstrapModule;
import com.ao.ioc.module.ConfigurationModule;
import com.ao.ioc.module.DaoModule;
import com.ao.ioc.module.SecurityModule;
import com.ao.ioc.module.ServiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorFactory {

	/**
	 * Retrieves a new injector with the given properties.
	 * @param properties The injector properties.
	 * @return The injector.
	 */
	public static Injector get(Properties properties) {
		return Guice.createInjector(
				new BootstrapModule(properties),
				new ConfigurationModule(properties),
				new DaoModule(properties),
				new ServiceModule(properties),
				new SecurityModule(properties)
				);
	}
}
