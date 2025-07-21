package com.ao.model.builder;

/**
 * Builder interface
 * @author marco
 */
public interface Builder<T> {

	/**
	 * Builds the instance as described by the builder.
	 * @return The built instance.
	 */
	T build();
}
