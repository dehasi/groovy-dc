package decompiler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Rafa on 23.02.2016.
 */
@Ignore("nothing to test yet")
public class DecompilerTest {
    static final private String TESTS_DIRECTORY = "G:\\CSCLocal\\groovy-dc\\build\\classes\\main\\interfaces\\";
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
    public void testEmtpyInterface() throws IOException {
        StringBuilder emptyInterface = decompiler.decompileFromFileSystem(TESTS_DIRECTORY + "EmptyInterface.class");
        System.out.println(emptyInterface);
    }

    @Test
    public void testInterfaceWithFields() {
        StringBuilder interfaceWithFields = decompiler.decompileFromFileSystem(TESTS_DIRECTORY + "InterfaceWithFields.class");
        System.out.println(interfaceWithFields);
    }

    @Test
    public void testFullInterface() {

    }

    @Test
    public void testGenericInterface() {

    }
}
