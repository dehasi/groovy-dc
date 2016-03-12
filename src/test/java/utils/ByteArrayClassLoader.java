package utils;

/**
 * There should be useful comment
 */
public class ByteArrayClassLoader extends ClassLoader {

    public Class findClass(String name, byte[] bytes){
        return defineClass(name, bytes, 0, bytes.length);
    }

}