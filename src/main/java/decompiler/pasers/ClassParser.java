package decompiler.pasers;


import static decompiler.utils.MethodParserUtils.getMethod;
import static decompiler.utils.ParserUtils.EMPTY_STRING_BUILDER;
import static decompiler.utils.ParserUtils.parseInterfaces;

public class ClassParser extends ASMParser {



    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.err.println("parseMethod = " + name);
        if (needSkipMethod(name)) return EMPTY_STRING_BUILDER;
        return new StringBuilder();
    }

    @Override
    public StringBuilder parseHeader(int version, int access, String name, String signature, String superName, String[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n')
                .append(parseClassName(access, name));

        if (signature != null) {
            sb.append(getMethod(signature));
        }
        sb.append(' ')
                .append(parseInterfaces(access, interfaces, "implements "))
                .append(" {\n");
        return sb;
    }

    private String parseClassName(int access, String name) {
        int i = name.lastIndexOf('/');
        return "class " + name.substring(++i);
    }

    @Override
    public StringBuilder parseField(int access, String name, String desc, String signature, Object value) {

        System.err.println("parseField = " + name);
        if (needSkipField(name)) return EMPTY_STRING_BUILDER;

        return super.parseField(access, name, desc, signature, value);
    }

    private static boolean needSkipMethod(String methodName) {
        final String[] methodsForSkip = {
//                "<init>",
//                "<cinit>",

                "this$dist$invoke$1",
                "this$dist$set$1",
                "this$dist$get$1",
                "$getStaticMetaClass",
                "getMetaClass",
                "setMetaClass",
                "invokeMethod",
                "getProperty",
                "setProperty",
                "__$swapInit",
                "super$1$notify",
                "super$1$hashCode",
                "super$1$toString",
                "super$1$clone",
                "super$1$wait",
                "super$1$wait",
                "super$1$wait",
                "super$1$notifyAll",
                "super$1$equals",
                "super$1$finalize",
                "super$1$getClass",
                "$createCallSiteArray",
                "$getCallSiteArray",
                "class$"
        };
        for (String skip : methodsForSkip)
            if (skip.equals(methodName)) return true;
        return false;
    }

    private static boolean needSkipField(String fieldName) {
        final String[] skippedFields = {
                "metaClass",
                "__timeStamp",
                "__timeStamp__239_neverHappen1458945160610"
        };
        for (String f : skippedFields)
            if (f.equals(fieldName)) return true;
        return fieldName.contains("$");
    }
}
