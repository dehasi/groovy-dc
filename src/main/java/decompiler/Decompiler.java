package decompiler;

import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;

public class Decompiler {

    public StringBuilder decompile(ClassReader classReader) {
        CVisitor cVisitor = new CVisitor();
        classReader.accept(cVisitor, 0);
        return cVisitor.getBuffer();
    }

}
