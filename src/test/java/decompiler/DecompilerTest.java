package decompiler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class DecompilerTest {
    static final private String TESTS_DIRECTORY = "G:\\CSCLocal\\groovy-dc\\build\\classes\\main\\interfaces\\";
    static final private String OUTPUT_DIRECTORY = "/tmp/java";
    static final private String GROOVY_FILES_DIRECTORY = "resources/test/groovy";

    Decompiler decompiler = new Decompiler();
    Loader loader = new Loader();

    @Before
    public void compileGroovyClasses() {
    //TODO: implement compilation
    }

    @Before
    public void init() {
        //TODO: write smth useful
    }

    @Test
    public void testEmptyInterface() throws IOException {
        final String path = TESTS_DIRECTORY + "EmptyInterface.class";
        System.out.println(decompiler.decompile(loader.loadFromFileSystem(path)));
    }

    @Test
    public void testInterfaceWithFields() throws IOException {
        final String path = TESTS_DIRECTORY + "InterfaceWithFields.class";
        System.out.println(decompiler.decompile(loader.loadFromFileSystem(path)));
    }

    @Test
    public void testFullInterface() throws IOException {
        final String path = TESTS_DIRECTORY + "FullInterface.class";
        System.out.println(decompiler.decompile(loader.loadFromFileSystem(path)));
    }

    @Test
    @Ignore ("nothig to test")
    public void testGenericInterface() throws IOException {
        final String path = TESTS_DIRECTORY + "GenericInterface.class";
        System.out.println(decompiler.decompile(loader.loadFromFileSystem(path)));
    }

}
