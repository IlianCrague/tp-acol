package fr.ensimag.tpacol;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Serialization {
    private Serialization() {
        // Utility class
    }

    public static <T> T load(String file, Class<T> type, boolean saveFile, TypeDescription... typeDescriptions) throws IOException {
        try (InputStream input = saveFile ? readSaveFile(file) : readRessource(file)) {
            Constructor constructor = new Constructor(type, new LoaderOptions());
            for (TypeDescription description : typeDescriptions) {
                constructor.addTypeDescription(description);
            }
            Yaml yaml = new Yaml(constructor);
            yaml.setBeanAccess(BeanAccess.FIELD);
            return yaml.load(input);
        } catch (RuntimeException e) {
            throw new IOException("Failed to deserialize YAML file: " + file, e);
        }
    }

    public static <T> T load(
            String file,
            Class<T> type,
            boolean saveFile,
            Map<String, Class<?>> listProperties,
            Map<Class<?>, String> classTags
    ) throws IOException {
        TypeDescription rootType = new TypeDescription(type);
        for (Map.Entry<String, Class<?>> entry : listProperties.entrySet()) {
            rootType.addPropertyParameters(entry.getKey(), entry.getValue());
        }

        TypeDescription[] descriptions = new TypeDescription[classTags.size() + 1];
        descriptions[0] = rootType;
        int i = 1;
        for (Map.Entry<Class<?>, String> entry : classTags.entrySet()) {
            descriptions[i++] = new TypeDescription(entry.getKey(), entry.getValue());
        }

        return load(file, type, saveFile, descriptions);
    }

    public static <T> T load(String file, Class<T> type, boolean saveFile, Map<Class<?>, String> classTags) throws IOException {
        return load(file, type, saveFile, java.util.Map.of(), classTags);
    }

    public static void save(String file, Object value, Map<Class<?>, String> classTags) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Representer representer = new Representer(options);
        representer.getPropertyUtils().setAllowReadOnlyProperties(false);
        for (Map.Entry<Class<?>, String> entry : classTags.entrySet()) {
            representer.addClassTag(entry.getKey(), new Tag(entry.getValue()));
        }

        Yaml yaml = new Yaml(representer, options);
        yaml.setBeanAccess(BeanAccess.FIELD);
        String out = yaml.dumpAsMap(value);
        Files.writeString(Path.of(normalizeYamlPath(file)), out);
    }

    private static InputStream readSaveFile(String file) throws IOException {
        Path path = Path.of(normalizeYamlPath(file));
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

    private static InputStream readRessource(String file) throws IOException {
        file = normalizeYamlPath(file);
        InputStream resourceStream = Serialization.class.getResourceAsStream("/" + file);
        if (resourceStream != null) {
            return resourceStream;
        } else {
            throw new IOException("Resource not found: " + file);
        }
    }

    private static String normalizeYamlPath(String file) {
        return file.endsWith(".yml") ? file : file + ".yml";
    }
}
