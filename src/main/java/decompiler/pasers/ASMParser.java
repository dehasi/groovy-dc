package decompiler.pasers;

import decompiler.visitors.SVisitor;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.Map;

public abstract class ASMParser {

    public static final String DEF = "def";

    public StringBuilder parseField(int access, String name, String desc, String signature, Object value) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(parseFieldSignature(desc, signature))
                .append(' ')
                .append(name)
                .append(parseValue(value))
                .append('\n');
        return sb;
    }

    public abstract StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions);

    public abstract StringBuilder parseHeader(int version, int access, String name, String signature, String superName, String[] interfaces);

    public static StringBuilder parseAnnotation(String desc, boolean visible) {
        StringBuilder sb = new StringBuilder("@");
        sb.append(desc.substring(1).replace('/', '.').replace(';', ' '));
        return sb;
    }

    protected StringBuilder parseFieldSignature(String desc, String signature) {
        StringBuilder sb = new StringBuilder();
        sb.append('\t');
        if (signature != null) {
            Map<String, List<String>> stringListMap = ParserUtils.getSignatureMap(signature);
            sb.append(stringListMap.get(SVisitor.typeVariable).get(0));
        } else {
            Type t = Type.getType(desc);
            if (desc.length() > 1) {
                sb.append(ParserUtils.parseObjName(t));
            } else {
                sb.append(t.getClassName());
            }
        }
        return sb;
    }

    protected StringBuilder parseValue(Object value) {
        StringBuilder sb = new StringBuilder();
        if (value == null) {
            return sb;
        }
        return sb.append(" = ").append(value);
    }

}
