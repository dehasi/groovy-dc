package decompiler;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.IOException;

/**
 *
 */
public class Decompiler {
    //TODO smth like dependency injection
    InterfaceDecompiler interfaceDecompiler;

    public StringBuilder decompileFromRepository(String className) {
        JavaClass myownclass = null;
        try {
            myownclass = Repository.lookupClass( "java.lang.String" );
//            System.out.println(myownclass.isInterface());
            return decompile(myownclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new StringBuilder();
    }


    public StringBuilder decompileFromFileSystem(String path) {
        JavaClass myownclass = null;
        try {
            myownclass = new ClassParser(path).parse();
//            System.out.println(myownclass.isInterface());
            return decompile(myownclass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new StringBuilder();
    }

    private StringBuilder decompile(JavaClass clazz) {
        if (clazz.isInterface()) {
            return interfaceDecompiler.decompile(clazz);
        } else {
            throw new UnsupportedOperationException("Only interface decomile");
        }
    }

    public Decompiler () {
        interfaceDecompiler = new InterfaceDecompiler();
    }
}
