package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class CVisitor implements ClassVisitor {

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        System.out.println("Visiting class: " + name);
        System.out.println("Class Major Version: " + version);
        System.out.println("Super class: " + superName);
        for (String s : interfaces) {
            System.out.println("implements: " + s);
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
        System.out.println("Class Attribute: "+attr.type);
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
    public FieldVisitor visitField(int access, String name,
                                   String desc, String signature, Object value) {
        System.out.println("Field: " + name + " " + desc + " value:" + value);
        return (new FVisitor());//.visitField(access, name, desc, signature, value);

    }


    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        return null;
    }

    @Override
    public void visitEnd() {
        System.err.println("end");
    }
}
