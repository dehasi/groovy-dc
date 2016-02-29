package decompiler.classStructure;

/**
 * Created by Rafa on 29.02.2016.
 */
public class MethodHolder {
    public final int access;
    public final String name;
    public final String desc;
    public final String signature;
    public final String[] exceptions;

    public MethodHolder(int access, String name, String desc, String signature, String[] exceptions) {
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
    }
    public MethodHolder (Builder builder) {
        this.access = builder.access;
        this.name = builder.name;
        this.desc = builder.desc;
        this.signature = builder.signature;
        this.exceptions = builder.exceptions;
    }

    public class Builder {
        private int access;
        private String name;
        private String desc;
        private String signature;
        private String[] exceptions;

        public MethodHolder build () {
            return new MethodHolder(this);
        }
    }
}
