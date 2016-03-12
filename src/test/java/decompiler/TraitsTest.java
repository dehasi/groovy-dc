package decompiler;

import org.junit.Test;

import java.io.IOException;

import static utils.TestUtils.routine;

public class TraitsTest {

    private static final String packageName = "traits";

    @Test
    public void testEmptyTrait() throws IOException {
        routine("EmptyTrait", packageName);
    }

    @Test
    public void testOneFuncTrait() throws IOException {
        routine("OneFuncTrait", packageName);
    }

}
