package decompiler;

import decompiler.visitors.CVisitor;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("hello world!");
        ClassReader classReader = new ClassReader("java.lang.String");
//        System.out.println(classReader.getClassName());
//        System.out.println(classReader.getAccess());
//        for (String s : classReader.getInterfaces()) {
//            System.out.println(s);
//        }
        System.out.println(classReader.getItem(5));

        classReader.accept(new CVisitor(),0);

    }


}
