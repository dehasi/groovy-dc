package utils;

import decompiler.Decompiler;
import decompiler.Loader;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;
import org.junit.Assert;
import org.objectweb.asm.ClassReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class TestUtils {
    static final public String TESTS_DIRECTORY = "build\\classes\\main\\";
    static final public String OUTPUT_DIRECTORY = "temp";

    @Deprecated
    public static boolean compile(String path) {
        //TODO compile from string. file is not nessesary
        File file = new File(path);
        try {
            CompilationUnit unit = new CompilationUnit();
            unit.addSource(file);
            unit.compile(Phases.CLASS_GENERATION);

            final GroovyClass gclass = (GroovyClass) unit.getClasses().get(0);

            return true;
        } catch (CompilationFailedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public static String writeDecompiledFile(String name, String content) {
        String path = OUTPUT_DIRECTORY + File.separator + name + ".groovy";

        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path)))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    public static Class<?> convertGroovyClass2Java(GroovyClass groovyClass) {
        ByteArrayClassLoader classLoader = new ByteArrayClassLoader();
        return classLoader.findClass(groovyClass.getName(), groovyClass.getBytes());
    }

    public static GroovyClass compile(String name, String source) {
        CompilationUnit unit = new CompilationUnit();
        unit.addSource(name, source);
        try {
            unit.compile(Phases.CLASS_GENERATION);
            if (unit.getClasses() != null && unit.getClasses().size() == 1) {
                return (GroovyClass) unit.getClasses().get(0);
            }
        } catch (CompilationFailedException e) {
            e.printStackTrace();
        }
        //TODO: may be null is redundant? May be excetion is enouth?
        return null;
    }


    public static Class<?> loadJavaClassByName(String name) {
        ClassLoader classLoader = TestUtils.class.getClassLoader();

        try {
            Class aClass = classLoader.loadClass(name);
            System.out.println("aClass.getName() = " + aClass.getName());
            return aClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean compareClazzes(Class<?> c1, Class<?> c2) {
        //TODO: implement
        return true;
    }

    @Deprecated
    public static void routine(String name) throws IOException {
        Decompiler decompiler = new Decompiler();
        Loader loader = new Loader();
        final String path = TESTS_DIRECTORY + "interfaces\\" + name + ".class";
        String content = decompiler.decompile(loader.loadFromFileSystem(path)).toString();
        String res = writeDecompiledFile(name, content);
        System.out.println(content);
        Assert.assertTrue(compile(res));
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
