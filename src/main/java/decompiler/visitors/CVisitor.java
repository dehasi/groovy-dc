package decompiler.visitors;

import decompiler.ASMParser;
import decompiler.ParserUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class CVisitor extends AbstactVisitor implements ClassVisitor {



    private ASMParser parser;
    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        parser = ParserUtils.getParser(ParserUtils.getType(access));
        buffer.append(parser.parseHeader(version, access, name, signature, superName, interfaces));
    }

    @Override
    public void visitSource(String s, String s1) {
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
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
        buffer.append(parser.parseField(access, name, desc, signature, value));
        return (new FVisitor());
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        buffer.append(parser.parseMethod(access, name, desc, signature, exceptions));
        return new MVisitor();
    }

    @Override
    public void visitEnd() {
        buffer.append('}');
    }


}
