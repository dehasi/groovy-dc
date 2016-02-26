package decompiler;


import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

public class InterfaceDecompiler {

    //TODO: possible extract interface
    public StringBuilder decompile(JavaClass clazz) {
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
//        sb.append(clazz.getClassName());
        sb
                .append("package ")
                .append(clazz.getPackageName())
                .append(";\n")
                .append(clazz.getClassName().replaceAll(clazz.getPackageName() +".", ""))
        ;
        return sb;
    }


    private StringBuilder decompileFields(JavaClass clazz) {
        StringBuilder sb = new StringBuilder();
        for (Field field : clazz.getFields()) {
            Type type = field.getType();
            sb
                    .append('\t')
                    .append(type.equals(Type.OBJECT)? "def":type)
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
                    .append(returnType.equals(Type.OBJECT)? "def":returnType)
                    .append(' ')
                    .append(method.getName()) //TODO: filter name java.lang.Integer -> Integer
                    .append('\n');
        }

        return sb;
    }
}
