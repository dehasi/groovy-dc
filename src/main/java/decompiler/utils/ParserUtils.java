package decompiler.utils;

import decompiler.ObjectType;
import decompiler.pasers.ASMParser;
import decompiler.pasers.ClassParser;
import decompiler.pasers.InterfaceParser;
import decompiler.pasers.TraitParser;
import decompiler.visitors.SVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;

public class ParserUtils {
    public static final String TRAINT_ANNOTATION = "Lgroovy/transform/Trait;";
    public static final String DEF = "def";
    public static final StringBuilder EMPTY_STRING_BUILDER = new StringBuilder();
    public static final String EMPTY_STRING = "";
    public static final String GROOVY_OBJECT = "groovy.lang.GroovyObject";
    public static final String OBJECT = "java.lang.Object";

    public static ASMParser getParser(ObjectType type) {
        switch (type) {
            case INTERFACE:
                return new InterfaceParser();
            case CLASS:
                return new ClassParser();
            case TRAIT:
                return new TraitParser();
            default:
                throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    public static ObjectType getType(int access) {
        if ((access == (ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT))) {
            return ObjectType.INTERFACE;
        } else if (((ACC_PUBLIC & access) == ACC_PUBLIC) && ((ACC_SUPER & access) == ACC_SUPER)) {
            return ObjectType.CLASS;
        } else {
            return ObjectType.CLASS;

//            throw new UnsupportedOperationException("I can create only interface parser");
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

    public static boolean isInterface(int access) {
        return (access == (ACC_PUBLIC + ACC_INTERFACE + ACC_ABSTRACT));
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

    public static StringBuilder parseInterfaces(int access, String[] interfaces, String extOrImp) {
        if (interfaces == null || interfaces.length == 0) return EMPTY_STRING_BUILDER;

        StringBuilder sb = new StringBuilder();
        sb.append(extOrImp);
        for (String s : interfaces) {
            sb.append(s.replace('/', '.')).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }

    private static boolean needSkipInterface (String name) {
        final String[] forSkip = {
                "groovy.lang.GroovyObject"
        };
        for (String i : forSkip)
            if (i.equals(name)) return true;
        return false;
     }

    public static StringBuilder getModifiers(int access) {
        StringBuilder buf = new StringBuilder();
        for (int bit; access != 0; access -= bit) {
            bit = access & -access;
            switch (bit) {
                case Opcodes.ACC_PUBLIC:
                    buf.append("public ");
                    break;
                case Opcodes.ACC_PRIVATE:
                    buf.append("private ");
                    break;
                case Opcodes.ACC_PROTECTED:
                    buf.append("protected ");
                    break;
                case Opcodes.ACC_STATIC:
                    buf.append("static ");
                    break;
                case Opcodes.ACC_FINAL:
                    buf.append("final ");
                    break;
                case Opcodes.ACC_ABSTRACT:
                    buf.append("abstract ");
                    break;
                case Opcodes.ACC_NATIVE:
                    buf.append("native ");
                    break;
                case Opcodes.ACC_STRICT:
                    buf.append("strictfp ");
                    break;
                case Opcodes.ACC_SYNCHRONIZED:
                    buf.append("synchronized ");
                    break;
            }
        }
        return buf;
    }
}
