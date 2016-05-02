package utils;

public   class CompareUtils {
    //TODO split to few small functions
    public static boolean compareClazzes(Class<?> c1, Class<?> c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        /** compare name*/
        if (!c1.getSimpleName().equals(c2.getSimpleName())) {
            return false;
        }
        if (!c1.getCanonicalName().equals(c2.getCanonicalName())) {
            return false;
        }
        if (!c1.getName().equals(c2.getName())) {
            return false;
        }
        if (!c1.getTypeName().equals(c2.getTypeName())) {
            return false;
        }

        //TODO: implement
        return true;
    }
}
