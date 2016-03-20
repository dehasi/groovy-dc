package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

public class MVisitor extends MethodVisitor {
    Map<String, AVisitor> classAnnotationsMap = new HashMap<>();
    public MVisitor(int api) {
        super(api);
    }

    public MVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return new AVisitor(Opcodes.ASM4);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.err.println("desc: " + desc);
        return new AVisitor(Opcodes.ASM4);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        System.err.println("param: " +parameter +  "desc: " + desc);
        return new AVisitor(Opcodes.ASM4);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.err.println("Attribute: " + attribute );
    }

    @Override
    public void visitCode() {

    }

    @Override
    public void visitFrame(int i, int i1, Object[] objects, int i2, Object[] objects1) {

    }

    @Override
    public void visitInsn(int i) {

    }

    @Override
    public void visitIntInsn(int i, int i1) {

    }

    @Override
    public void visitVarInsn(int i, int i1) {

    }

    @Override
    public void visitTypeInsn(int i, String s) {

    }

    @Override
    public void visitFieldInsn(int i, String s, String s1, String s2) {

    }


    @Override
    public void visitJumpInsn(int i, Label label) {

    }

    @Override
    public void visitLabel(Label label) {

    }

    @Override
    public void visitLdcInsn(Object o) {

    }

    @Override
    public void visitIincInsn(int i, int i1) {

    }

    @Override
    public void visitTableSwitchInsn(int i, int i1, Label label, Label[] labels) {

    }

    @Override
    public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {

    }

    @Override
    public void visitMultiANewArrayInsn(String s, int i) {

    }

    @Override
    public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {

    }

    @Override
    public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {

    }

    @Override
    public void visitLineNumber(int i, Label label) {

    }

    @Override
    public void visitMaxs(int i, int i1) {

    }

    @Override
    public void visitEnd() {

    }
}
