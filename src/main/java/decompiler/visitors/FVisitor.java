package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;


public class FVisitor implements FieldVisitor {
    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        System.out.println("Visit annotation : " + s + " " + b);
        return new AVisitor();
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.out.println("FV :" + attribute.type);
    }

    @Override
    public void visitEnd() {

    }
}
