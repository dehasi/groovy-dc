package decompiler.pasers;

public interface ASMParser {
    StringBuilder parseField(int access, String name, String desc, String signature, Object value);
    StringBuilder parseMethod(int access, String name, String desc, String signature, String[] exceptions);
    StringBuilder parseHeader(int version, int access, String name,String signature, String superName, String[] interfaces);

}
