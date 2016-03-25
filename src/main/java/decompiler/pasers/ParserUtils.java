package decompiler.pasers;

import decompiler.ObjectType;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Collection;
import java.util.Collections;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ASM4;

public class ParserUtils {
    private static final String TRAINT_ANNOTATION = "Lgroovy/transform/Trait;";

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


    public static String getShortName(String name, String toImport) {
        return name.replace("/", ".").replace("java.lang.", "");
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

    public <E> Collection<E> emptyIfNull(Collection<E> collection) {
        if (collection == null) {
            return Collections.emptyList();
        }
        return collection;
    }

    public static boolean isInterface(int access) {
        return (access == (ACC_PUBLIC + ACC_INTERFACE + ACC_ABSTRACT));
    }

    //TODO may be useful in future
    public static String decodeMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (signature == null) signature = desc;
        StringBuilder sb = new StringBuilder();
        if (access != -1) {
            appendModifiers(sb, access);
        }
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);

        String declaration = v.getDeclaration(), rType = v.getReturnType();
        if (declaration.charAt(0) == '<') {
            sb.append(declaration, 0, declaration.indexOf("(")).append(' ');
        } else if (rType.isEmpty() || rType.charAt(0) == '[') {
            sb.append("java.lang.Object");
        }
        sb.append(rType).append(' ').append(name).append('(');

        // TODO: deep magic. refactor and clariify what if arrays
        int i = 0;
        for (String s :  declaration.substring(declaration.indexOf('(')+1,declaration.length() -1).split(",")) {
            sb.append(s).append(" var").append(i).append(", ");
            ++i;
        }
        if (i > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(')');
        if ((access & Opcodes.ACC_VARARGS) != 0 && declaration.endsWith("[])")) {
            sb.replace(sb.length() - 3, sb.length(), "...)");
        }
        String genericExceptions = v.getExceptions();

        if (genericExceptions != null && !v.getDeclaration().isEmpty()) {
            sb.append(" throws ").append(genericExceptions);
        } else if (exceptions != null && exceptions.length > 0) {
            sb.append(" throws ");
            int pos = sb.length();
            for (String e : exceptions) sb.append(e).append(", ");
            int e = sb.length() - 2;
            sb.setLength(e);
            for (; pos < e; pos++) if (sb.charAt(pos) == '/') sb.setCharAt(pos, '.');
        }
        return sb.toString();
    }

    private static void appendModifiers(StringBuilder buf, int access) {
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
    }

    public static String getDeclatation(String signature) {
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);
        return v.getDeclaration();
    }


    public static String getReturnValue(String signature) {
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);
        return v.getReturnType().length() != 0?v.getReturnType():"void";
    }
    public static boolean isTrait(String desc, boolean visible) {
        return TRAINT_ANNOTATION.equals(desc);
    }
}
