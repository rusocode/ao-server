package com.ao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Utility for loading resources from the filesystem or classpath.
 * It prioritizes the external filesystem over the classpath.
 */
public class ResourceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);

    /**
     * Gets an InputStream for the given path.
     * It first tries to find the file in the external 'data' directory at the project root,
     * then at the root directly, and finally in the classpath.
     *
     * @param path The path to the resource.
     * @return The InputStream, or null if not found.
     */
    public static InputStream getStream(String path) {
        if (path == null) return null;

        // 1. Try external data directory (modern approach)
        File externalFile = new File(path);
        
        // Search in current dir and up to 2 levels up (for multi-module tests)
        int maxDepth = 2;
        int currentDepth = 0;
        while (!externalFile.exists() && currentDepth < maxDepth) {
            externalFile = new File(".." + File.separator + externalFile.getPath());
            currentDepth++;
        }

        if (externalFile.exists()) {
            try {
                LOGGER.debug("Loading resource from filesystem: {}", externalFile.getAbsolutePath());
                return new FileInputStream(externalFile);
            } catch (FileNotFoundException e) {
                // Should not happen if exists() returned true
            }
        }

        // 2. Try as a resource in the classpath (fallback)
        LOGGER.debug("Resource not found in filesystem, trying classpath: {}", path);
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
