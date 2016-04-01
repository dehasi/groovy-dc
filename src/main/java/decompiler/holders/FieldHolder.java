package decompiler.holders;


import java.util.List;

public class FieldHolder {
    public List<AnnotationHolder> annotations;
    public StringBuilder field;
    public String name;


    public FieldHolder(String name, StringBuilder field) {
        this.field = field;
        this.name = name;
    }
}
