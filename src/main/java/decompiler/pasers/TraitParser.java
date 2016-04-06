package decompiler.pasers;

import decompiler.holders.HeadHolder;

public class TraitParser  extends ASMParser {
    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return null;
    }

    @Override
    public HeadHolder parseHeader(int version, int access, String name, String signature, String superName, String[] interfaces) {
        HeadHolder holder = createHeadHolder(version, access, name, signature, superName, interfaces);
        holder.parsedName = parseTraitName(name);
        return holder;
    }

    private String parseTraitName(String name) {
        int i = name.lastIndexOf('/');
        return "trait " + name.substring(++i);
    }
}
