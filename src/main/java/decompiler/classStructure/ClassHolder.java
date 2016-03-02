package decompiler.classStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafa on 29.02.2016.
 */
public class ClassHolder {
    private List<ImportHolder> imports;
    private HeadHolder head;
    private List<FieldHolder> fields;
    private List<MethodHolder> methods;
    private List<AnnotationHolder> annotations;


    public ClassHolder(Builder builder) {
        this.head = builder.head;
        this.fields = builder.fields;
        this.methods = builder.methods;
        this.annotations =builder.annotations;
    }

    public static class Builder {
        HeadHolder head;
        List<FieldHolder> fields;
        List<MethodHolder> methods;
        List<AnnotationHolder> annotations;

        public Builder addField(FieldHolder field) {
            if (fields == null) {
                fields = new ArrayList<>();
            }
            fields.add(field);
            return this;
        }

        public Builder addMethod(MethodHolder method) {
            if (methods == null) {
                methods = new ArrayList<>();
            }
            methods.add(method);
            return this;
        }

        public Builder addHead(HeadHolder head) {
            this.head = head;
            return this;
        }

        public Builder addAnnotation(AnnotationHolder annotation) {
            if (annotations == null) {
                annotations = new ArrayList<AnnotationHolder>();
            }
            annotations.add(annotation);
            return this;
        }

        public ClassHolder build() {
            return new ClassHolder(this);
        }
    }


    public List<ImportHolder> getImports() {
        return imports;
    }

    public HeadHolder getHead() {
        return head;
    }

    public List<FieldHolder> getFields() {
        return fields;
    }

    public List<MethodHolder> getMethods() {
        return methods;
    }

    public List<AnnotationHolder> getAnnotations() {
        return annotations;
    }
}
