package decompiler;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Created by Rafa on 23.02.2016.
 */

public class DecompilerTest {
    static final private String TESTS_DIRECTORY = "G:\\CSCLocal\\groovy-dc\\build\\classes\\main\\";
    static final private String OUTPUT_DIRECTORY = "/tmp/java";
    static final private String GROOVY_FILES_DIRECTORY = "resources/test/groovy";

    Loader loader = null;
    Decompiler decompiler = new Decompiler();

    @Before
    //TODO: implement compilation
    public void compileGroovyClasses() {
        try {
            Process process = Runtime.getRuntime().exec("groovy " + GROOVY_FILES_DIRECTORY + "*.groovy");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String res = decompiler.decompile(clazz);
        System.out.println(res);
    }

    @Test
    public void testInterfaceWithFields() {
        Class<?> clazz = loader.loadFromDirectory("interfaces.InterfaceWithFields");
        String res = decompiler.decompile(clazz);
        System.out.println(res);
    }

    @Test
    public void testFullInterface() {
        Class<?> clazz = loader.loadFromDirectory("interfaces.FullInterface");
        String res = decompiler.decompile(clazz);
        System.out.println(res);
    }
}
