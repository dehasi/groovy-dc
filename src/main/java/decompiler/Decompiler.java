package decompiler;

import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;

/**
 *
 */
public class Decompiler {

    public StringBuilder decompile(ClassReader classReader) {
        CVisitor cVisitor = new CVisitor();
        classReader.accept(cVisitor, 0);
        StringBuilder buffer = cVisitor.getBuffer();
        return cVisitor.getBuffer();
    }

    private StringBuilder parseHeader(ClassReader classReader) {
        StringBuilder sb = new StringBuilder();

        return sb;
    }
}
