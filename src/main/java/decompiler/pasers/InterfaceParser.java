package decompiler.pasers;

import decompiler.visitors.AVisitor;
import decompiler.visitors.SVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.Textifier;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static decompiler.pasers.ParserUtils.getDeclatation;
import static decompiler.pasers.ParserUtils.getShortName;
import static decompiler.pasers.ParserUtils.isInterface;
import static decompiler.pasers.ParserUtils.parseInterfaceName;

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
                .append(parseFieldSignature(desc, signature))
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

    protected StringBuilder parseFieldSignature(String desc, String signature) {
        StringBuilder sb = new StringBuilder();
        sb.append('\t');
        if (signature != null) {
            Map<String, List<String>> stringListMap = getSignatureMap(signature);
            sb.append(stringListMap.get(SVisitor.typeVariable).get(0));
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
        } else if (type.getClassName().startsWith("java.lang.")) {
            sb.append(type.getClassName().substring(10));

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

                .append('\n')
                .append(parseInterfaceName(name));
        if (signature != null) {
            sb.append(getDeclatation(signature));
        }
        sb.append(' ')
                .append(parseInterfaces(access, interfaces))
                .append(" {\n");
        return sb;
    }

    @Override
    public StringBuilder parseAnnotation(String desc, boolean visible) {
        StringBuilder sb = new StringBuilder("@");
        //TODO: implement parsing internals
        AVisitor aVisitor = new AVisitor(Opcodes.ASM4);
        Printer printer = new Textifier();
//        TraceAnnotationVisitor v = new TraceAnnotationVisitor(aVisitor,printer);

//        new (signature).accept(v);
        sb.append(desc.substring(1).replace('/','.').replace(';','\n'));
        return sb;
    }


    private StringBuilder parseInterfaceSignature(String signature) {
        StringBuilder sb = new StringBuilder();
        if (signature == null) {
            return sb;
        }
        List<String> res = getSignatureMap(signature).get(SVisitor.formalTypeParameter);
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
        sb.append(isInterface(access) ? "extends " : "implements ");
        for (String s : interfaces) {
            sb.append(s.replace('/', '.')).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }

    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.indexOf('<') != -1) {
            return new StringBuilder();
        }

        String declaration = ParserUtils.getDeclatation(signature != null ? signature : desc);
        String rv = ParserUtils.getReturnValue(signature != null ? signature : desc);
        String generic = "";
        if (signature != null) {
            int begin = declaration.indexOf('<');
            if (begin != -1) {
                generic = declaration.substring(declaration.indexOf('<'), declaration.indexOf('>') + 1) + " ";
                generic = "public " + generic;
            }
        }

        StringBuilder sb = new StringBuilder("\t");
        sb.append(generic)
                .append(rv)
                .append(' ')
                .append(name)
                .append(parseMethodArgs(declaration))
                .append(parseExceptions(exceptions));
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

    private StringBuilder parseMethodReturnType(String desc) {
        StringBuilder sb = new StringBuilder();
        Type t = Type.getReturnType(desc);
        return sb.append(parseObjName(t));
    }

    private StringBuilder parseMethodArgs(String declatation) {
        StringBuilder sb = new StringBuilder();
        if (declatation == null || declatation.length() == 0) {
            return sb;
        }
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

    private StringBuilder parseMthodSignature(String signature) {
        StringBuilder sb = new StringBuilder();
        if (signature == null) {
            return sb;
        }
        Map<String, List<String>> signatureMap = getSignatureMap(signature);

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

    private Map<String, List<String>> getSignatureMap(String signature) {
        Map<String, List<String>> signatureMap = new HashMap<>();
        SignatureReader signatureReader = new SignatureReader(signature);
        SVisitor sVisitor = new SVisitor(signatureMap);
        signatureReader.accept(sVisitor);
        return signatureMap;
    }



}
