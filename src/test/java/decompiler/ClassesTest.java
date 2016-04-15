package decompiler;


import org.junit.Ignore;
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

    @Test
    @Ignore("smth wrong with headers. but traits has prio")
    public void GenericClassTest() throws IOException {
        routine("GenericClass", packageName);
    }

    @Test
    public void InheritClassTest() throws IOException {
        routine("InheritClass", packageName);
    }

}
