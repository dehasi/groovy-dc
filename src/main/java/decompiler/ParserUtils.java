package decompiler;

import org.objectweb.asm.Opcodes;

public class ParserUtils {

    public static DecompilerParser getParser(ObjectType type) {
        switch (type) {
            case INTERFACE: return new InterfaceParser();
            default: throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    public static ObjectType getType(int access) {
        switch (access) {
            case Opcodes.ACC_INTERFACE :{
                return ObjectType.INTERFACE;
            }
            default: {
                throw new UnsupportedOperationException("I can create only interface parser");
            }
        }
    }

    public static String getShortName(String name, String toImport) {
        return name.replace("/", ".").replace("java.lang.", "");
    }

    public static String getShortName(String name) {
        return name.replace("/", ".").replace("java.lang.", "");
    }
}
