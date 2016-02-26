package decompiler;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.Type;

/**
 * Decompile common things like variables
 */
public class FieldsDecompiler {
    private Handler handler;
    public StringBuilder decompile(JavaClass clazz) {
        StringBuilder sb = new StringBuilder();
        for (Field field : clazz.getFields()) {

            Type type = field.getType();
//            if (type.equals(Type.OBJECT)) {
//                System.out.println("def ");
//            } else {
//                System.out.println(type);
//            }
//            System.out.println(field.getName());
            sb
                    .append('\t')
                    .append(type.equals(Type.OBJECT)? "def":type)
                    .append(' ')
                    .append(field.getName())
                    .append('\n');
//            System.out.println(field.getAttributes());
        }
        return sb;
    }
    public FieldsDecompiler(Handler handler) {
        this.handler = handler;
    }
}
