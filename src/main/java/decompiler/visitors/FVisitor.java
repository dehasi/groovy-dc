package decompiler.visitors;

import decompiler.pasers.ASMParser;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;


public class FVisitor extends FieldVisitor {

    Map<StringBuilder, AVisitor> fieldAnnotationsMap = new HashMap<>();

    public FVisitor(int api) {
        super(api);
    }

    public FVisitor(int api, FieldVisitor fv) {
        super(api, fv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {

//        System.err.println("Visit annotation: s" + s + " b" + b);
        AVisitor aVisitor = new AVisitor(Opcodes.ASM4);
        StringBuilder head = ASMParser.parseAnnotation(s,b);
        fieldAnnotationsMap.put(head, aVisitor);
        return aVisitor;
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.err.println("FV :" + attribute.type);
    }

    @Override
    public void visitEnd() {

    }

    public Map<StringBuilder, AVisitor> getFieldAnnotationsMap() {
        return fieldAnnotationsMap;
    }
}
