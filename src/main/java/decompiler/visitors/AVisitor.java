package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;

/**
 * Created by Rafa on 27.02.2016.
 */
public class AVisitor implements AnnotationVisitor {

    @Override
    public void visit(String s, Object o) {
        System.out.println("visit annotation");
    }

    @Override
    public void visitEnum(String s, String s1, String s2) {
        System.out.println("AV enum: " +s + " " +s1 + " " + s2);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, String s1) {
        return new AVisitor();
    }

    @Override
    public AnnotationVisitor visitArray(String s) {
        return new AVisitor();
    }

    @Override
    public void visitEnd() {

    }
}
