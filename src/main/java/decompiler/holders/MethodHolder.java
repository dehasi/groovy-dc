package decompiler.holders;

public class MethodHolder {
    public int access;
    public String name;
    public String desc;
    public String signature;
    public String[] exceptions;

    public String[] parsedAnnotations;
    public StringBuilder parsedExceiptions;
    public String[] parsedArgs;
    public String parsedGenericDeclaration;
}
