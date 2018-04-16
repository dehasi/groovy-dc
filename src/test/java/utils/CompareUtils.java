package utils;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.junit.Assert.fail;

final class CompareUtils {

    static boolean compareClazzes(Class<?> c1, Class<?> c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        assertNamesEqual(c1, c2);

        //TODO: implement
        return true;
    }

    private static void assertNamesEqual(Class<?> c1, Class<?> c2) {
        if (!Objects.equals(c1.getSimpleName(), c2.getSimpleName())) {
            fail(String.format("Simple names are not equal: %s != %s", c1.getSimpleName(), c2.getSimpleName()));
        }
        if (!Objects.equals(c1.getCanonicalName(), c2.getCanonicalName())) {
            fail(String.format("Canonical names are not equal: %s != %s", c1.getCanonicalName(), c2.getCanonicalName()));
        }
        if (!Objects.equals(c1.getName(), c2.getName())) {
            fail(String.format("Names are not equal: %s != %s", c1.getName(), c2.getName()));
        }
        if (!Objects.equals(c1.getTypeName(), c2.getTypeName())) {
            fail(String.format("Type names are not equal: %s != %s", c1.getTypeName(), c2.getTypeName()));
        }
    }

    private static void assertFieldsEqual(Field f1, Field f2) {
        if (!Objects.equals(f1.getName(), f2.getName()) ) {
            fail(String.format("Field names are not equal: %s != %s", f1.getName(), f2.getName()));
        }
        if (!Objects.equals(f1.getType(), f2.getType()) ) {
            fail(String.format("Field types are not equal: %s != %s", f1.getType(), f2.getType()));
        }
    }
    private CompareUtils() {
    }
}
