package decompiler;


import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static utils.TestUtils.routine;
@Ignore("senseless")
public class AnnotationsTest {
    private static final String packageName = "annotations";

    @Test
    public void EmptyAnnotationTest() throws IOException {
        routine("EmptyAnnotation", packageName);
    }


    @Test
    public void SimpleAnnotationTest() throws IOException {
        routine("SimpleAnnotation", packageName);
    }

}
