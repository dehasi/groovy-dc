package decompiler.pasers;

import decompiler.holders.FieldHolder;
import decompiler.holders.MethodHolder;
import decompiler.utils.MethodParserUtils;
import decompiler.utils.ParserUtils;
import decompiler.visitors.SVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static decompiler.pasers.ClassParser.needSkipMethod;
import static decompiler.utils.MethodParserUtils.parseExceptions;
import static decompiler.utils.MethodParserUtils.parseSignature;
import static decompiler.utils.ParserUtils.getShortName;

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

    public static MethodHolder createMethodHolder(int access, String name, String desc, String signature, String[] exceptions) {
        MethodHolder holder = new MethodHolder();
        holder.skip = needSkipMethod(name);
        holder.access = access;
        holder.name = name;
        holder.desc = desc;
        holder.signature = signature;
        holder.exceptions = exceptions;

        holder.parsedModifiers = ParserUtils.getModifiers(access);
        holder.parsedGenericDeclaration = MethodParserUtils.getMethodGeneticDeclatation(signature, parseSignature(signature != null ? signature : desc));
        holder.parsedReturnValue = MethodParserUtils.getMethodReturnValue(signature != null ? signature : desc);
        holder.parsedArgs = createMethodArgsArray(parseSignature(signature != null ? signature : desc));
        holder.parsedExceiptions = parseExceptions(exceptions);
        return holder;
    }

    public static FieldHolder createFiledHolder (int access, String name, String desc, String signature, Object value){
        FieldHolder holder = new FieldHolder();
        holder.access = access;
        holder.name = name;
        holder.desc = desc;
        holder.signature = signature;
        holder.value = value;

        return holder;
    }

    private static String[] createMethodArgsArray(String declatation) {
        if (declatation == null || declatation.length() == 0) return new String[0];
        List<String> argList = new ArrayList<>();
        int begin = declatation.indexOf('(');
        int end = declatation.indexOf(')');
        if (end - begin == 1) {
            return new String[0];
        }
        String args = declatation.substring(begin + 1, end);
        int i = 0;
        final String var = "var";

        for (String t : args.split(",")) {
            argList.add(getShortName(t.trim()) + ' ' + var + i++);
        }
        return argList.toArray(new String[argList.size()]);
    }
}
