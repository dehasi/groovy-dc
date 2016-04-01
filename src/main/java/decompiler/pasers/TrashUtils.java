package decompiler.pasers;

import decompiler.utils.ParserUtils;
import decompiler.visitors.SVisitor;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * Probably useless methods
 */
public class TrashUtils {

    private StringBuilder parseInterfaceSignature(String signature) {
        StringBuilder sb = new StringBuilder();
        if (signature == null) {
            return sb;
        }
        List<String> res = ParserUtils.getSignatureMap(signature).get(SVisitor.formalTypeParameter);
        if (res != null) {
            sb.append('<');
            int i = 0;
            for (String s : res) {
                sb.append(s).append(',');
                i++;
            }
            if (i > 0) {
                sb.setLength(sb.length() - 1);
            }
            sb.append('>');
        }
        return sb;
    }

    private String parseSuperclassClassName(String superName) {
        return "";
    }

    private StringBuilder parseMethodReturnType(String desc) {
        StringBuilder sb = new StringBuilder();
        Type t = Type.getReturnType(desc);
        return sb.append(ParserUtils.parseObjName(t));
    }

    public static <E> Collection<E> emptyIfNull(Collection<E> collection) {
        if (collection == null) {
            return Collections.emptyList();
        }
        return collection;
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
        for (String s : declaration.substring(declaration.indexOf('(') + 1, declaration.length() - 1).split(",")) {
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

    private StringBuilder parseMthodSignature(String signature) {
        StringBuilder sb = new StringBuilder();
        if (signature == null) {
            return sb;
        }
        Map<String, List<String>> signatureMap = ParserUtils.getSignatureMap(signature);

        if (signatureMap.get(SVisitor.formalTypeParameter) != null) {
            sb.append('<');
            for (String t : signatureMap.get(SVisitor.formalTypeParameter)) {
                sb.append(t).append(',');
            }
            sb.setLength(sb.length() - 1);
            sb.append('>');
        }
        return sb.append(' ');
    }

    private static void appendModifiers(StringBuilder buf, int access) {
        buf.append(ParserUtils.getModifiers(access));
    }
}
