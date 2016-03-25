package decompiler.visitors;

import decompiler.ObjectType;
import decompiler.holders.FieldHolder;
import decompiler.pasers.ASMParser;
import decompiler.pasers.ParserUtils;
import decompiler.utils.CVisitorUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static decompiler.pasers.ParserUtils.isTrait;
import static decompiler.pasers.ParserUtils.parsePackagaName;

public class CVisitor extends ClassVisitor {

    Map<StringBuilder, AVisitor> classAnnotationsMap = new HashMap<>();
    Map<String, FVisitor> fieldAnnotationsMap = new HashMap<>();
    Map<String, MVisitor> methodAnnotationsMap = new HashMap<>();

    List<FieldHolder> fields = new ArrayList<>();

    StringBuilder header = new StringBuilder();
    StringBuilder pack = new StringBuilder();
    StringBuilder clazz = new StringBuilder();
    List<StringBuilder> annt = new ArrayList<>();

    protected StringBuilder buffer = new StringBuilder();

    public StringBuilder getClazz() {
        return clazz;
    }

    private ASMParser parser;

    public CVisitor(int api) {
        super(api);
    }

    public CVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        parser = ParserUtils.getParser(ParserUtils.getType(access));
        pack = new StringBuilder(parsePackagaName(name));
        header = parser.parseHeader(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(String s, String s1) {
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (isTrait(desc, visible)) {
            parser = ParserUtils.getParser(ObjectType.TRAIT);
        }
        StringBuilder annotation = parser.parseAnnotation(desc, visible);
        annt.add(annotation);
        AVisitor aVisitor = new AVisitor(Opcodes.ASM4);
        classAnnotationsMap.put(annotation, aVisitor);
        return aVisitor;
    }

    @Override
    public void visitAttribute(Attribute attr) {
        System.out.println("Class Attribute: " + attr.type);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {

    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        fields.add(new FieldHolder(name, parser.parseField(access, name, desc, signature, value)));
        FVisitor fVisitor = new FVisitor(Opcodes.ASM4);
        fieldAnnotationsMap.put(name, fVisitor);
        return fVisitor;
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        buffer.append(parser.parseMethod(access, name, desc, signature, exceptions));
        MVisitor mVisitor = new MVisitor(Opcodes.ASM4);
        methodAnnotationsMap.put(name, mVisitor);
        return mVisitor;
    }

    @Override
    public void visitEnd() {
        buffer.append(CVisitorUtils.toStringFiels(fields, fieldAnnotationsMap));
        clazz.append(pack).append(
                CVisitorUtils.toStringAnnts(annt, classAnnotationsMap)
        ).append(header).append(buffer).append('}');
    }

    public Map<StringBuilder, AVisitor> getClassAnnotaitonsMap() {
        return classAnnotationsMap;
    }

    public Map<String, FVisitor> getFieldAnnotationsMap() {
        return fieldAnnotationsMap;
    }

    public Map<String, MVisitor> getMethodAnnotationsMap() {
        return methodAnnotationsMap;
    }
}
