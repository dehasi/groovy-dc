package decompiler.utils;

import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;

import static decompiler.utils.ParserUtils.EMPTY_STRING;
import static decompiler.utils.ParserUtils.EMPTY_STRING_BUILDER;
import static decompiler.utils.ParserUtils.getShortName;
import static org.objectweb.asm.Opcodes.ASM4;

public class MethodParserUtils {

    public static String parseSignature(String signature) {
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);
        return v.getDeclaration();
    }


    public static String getMethodReturnValue(String signature) {
        TraceSignatureVisitor v = new TraceSignatureVisitor(ASM4);
        new SignatureReader(signature).accept(v);
        return v.getReturnType().length() != 0 ? v.getReturnType() : "void";
    }

    public static StringBuilder parseExceptions(String[] exceptions) {
        if (exceptions == null || exceptions.length == 0) { return EMPTY_STRING_BUILDER; }

        StringBuilder sb = new StringBuilder();
        sb.append(" throws ");
        for (String ex : exceptions) {
            sb.append(getShortName(ex)).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb;
    }

    public static StringBuilder parseMethodArgs(String declatation) {
        if (declatation == null || declatation.length() == 0) return EMPTY_STRING_BUILDER;
        StringBuilder sb = new StringBuilder();
        int begin = declatation.indexOf('(');
        int end =  declatation.indexOf(')');
        if (end - begin == 1 ) {
            return sb.append(declatation);
        }
        String args = declatation.substring(begin + 1,end);
        int i = 0;
        final String var = "var";
        sb.append('(');
        for (String t : args.split(",")) {
            sb.append(getShortName(t.trim())).append(' ').append(var).append(i++).append(',').append(' ');
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.append(')');
    }

    public static String getMethodGeneticDeclatation(String signature, String declaration) {
        String generic = EMPTY_STRING;
        if (signature != null) {
            int begin = declaration.indexOf('<');
            if (begin != -1) {
                generic = declaration.substring(declaration.indexOf('<'), declaration.indexOf('>') + 1) + " ";
            }
        }
        return generic;
    }
}
