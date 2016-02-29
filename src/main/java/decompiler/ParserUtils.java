package decompiler;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

public class ParserUtils {

    public static DecompilerParser getParser(ObjectType type) {
        switch (type) {
            case INTERFACE:
                return new InterfaceParser();
            default:
                throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    public static ObjectType getType(int access) {
        if ((access & (ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT )) != 0) {
            return ObjectType.INTERFACE;
        } else {
            throw new UnsupportedOperationException("I can create only interface parser");
        }
    }



    public static String getShortName(String name, String toImport) {
        return name.replace("/", ".").replace("java.lang.", "");
    }

    public static String getShortName(String name) {
        return name.replace("/", ".").replace("java.lang.", "");
    }
}
