package com.ao.model.builder;

/**
 * Builder interface.
 */

public interface Builder<T> {

    /**
     * Builds the instance as described by the builder.
     *
     * @return the built instance
     */
    T build();

}
