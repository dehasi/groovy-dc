package decompiler;

import decompiler.visitors.SVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;

import java.util.List;

import static decompiler.ParserUtils.getShortName;
import static decompiler.ParserUtils.isInterface;
import static decompiler.ParserUtils.parseInterfaceName;
import static decompiler.ParserUtils.parsePackagaName;

/**
 * Created by Rafa on 28.02.2016.
 */
public class InterfaceParser implements ASMParser {
    public static final String DEF = "def";

    private static String[] simpleTypeNames = {
            "void",
            "boolean",
            "char",
            "byte",
            "short",
            "int",
            "float",
            "long",
            "double",
            "array",
            "Object"
    };

    @Override
    public StringBuilder parseField(int access, String name, String desc, String signature, Object value) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(Field(desc, signature))
                .append(' ')
                .append(name)
                .append(parseValue(value))
                .append('\n');
        return sb;
    }

    protected StringBuilder parseValue(Object value) {
        StringBuilder sb = new StringBuilder();
        if (value == null) {
            return sb;
        }
        return sb.append(" = ").append(value);
    }

    protected StringBuilder Field(String desc, String signature) {
        StringBuilder sb = new StringBuilder();
        sb.append('\t');
        if (signature != null) {
            SVisitor sVisitor = new SVisitor();
            SignatureReader signatureReader = new SignatureReader(signature);
            signatureReader.accept(sVisitor);
            //TODO <generic> magic
            int t = 0;
            t++;
        } else {
            Type t = Type.getType(desc);
            if (desc.length() > 1) {
                sb.append(parseObjName(t));
            } else {
                sb.append(t.getClassName());
            }

        }
        return sb;
    }

    private StringBuilder parseObjName(Type type) {
        StringBuilder sb = new StringBuilder();
        if (type.getClassName().equals("java.lang.Object")) {
            sb.append(DEF);
        } else {
            sb.append(type.getClassName());
        }
        return sb;
    }

    @Override
    public StringBuilder parseHeader(int version, int access, String name,
                                     String signature, String superName, String[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(parsePackagaName(name))
                .append('\n')
                .append(parseInterfaceName(name))
                .append(parseInterfaceSignature(signature))
                .append(' ')
                .append(parseInterfaces(access, interfaces))
                .append(" {\n");
        return sb;
    }


    private StringBuilder parseInterfaceSignature(String signature) {
        StringBuilder sb = new StringBuilder();
        if (signature == null) {
            return sb;
        }
        SignatureReader signatureReader = new SignatureReader(signature);
        SVisitor sVisitor = new SVisitor();
        signatureReader.accept(sVisitor);
        List<String> res = sVisitor.getSignatureMap().get(SVisitor.formalTypeParameter);
        //TODO: refactor
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

    private StringBuilder parseInterfaces(int access, String[] interfaces) {
        StringBuilder sb = new StringBuilder();
        if (interfaces == null || interfaces.length == 0) {
            return sb;
        }
        sb.append(isInterface(access)? "extends ":"implements ");
        for (String s : interfaces) {
            sb.append(s).append(',');
        }
        sb.setLength(sb.length() -1);
        return sb;
    }

    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.indexOf('<') != -1) {
            return new StringBuilder();
        }
        StringBuilder sb = new StringBuilder("\t");
        if (signature != null) {
            //TODO: generics
        } else {
            sb
                    .append(parseMethodReturnType(desc, signature))
                    .append(' ')
                    .append(name)
                    .append(parseMethodArgs(desc, signature))
                    .append(parseExceptions(exceptions));
        }
        return sb.append('\n');
    }

    private StringBuilder parseExceptions(String[] exceptions) {
        StringBuilder sb = new StringBuilder();
        if (exceptions == null || exceptions.length == 0) {
            return sb;
        }

        sb.append(" throws ");
        for (String ex : exceptions) {
            sb.append(getShortName(ex)).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb;
    }

    private StringBuilder parseMethodReturnType(String desc, String signature) {
        StringBuilder sb = new StringBuilder();
        Type t = Type.getReturnType(desc);
        return sb.append(parseObjName(t));
    }

    private StringBuilder parseMethodArgs(String desc, String signature) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        final String var = "var";
        sb.append('(');
        for (Type t : Type.getArgumentTypes(desc)) {
            sb.append(parseObjName(t))
                    .append(' ')
                    .append(var).append(i).append(',').append(' ');
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.append(')');
    }
}
