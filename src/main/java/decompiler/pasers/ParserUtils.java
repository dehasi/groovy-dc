package decompiler.pasers;

import decompiler.ObjectType;
import decompiler.visitors.SVisitor;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ASM4;

public class ParserUtils {
    private static final String TRAINT_ANNOTATION = "Lgroovy/transform/Trait;";
    private static final String DEF = "def";

    public static ASMParser getParser(ObjectType type) {
        switch (type) {
            case INTERFACE:
                return new InterfaceParser();
            default:
                throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    public static ObjectType getType(int access) {
        if ((access == (ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT))) {
            return ObjectType.INTERFACE;
        } else {
            throw new UnsupportedOperationException("I can create only interface parser");
        }
    }

    public static String getShortName(String name) {
        if (name.equals("java.lang.Object")) return "def";
        return name.replace("/", ".").replace("java.lang.", "");
    }

    public static ASMParser getParser(int access) {
        return getParser(getType(access));
    }

    public static String parsePackagaName(String name) {
        int i = name.lastIndexOf('/');
        return "package " + name.substring(0, i).replace('/', '.') + ";\n";
    }

    public static String parseInterfaceName(String name) {
        int i = name.lastIndexOf('/');
        return "interface " + name.substring(++i);
    }



    public static boolean isInterface(int access) {
        return (access == (ACC_PUBLIC + ACC_INTERFACE + ACC_ABSTRACT));
    }


    public static String getDeclatation(String signature) {
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);
        return v.getDeclaration();
    }


    public static String getReturnValue(String signature) {
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);
        return v.getReturnType().length() != 0 ? v.getReturnType() : "void";
    }

    public static boolean isTrait(String desc, boolean visible) {
        return TRAINT_ANNOTATION.equals(desc);
    }

    public static StringBuilder parseObjName(Type type) {
        StringBuilder sb = new StringBuilder();
        if (type.getClassName().equals("java.lang.Object")) {
            sb.append(DEF);
        } else if (type.getClassName().startsWith("java.lang.")) {
            sb.append(type.getClassName().substring(10));

        } else {
            sb.append(type.getClassName());
        }
        return sb;
    }

    public static Map<String, List<String>> getSignatureMap(String signature) {
        Map<String, List<String>> signatureMap = new HashMap<>();
        org.objectweb.asm.signature.SignatureReader signatureReader = new org.objectweb.asm.signature.SignatureReader(signature);
        SVisitor sVisitor = new SVisitor(signatureMap);
        signatureReader.accept(sVisitor);
        return signatureMap;
    }
}
