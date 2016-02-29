package decompiler.converters;

import decompiler.classStructure.ClassHolder;
import decompiler.classStructure.HeadHolder;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Created by Rafa on 29.02.2016.
 */
public class ClassReaser2HolderConverter {

    ClassHolder convert(ClassReader classReader) {
        ConverterVisitor visitor = new ConverterVisitor();
        classReader.accept(visitor, 0);
        return visitor.getClassHolder();
    }

    private static class ConverterVisitor implements ClassVisitor {

        ClassHolder.Builder builder;
        @Override
        public void visit(int version, int access, String name,
                          String signature, String superName, String[] interfaces) {
            builder = new ClassHolder.Builder();
            builder.addHead(new HeadHolder(version,access,name,signature,superName,interfaces));
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
            return null;
        }


        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return null;
        }

        @Override
        public void visitEnd() {

        }
        ClassHolder getClassHolder() {
            return builder.build();
        }

    }
}
