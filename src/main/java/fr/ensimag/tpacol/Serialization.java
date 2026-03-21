package fr.ensimag.tpacol;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serialization {
    public static InputStream readFile(String file) throws IOException {
        if (!file.endsWith(".yml")) {
            file += ".yml";
        }
        Path path = Path.of(file);
        if (Files.exists(path)) {
            try {
                return Files.newInputStream(path);
            } catch (Exception e) {
                throw new RuntimeException("Failed to read file: " + file, e);
            }
        } else {
            try (InputStream resourceStream = Serialization.class.getResourceAsStream("/" + file)) {
                // save the resource to the file system for future use
                if (resourceStream != null) {
                    Files.copy(resourceStream, path);
                    return Files.newInputStream(path);
                } else {
                    throw new IOException("Resource not found: " + file);
                }
            }
        }
    }
}
