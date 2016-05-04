package decompiler.pasers;

import decompiler.holders.HeadHolder;
import decompiler.utils.ParserUtils;

import static decompiler.utils.MethodParserUtils.parseSignature;
import static decompiler.utils.ParserUtils.EMPTY_STRING;
import static decompiler.utils.ParserUtils.filterInterfaces;
import static decompiler.utils.ParserUtils.parseInterfaces;

public class TraitParser  extends ASMParser {
    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return null;
    }

    @Override
    public HeadHolder parseHeader(int version, int access, String name, String signature, String superName, String[] interfaces) {
        HeadHolder holder = createHeadHolder(version, access, name, signature, superName, interfaces);
        holder.parsedName = parseTraitName(name);
        holder.parsedSignature = parseSignature(signature);
        holder.parsedInterface = ParserUtils.EMPTY_STRING;
        interfaces = filterInterfaces(interfaces);
        if (interfaces.length > 0) {
            holder.parsedInterface =
                    parseInterfaces(access, interfaces, (signature == null) ? " implements " : EMPTY_STRING).toString();
        }

        holder.parsedSuper = EMPTY_STRING;
        if (signature != null) {
            superName = superName.replace('/', '.');
            if (!ParserUtils.OBJECT.equals(superName)) {
                holder.parsedSuper = "extends " + superName.replace('/', '.');
            }

        }
        return holder;
    }

    private String parseTraitName(String name) {
        int i = name.lastIndexOf('/');
        return "trait " + name.substring(++i);
    }
}
