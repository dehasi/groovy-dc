package decompiler;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafa on 22.02.2016.
 */
public class Loader {

    Path path;
    ClassLoader classLoader;

    public Loader(String path) throws MalformedURLException {
        this.path = Paths.get(path);
        classLoader = new URLClassLoader(new URL[]{this.path.toUri().toURL()});
    }

    Loader(Path path) throws MalformedURLException {
        this.path = path;
        classLoader = new URLClassLoader(new URL[]{path.toUri().toURL()});
    }

    public List<Class<?>> findAllInDirectory() {
        List<Class<?>> classes = new ArrayList<>();
        try {
            Files.walk(path).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    if (filePath.getFileName().toString().endsWith(".class")) {
                        Class<?> clazz = loadFromDirectory(pathToClassName(filePath));
                        classes.add(clazz);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private String pathToClassName(Path filePath) {
        return (filePath.toString().substring(path.toString().length() + 1))
                .replaceAll(".class", "")
                .replace('\\', '.');
    }

    //TODO: add @NotNull  and fix gradle
    public Class<?> loadFromDirectory(String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToFile(String clazz) {

    }

}
