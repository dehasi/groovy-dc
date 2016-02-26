package decompiler;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

/**
 * Decompile common things like variables
 */
public class FieldsDecompiler {
    private Handler handler;
    public StringBuilder decompile(JavaClass clazz) {
        StringBuilder sb = new StringBuilder();
        for (Field field : clazz.getFields()) {
            System.out.println(field.getAttributes());
        }
        return sb;
    }
    public FieldsDecompiler(Handler handler) {
        this.handler = handler;
    }
}
