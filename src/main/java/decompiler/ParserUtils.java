package decompiler;

import java.util.Collection;
import java.util.Collections;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

public class ParserUtils {

    public static ASMParser getParser(ObjectType type) {
        switch (type) {
            case INTERFACE:
                return new InterfaceParser();
            default:
                throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    public static ObjectType getType(int access) {
        if ((access  ==  (ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT ))) {
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

    public static ASMParser getParser(int access) {
        return getParser(getType(access));
    }

    public static String parsePackagaName(String name) {
        int i = name.lastIndexOf('/');
        return "package " + name.substring(0,i).replace('/', '.') + ";";
    }

    public static String parseInterfaceName(String name) {
        int i = name.lastIndexOf('/');
        return "interface " + name.substring(++i);
    }

    public <E> Collection<E> emptyIfNull(Collection<E> collection) {
        if (collection == null) {
            return Collections.emptyList();
        }
        return collection;
    }

    public static boolean isInterface (int access) {
        return (access == (ACC_PUBLIC + ACC_INTERFACE + ACC_ABSTRACT));
    }
}
