package decompiler.pasers;

public abstract class ASMParser {

    public static final String DEF = "def";

    public  abstract StringBuilder parseField(int access, String name, String desc, String signature, Object value);
    public  abstract StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions);
    public  abstract StringBuilder parseHeader(int version, int access, String name,String signature, String superName, String[] interfaces);
    public  static StringBuilder parseAnnotation(String desc, boolean visible) {
        StringBuilder sb = new StringBuilder("@");
        sb.append(desc.substring(1).replace('/','.').replace(';',' '));
        return sb;
    }

}
