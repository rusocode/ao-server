package com.ao.utils;

import org.tinylog.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utility for loading resources from the filesystem or classpath. It prioritizes the external filesystem over the classpath.
 */
public class ResourceUtils {

    /**
     * Gets an InputStream for the given path. It first tries to find the file in the external 'data' directory at the project
     * root, then at the root directly, and finally in the classpath.
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
                Logger.debug("Loading resource from filesystem: {}", externalFile.getAbsolutePath());
                return new FileInputStream(externalFile);
            } catch (FileNotFoundException e) {
                // Should not happen if exists() returned true
            }
        }

        // 2. Try as a resource in the classpath (fallback)
        Logger.debug("Resource not found in filesystem, trying classpath: {}", path);
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
