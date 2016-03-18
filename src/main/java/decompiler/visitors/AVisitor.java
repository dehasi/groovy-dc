package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;

public class AVisitor extends AnnotationVisitor {

    public AVisitor(int api) {
        super(api);
    }

    public AVisitor(int api, AnnotationVisitor av) {
        super(api, av);
    }

    @Override
    public void visit(String name, Object value) {
        System.err.println("visit| name : " + name + " value" + value);
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        System.err.println("visitEnum|  name: "  + name  + "desc: " + desc + " value" + value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        System.err.println("visitAnnotation| name: "  + name  + "desc: " + desc);
        return this;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        System.err.println("visitArray| name : " + name);
        return this;
    }

    @Override
    public void visitEnd() {

    }
}
