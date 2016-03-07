package decompiler;

import org.junit.BeforeClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

/**
 * Created by Rafa on 01.03.2016.
 */

public class TestUtils {
    static final public String TESTS_DIRECTORY = "G:\\CSCLocal\\groovy-dc\\build\\classes\\main\\";
    static final public String OUTPUT_DIRECTORY = "G:\\CSCLocal\\groovy-dc\\temp";
    static final public String GROOVY_FILES_DIRECTORY = "resources/test/groovy";

    @BeforeClass
    public static boolean compile(String path) {
        Process compileProcess = null;
        String compiler = "groovyc";
        String classpath = " -cp " + OUTPUT_DIRECTORY;

        StringBuilder cmd = new StringBuilder("groovyc");
        cmd.append(" -cp ")
                .append(OUTPUT_DIRECTORY)
                .append(" ")
                .append(path)
                .append(" -d ")
                .append(OUTPUT_DIRECTORY);
        try {
            compileProcess = Runtime.getRuntime().exec(cmd.toString());
            int returnCode = compileProcess.waitFor();
            PrintStream prtStrm = new PrintStream(compileProcess.getOutputStream());
            prtStrm.println();
            compileProcess.destroy();
            return returnCode == 0;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return false;
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

}
