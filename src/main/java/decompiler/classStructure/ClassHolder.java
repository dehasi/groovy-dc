package decompiler.classStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafa on 29.02.2016.
 */
public class ClassHolder {
    public HeadHolder head;
    public List<FieldHolder> fields;
    public List<MethodHolder> methods;


    public ClassHolder(Builder builder) {
    }

    public static class Builder {
        HeadHolder head;
        List<FieldHolder> fields;
        List<MethodHolder> methods;

        public Builder addField(FieldHolder field) {
            if (fields == null) {
                fields = new ArrayList<>();
            }
            fields.add(field);
            return this;
        }

        public Builder addHead(HeadHolder head) {
            this.head = head;
            return this;
        }

        public ClassHolder build() {
            return new ClassHolder(this);
        }
    }
}
