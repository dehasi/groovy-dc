package decompiler;

import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;

/**
 *
 */
public class Decompiler {

    public StringBuilder decompile(ClassReader classReader) {

        classReader.accept(new CVisitor(), 0);
        return new StringBuilder();
    }

    private StringBuilder parseHeader(ClassReader classReader) {
        StringBuilder sb = new StringBuilder();

        return sb;
    }
}
