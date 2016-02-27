package decompiler;

public class ParserUtils {

    public static DecompilerParser getParser(ObjectType type) {
        switch (type) {
            case INTERFACE: return new InterfaceParser();
            default: throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    public static ObjectType getType(String name) {
        //TODO: how to split interface and class?
        return ObjectType.INTERFACE;
    }

    public static String getShortName(String name, String toImport) {
        return name.replace("/", ".").replace("java.lang.", "");
    }

    public static String getShortName(String name) {
        return name.replace("/", ".").replace("java.lang.", "");
    }
}
