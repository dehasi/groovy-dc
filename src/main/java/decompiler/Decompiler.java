package decompiler;

import decompiler.visitors.CVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassReader;

public class Decompiler {

    public StringBuilder decompile(ClassReader classReader) {
        CVisitor cVisitor = new CVisitor(Opcodes.ASM4);
        classReader.accept(cVisitor, Opcodes.ASM4);
        return cVisitor.getBuffer();
    }

}
