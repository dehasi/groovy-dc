package decompiler;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

import java.io.IOException;

public class BCELHelloWorld {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JavaClass myownclass = Repository.lookupClass( "java.lang.String" );
        System.out.println(myownclass.isInterface());
    }

}