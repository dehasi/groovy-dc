package decompiler.pasers;


import static decompiler.pasers.ParserUtils.getDeclatation;
import static decompiler.pasers.ParserUtils.parseInterfaces;

public class ClassParser extends ASMParser {
    @Override
    public StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder parseHeader(int version, int access, String name, String signature, String superName, String[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n')
                .append(parseClassName(access, name));

        if (signature != null) {
            sb.append(getDeclatation(signature));
        }
        sb.append(' ')
                .append(parseInterfaces(access, interfaces, "implements "))
                .append(" {\n");
        return sb;
    }

    private String parseClassName(int access, String name) {
        int i = name.lastIndexOf('/');
        return "class " + name.substring(++i);
    }

    @Override
    public StringBuilder parseField(int access, String name, String desc, String signature, Object value) {
        if (name.contains("$")) {
            return new StringBuilder();
        }
        return super.parseField(access, name, desc, signature, value);
    }
}
