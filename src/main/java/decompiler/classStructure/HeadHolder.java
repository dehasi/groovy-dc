package decompiler.classStructure;

/**
 * Created by Rafa on 29.02.2016.
 */
public class HeadHolder {
    private int version;
    private int access;
    private String name;
    private String signature;
    private String superName;
    private String[] interfaces;

    public HeadHolder(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    public int getVersion() {
        return version;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public String getSuperName() {
        return superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }
}
