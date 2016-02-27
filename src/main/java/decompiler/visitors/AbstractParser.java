package decompiler.visitors;

import org.objectweb.asm.Type;

public class AbstractParser {
    public static final String DEF = "def";

    private static String[] simpleTypeNames= {
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

    protected StringBuilder parseInterfaceField(int access, String name, String desc, String signature, Object value) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(parseType(desc, signature))
                .append(' ')
                .append(name)
                .append('\n');
        return sb;
    }

    protected StringBuilder parseValue(Object value) {
        StringBuilder sb = new StringBuilder();
        if (value == null) {
            return sb;
        }
        return sb.append(value);
    }

    protected StringBuilder parseType(String desc, String signature) {
        StringBuilder sb = new StringBuilder();

        if (signature != null) {
            //TODO <generic> magic
        } else {
            Type t = Type.getType(desc);
            if (desc.length() > 1) {
                sb.append(parseObjName(t));
            } else {
                sb.append(simpleTypeNames[t.getSort()]);
            }

        }
        return sb;
    }

    private StringBuilder parseObjName(Type type) {
        StringBuilder sb = new StringBuilder();
        switch (type.getClassName().charAt(0)) {
            case 'B':
                sb.append("Byte");
                break;
            case 'C':
                sb.append("Character");
                break;
            case 'D':
                sb.append("Double");
                break;
            case 'L':
                //TODO check
                sb.append(DEF);
                break;
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
                break;
//                for (var2 = 1; var0[var1 + var2] != 59; ++var2) {
//                    ;
//                }
//
//                return new Type(10, var0, var1 + 1, var2 - 1);
            case 'F':
                sb.append("Float");
                break;
            case 'I':
                sb.append("Integer");
                break;
            case 'J':
                sb.append("Long");
                break;
            case 'S':
                sb.append("Short");
                break;
            case 'V':
                sb.append("Void");
                break;
            case 'Z':
                sb.append("Boolean");
                break;
            case '[':
//                for (var2 = 1; var0[var1 + var2] == 91; ++var2) {
//                    ;
//                }
        }
                return sb;
        }
    }
