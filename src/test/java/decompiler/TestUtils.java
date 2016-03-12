package decompiler;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;
import org.junit.BeforeClass;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarInputStream;
public class TestUtils {
    static final public String TESTS_DIRECTORY = "build\\classes\\main\\";
    static final public String OUTPUT_DIRECTORY = "temp";
    static final public String GROOVY_FILES_DIRECTORY = "resources/test/groovy";

    @BeforeClass
    public static boolean compile(String path) {
        Properties properties = new Properties();
        properties.setProperty("-cp", OUTPUT_DIRECTORY);
        properties.setProperty("-d", OUTPUT_DIRECTORY);
        CompilerConfiguration configuration = new CompilerConfiguration(properties);
        File file = new File(path);
        try {
            CompilationUnit unit = new CompilationUnit( configuration );
            unit.addSources(new File[]{file});
            unit.compile(Phases.CLASS_GENERATION);
            final GroovyClass gclass = (GroovyClass) unit.getClasses().get(0);
            //TODO convert bytes to Class<?>
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
        String path = OUTPUT_DIRECTORY + File.separator + name + ".groovy";

        try ( Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path)))){
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

}
