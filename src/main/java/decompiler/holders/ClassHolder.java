package decompiler.holders;

import decompiler.ObjectType;

import java.util.ArrayList;
import java.util.List;

public class ClassHolder {
    public ObjectType type;
    public AnnotationHolder annotationHolder;
    public HeadHolder header;
    public List<FieldHolder> fields = new ArrayList<>();
    public List<MethodHolder> methods = new ArrayList<>();
    public List<ClassHolder> inner = new ArrayList<>();


    @Override
    public String toString() {
        return super.toString();
    }

    public StringBuilder toStringBuilder() {
        return new StringBuilder();
    }

    public boolean contains(MethodHolder method) {
        // TODO check exceptions, desc, signature
        for(MethodHolder m : methods) {
            if (m.name.equals(method.name))
                return true;
        }
        return false;
    }
}
