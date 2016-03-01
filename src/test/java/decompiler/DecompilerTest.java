package decompiler;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static decompiler.TestUtils.TESTS_DIRECTORY;
import static decompiler.TestUtils.compile;
import static decompiler.TestUtils.writeDecompiledFile;

public class DecompilerTest {
    //TODO: DI
    Decompiler decompiler = new Decompiler();
    Loader loader = new Loader();

    @AfterClass //TODO inherit from abstract test
    public static void clear() {
        purgeDirectory(TestUtils.OUTPUT_DIRECTORY);
    }

    @Test
    public void testEmptyInterface() throws IOException {
        final String path = TestUtils.TESTS_DIRECTORY + "EmptyInterface.class";
        String content = decompiler.decompile(loader.loadFromFileSystem(path)).toString();
        String name = "EmptyInterface.groovy";
        String res = writeDecompiledFile(name, content);
        Assert.assertTrue(compile(res));

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
    @Ignore("nothig to test")
    public void testGenericInterface() throws IOException {
        final String path = TESTS_DIRECTORY + "GenericInterface.class";
        System.out.println(decompiler.decompile(loader.loadFromFileSystem(path)));
    }

    public static void purgeDirectory(String path) {

        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile() && f.exists()) {
                f.delete();
            }
        }
    }
}