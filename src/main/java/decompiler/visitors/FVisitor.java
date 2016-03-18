package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;


public class FVisitor extends FieldVisitor {
    public FVisitor(int api) {
        super(api);
    }

    public FVisitor(int api, FieldVisitor fv) {
        super(api, fv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        System.err.println("Visit annotation: s" + s + " b" + b);
        return new AVisitor(Opcodes.ASM4);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.err.println("FV :" + attribute.type);
    }

    @Override
    public void visitEnd() {

    }
}
