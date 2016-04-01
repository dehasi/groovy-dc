package decompiler.visitors;

import decompiler.pasers.ASMParser;
import decompiler.utils.AVisitorEntry;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVisitor extends MethodVisitor {
    Map<StringBuilder, AVisitor> methodAnnotationsMap = new HashMap<>();
    Map<Integer, List<AVisitorEntry>> parameters = new HashMap<>();
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
        StringBuilder annotation = ASMParser.parseAnnotation(desc, visible);
        AVisitor aVisitor = new AVisitor(Opcodes.ASM4);
        methodAnnotationsMap.put(annotation, aVisitor);
       return aVisitor;
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        System.err.println("param: " +parameter +  "desc: " + desc);
        StringBuilder annt = ASMParser.parseAnnotation(desc, visible);
        AVisitor aVisitor = new AVisitor(Opcodes.ASM4);
        if (parameters.get(parameter) == null) parameters.put(parameter, new ArrayList<>());
        parameters.get(parameter).add(new AVisitorEntry(annt, aVisitor));
        return aVisitor;
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.err.println("Attribute: " + attribute );
    }

    @Override
    public void visitEnd() {

    }

    public Map<StringBuilder, AVisitor> getMethodAnnotationsMap() {
        return methodAnnotationsMap;
    }

    public Map<Integer, List<AVisitorEntry>> getParameters() {
        return parameters;
    }
}
