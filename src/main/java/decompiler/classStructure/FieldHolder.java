package decompiler.classStructure;

public class FieldHolder {
    public final int access;
    public final String name;
    public final String desc;
    public final String signature;
    public final Object value;

    public FieldHolder(int access, String name, String desc, String signature, Object value) {
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.value = value;
    }
    public FieldHolder (Builder builder) {
        this.access = builder.access;
        this.name = builder.name;
        this.desc = builder.desc;
        this.signature = builder.signature;
        this.value = builder.value;
    }

    public class Builder {
        private int access;
        private String name;
        private String desc;
        private String signature;
        private Object value;

        FieldHolder build () {
            return new FieldHolder(this);
        }
    }
}
