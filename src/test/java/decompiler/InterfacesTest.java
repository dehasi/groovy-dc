package decompiler;

import org.junit.Test;

import java.io.IOException;

import static utils.TestUtils.routine;

public class InterfacesTest {

    private static final String packageName = "interfaces";

    @Test
    public void testEmptyInterface() throws IOException {
        routine("EmptyInterface", packageName);
    }

    @Test
    public void testInterfaceExtend() throws IOException {
        routine("InterfaceExtend", packageName);
    }

    @Test
    public void testInterfaceWithFields() throws IOException {
        routine("InterfaceWithFields", packageName);
    }

    @Test
    public void testFullInterface() throws IOException {
        routine("FullInterface", packageName);
    }

    @Test
    public void testGenericInterface() throws IOException {
        routine("GenericInterface", packageName);
    }

    @Test
    public void testAnnotationInterface() throws IOException {
        routine("AnnotationInterface", packageName);
    }

}