package decompiler.pasers;

import decompiler.holders.HeadHolder;

import static decompiler.utils.MethodParserUtils.getMethodGeneticDeclatation;
import static decompiler.utils.MethodParserUtils.getMethodReturnValue;
import static decompiler.utils.MethodParserUtils.parseExceptions;
import static decompiler.utils.MethodParserUtils.parseMethodArgs;
import static decompiler.utils.MethodParserUtils.parseSignature;
import static decompiler.utils.ParserUtils.parseInterfaces;

public class InterfaceParser extends ASMParser {

    @Override
    public HeadHolder parseHeader(int version, int access, String name,
                                  String signature, String superName, String[] interfaces) {

        HeadHolder holder = createHeadHolder(version, access, name, signature, superName, interfaces);
        holder.parsedName = parseInterfaceName(name);
        holder.parsedSignature = parseSignature(signature);
        holder.parsedInterface = parseInterfaces(access, interfaces, "extends ").toString();
        return holder;
    }

    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.indexOf('<') != -1) {
            return new StringBuilder();
        }

        String rv = getMethodReturnValue(signature != null ? signature : desc);
        String declaration = parseSignature(signature != null ? signature : desc);
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
