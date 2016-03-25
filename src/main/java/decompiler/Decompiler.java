package decompiler;

import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class Decompiler {

    public StringBuilder decompile(ClassReader classReader) {
        CVisitor cVisitor = new CVisitor(Opcodes.ASM4);
        classReader.accept(cVisitor, Opcodes.ASM4);
        return cVisitor.getClazz();
    }

}
