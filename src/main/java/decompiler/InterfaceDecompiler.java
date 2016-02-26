package decompiler;


import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

public class InterfaceDecompiler {

    public StringBuilder decompile(JavaClass clazz) {
        ConstantPool constantPool = clazz.getConstantPool();
        StringBuilder sb = new StringBuilder();
        sb
                .append(decompileHeader(clazz))
                .append(" {\n")
                .append(decompileFields(clazz))
                .append('\n')
                .append(decompileMethods(clazz))
                .append("}");
        return sb;
    }

    private StringBuilder decompileHeader(JavaClass clazz) {

        StringBuilder sb = new StringBuilder();
        sb
                .append("package ")
                .append(clazz.getPackageName())
                .append(";\n")
                .append(clazz.getClassName().replaceAll(clazz.getPackageName() +".", ""));
        return sb;
    }


    private StringBuilder decompileFields(JavaClass clazz) {
        StringBuilder sb = new StringBuilder();
        for (Field field : clazz.getFields()) {
            Type type = field.getType();
            sb
                    .append('\t')
                    .append(defIfObj(type))
                    .append(' ')
                    .append(field.getName()) //TODO: filter name java.lang.Integer -> Integer
                    .append('\n');
        }
        return sb;
    }

    private StringBuilder decompileMethods(JavaClass clazz) {
        StringBuilder sb = new StringBuilder();
        for (Method method : clazz.getMethods()){
            Type returnType = method.getReturnType();
            sb
                    .append('\t')
                    .append(defIfObj(returnType))
                    .append(' ')
                    .append(method.getName())
                    .append('(')
                    .append(decompileMethodBody(method))
                    .append(')')
                    .append('\n');
        }

        return sb;
    }

    private StringBuilder decompileMethodBody(Method method) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        final String var = "var";
        for (Type type : method.getArgumentTypes() ) {
           sb
                    .append(defIfObj(type))
                    .append(' ')
                    .append(var).append(i)
                    .append(',')
                   .append(' ');
            ++i;
        }
        if (i > 0) {
            sb.setLength(sb.length()-2);
        }
        return sb;
    }

    private String defIfObj(Type type) {
        final String def = "def";
        return type.equals(Type.OBJECT)? def : type.toString();
    }
}
