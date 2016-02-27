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
        buffer.append(parseHeader(version, access, name, signature, superName, interfaces));
    }

    @Override
    public void visitSource(String s, String s1) {
//        System.out.println("Visiting source: " + s);
//        System.out.println(s1);
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
    }

    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {

    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        buffer.append(parseInterfaceField(access, name, desc, signature, value));
        return (new FVisitor());
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        buffer.append(parseMehod(access, name, desc, signature, exceptions));
        return new MVisitor();
    }

    @Override
    public void visitEnd() {
        buffer.append('}');
    }

    public StringBuilder getBuffer() {
        return buffer;
    }
}
