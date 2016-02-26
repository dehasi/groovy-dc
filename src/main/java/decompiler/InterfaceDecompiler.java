package decompiler;


import org.apache.bcel.classfile.JavaClass;

public class InterfaceDecompiler {

    //TODO: DI
    private FieldsDecompiler fieldsDecompiler = new FieldsDecompiler(new Handler() {
        @Override
        public StringBuilder handle(StringBuilder sb) {
            return new StringBuilder();
        }
    });

    //TODO: possible extract interface
    public StringBuilder decompile(JavaClass clazz) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(decompileHeader(clazz))
                .append(" {\n")
                .append(fieldsDecompiler.decompile(clazz))
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
}
