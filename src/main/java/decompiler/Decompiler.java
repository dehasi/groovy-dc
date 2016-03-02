package decompiler;

import decompiler.classStructure.ClassHolder;
import decompiler.converters.ClassReaser2HolderConverter;
import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;

/**
 *
 */
public class Decompiler {

    ClassReaser2HolderConverter converter = new ClassReaser2HolderConverter();
    public StringBuilder decompile(ClassReader classReader) {
        CVisitor cVisitor = new CVisitor();
        classReader.accept(cVisitor, 0);
        StringBuilder buffer = cVisitor.getBuffer();

        ClassHolder res = converter.toDestination(classReader);
        return cVisitor.getBuffer();
    }

    private StringBuilder parseHeader(ClassReader classReader) {
        StringBuilder sb = new StringBuilder();

        return sb;
    }
}
