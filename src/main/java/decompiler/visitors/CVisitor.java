package decompiler.visitors;

import decompiler.Decompiler;
import decompiler.ObjectType;
import decompiler.holders.ClassHolder;
import decompiler.holders.FieldHolder;
import decompiler.holders.MethodHolder;
import decompiler.pasers.ASMParser;
import decompiler.utils.CVisitorUtils;
import decompiler.utils.ParserUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static decompiler.utils.Loader.loadFromFileSystemOrNull;
import static decompiler.utils.ParserUtils.isTrait;
import static decompiler.utils.ParserUtils.parsePackagaName;
import static decompiler.utils.TraitUtils.processTrait;

public class CVisitor extends ClassVisitor {

    private static Set<String> pathSet = new HashSet<>();
    Map<StringBuilder, AVisitor> classAnnotationsMap = new HashMap<>();
    Map<String, FVisitor> fieldAnnotationsMap = new HashMap<>();
    Map<String, MVisitor> methodAnnotationsMap = new HashMap<>();

    ClassHolder classHolder = new ClassHolder();
    StringBuilder pack = new StringBuilder();
    StringBuilder clazz = new StringBuilder();
    List<StringBuilder> annt = new ArrayList<>();

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
        classHolder.type = ParserUtils.getType(access);
        parser = ParserUtils.getParser(classHolder.type);
        pack = new StringBuilder(parsePackagaName(name));
        classHolder.header = parser.parseHeader(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(String s, String s1) { }

    @Override
    public void visitOuterClass(String s, String s1, String s2) { }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (isTrait(desc, visible)) {
            parser = ParserUtils.getParser(ObjectType.TRAIT);
            classHolder.header = parser.parseHeader(classHolder.header.version,
                    classHolder.header.access, classHolder.header.name, classHolder.header.signature,
                    classHolder.header.superName, classHolder.header.interfaces);
            classHolder.type = ObjectType.TRAIT;
            return null;
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
        String s = "build/classes/groovy/main/" + name + ".class";
        if (pathSet.add(s)) {
            ClassReader classReader = loadFromFileSystemOrNull(s);
            ClassHolder e = (new Decompiler()).decompileToHolder(classReader);
            classHolder.inner.add(e);
        }
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        classHolder.fields.add(new FieldHolder(name, parser.parseField(access, name, desc, signature, value)));
        FVisitor fVisitor = new FVisitor(Opcodes.ASM4);
        fieldAnnotationsMap.put(name, fVisitor);
        return fVisitor;
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodHolder method = ASMParser.createMethodHolder(access, name, desc, signature, exceptions);
        method.parent = classHolder.type;
        classHolder.methods.add(method);
        MVisitor mVisitor = new MVisitor(Opcodes.ASM4);
        methodAnnotationsMap.put(name + signature, mVisitor);
        return mVisitor;
    }

    @Override
    public void visitEnd() {
        if (classHolder.type == ObjectType.TRAIT) {
            classHolder = processTrait(classHolder);
        }
        result.append(CVisitorUtils.toStringFields(classHolder.fields, fieldAnnotationsMap));
        clazz
                .append(pack)
                .append(CVisitorUtils.toStringAnntotations(annt, classAnnotationsMap))
                .append(classHolder.header.toString())
                .append("{\n")
                .append(CVisitorUtils.toStringFields(classHolder.fields, fieldAnnotationsMap))
                .append(CVisitorUtils.toStringMethods(classHolder.methods, methodAnnotationsMap, classHolder.type))
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

    public ClassHolder getClassHolder() {
        return classHolder;
    }
}
