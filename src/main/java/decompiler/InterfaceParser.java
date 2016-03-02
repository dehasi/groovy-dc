package decompiler;

import org.objectweb.asm.Type;

import static decompiler.ParserUtils.getShortName;

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
        if (type.getClassName().equals("java.lang.Object")){
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
                //TODO: exdends implements
                .append(" {\n");
        return sb;
    }

    private String parsePackagaName(String name) {
        int i = name.lastIndexOf('/');
        return "package " + name.substring(0,i).replace('/', '.') + ";";
    }

    private String parseInterfaceName(String name) {
        int i = name.lastIndexOf('/');
        return "interface " + name.substring(++i);
    }

    private String parseSuperclassClassName(String superName) {
        return "";
    }

    private String parseInterfaces(String[] interfaces) {
        return "";
    }

    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        StringBuilder sb = new StringBuilder("\t");
//        Method
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
        for(String ex : exceptions) {
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
