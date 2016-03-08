package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;

/**
 * Created by Rafa on 27.02.2016.
 */
public class AVisitor extends AnnotationVisitor {

    public AVisitor(int api) {
        super(api);
    }

    public AVisitor(int api, AnnotationVisitor av) {
        super(api, av);
    }

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
        return this;
    }

    @Override
    public AnnotationVisitor visitArray(String s) {
        return this;
    }

    @Override
    public void visitEnd() {

    }
}
