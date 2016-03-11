package decompiler;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.jar.JarInputStream;

import org.codehaus.groovy.tools.Compiler;
public class TestUtils {
    static final public String TESTS_DIRECTORY = "build\\classes\\main\\";
    static final public String OUTPUT_DIRECTORY = "temp";
    static final public String GROOVY_FILES_DIRECTORY = "resources/test/groovy";

    @BeforeClass
    public static boolean compile(String path) {
        Process compileProcess = null;
        String compiler = "groovyc";
        String classpath = " -cp " + OUTPUT_DIRECTORY;
        Properties properties = new Properties();
        properties.setProperty("-cp", OUTPUT_DIRECTORY);
        properties.setProperty("-d", OUTPUT_DIRECTORY);
        CompilerConfiguration configuration = new CompilerConfiguration(properties);
        Compiler c = new Compiler(configuration);
        File file = new File(path);
        try {
//            c.compile(file);
            CompilationUnit unit = new CompilationUnit( configuration );
            unit.addSources(new File[]{file});
            unit.compile(Phases.CLASS_GENERATION);
            final GroovyClass gclass = (GroovyClass) unit.getClasses().get(0);
            JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(gclass.getBytes()));
            ClassLoader classLoader = new URLClassLoader(new URL[]{});
            return true;
        } catch (CompilationFailedException | IOException e){
            e.printStackTrace();
            return false;
        }

    }

    @BeforeClass
    public static String writeDecompiledFile(String name, String content) {
        String path = OUTPUT_DIRECTORY + File.separator + name;

        try ( Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path)))){
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    @Test
    public void pwdTest() {
        Path currentRelativePath = Paths.get("build\\src\\main\\");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }
}
