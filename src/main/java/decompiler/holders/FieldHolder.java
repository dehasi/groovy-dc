package decompiler.holders;


import java.util.List;

public class FieldHolder {
    public List<AntHolder> annotations;
    public StringBuilder field;
    public String name;


    public FieldHolder(String name, StringBuilder field) {
        this.field = field;
        this.name = name;
    }

    public void setAnnotations(List<AntHolder> annotations) {
        this.annotations = annotations;
    }

    public List<AntHolder> getAnnotations() {
        return annotations;
    }
}
