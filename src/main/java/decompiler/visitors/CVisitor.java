package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class CVisitor extends AbstractParser implements ClassVisitor {

    private StringBuilder buffer = new StringBuilder();
    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {

        buffer.append("Visiting class: ").append(name).append('\n');
        buffer.append("Class Major Version: ").append(version).append('\n');
        buffer.append("Super class: ").append(superName).append('\n');
        for (String s : interfaces) {
            buffer.append("implements: ").append(s).append('\n');
        }
//        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(String s, String s1) {
        System.out.println("Visiting source: " + s);
        System.out.println(s1);
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
//        super.visitAnnotation(s,b);
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr) {
        System.out.println("Class Attribute: " + attr.type);
//        super.visitAttribute(attr);
    }

    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {

    }

    /**
     * 075
     * When a field is encountered
     * 076
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        buffer.append("Field:");
        buffer.append("name: ")
                .append(name)
                .append(" value:")
                .append(value)
                .append(" desc:")
                .append(desc)
                .append("signature ")
                .append(signature);
        return (new FVisitor());
    }


    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        return new MVisitor();
    }

    @Override
    public void visitEnd() {
        System.err.println("end");
    }

    public StringBuilder getBuffer() {
        return buffer;
    }
}
