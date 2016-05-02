package utils;

import decompiler.Decompiler;
import decompiler.utils.Loader;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;
import org.junit.Assert;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;

import static utils.CompareUtils.compareClazzes;

public class TestUtils {
    static final public String TESTS_DIRECTORY = "build/classes/main/";

    public static Class<?> convertGroovyClass2Java(GroovyClass groovyClass) {
        ByteArrayClassLoader classLoader = new ByteArrayClassLoader();
        return classLoader.findClass(groovyClass.getName(), groovyClass.getBytes());
    }

    public static GroovyClass compile(String name, String source) {
        CompilationUnit unit = new CompilationUnit();
        unit.addSource(name, source);
        try {
            unit.compile(Phases.CLASS_GENERATION);
            return (GroovyClass) unit.getClasses().get(0);
        } catch (CompilationFailedException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static Class<?> loadJavaClassByName(String name) {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        try {
            return classLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public static void routine(String name, String packageName) throws IOException {
        Decompiler decompiler = new Decompiler();
        Loader loader = new Loader();
        final String path = TESTS_DIRECTORY + packageName + File.separator + name + ".class";

        ClassReader classReader = loader.loadFromFileSystem(path);
        String content = decompiler.decompile(classReader).toString();

        System.out.println(content); //TODO remove in production

        Class<?> expectedClass = loadJavaClassByName(classReader.getClassName().replace('/', '.'));
        Assert.assertNotNull(expectedClass);

        GroovyClass groovyClass = compile(name, content);
        Assert.assertNotNull(groovyClass);

        Class<?> actualClass = convertGroovyClass2Java(groovyClass);
        Assert.assertNotNull(actualClass);

        Assert.assertTrue(compareClazzes(expectedClass, actualClass));
    }
}
