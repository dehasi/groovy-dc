package decompiler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;

@Ignore ("nothig to test")
public class DecompilerTest {
    static final private String TESTS_DIRECTORY = "G:\\CSCLocal\\groovy-dc\\build\\classes\\main\\";
    static final private String OUTPUT_DIRECTORY = "/tmp/java";
    static final private String GROOVY_FILES_DIRECTORY = "resources/test/groovy";

    Loader loader = null;
    Decompiler decompiler = new Decompiler();

    @Before
    public void compileGroovyClasses() {
    //TODO: implement compilation
    }

    @Before
    public void init() {
        try {
            loader = new Loader(TESTS_DIRECTORY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmtpyInterface() {
        Class<?> clazz = loader.loadFromDirectory("interfaces.EmptyInterface");
        StringBuilder res = decompiler.decompile(clazz);
        System.out.println(res);
    }

    @Test
    public void testInterfaceWithFields() {
        Class<?> clazz = loader.loadFromDirectory("interfaces.InterfaceWithFields");
        StringBuilder res = decompiler.decompile(clazz);
        System.out.println(res);
    }

    @Test
    public void testFullInterface() {
        Class<?> clazz = loader.loadFromDirectory("interfaces.FullInterface");
        StringBuilder res = decompiler.decompile(clazz);
        System.out.println(res);
    }

    @Test
    public void testGenericInterface() {
        Class<?> clazz = loader.loadFromDirectory("interfaces.GenericInterface");
        StringBuilder res = decompiler.decompile(clazz);
        System.out.println(res);
    }
}
