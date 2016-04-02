package decompiler;


import org.junit.Test;

import java.io.IOException;

import static utils.TestUtils.routine;

public class ClassesTest {
    private static final String packageName = "classes";

    @Test
    public void EmptyClassTest() throws IOException {
        routine("EmptyClass", packageName);
    }

    @Test
    public void FieldClassTest() throws IOException {
        routine("FieldClass", packageName);
    }

    @Test
    public void MethodClassTest() throws IOException {
        routine("MethodClass", packageName);
    }
}
