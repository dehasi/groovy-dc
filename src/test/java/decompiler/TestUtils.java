package decompiler;

import java.io.IOException;

/**
 * Created by Rafa on 01.03.2016.
 */
public class TestUtils {
    public static boolean compile(String path) {
        Process compileProcess = null;
        try {
            compileProcess = Runtime.getRuntime().exec("echo hello");
            int returnCode = compileProcess.waitFor();
            return returnCode == 0;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
