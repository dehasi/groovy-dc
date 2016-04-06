package decompiler.holders;

public class HeadHolder {

    public int version;
    public int access;
    public String name;
    public String signature;
    public String superName;
    public String[] interfaces;


    public String parsedName;
    public String parsedSignature;
    public String parsedInterface;

    @Override
    public String toString() {

        return parsedName  + " " +  parsedSignature  + " " +  parsedInterface + "{\n";
    }
}
