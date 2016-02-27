package decompiler;

import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;

/**
 *
 */
public class Decompiler {

    public StringBuilder decompile(ClassReader classReader) {
        classReader.accept(new CVisitor(),0);
        return new StringBuilder();
    }
}
