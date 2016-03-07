package decompiler;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static decompiler.TestUtils.TESTS_DIRECTORY;
import static decompiler.TestUtils.compile;
import static decompiler.TestUtils.writeDecompiledFile;

public class TraitsTest {
    //TODO: DI
    Decompiler decompiler = new Decompiler();
    Loader loader = new Loader();
    @Test
    public void testEmptyTrait() throws IOException {
        final String path = TESTS_DIRECTORY + "traits\\" +"EmptyTrait" + ".class";
        String content = decompiler.decompile(loader.loadFromFileSystem(path)).toString();
        String res = writeDecompiledFile("EmptyTrait" + ".groovy", content);
        System.out.println(content);
        Assert.assertTrue(compile(res));
    }


}
