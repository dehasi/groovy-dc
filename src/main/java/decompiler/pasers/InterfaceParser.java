package decompiler.pasers;

import static decompiler.pasers.ParserUtils.parseInterfaces;
import static decompiler.utils.MethodParserUtils.getMethod;
import static decompiler.utils.MethodParserUtils.getMethodGeneticDeclatation;
import static decompiler.utils.MethodParserUtils.getMethodReturnValue;
import static decompiler.utils.MethodParserUtils.parseExceptions;
import static decompiler.utils.MethodParserUtils.parseMethodArgs;

public class InterfaceParser extends ASMParser {

    @Override
    public StringBuilder parseHeader(int version, int access, String name,
                                     String signature, String superName, String[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append(parseInterfaceName(name));

        if (signature != null) { sb.append(getMethod(signature)); }

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

        String rv = getMethodReturnValue(signature != null ? signature : desc);
        String declaration = getMethod(signature != null ? signature : desc);
        String generic = getMethodGeneticDeclatation(signature, declaration);

        StringBuilder sb = new StringBuilder("\t");
        sb.append(generic)
                .append(rv)
                .append(' ')
                .append(name)
                .append(parseMethodArgs(declaration))
                .append(parseExceptions(exceptions));
        return sb.append('\n');
    }

    public static String parseInterfaceName(String name) {
        int i = name.lastIndexOf('/');
        return "interface " + name.substring(++i);
    }

}
