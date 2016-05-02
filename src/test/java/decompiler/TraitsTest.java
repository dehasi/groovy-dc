package decompiler;

import org.junit.Test;

import java.io.IOException;

import static utils.TestUtils.routine;

//@Ignore("Nothing to test")
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

    @Test
    public void testFullTrait() throws IOException {
        routine("FullTrait", packageName);
    }


    @Test
    public void testFullTrait1() throws IOException {
        routine("FullTrait$Trait$Helper", packageName);
    }


    @Test
    public void testFullTrait2() throws IOException {
        routine("FullTrait$Trait$FieldHelper", packageName);
    }

}
