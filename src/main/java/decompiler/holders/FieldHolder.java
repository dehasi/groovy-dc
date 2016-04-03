package decompiler.holders;


import java.util.List;

public class FieldHolder {
    public boolean visible = true;
    public int access;
    public String name;
    public String desc;
    public String signature;
    public Object value;

    public List<AnnotationHolder> annotations;
    public StringBuilder field;
    public StringBuilder parsedModifiers;

    public FieldHolder(String name, StringBuilder field) {
        this.field = field;
        this.name = name;
    }

    public FieldHolder() {
    }
}
