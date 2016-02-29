package decompiler.classStructure;

/**
 * Created by Rafa on 29.02.2016.
 */
public class HeadHolder {
    int version; int access; String name;
    String signature; String superName; String[] interfaces;

    public HeadHolder(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }
}
