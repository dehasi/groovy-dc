package decompiler.pasers;

import static decompiler.pasers.ParserUtils.EMPTY_STRING_BUILDER;
import static decompiler.pasers.ParserUtils.getDeclatation;
import static decompiler.pasers.ParserUtils.getShortName;
import static decompiler.pasers.ParserUtils.parseInterfaces;
public class InterfaceParser extends ASMParser {

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
                .append(parseInterfaces(access, interfaces, "extends "))
                .append(" {\n");
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
        if (exceptions == null || exceptions.length == 0) { return EMPTY_STRING_BUILDER; }

        StringBuilder sb = new StringBuilder();
        sb.append(" throws ");
        for (String ex : exceptions) {
            sb.append(getShortName(ex)).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb;
    }


    private StringBuilder parseMethodArgs(String declatation) {
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



    public static String parseInterfaceName(String name) {
        int i = name.lastIndexOf('/');
        return "interface " + name.substring(++i);
    }




}
