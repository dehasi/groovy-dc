package decompiler.visitors;

import decompiler.ObjectType;
import decompiler.holders.FieldHolder;
import decompiler.holders.HeadHolder;
import decompiler.holders.MethodHolder;
import decompiler.pasers.ASMParser;
import decompiler.utils.CVisitorUtils;
import decompiler.utils.ParserUtils;
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

import static decompiler.utils.ParserUtils.isTrait;
import static decompiler.utils.ParserUtils.parsePackagaName;

public class CVisitor extends ClassVisitor {

    Map<StringBuilder, AVisitor> classAnnotationsMap = new HashMap<>();
    Map<String, FVisitor> fieldAnnotationsMap = new HashMap<>();
    Map<String, MVisitor> methodAnnotationsMap = new HashMap<>();

    List<FieldHolder> fields = new ArrayList<>();
    List<MethodHolder> methods= new ArrayList<>();

    HeadHolder header;
    StringBuilder pack = new StringBuilder();
    StringBuilder clazz = new StringBuilder();
    List<StringBuilder> annt = new ArrayList<>();
    ObjectType type;
    protected StringBuilder result = new StringBuilder();

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
        type = ParserUtils.getType(access);
        parser = ParserUtils.getParser(type);
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
            header = parser.parseHeader(header.version,
                    header.access, header.name, header.signature,
                    header.superName, header.interfaces);
        }
        StringBuilder annotation = ASMParser.parseAnnotation(desc, visible);
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
        methods.add(ASMParser.createMethodHolder(access, name, desc, signature, exceptions));
        MVisitor mVisitor = new MVisitor(Opcodes.ASM4);
        methodAnnotationsMap.put(name+signature, mVisitor);
        return mVisitor;
    }

    @Override
    public void visitEnd() {
        result.append(CVisitorUtils.toStringFields(fields, fieldAnnotationsMap));
        clazz
                .append(pack)
                .append(CVisitorUtils.toStringAnntotations(annt, classAnnotationsMap))
                .append(header.toString())
                .append(CVisitorUtils.toStringFields(fields, fieldAnnotationsMap))
                .append(CVisitorUtils.toStringMethods(methods, methodAnnotationsMap, type))
                .append('}');
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
